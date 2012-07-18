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
 * <p>Generalised Rosenbrock function.</p>
 *
 * <p><b>Reference:</b> X. Yao, Y. Liu, G. Liu, <i>Evolutionary Programming
 * Made Faster</i>,  IEEE Transactions on Evolutionary Computation,
 * 3(2):82--102, 1999</p>
 *
 * Characteristics:
 * <ul>
 * <li>Unimodal</li>
 * <li>Non Separable</li>
 * <li>Continuous</li>
 * <li>Regular</li>
 * </ul>
 *
 * f(x) = 0; x = 1
 *
 * x e [-2.048,2.048]
 *
 * R(-2.048, 2.048)^30
 *
 */
public class Rosenbrock implements ContinuousFunction {

    private static final long serialVersionUID = -5850480295351224196L;

    /**
     * {@inheritDoc}
     */
    @Override
    public Double apply(Vector input) {
        double tmp = 0;

        for (int i = 0; i < input.size()-1; ++i) {
            double a = input.doubleValueOf(i);
            double b = input.doubleValueOf(i+1);

            tmp += ((100 * (b - a * a) * (b - a * a)) + ((a - 1.0) * (a - 1.0)));
        }

        return tmp;
    }
}
