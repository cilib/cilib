package cilib
package example

import eu.timepit.refined.auto._
import spire.implicits._
import spire.math.Interval
import zio.console._

import cilib.exec._
import cilib.pso.Defaults._
import cilib.pso._

object LBestPSO extends zio.App {
  val bounds = Interval(-5.12, 5.12) ^ 30
  val env =
    Environment(
      cmp = Comparison.quality(Min),
      eval = Eval.unconstrained(ExampleHelper.spherical andThen Feasible)
    )

  // LBest is a network topology where every Paricle 'x' has (n/2) neighbours
  // on each side. For example, a neighbourhood size of 3 means that there is
  // a single neighbour on both sides of the current particle.

  // Define a LBest PSO and run it for a single iteration
  val cognitive: Guide[Mem[Double], Double] = Guide.pbest
  val social: Guide[Mem[Double], Double]    = Guide.lbest[Mem[Double]](3)

  val lbestPSO =
    gbest(0.729844, 1.496180, 1.496180, cognitive, social)

  val swarm =
    Position.createCollection(PSO.createParticle(x => Entity(Mem(x, x.zeroed), x)))(bounds, 20)
  val iter = Iteration.sync(lbestPSO)

  def run(args: List[String]) =
    putStrLn(Runner.repeat(1000, iter, swarm).provide(env).runAll(RNG.fromTime).toString).exitCode

}
