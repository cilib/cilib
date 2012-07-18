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
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SchwefelTest {

    public SchwefelTest() {
    }

    /** Test of evaluate method, of class za.ac.up.cs.ailib.Functions.Schwefel. */
    @Test
    public void testEvaluate() {
        ContinuousFunction function = new Schwefel();

        Vector x = Vector.of(1.0, 2.0, 3.0);
        Vector y = Vector.of(-1.0, -2.0, -3.0);

        assertEquals(1262.726744, function.apply(x), 0.0000009);
        assertEquals(1251.170579, function.apply(y), 0.0000009);
    }
}
