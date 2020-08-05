package cilib
package example

import eu.timepit.refined.auto._
import scalaz._, Scalaz._
import spire.implicits._
import spire.math.Interval
import zio.console._

import cilib.exec._
import cilib.ga._

object RandomSearchGA extends zio.App {
  type Ind = Individual[Unit]

  val bounds = Interval(-5.12, 5.12) ^ 30
  val env =
    Environment(
      cmp = Comparison.dominance(Min),
      eval = Eval.unconstrained(ExampleHelper.spherical andThen Feasible)
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

  def run(args: List[String]) =
    putStrLn(Runner.repeat(1000, myGA, swarm).run(env).run(RNG.fromTime).toString).exitCode
}
