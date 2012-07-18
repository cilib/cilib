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
package net.sourceforge.cilib.functions.continuous;

import net.sourceforge.cilib.functions.ContinuousFunction;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 *
 * R(-900, 900)^30
 * 
 */
public class Neumaier3 implements ContinuousFunction {

    private static final long serialVersionUID = 192809046725649930L;

//    /**
//     * {@inheritDoc}
//     */
//    public Double getMinimum() {
////        double dimension = getDimension();
////        return (dimension * (dimension + 4.0) * (dimension - 1.0)) / 6.0;
//        throw new UnsupportedOperationException();
//    }
    /**
     * {@inheritDoc}
     */
    public Double apply(Vector input) {
        double tmp1 = 0;
        double tmp2 = 0;
        for (int i = 0; i < input.size(); ++i) {
            tmp1 += (input.doubleValueOf(i) - 1) * (input.doubleValueOf(i) - 1);
        }
        for (int i = 1; i < input.size(); ++i) {
            tmp2 += input.doubleValueOf(i) * input.doubleValueOf(i - 1);
        }
        return tmp1 - tmp2;
    }
}
