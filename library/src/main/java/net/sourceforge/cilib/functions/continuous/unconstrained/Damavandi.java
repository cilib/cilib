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
 * <p>
 * The Damavandi function obtained from N. Damavandi, S. Safavi-Naeini,
 * "A hybrid evolutionary programming method for circuit optimization".
 *
 * <p>
 * Global Minimum: f(x,y) = 0;  (x,y) = (2, 2)
 * Note that if (x,y) = (2,2), then the function results in division by 0
 * Local Minimum: f(x,y) = 2; (x,y) = (7, 7)
 *
 * <p>
 * Characteristics:
 * <ul>
 * <li>Multimodal</li>
 * <li>Non-separanle</li>
 * <li>Deceptive</li>
 * </ul>
 *
 * R(0, 12)^2
 *
 */
public class Damavandi implements ContinuousFunction {

    private static final long serialVersionUID = 2857754134712271398L;

    /**
     * {@inheritDoc}
     */
    @Override
    public Double apply(Vector input) {
        double x1 = input.doubleValueOf(0);
        double x2 = input.doubleValueOf(1);

        double numerator = Math.sin(Math.PI * (x1 - 2)) * Math.sin(Math.PI * (x2 - 2));
        double denumerator = Math.PI * Math.PI * (x1 - 2) * (x2 - 2);
        double factor1 = 1 - Math.pow(Math.abs(numerator / denumerator), 5);
        double factor2 = 2 + (x1 - 7) * (x1 - 7) + 2 * (x2 - 7) * (x2 - 7);

        return factor1 * factor2;
    }
}
