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
package net.sourceforge.cilib.algorithm;

import net.sourceforge.cilib.util.Cloneable;

/**
 * Any class can implement this interface to be notified about algorithm events. Classes
 * implementing this interface can be added to the algorithm as an event listener using
 * {@link Algorithm#addAlgorithmListener(AlgorithmListener)}.
 */
public interface AlgorithmListener extends Cloneable {
    /**
     * This event is fired just prior to the execution of the main loop of the algorithm.
     * @param e an event containing a reference to the source algorithm.
     */
    void algorithmStarted(AlgorithmEvent e);

    /**
     * This event is fired when the algorithm has completed normally.
     * @param e an event containing a reference to the source algorithm.
     */
    void algorithmFinished(AlgorithmEvent e);

    /**
     * This event is fired after each iteration of the mail loop of the algorithm.
     * @param e an event containing a reference to the source algorithm.
     */
    void iterationCompleted(AlgorithmEvent e);

    /**
     * {@inheritDoc}
     */
    @Override
    AlgorithmListener getClone();
}
