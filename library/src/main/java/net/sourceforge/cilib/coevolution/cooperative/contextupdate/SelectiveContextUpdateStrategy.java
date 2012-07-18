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
package net.sourceforge.cilib.coevolution.cooperative.contextupdate;

import net.sourceforge.cilib.coevolution.cooperative.ContextEntity;
import net.sourceforge.cilib.coevolution.cooperative.problem.DimensionAllocation;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 * This {@linkplain ContextUpdateStrategy} only updates the given context if the new
 * participant solution impoves the fitness of the context vector.
 */
public class SelectiveContextUpdateStrategy implements ContextUpdateStrategy {
    private static final long serialVersionUID = -7457923682790990569L;

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateContext(ContextEntity context, Vector solution,
            DimensionAllocation allocation) {
        ContextEntity testContext = context.getClone();
        testContext.copyFrom(solution, allocation);
        testContext.calculateFitness();
        if (testContext.getFitness().compareTo(context.getFitness()) > 0) {
            context.setCandidateSolution(testContext.getCandidateSolution());
            context.setFitness(testContext.getFitness());
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public ContextUpdateStrategy getClone() {
        return new SelectiveContextUpdateStrategy();
    }

}
