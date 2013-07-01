/**           __  __
 *    _____ _/ /_/ /_    Computational Intelligence Library (CIlib)
 *   / ___/ / / / __ \   (c) CIRG @ UP
 *  / /__/ / / / /_/ /   http://cilib.net
 *  \___/_/_/_/_.___/
 */
package net.cilib.coevolution.cooperative.contributionselection;

import static org.junit.Assert.assertEquals;
import net.cilib.algorithm.population.SinglePopulationBasedAlgorithm;
import net.cilib.entity.EntityType;
import net.cilib.problem.solution.MinimisationFitness;
import net.cilib.pso.PSO;
import net.cilib.pso.particle.Particle;
import net.cilib.pso.particle.StandardParticle;
import net.cilib.type.types.container.Vector;

import org.junit.Test;

public class TopologyBestContributionSelectionStrategyTest {

    @Test
    public void TopologyBestContributionSelectionTest(){

        SinglePopulationBasedAlgorithm algorithm = new PSO();

        Particle e1 = new StandardParticle();
        Particle e2 = new StandardParticle();
        Particle e3 = new StandardParticle();

        e1.getProperties().put(EntityType.FITNESS, new MinimisationFitness(2.0));
        e2.getProperties().put(EntityType.FITNESS, new MinimisationFitness(1.5));
        e3.getProperties().put(EntityType.FITNESS, new MinimisationFitness(1.0));

        e1.getProperties().put(EntityType.Particle.BEST_FITNESS, new MinimisationFitness(0.0));
        e2.getProperties().put(EntityType.Particle.BEST_FITNESS, new MinimisationFitness(1.0));
        e3.getProperties().put(EntityType.Particle.BEST_FITNESS, new MinimisationFitness(0.5));

        Vector v1 = Vector.of(1);
        e1.getProperties().put(EntityType.CANDIDATE_SOLUTION, v1);

        Vector v2 = Vector.of(2);
        e2.getProperties().put(EntityType.CANDIDATE_SOLUTION, v2);

        Vector v3 = Vector.of(3);
        e3.getProperties().put(EntityType.CANDIDATE_SOLUTION, v3);

        algorithm.setTopology(fj.data.List.list(e1, e2, e3));
//        ((Topology<Particle>)algorithm.getTopology()).add(e1);
//        ((Topology<Particle>)algorithm.getTopology()).add(e2);
//        ((Topology<Particle>)algorithm.getTopology()).add(e3);

        TopologyBestContributionSelectionStrategy test = new TopologyBestContributionSelectionStrategy();

        Vector selected = test.getContribution(algorithm);

        assertEquals(3.0, selected.get(0).doubleValue(), 0.0);
    }

}
