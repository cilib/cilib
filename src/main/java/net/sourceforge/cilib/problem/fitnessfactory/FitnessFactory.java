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
package net.sourceforge.cilib.problem.fitnessfactory;

import net.sourceforge.cilib.problem.Fitness;

/**
 * A factory for creating new {@link Fitness} objects.
 * @author Theuns Cloete
 */
public interface FitnessFactory<T> {

    /**
     * Create a new {@link Fitness} object that represents the given value.
     * @param value the value that represents the fitness
     * @return a new {@link Fitness} object representing the given value
     */
    Fitness newFitness(T value);
}
