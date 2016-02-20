import scalaz._
import spire.algebra.{Monoid => _, _}
import spire.implicits._
import spire.math.Interval
import spire.math.interval.{Bound,ValueBound}

package object cilib {

//  val ::  = ICons
//  val Nil = INil

  // Should expand into a typeclass? Getter?
  type Selection[A] = (List[A], A) => List[A]

  def positive(d: Double): Maybe[Double @@ Tags.Positive] =
    if (d > 0.0) Tag.subst(Maybe.just(d))
    else Maybe.empty

  def negative(d: Double): Maybe[Double @@ Tags.Negative] =
    if (d < 0.0) Tag.subst(Maybe.just(d))
    else Maybe.empty

  // Find a better home for this
  implicit object DoubleMonoid extends Monoid[Double] {
    def zero = 0.0
    def append(a: Double, b: => Double) = a + b
  }

  implicit def PositionModule[A](implicit sc: Rng[A]) =
    new Module[Position[A],A] {
      implicit def scalar = sc

      def negate(x: Position[A]) = x.map(scalar.negate)
      def zero = Position(NonEmptyList(scalar.zero), NonEmptyList(spire.math.Interval(0.0, 0.0)))

      def plus(x: Position[A], y: Position[A]) = {
        import scalaz.syntax.align._
        x.align(y).map(_.fold(
          s = x => x,
          t = x => x,
          q = scalar.plus(_, _)
        ))
      }

      def timesl(r: A, v: Position[A]): Position[A] =
        v map (scalar.times(r, _))
    }

  implicit class IntervalOps[A](val interval: Interval[A]) extends AnyVal {
    def ^(n: Int): NonEmptyList[Interval[A]] =
      NonEmptyList.nel(interval, IList.fill(n-1)(interval))

    private def getValue(b: Bound[A]) =
      ValueBound.unapply(b).getOrElse(sys.error("Empty and Unbounded bounds are not supported"))

    def lowerValue = getValue(interval.lowerBound)
    def upperValue = getValue(interval.upperBound)
  }

  implicit def intervalEqual[A]  = scalaz.Equal.equalA[Interval[A]]
}
