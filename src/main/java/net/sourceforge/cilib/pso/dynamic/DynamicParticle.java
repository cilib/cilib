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
package net.sourceforge.cilib.pso.dynamic;

import net.sourceforge.cilib.algorithm.Algorithm;
import net.sourceforge.cilib.entity.EntityType;
import net.sourceforge.cilib.pso.particle.StandardParticle;

/**
 * Special particle type to use with dynamic algorithms. The extra functionality
 * that it adds is the ability to re-evaluate both current and best position of
 * the particle. A dynamic algorithm usually re-evaluates all particles when a
 * change in the environment has been detected.
 *
 */
public class DynamicParticle extends StandardParticle {

    private static final long serialVersionUID = 1752969607979236619L;

    public DynamicParticle() {
        super();
    }

    public DynamicParticle(DynamicParticle copy) {
        super(copy);
    }

    public DynamicParticle getClone() {
           return new DynamicParticle(this);
    }

    /**
     * Re-evaluate both best and current position of the particle.
     */
    public void reevaluate() {
        DynamicParticle dp = this.getClone();
        dp.getProperties().put(EntityType.CANDIDATE_SOLUTION, dp.getBestPosition());
        this.getProperties().put(EntityType.Particle.BEST_FITNESS, getFitnessCalculator().getFitness(dp));

        this.calculateFitness();
    }
}
