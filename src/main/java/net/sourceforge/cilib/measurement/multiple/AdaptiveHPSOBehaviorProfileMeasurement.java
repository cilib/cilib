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
package net.sourceforge.cilib.measurement.multiple;

//import static com.google.common.base.Preconditions.checkState;

import java.util.List;
import net.sourceforge.cilib.algorithm.Algorithm;
import net.sourceforge.cilib.algorithm.initialisation.HeterogeneousPopulationInitialisationStrategy;
import net.sourceforge.cilib.entity.Particle;
import net.sourceforge.cilib.entity.Topology;
import net.sourceforge.cilib.measurement.Measurement;
import net.sourceforge.cilib.pso.PSO;
import net.sourceforge.cilib.pso.hpso.HeterogeneousIterationStrategy;
import net.sourceforge.cilib.pso.particle.ParticleBehavior;
import net.sourceforge.cilib.type.types.Int;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 * This class measures how many particles in a an adaptive heterogeneous PSO are
 * currently following each of the different behaviors in the behavior pool.
 *
 * @see AdaptiveHeterogeneousIterationStrategy
 */
public class AdaptiveHPSOBehaviorProfileMeasurement implements Measurement<Vector> {
    private static final long serialVersionUID = -255308745515061075L;

    /**
     * {@inheritDoc}
     */
    @Override
    public AdaptiveHPSOBehaviorProfileMeasurement getClone() {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDomain() {
        return "T";
    }

    /**
     * Measure the behavior profile.
     * @param algorithm The algorithm
     * @return A {@link Vector} containing an integer values.
     * Each value corresponds to a behavior in the behavior pool and indicates
     * how many particles are currently following that behavior.
     */
    @Override
    public Vector getValue(Algorithm algorithm) {
        PSO pso = (PSO) algorithm;
        Topology<Particle> topology = pso.getTopology();        
        HeterogeneousIterationStrategy strategy = (HeterogeneousIterationStrategy) pso.getIterationStrategy();
        HeterogeneousPopulationInitialisationStrategy initStrategy = (HeterogeneousPopulationInitialisationStrategy) pso.getInitialisationStrategy();
        List<ParticleBehavior> initialBehaviorPool = initStrategy.getBehaviorPool();
        List<ParticleBehavior> behaviorPool = strategy.getBehaviorPool();
        
        Vector.Builder builder = Vector.newBuilder();
        for (int i = 0; i < behaviorPool.size(); i++) {
            builder.add(Int.valueOf(0));
        }

        Vector profile = builder.build();
        for (Particle p : topology) {            
            for (int i = 0; i < profile.size(); i++) {
                if (p.getParticleBehavior() == behaviorPool.get(i) || p.getParticleBehavior() == initialBehaviorPool.get(i)) {
                    profile.setInt(i, profile.get(i).intValue() + 1);
                }
            }
        }

        return profile;
    }

}
