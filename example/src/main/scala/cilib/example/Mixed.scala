package cilib
package example

import cilib.de._
import cilib.exec._
import cilib.pso._
import eu.timepit.refined.auto._
import spire.implicits._
import spire.math._
import zio.console._
import zio.{ ExitCode, URIO }

object Mixed extends zio.App {

  val bounds: NonEmptyVector[Interval[Double]] = Interval(-5.12, 5.12) ^ 30
  val env: Environment =
    Environment(
      cmp = Comparison.dominance(Min),
      eval = Eval.unconstrained(ExampleHelper.spherical andThen Feasible)
    )

  // Define the DE
  val de: NonEmptyVector[Individual[Mem[Double], Double]] => (
    Individual[Mem[Double], Double] => Step[Individual[Mem[Double], Double]]
  ) = DE.de(0.5, 0.5, DE.randSelection[Mem[Double], Double], 1, DE.bin[Position, Double])

  // Define a normal GBest PSO and run it for a single iteration
  val cognitive: Guide[Mem[Double], Double] = Guide.pbest[Mem[Double], Double]
  val social: Guide[Mem[Double], Double]    = Guide.gbest[Mem[Double]]
  val gbestPSO: NonEmptyVector[Particle[Mem[Double], Double]] => (
    Particle[Mem[Double], Double] => Step[Particle[Mem[Double], Double]]
  ) = cilib.pso.Defaults.gbest(0.729844, 1.496180, 1.496180, cognitive, social)

  // The swarm / population is the maximal set of features needed for the state,
  // so in the case of DE and PSO, the state from the particle is needed to be
  // managed
  val swarm: RVar[NonEmptyVector[Particle[Mem[Double], Double]]] =
    Position.createCollection(PSO.createParticle(x => Entity(Mem(x, x.zeroed), x)))(bounds, 20)

  val combinedAlg: NonEmptyVector[Entity[Mem[Double], Double]] => Entity[Mem[Double], Double] => Step[Entity[Mem[
    Double
  ], Double]] =
    collection =>
      x =>
        for {
          a <- gbestPSO(collection)(x)
          b <- de(collection)(a)
          // The entity might have moved, so the current pbest is no longer valid
          c <- cilib.pso.PSO.updatePBest(b)
        } yield c

  val alg: NonEmptyVector[Entity[Mem[Double], Double]] => Step[NonEmptyVector[Entity[Mem[Double], Double]]] =
    Iteration.sync(combinedAlg)

  def run(args: List[String]): URIO[Console with Console, ExitCode] =
    putStrLn(Runner.repeat(1000, alg, swarm).provide(env).runAll(RNG.fromTime).toString).exitCode
}
