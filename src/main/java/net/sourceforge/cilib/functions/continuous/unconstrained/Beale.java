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
package net.sourceforge.cilib.functions.continuous.unconstrained;

import net.sourceforge.cilib.functions.ContinuousFunction;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 * <p><b>Beale Function.</b></p>
 *
 * <p><b>Reference:</b> S.K. Mishra, <i>Some New Test Functions
 * for Global Optimization and Performance of Repulsive Particle Swarm Methods</i>
 * North-Eastern Hill University, India, 2002</p>
 *
 * <p>Minimum:
 * <ul>
 * <li> &fnof;(<b>x</b>*) = 0</li>
 * <li> <b>x</b>* = (3, 0.5)</li>
 * <li> for x<sub>1</sub>, x<sub>2</sub> in [-4.5, 4.5]</li>
 * </ul>
 * </p>
 *
 * <p>Characteristics:
 * <ul>
 * <li>Only defined for 2 dimensions</li>
 * <li>Unimodal</li>
 * <li>Separable</li>
 * <li>Regular</li>
 * </ul>
 * </p>
 *
 * R(-4.5,4.5)^2
 *
 */
public class Beale implements ContinuousFunction {

    private static final long serialVersionUID = -7803711986955989075L;

    /**
     * {@inheritDoc}
     */
    @Override
    public Double apply(Vector input) {
        double x1 = input.doubleValueOf(0);
        double x2 = input.doubleValueOf(1);
        return (1.5 - x1 + x1 * x2) * (1.5 - x1 + x1 * x2) + (2.25 - x1 + x1 * x2 * x2) * (2.25 - x1 + x1 * x2 * x2) + (2.625 - x1 + x1 * x2 * x2 * x2) * (2.625 - x1 + x1 * x2 * x2 * x2);
    }
}

