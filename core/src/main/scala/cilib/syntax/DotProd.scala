package cilib
package syntax

import cilib.algebra._
import spire.algebra._
import zio.prelude._

object dotprod {
  implicit class DotProdSyntax[F[_], A](private val x: F[A]) extends AnyVal {
    def ∙(a: F[A])(implicit D: DotProd[F, A]): Double = D.dot(x, a)
  }

  implicit class AlgebraSyntax[F[+_], A](private val x: F[A]) extends AnyVal {
    def normalize(implicit M: LeftModule[F[A], Double], D: DotProd[F, A]): F[A] =
      Algebra.normalize(x)

    def norm(implicit D: DotProd[F, A]): Double =
      D.norm(x)

    def orthonormalize(
      implicit F: Covariant[F],
      F2: ForEach[F],
      F3: Field[A],
      A: NRoot[A],
      D: DotProd[F, A],
      M: LeftModule[F[A], Double]
    ): NonEmptyVector[F[A]] =
      Algebra.orthonormalize(NonEmptyVector(x))
  }

}
