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
package net.sourceforge.cilib.stoppingcondition;

import com.google.common.base.Preconditions;

/**
 * A stopping predicate used to stop an algorithm when a measurement is greater than or equal to a value.
 */
public class Maximum implements CompletionCalculator {
    
    private double percentage;
    
    public Maximum() {
        this.percentage = 0.0;
    }

    @Override
    public double getPercentage(double actualValue, double targetValue) {
        Preconditions.checkArgument(targetValue != 0.0, "targetValue cannot be zero.");
        percentage = Math.max(percentage, actualValue / targetValue);
        return Math.max(Math.min(percentage, 1.0), 0.0);
    }

    @Override
    public boolean apply(double actualValue, double targetValue) {
        return actualValue >= targetValue;
    }
}
