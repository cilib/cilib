/**           __  __
 *    _____ _/ /_/ /_    Computational Intelligence Library (CIlib)
 *   / ___/ / / / __ \   (c) CIRG @ UP
 *  / /__/ / / / /_/ /   http://cilib.net
 *  \___/_/_/_/_.___/
 */
package net.cilib.niching.creation;

import fj.data.List;
import net.cilib.algorithm.population.SinglePopulationBasedAlgorithm;
import net.cilib.entity.Entity;
import net.cilib.pso.particle.Particle;
import net.cilib.niching.NichingSwarms;
import net.cilib.niching.NichingFunctionsTest;
import net.cilib.problem.solution.MinimisationFitness;
import net.cilib.pso.PSO;
import net.cilib.pso.particle.ParticleBehavior;
import net.cilib.type.types.container.Vector;
import org.junit.Assert;
import org.junit.Test;

public class ClosestNeighbourNicheCreationStrategyTest {

    @Test
    public void testCreation() {
        Particle p1 = NichingFunctionsTest.createParticle(new MinimisationFitness(3.0), Vector.of(0.0, 1.0));
        Particle p2 = NichingFunctionsTest.createParticle(new MinimisationFitness(2.0), Vector.of(1.0, 1.0));
        Particle p3 = NichingFunctionsTest.createParticle(new MinimisationFitness(1.0), Vector.of(2.0, 2.0));

        PSO pso = new PSO();
        pso.setTopology(fj.data.List.list(p1, p2, p3));

        ClosestNeighbourNicheCreationStrategy creator = new ClosestNeighbourNicheCreationStrategy();
        creator.setSwarmBehavior(new ParticleBehavior());
        NichingSwarms swarms = creator.f(NichingSwarms.of(pso, List.<SinglePopulationBasedAlgorithm>nil()), p1);

        Assert.assertEquals(1, swarms._1().getTopology().length());
        Assert.assertEquals(Vector.of(2.0, 2.0), ((Entity)swarms._1().getTopology().head()).getCandidateSolution());
        Assert.assertEquals(2, swarms._2().head().getTopology().length());
        Assert.assertEquals(Vector.of(0.0, 1.0), ((Entity) swarms._2().head().getTopology().head()).getCandidateSolution());
        Assert.assertEquals(Vector.of(1.0, 1.0), ((Entity) swarms._2().head().getTopology().index(1)).getCandidateSolution());
    }
}
