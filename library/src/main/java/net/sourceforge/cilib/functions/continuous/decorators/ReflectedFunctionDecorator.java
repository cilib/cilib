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
package net.sourceforge.cilib.functions.continuous.decorators;

import net.sourceforge.cilib.functions.ContinuousFunction;
import net.sourceforge.cilib.functions.Function;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 * <p>
 * Class to reflect the wrapped {@linkplain Function} in a horizontal or
 * vertical fashion.
 *
 * Characteristics:
 *
 * Sets f(x) to f(-x) or -f(x) or -f(-x) based on what is required, by
 * reflecting on a specific axis.
 *
 * Setting values in xml works the same as setting string values
 *
 */
public class ReflectedFunctionDecorator implements ContinuousFunction {
    private static final long serialVersionUID = -5042848697343918398L;
    private ContinuousFunction function;
    private boolean horizontalReflection;
    private boolean verticalReflection;

    public ReflectedFunctionDecorator() {
        horizontalReflection = false;
        verticalReflection = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double apply(Vector input) {
        Vector tmp = Vector.copyOf(input);

        if (horizontalReflection) {
            for (int i = 0; i < input.size(); i++) {
                tmp.setReal(i, -input.doubleValueOf(i));
            }
        }

        if (verticalReflection) {
            return -function.apply(tmp);
        }

        return function.apply(tmp);
    }

    /**
     * Get the decorated function contained by this instance.
     * @return the function
     */
    public ContinuousFunction getFunction() {
        return function;
    }

    /**
     * Set the wrapped function.
     * @param function the function to set.
     */
    public void setFunction(ContinuousFunction function) {
        this.function = function;
    }

    /**
     * Get the horizonal reflection.
     * @return the horizontalReflection.
     */
    public boolean getHorizontalReflection() {
        return horizontalReflection;
    }

    /**
     * Invoking this method sets horizontalReflection to true.
     */
    public void setHorizontalReflection(boolean horizontalReflection) {
        this.horizontalReflection = horizontalReflection;
    }

    /**
     * Invoking this method sets horizontalReflection to true.
     */
    public void setHorizontalReflection(String horizontalReflection) {
        this.horizontalReflection = Boolean.parseBoolean(horizontalReflection);
    }

    /**
     * Get the vertical reflection.
     * @return the verticalReflection.
     */
    public boolean getVerticalReflection() {
        return verticalReflection;
    }

    /**
     * Invoking this method sets verticalReflection to true.
     */
    public void setVerticalReflection(boolean verticalReflection) {
        this.verticalReflection = verticalReflection;
    }

    /**
     * Invoking this method sets verticalReflection to true.
     */
    public void setVerticalReflection(String verticalReflection) {
        this.verticalReflection = Boolean.parseBoolean(verticalReflection);
    }
}
