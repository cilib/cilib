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
 * <p><b>Bird Function</b></p>
 *
 * <p><b>Reference:</b> S.K. Mishra, <i>Some New Test Functions
 * for Global Optimization and Performance of Repulsive Particle Swarm Methods</i>
 * North-Eastern Hill University, India, 2002</p>
 *
 * <p>Minimum:
 * <ul>
 * <li> &fnof;(<b>x</b>*) = -106.764537 (approx) </li>
 * <li> <b>x</b>* = (??,??)</li>
 * <li> for x<sub>1</sub>, x<sub>2</sub> in [-2*&pi;, 2*&pi;]</li>
 * </ul>
 * </p>
 *
 * <p>Characteristics:
 * <ul>
 * <li>Only defined for 2 dimensions</li>
 * <li>Unimodal</li>
 * <li>Seperable</li>
 * <li>Regular</li>
 * </ul>
 * </p>
 *
 * R(-6.285714286,6.285714286)^2
 *
 */
public class Bird implements ContinuousFunction {

    private static final long serialVersionUID = -7803711986955989075L;

    /* (non-Javadoc)
     * @see net.sourceforge.cilib.functions.redux.ContinuousFunction#evaluate(net.sourceforge.cilib.type.types.container.Vector)
     */
    @Override
    public Double apply(Vector input) {
        double x1 = input.doubleValueOf(0);
        double x2 = input.doubleValueOf(1);

        return Math.sin(x1) * Math.exp((1 - Math.cos(x2)) * (1 - Math.cos(x2))) + Math.cos(x2) * Math.exp((1 - Math.sin(x1)) * (1 - Math.sin(x1))) + (x1 - x2) * (x1 - x2);
    }
}
