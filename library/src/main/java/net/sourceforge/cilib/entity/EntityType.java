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

/**
 * The defined types for all properties within {@linkplain Entity} objects.
 */
public enum EntityType {
    CANDIDATE_SOLUTION,
    PREVIOUS_SOLUTION,
    FITNESS,
    PREVIOUS_FITNESS,
    STRATEGY_PARAMETERS;

    /**
     * {@linkplain Particle} specific constants.
     */
    public enum Particle {
        BEST_POSITION,
        BEST_FITNESS,
        VELOCITY;

        public enum Count {
            PBEST_STAGNATION_COUNTER;
        }
    }

    /**
     * Coevolution constants... This is probably going to be refactored to another location.
     * TODO: Check this
     */
    public enum Coevolution { // Not sure about this... has a funky smell to it.
        DISTANCE,
        BOARD,
        POPULATION_ID;
    }
}
