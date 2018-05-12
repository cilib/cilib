package cilib

import scalaz._
import Scalaz._

import eu.timepit.refined.api.Refined
import eu.timepit.refined.numeric._

import spire.algebra.{Module, Rng}
import spire.math._

sealed abstract class Position[A] {

  def map[B](f: A => B): Position[B] =
    Point(pos.map(f), boundary)

  def flatMap[B](f: A => Position[B]): Position[B] =
    Point(pos.flatMap(f(_).pos), boundary)

  def zip[B](other: Position[B]): Position[(A, B)] =
    Point(pos.zip(other.pos), boundary)

  def traverse[G[_]: Applicative, B](f: A => G[B]): G[Position[B]] =
    pos.traverse(f).map(Point(_, boundary))

  def take(n: Int): IList[A] =
    pos.list.take(n)

  def drop(n: Int): IList[A] =
    this match {
      case Point(x, _)       => x.list.drop(n)
      case Solution(x, _, _) => x.list.drop(n)
    }

  def pos =
    this match {
      case Point(x, _)       => x
      case Solution(x, _, _) => x
    }

  def toPoint: Position[A] =
    this match {
      case Point(_, _)       => this
      case Solution(x, b, _) => Point(x, b)
    }

  def objective: Option[Objective[A]] =
    this match {
      case Point(_, _)       => None
      case Solution(_, _, o) => Some(o)
    }

  def boundary =
    this match {
      case Point(_, b)       => b
      case Solution(_, b, _) => b
    }

  def forall(f: A => Boolean) =
    pos.list.toList.forall(f)
}

final case class Point[A] private[cilib] (x: NonEmptyList[A], b: NonEmptyList[Interval[Double]])
    extends Position[A]
final case class Solution[A] private[cilib] (x: NonEmptyList[A],
                                             b: NonEmptyList[Interval[Double]],
                                             o: Objective[A])
    extends Position[A]

object Position {

  implicit def positionInstances: Bind[Position] with Traverse1[Position] with Align[Position] =
    new Bind[Position] with Traverse1[Position] with Align[Position] {
      override def map[A, B](fa: Position[A])(f: A => B): Position[B] =
        fa.map(f)

      override def bind[A, B](fa: Position[A])(f: A => Position[B]): Position[B] =
        fa.flatMap(f)

      override def traverseImpl[G[_]: Applicative, A, B](fa: Position[A])(
          f: A => G[B]): G[Position[B]] =
        fa.traverse(f)

      override def traverse1Impl[G[_], A, B](fa: Position[A])(f: A => G[B])(
          implicit A: Apply[G]): G[Position[B]] =
        fa.traverse1(f)

      def alignWith[A, B, C](f: A \&/ B => C) =
        (a, b) => Point(a.pos.alignWith(b.pos)(f), a.boundary)

      def foldMapRight1[A, B](fa: Position[A])(z: A => B)(f: (A, => B) => B): B =
        fa.pos.foldMapRight1(z)(f)

    }

  implicit def positionDotProd[A](implicit A: Numeric[A]): algebra.DotProd[Position, A] =
    new algebra.DotProd[Position, A] {
      import spire.implicits._

      def dot(a: Position[A], b: Position[A]): Double =
        a.zip(b).pos.foldLeft(A.zero) { case (a, b) => a + (b._1 * b._2) }.toDouble
    }

  implicit def positionPointwise[A](implicit A: Numeric[A]): algebra.Pointwise[Position, A] =
    new algebra.Pointwise[Position, A] {
      import spire.implicits._

      def pointwise(a: Position[A], b: Position[A]) =
        a.zip(b).map(x => x._1 * x._2)
    }

  implicit class PositionVectorOps[A](val x: Position[A]) extends AnyVal {
    def zeroed(implicit A: Rng[A]): Position[A] =
      x.map(_ => A.zero)

    def +(other: Position[A])(implicit M: Module[Position[A], A]): Position[A] =
      M.plus(x, other)

    def -(other: Position[A])(implicit M: Module[Position[A], A]): Position[A] =
      M.minus(x, other)

    def *:(scalar: A)(implicit M: Module[Position[A], A]): Position[A] =
      M.timesl(scalar, x)

    def unary_-(implicit M: Module[Position[A], A]): Position[A] =
      M.negate(x)

    def isZero(implicit R: Rng[A]) = {
      def test(xs: IList[A]): Boolean =
        xs match {
          case INil()        => true
          case ICons(x, xss) => if (x != R.zero) false else test(xss)
        }

      test(x.pos.list)
    }
  }

