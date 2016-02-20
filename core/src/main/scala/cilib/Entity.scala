package cilib

import _root_.scala.Predef.{any2stringadd => _, _}
import scala.language.higherKinds
import scalaz._
import Scalaz._

import spire.algebra.{Field,Module,NRoot,Ring}
import spire.implicits._
import spire.math._

final case class Entity[S,A](state: S, pos: Position[A])

object Entity {
  //  implicit def entityQuality[S,A](implicit Q: Quality[Position[A]]) = new Quality[Entity[S,A]] {
  //   def quality(a: Entity[S,A]) =
  //     Q.quality(a.pos)
  // }

  // Step to evaluate the particle
  def eval[S,A:Numeric](f: Position[A] => Position[A])(entity: Entity[S,A]): Step[A,Entity[S,A]] = {

    Step.evalF(f(entity.pos)).map(p => Lenses._position.set(p)(entity))
  }
}

sealed abstract class Position[A] {
  import Position._

  def map[B](f: A => B): Position[B] =
    Point(pos map f, boundary)

  def flatMap[B](f: A => Position[B]): Position[B] =
    Point(pos flatMap (f(_).pos), boundary)

  def zip[B](other: Position[B]): Position[(A, B)] =
    Point(pos.zip(other.pos), boundary)

  def traverse[G[_]: Applicative, B](f: A => G[B]): G[Position[B]] =
    pos.traverse(f).map(Point(_, boundary))

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

  def objective: Maybe[Objective[A]] =
    this match {
      case Point(_,_)        => Maybe.empty
      case Solution(_, _, o) => Maybe.just(o)
    }

  def boundary =
    this match {
      case Point(_, b)       => b
      case Solution(_, b, _) => b
    }
}

final case class Point[A] private[cilib] (x: List[A], b: NonEmptyList[Interval[Double]]) extends Position[A]
final case class Solution[A] private[cilib] (x: List[A], b: NonEmptyList[Interval[Double]], o: Objective[A]/*f: Fit, v: List[Constraint[A,Double]]*/) extends Position[A]

object Position {

  implicit def positionInstances: Bind[Position] with Traverse[Position] with Align[Position] =
    new Bind[Position] with Traverse[Position] with Align[Position] {
      override def map[A, B](fa: Position[A])(f: A => B): Position[B] =
        fa map f

      override def bind[A, B](fa: Position[A])(f: A => Position[B]): Position[B] =
        fa flatMap f

      override def traverseImpl[G[_]: Applicative, A, B](fa: Position[A])(f: A => G[B]): G[Position[B]] =
        fa traverse f

      def alignWith[A, B, C](f: A \&/ B => C) =
        (a, b) => Point(a.pos.alignWith(b.pos)(f), a.boundary)

    }

  implicit class PositionVectorOps[A](val x: Position[A]) extends AnyVal {
    def zeroed(implicit A: Ring[A]): Position[A] =
      x.map(_ => A.zero)

    def + (other: Position[A])(implicit M: Module[Position[A],A]): Position[A] =
      M.plus(x, other)

    def - (other: Position[A])(implicit M: Module[Position[A],A]): Position[A] =
      M.minus(x, other)

    /*def * (other: Position[F, A])(implicit F: Zip[F]) = Solution(x.pos.zipWith(other.pos)((a, ob) => ob.map(_ * a).getOrElse(a))._2) */

    def *: (scalar: A)(implicit M: Module[Position[A],A]): Position[A] =
      M.timesl(scalar, x)

    def unary_-(implicit M: Module[Position[A],A]): Position[A] =
      M.negate(x)

    def dot(other: Position[A])(implicit R: Ring[A]): A =
      x.zip(other).pos.foldLeft(R.zero) { case (a, b) => a + (b._1 * b._2) }

    def ∙ (other: Position[A])(implicit R: Ring[A]): A =
      x.dot(other)

    def isZero(implicit R: Ring[A]) = x.pos.forall(_ == R.zero)

    def magnitude(implicit R: Ring[A], N: NRoot[A]): A =
      sqrt(x.pos.foldLeft(R.zero)((a, b) => a + (b ** 2)))

    def normalize(implicit F: Field[A], N: NRoot[A]): Position[A] = {
      val mag = x.magnitude
      if (mag == F.zero) x
      else (F.one / mag) *: x
    }

    def orthogonalize(vs: List[Position[A]])(implicit F: Field[A]): Position[A] =
      vs.foldLeft(x)((a, b) => a - a.project(b))

    def project(other: Position[A])(implicit F: Field[A]): Position[A] =
      if   ((other ∙ other) == F.zero) zeroed
      else ((x ∙ other) / (other ∙ other)) *: other
  }

  implicit def positionFitness[A] = new Fitness[Position,A] {
    def fitness(a: Position[A]) =
      a.objective
  }

  implicit def positionEqual[A:scalaz.Equal] =
    scalaz.Equal.equal[Position[A]]((a, b) => (a.pos === b.pos) && (a.boundary === b.boundary))

  def apply[A](xs: NonEmptyList[A], b: NonEmptyList[Interval[Double]]): Position[A] =
    Point(xs.list.toList, b)

  def createPosition[A](domain: NonEmptyList[Interval[Double]]) =
    domain.traverseU(x => Dist.uniform(Interval(x.lowerValue, x.upperValue))) map (x => Position(x, domain))

  def createPositions(domain: NonEmptyList[Interval[Double]], n: Int) =
    createPosition(domain) replicateM n

  def createCollection[A](f: Position[Double] => A)(domain: NonEmptyList[Interval[Double]], n: Int): RVar[List[A]] =
    createPositions(domain,n).map(_.map(f))

  def mean[A](ps: NonEmptyList[Position[A]])(implicit F: Field[A]) =
    (F.one / ps.size) *: ps.tail.foldLeft(ps.head)(_ + _)

  def orthonormalize[A:Field:NRoot](vs: NonEmptyList[Position[A]]) = {
    val bases = vs.foldLeft(NonEmptyList(vs.head)) { (ob, v) =>
      val ui = ob.foldLeft(v) { (u, o) => u - (v.project(o)) }
      if (ui.isZero) ob
      else ob append NonEmptyList(ui)
    }

    bases.map(_.normalize)
  }
}


trait NonEmpty[F[_]]
object NonEmpty {
  implicit object NonEmptyNEL extends NonEmpty[NonEmptyList]
}
