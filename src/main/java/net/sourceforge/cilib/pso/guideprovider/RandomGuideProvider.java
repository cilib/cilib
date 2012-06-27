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
package net.sourceforge.cilib.pso.guideprovider;

import net.sourceforge.cilib.algorithm.AbstractAlgorithm;
import net.sourceforge.cilib.entity.Particle;
import net.sourceforge.cilib.entity.Topology;
import net.sourceforge.cilib.math.random.ProbabilityDistributionFuction;
import net.sourceforge.cilib.math.random.UniformDistribution;
import net.sourceforge.cilib.pso.PSO;
import net.sourceforge.cilib.type.types.container.StructuredType;

/**
 * A concrete implementation of {@link GuideProvider} where the neighbourhood
 * best position of a random particle gets selected as a guide.
 */
public class RandomGuideProvider implements GuideProvider {

    private ProbabilityDistributionFuction random;

    public RandomGuideProvider() {
        this.random = new UniformDistribution();
    }

    public RandomGuideProvider(RandomGuideProvider copy) {
        this.random = copy.random;
    }

    @Override
    public RandomGuideProvider getClone() {
        return new RandomGuideProvider(this);
    }

    @Override
    public StructuredType get(Particle particle) {
        Topology<Particle> topology = ((PSO) AbstractAlgorithm.get()).getTopology();
        Particle other = topology.get((int) random.getRandomNumber(0, topology.size()));
        
        return other.getBestPosition();
    }
}
