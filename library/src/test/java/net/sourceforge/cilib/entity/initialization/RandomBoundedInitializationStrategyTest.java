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

import net.sourceforge.cilib.controlparameter.ConstantControlParameter;
import net.sourceforge.cilib.entity.EntityType;
import net.sourceforge.cilib.entity.Particle;
import net.sourceforge.cilib.pso.particle.StandardParticle;
import net.sourceforge.cilib.type.types.Int;
import net.sourceforge.cilib.type.types.Numeric;
import net.sourceforge.cilib.type.types.Real;
import net.sourceforge.cilib.type.types.container.Vector;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import org.junit.Assert;
import org.junit.Test;

public class RandomBoundedInitializationStrategyTest {

    @Test
    public void initialize() {
        Vector vector = Vector.of(Real.valueOf(5.0),
                Real.valueOf(10.0),
                Int.valueOf(7));

        Particle particle = new StandardParticle();
        particle.getProperties().put(EntityType.CANDIDATE_SOLUTION, vector);

        RandomBoundedInitializationStrategy<Particle> strategy = new RandomBoundedInitializationStrategy<Particle>();
        strategy.setLowerBound(ConstantControlParameter.of(-5.0));
        strategy.setUpperBound(ConstantControlParameter.of(5.0));
        strategy.initialize(EntityType.CANDIDATE_SOLUTION, particle);

        for (int i = 0; i < vector.size(); i++) {
            Numeric numeric = vector.get(i);
            Assert.assertThat(numeric.doubleValue(), is(greaterThanOrEqualTo(-5.0)));
            Assert.assertThat(numeric.doubleValue(), is(lessThanOrEqualTo(5.0)));
        }
    }

}