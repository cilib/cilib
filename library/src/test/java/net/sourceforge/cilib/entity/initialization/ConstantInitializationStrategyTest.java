/**
 * Computational Intelligence Library (CIlib)
 * Copyright (C) 2003 - 2010
 * Computational Intelligence Research Group (CIRG@UP)
 * Department of Computer Science
 * University of Pretoria
 * South Africa
 *
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, see <http://www.gnu.org/licenses/>.
 */
package net.sourceforge.cilib.entity.initialization;

import net.sourceforge.cilib.ec.Individual;
import net.sourceforge.cilib.entity.EntityType;
import net.sourceforge.cilib.math.Maths;
import net.sourceforge.cilib.type.types.Real;
import net.sourceforge.cilib.type.types.container.Vector;
import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

/**
 *
 */
public class ConstantInitializationStrategyTest {

    @Test
    public void testGetClone() {
        ConstantInitializationStrategy strategy = new ConstantInitializationStrategy(1.0);
        ConstantInitializationStrategy clone = strategy.getClone();

        Assert.assertNotSame(strategy, clone);
        Assert.assertEquals(strategy.getConstant(), clone.getConstant(), Maths.EPSILON);
    }

    @Test
    public void initialize() {
        Vector vector = Vector.of(1.0, 1.0, 1.0);
        Individual individual = new Individual();
        individual.getProperties().put(EntityType.CANDIDATE_SOLUTION, Vector.copyOf(vector));

        ConstantInitializationStrategy<Individual> initializationStrategy = new ConstantInitializationStrategy<Individual>();
        initializationStrategy.initialize(EntityType.CANDIDATE_SOLUTION, individual);

        Vector chromosome = (Vector) individual.getCandidateSolution();

        for (int i = 0; i < vector.size(); i++) {
            Assert.assertThat(vector.doubleValueOf(i), is(not(chromosome.doubleValueOf(i))));
        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void invalidInitialize() {
        Individual individual = new Individual();
        individual.getProperties().put(EntityType.CANDIDATE_SOLUTION, Real.valueOf(0.0));

        ConstantInitializationStrategy<Individual> initializationStrategy = new ConstantInitializationStrategy<Individual>();

        initializationStrategy.initialize(EntityType.CANDIDATE_SOLUTION, individual);
    }
}
