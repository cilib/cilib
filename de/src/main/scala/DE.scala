package cilib
package de

import spire.math._
import spire.algebra._
import spire.implicits._

import scalaz._
import Scalaz._
import monocle._
import Monocle._

object DE {

    def de[S, A: Numeric : Rng](
                                   p_r: Double,
                                   p_m: Double,
                                   targetSelection: NonEmptyList[Individual[S, A]] => RVar[Individual[S, A]]
                                   //y: N,
                                   //z: Int
                               ): NonEmptyList[Individual[S, A]] => Individual[S, A] => Step[A, Individual[S, A]] =
        collection => x => for {
            evaluated <- Step.eval((a: Position[A]) => a)(x)
            trial <- Step.pointR(basicMutation(Numeric[A].fromDouble(p_m), targetSelection, collection, x))
            pivots <- Step.pointR(bin(p_r, evaluated))
            offspring = crossover(x, trial, pivots)
            evaluatedOffspring <- Step.eval((a: Position[A]) => a)(offspring)
            fittest <- better(evaluated, evaluatedOffspring)
        } yield fittest

    // DEs
    ///////////////////////////////////////////
    // DE/best/1/bin:
    def deBest[S, A: Numeric : Rng](p_r: Double, p_m: Double) = de(p_r, p_m, selectBestTarget[S, A])

    // Selection Methods
    ///////////////////////////////////////////
    def selectBestTarget[S, A](individuals: NonEmptyList[Individual[S, A]]): RVar[Individual[S, A]] = {
        val maxComparison = Comparison.dominance(Max)
        RVar.point(individuals.foldLeft1((a, b) => maxComparison.apply(a, b)(Entity.entityFitness)))
    }

    // Mutation Methods
    ///////////////////////////////////////////
    def basicMutation[S, A: Rng](
                                    p_m: A,
                                    selection: NonEmptyList[Individual[S, A]] => RVar[Individual[S, A]],
                                    collection: NonEmptyList[Individual[S, A]],
                                    x: Individual[S, A]): RVar[Position[A]] = {
        val target: RVar[Individual[S, A]] = selection(collection)
        val filtered = filter(target, collection, x)
        val differenceVector = getDifferenceVector(filtered)
        for {
            t <- target
            p <- differenceVector
        } yield p.foldLeft(t.pos)((a, c) => a + (p_m *: c))
    }

    // Crossover Methods
    ///////////////////////////////////////////
    def crossover[S, A](target: Individual[S, A], trial: Position[A], pivots: NonEmptyList[Boolean]) =
        target.copy(
            pos = {
                target.pos.zip(trial).zip(Position(pivots, trial.boundary)).map { case ((a, b), p) => // Position apply function :(
                    if (p) b else a
                }
            })

    // Pivot Calculation Methods
    ///////////////////////////////////////////
    // This is not a nice implementation ??? -> it feels far too low level and inelegant
    def bin[S, A: Rng](
                          p_r: Double,
                          parent: Individual[S, A]
                      ): RVar[NonEmptyList[Boolean]] =
        for {
            j <- Dist.uniformInt(spire.math.Interval(0, parent.pos.pos.size - 1))
            l <- parent.pos.pos.toList.traverse(_ => Dist.stdUniform.map(_ < p_r))
        } yield {
            val t: List[Boolean] = l.applyOptional(index(j)).set(true)
            NonEmptyList(t.head, t.tail: _*)
        }

    // def exp[A:Rng](
    //   p_r: Double,
    //   parent: Individual[A]
    // ): RVar[List[Boolean]] =
    //   for {
    //     start <- Dist.uniformInt(0, parent.pos.pos.size - 1)
    //     length = parent.pos.pos.size - 1
    //     circular = Stream.continually((0 to length).toList).drop(start).take(length)
    //     list <-


    // Helper Methods .... Private?
    ///////////////////////////////////////////
    // Duplicated from PSO.....
    def better[S, A](a: Individual[S, A], b: Individual[S, A]): Step[A, Individual[S, A]] =
        Step.withCompare(comp => Comparison.compare(a, b).apply(comp))

    def filter[S, A](target: RVar[Individual[S, A]], collection: NonEmptyList[Individual[S, A]], x: Individual[S, A]): RVar[IList[Individual[S, A]]] =
        target.map(t => collection.list.filterNot(a => List(t, x).contains(a)))

    def createPairs[Z](acc: List[(Z, Z)], xs: List[Z]): List[(Z, Z)] =
        xs match {
            case Nil => acc
            case a :: b :: xss => createPairs((a, b) :: acc, xss)
            case _ => sys.error("ugg")
        }

    def getDifferenceVector[S, A](filteredCollection: RVar[IList[Individual[S, A]]]): RVar[List[Position[A]]] = {
        filteredCollection.flatMap(_.toNel match {
            case Some(l) =>
                RVar.shuffle(l)
                    .map(a => createPairs(List.empty[(Individual[S, A], Individual[S, A])], a.toList.take(2))
                        .map(z => z._1.pos - z._2.pos))
            case None =>
                RVar.point(List.empty)
        })
    }

}
