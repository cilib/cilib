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
package net.sourceforge.cilib.entity.operators.mutation;

import java.util.List;
import java.util.ListIterator;

import net.sourceforge.cilib.controlparameter.ControlParameter;
import net.sourceforge.cilib.controlparameter.ProportionalControlParameter;
import net.sourceforge.cilib.entity.Entity;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 */
public class UniformMutationStrategy extends MutationStrategy {

    private static final long serialVersionUID = -3951730432882403768L;
    private ControlParameter minStrategy, maxStrategy;

    public UniformMutationStrategy() {
        super();
        minStrategy = new ProportionalControlParameter();
        maxStrategy = new ProportionalControlParameter();
    }

    public UniformMutationStrategy(UniformMutationStrategy copy) {
        super(copy);
        this.minStrategy = copy.minStrategy.getClone();
        this.maxStrategy = copy.maxStrategy.getClone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniformMutationStrategy getClone() {
        return new UniformMutationStrategy(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void mutate(List<? extends Entity> entity) {
        for (ListIterator<? extends Entity> individual = entity.listIterator(); individual.hasNext();) {
            Entity current = individual.next();
            Vector chromosome = (Vector) current.getCandidateSolution();

            if (this.getMutationProbability().getParameter() >= this.getRandomDistribution().getRandomNumber()) {
                for (int i = 0; i < chromosome.size(); i++) {
                    double value = this.getOperatorStrategy().evaluate(chromosome.doubleValueOf(i), this.getRandomDistribution().getRandomNumber(minStrategy.getParameter(), maxStrategy.getParameter()));
                    chromosome.setReal(i, value);
                }
            }
        }
    }
}