  implicit def positionFitness[A]: Fitness[Position, A] =
    new Fitness[Position, A] {
      def fitness(a: Position[A]) =
        a.objective
    }

  implicit def positionEqual[A: scalaz.Equal]: scalaz.Equal[Position[A]] =
    scalaz.Equal.equal[Position[A]]((a, b) => (a.pos === b.pos) && (a.boundary === b.boundary))

  implicit val positionFoldable1: Foldable1[Position] =
    new Foldable1[Position] {
      def foldMap1[A, B](fa: Position[A])(f: A => B)(implicit F: Semigroup[B]): B =
        fa match {
          case Point(xs, _) =>
            xs.foldMap1(f)
          case Solution(xs, _, _) =>
            xs.foldMap1(f)
        }

      def foldMapRight1[A, B](fa: Position[A])(z: A => B)(f: (A, => B) => B): B =
        fa match {
          case Point(xs, _) =>
            xs.foldMapRight1(z)(f)
          case Solution(xs, _, _) =>
            xs.foldMapRight1(z)(f)
        }
    }

  def eval[F[_], A](e: RVar[NonEmptyList[A] => Objective[A]], pos: Position[A]): RVar[Position[A]] =
    pos match {
      case Point(x, b) =>
        e.map(f => {
          val s: Objective[A] = f.apply(x)
          Solution(x, b, s)
        })

      case x @ Solution(_, _, _) =>
        RVar.pure(x)
    }

  /*private[cilib]*/
  def apply[A](xs: NonEmptyList[A], b: NonEmptyList[Interval[Double]]): Position[A] =
    Point(xs, b)

  def createPosition[A](domain: NonEmptyList[Interval[Double]]): RVar[Position[Double]] =
    domain.traverse(Dist.uniform).map(x => Position(x, domain))

  def createPositions(domain: NonEmptyList[Interval[Double]],
                      n: Int Refined Positive): RVar[NonEmptyList[Position[Double]]] =
    createPosition(domain)
      .replicateM(n.value)
      .map(_.toNel.getOrElse(
        sys.error("Impossible -> refinement requires n to be positive, i.e. n > 0")))

  def createCollection[A](f: Position[Double] => A)(
      domain: NonEmptyList[Interval[Double]],
      n: Int Refined Positive): RVar[NonEmptyList[A]] =
    createPositions(domain, n).map(_.map(f))
}


object BCHM {

  final case class BoundaryEnforce[A]()

  // clamp and wrap are the same function - the logic operator is just flipped
  def clamp[A: scalaz.Equal](x: Position[A])(implicit N: spire.math.Numeric[A]): NonEmptyList[A] =
    x.pos.zip(x.boundary).map {
      case (p, b) =>
        if (N.toDouble(p) < b.lowerValue) N.fromDouble(b.lowerValue)
        else if (N.toDouble(p) > b.upperValue) N.fromDouble(b.upperValue)
        else p
    }

  def wrap[A: scalaz.Equal](p: Position[A])(implicit N: spire.math.Numeric[A]): NonEmptyList[A] =
    p.pos.zip(p.boundary).map {
      case (p, b) => {
        if (N.toDouble(p) < b.lowerValue) N.fromDouble(b.upperValue)
        else if (N.toDouble(p) > b.upperValue) N.fromDouble(b.lowerValue)
        else p
      }
    }

  def inBoundsOr[A: scalaz.Equal](default: NonEmptyList[A])(x: Position[A])(implicit N: spire.math.Numeric[A]) =
    x.pos.zip(x.boundary).zip(default).map {
      case ((p, b), m) => if (b.contains(N.toDouble(p))) p else m
    }

  def initToMidpoint[A: scalaz.Equal](x: Position[A])(implicit N: spire.math.Numeric[A]) =
    inBoundsOr[A](x.boundary.map(b => N.fromAlgebraic((b.upperValue + b.lowerValue) / 2)))(x)

  def reverseVelocity[A: scalaz.Equal](target: NonEmptyList[A])(p: Position[A])(implicit N: spire.math.Numeric[A]) =
    inBoundsOr(target.map(v => N.fromAlgebraic(N.toDouble(v) * -1.0)))(p)

  def absorb[A: scalaz.Equal:spire.math.Numeric](target: NonEmptyList[A])(x: Position[A]) = {
    val a = inBoundsOr(target)(x)
    clamp(Lenses._vector[A].set(a)(x))
  }

  def reflect[A: scalaz.Equal: spire.math.Numeric](x: Position[A], velocity: Position[A]) = {
    val a = reverseVelocity(velocity.pos)(x)
    clamp(Lenses._vector[A].set(a)(x))
  }
}
