/**           __  __
 *    _____ _/ /_/ /_    Computational Intelligence Library (CIlib)
 *   / ___/ / / / __ \   (c) CIRG @ UP
 *  / /__/ / / / /_/ /   http://cilib.net
 *  \___/_/_/_/_.___/
 */
package net.cilib.measurement.single;

import net.cilib.algorithm.population.SinglePopulationBasedAlgorithm;
import net.cilib.entity.EntityType;
import net.cilib.pso.particle.Particle;
import net.cilib.measurement.Measurement;
import net.cilib.pso.particle.StandardParticle;
import net.cilib.type.types.Bounds;
import net.cilib.type.types.Real;
import net.cilib.type.types.container.Vector;
import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
*
*/
public class DimensionBoundViolationsPerParticleTest {

    @Test
    public void testDimensionBoundViolationsPerParticle() {
        Particle p1 = new StandardParticle();
        Particle p2 = new StandardParticle();
        Particle p3 = new StandardParticle();
        Particle p4 = new StandardParticle();

        Bounds bounds = new Bounds(0.0, 2.0);
        p1.getProperties().put(EntityType.CANDIDATE_SOLUTION, vectorOf(bounds, 0.5, -1.0, 2.1));
        p2.getProperties().put(EntityType.CANDIDATE_SOLUTION, vectorOf(bounds, 1.0, 2.0, 2.0));
        p3.getProperties().put(EntityType.CANDIDATE_SOLUTION, vectorOf(bounds, -1.0,0.0,1.0));
        p4.getProperties().put(EntityType.CANDIDATE_SOLUTION, vectorOf(bounds, 3.0,2.0,-1.0));

        final fj.data.List<Particle> topology = fj.data.List.list(p1, p2, p3, p4);

        final SinglePopulationBasedAlgorithm pba = mock(SinglePopulationBasedAlgorithm.class);
        when(pba.getTopology()).thenReturn((fj.data.List) topology);

        Measurement m = new DimensionBoundViolationsPerParticle();
        Assert.assertEquals(Real.valueOf(1.25), m.getValue(pba));
    }

    private Vector vectorOf(Bounds bounds, double... values) {
        Vector.Builder vector = Vector.newBuilder();
        for (int i = 0; i < values.length; i++) {
            vector.add(Real.valueOf(values[i], bounds));
        }
        return vector.build();
    }
}
