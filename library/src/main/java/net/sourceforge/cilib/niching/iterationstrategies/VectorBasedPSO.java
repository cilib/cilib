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
package net.sourceforge.cilib.niching.iterationstrategies;

import com.google.common.collect.Lists;
import net.sourceforge.cilib.algorithm.population.AbstractIterationStrategy;
import net.sourceforge.cilib.niching.NichingAlgorithm;
import static net.sourceforge.cilib.niching.NichingFunctions.createNiches;
import net.sourceforge.cilib.niching.NichingSwarms;

/**
 *
 */
public class VectorBasedPSO extends AbstractIterationStrategy<NichingAlgorithm> {

    @Override
    public AbstractIterationStrategy<NichingAlgorithm> getClone() {
        return this;
    }

    @Override
    public void performIteration(NichingAlgorithm alg) {
        NichingSwarms newSwarms = createNiches(alg.getNicheDetector(), 
                alg.getNicheCreator(),
                alg.getMainSwarmCreationMerger())
            .andThen(alg.getSubSwarmIterator())
            .f(NichingSwarms.of(alg.getMainSwarm(), alg.getPopulations()));

        alg.setPopulations(Lists.newArrayList(newSwarms._2().toCollection()));
        alg.setMainSwarm(newSwarms._1());
    }

}
