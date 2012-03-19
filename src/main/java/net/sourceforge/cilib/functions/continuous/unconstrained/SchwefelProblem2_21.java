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
 * SchwefelProblem2_21.
 *
 * Characteristics:
 *
 * f(x) = 0;
 *
 * x e [-100,100]
 *
 * R(-100, 100)^30
 *
 */
// TODO: Check discontinuous / continuous
public class SchwefelProblem2_21 implements ContinuousFunction {

    private static final long serialVersionUID = 8583159190281586599L;

    /**
     * {@inheritDoc}
     */
    @Override
    public Double apply(Vector input) {
        double max = Math.abs(input.doubleValueOf(0));
        double value;

        for (int i = 1; i < input.size(); ++i) {
            value = Math.abs(input.doubleValueOf(i));
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
