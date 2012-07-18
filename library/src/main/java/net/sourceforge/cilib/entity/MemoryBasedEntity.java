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
package net.sourceforge.cilib.entity;

import net.sourceforge.cilib.problem.Fitness;
import net.sourceforge.cilib.type.types.container.StructuredType;

/**
 * All {@link Entity} types that have a memory of pervious experiences
 * should implement this interface.
 */
public interface MemoryBasedEntity {

    /**
     * Get the best position of the {@linkplain MemoryBasedEntity}.
     * @return The {@linkplain net.sourceforge.cilib.type.types.Type}
     *         representing the best position.
     */
    StructuredType getBestPosition();

    /**
     * Get the best {@linkplain Fitness} for the {@linkplain MemoryBasedEntity}.
     * @return The entity's best {@linkplain Fitness}.
     */
    Fitness getBestFitness();
}
