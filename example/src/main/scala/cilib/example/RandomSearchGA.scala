package cilib
package example

import eu.timepit.refined.auto._
import scalaz._, Scalaz._
import scalaz.effect.IO.putStrLn
import scalaz.effect._
import spire.implicits._
import spire.math.Interval

import cilib.exec._
import cilib.ga._

object RandomSearchGA extends SafeApp {
  type Ind = Individual[Unit]

  val bounds = Interval(-5.12, 5.12) ^ 30
  val env =
    Environment(
      cmp = Comparison.dominance(Min),
      eval = Eval.unconstrained((xs: NonEmptyList[Double]) => Feasible(cilib.benchmarks.Benchmarks.spherical(xs)))
    )

  val randomSelection = (l: NonEmptyList[Ind]) => RVar.sample(2, l).map(_.getOrElse(List.empty))
  val distribution    = (position: Double) => Dist.stdNormal.flatMap(_ => Dist.gaussian(0, 1.25)).map(_ + position)

  val ga = GA.randomSearch(randomSelection, distribution)

  val swarm = Position.createCollection[Ind](x => Entity((), x))(bounds, 20)
  val myGA =
    Iteration
      .sync(ga)
      .map(_.suml)
      .flatMapK(r =>
        Step
          .withCompare(o => r.sortWith((x, y) => Comparison.fitter(x.pos, y.pos).apply(o)))
          .map(_.take(20).toNel.getOrElse(sys.error("Impossible -> List is empty?")))
      )

  override val runc: IO[Unit] =
    putStrLn(Runner.repeat(1000, myGA, swarm).run(env).run(RNG.fromTime).toString)
}
