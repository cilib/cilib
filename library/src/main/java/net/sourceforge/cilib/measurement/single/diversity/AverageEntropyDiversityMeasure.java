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
package net.sourceforge.cilib.measurement.single.diversity;

import net.sourceforge.cilib.measurement.entropy.DimensionalEntropyMeasurement;
import net.sourceforge.cilib.algorithm.Algorithm;
import net.sourceforge.cilib.algorithm.population.PopulationBasedAlgorithm;
import net.sourceforge.cilib.measurement.entropy.EntropyMeasurement;
import net.sourceforge.cilib.type.types.Real;
import net.sourceforge.cilib.type.types.container.TypeList;

/**
 * A diversity measurement that calculates the diversity of a population to be
 * the average entropy over all dimensions in the search space.
 *
 */
public class AverageEntropyDiversityMeasure extends Diversity {

    private EntropyMeasurement entropyMeasure;

    public AverageEntropyDiversityMeasure() {
        entropyMeasure = new DimensionalEntropyMeasurement();
    }

    public AverageEntropyDiversityMeasure(int intervals) {
        entropyMeasure = new DimensionalEntropyMeasurement(intervals);
    }

    public AverageEntropyDiversityMeasure(AverageEntropyDiversityMeasure copy) {
        this.entropyMeasure = copy.entropyMeasure;
    }

    @Override
    public AverageEntropyDiversityMeasure getClone() {
        return new AverageEntropyDiversityMeasure(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Real getValue(Algorithm algorithm) {
        PopulationBasedAlgorithm populationBasedAlgorithm = (PopulationBasedAlgorithm) algorithm;
        int dimensions = populationBasedAlgorithm.getOptimisationProblem().getDomain().getDimension();

        double diversity = 0.0;
        TypeList entropyMeasurements = entropyMeasure.getValue(algorithm);

        for (int i = 0; i < dimensions; i++) {
            diversity += ((Real) entropyMeasurements.get(i)).doubleValue();
        }

        diversity /= dimensions;

        diversity /= this.normalisationParameter.getNormalisationParameter(populationBasedAlgorithm);

        return Real.valueOf(diversity);
    }

    /**
     * Get the number of intervals over which entropy is measured.
     * @return The number of intervals over which entropy is measured.
     */
    public int getIntervals() {
        return entropyMeasure.getIntervals();
    }

    /**
     * Set the number of intervals over which entropy is measured.
     * @param i the number of intervals to set.
     */
    public void setIntervals(int intervals) {
        entropyMeasure.setIntervals(intervals);
    }
}
