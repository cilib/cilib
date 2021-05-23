package cilib
package example

import eu.timepit.refined.auto._
import spire.implicits._
import spire.math.Interval
import zio.console._

import cilib.exec._
import cilib.pso.Defaults._
import cilib.pso._

object VonNeumannPSO extends zio.App {

  val bounds = Interval(-5.12, 5.12) ^ 30
  val env =
    Environment(
      cmp = Comparison.dominance(Min),
      eval = Eval.unconstrained(ExampleHelper.spherical andThen Feasible)
    )

  // Define a normal GBest PSO and run it for a single iteration
  val cognitive = Guide.pbest[Mem[Double], Double]
  val social    = Guide.vonNeumann[Mem[Double]]
  val gbestPSO  = gbest(0.729844, 1.496180, 1.496180, cognitive, social)

  // RVar
  val swarm =
    Position.createCollection(PSO.createParticle(x => Entity(Mem(x, x.zeroed), x)))(bounds, 20)
  val iter = Iteration.sync(gbestPSO)

  def run(args: List[String]) =
    putStrLn(Runner.repeat(1000, iter, swarm).provide(env).runAll(RNG.fromTime).toString).exitCode

}
