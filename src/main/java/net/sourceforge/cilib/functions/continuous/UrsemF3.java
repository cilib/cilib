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
 * UrsemF3 function.
 *
 * <p>Title: CILib</p>
 * <p>Description: CILib (Computational Intelligence Library)</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 *
 * Minimum: 2.5
 * R(-2, 2)^2
 * 
 * @version 1.0
 */
public class UrsemF3 implements ContinuousFunction {

    private static final long serialVersionUID = -4477290008482842765L;

    /**
     * {@inheritDoc}
     */
    @Override
    public Double apply(Vector input) {
        double x = input.doubleValueOf(0);
        double y = input.doubleValueOf(1);

        double result = Math.sin(2.2 * Math.PI * x + 0.5 * Math.PI) * ((2.0 - Math.abs(y)) / 2.0) * ((3.0 - Math.abs(x)) / 2.0);
        result += Math.sin(0.5 * Math.PI * y * y + 0.5 * Math.PI) * ((2.0 - Math.abs(y)) / 2.0) * ((2.0 - Math.abs(x)) / 2.0);

        return result;
    }
}
