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
package net.sourceforge.cilib.type.types;

import net.sourceforge.cilib.type.types.container.Vector;
import org.junit.Assert;
import org.junit.Test;


/**
 *
 */
public class TypesTest {

    @Test
    public void numericDimension() {
        Real r = Real.valueOf(0.0);
        Int i = Int.valueOf(0);
        Bit b = Bit.valueOf(false);

        Assert.assertEquals(1, Types.dimensionOf(r));
        Assert.assertEquals(1, Types.dimensionOf(i));
        Assert.assertEquals(1, Types.dimensionOf(b));
    }

    @Test
    public void structureDimension() {
        Vector vector = new Vector();
        Assert.assertEquals(0, Types.dimensionOf(vector));

        vector.add(Real.valueOf(0.0));
        Assert.assertEquals(1, Types.dimensionOf(vector));
    }

    @Test
    public void nonStructureDimension() {
        Real r = Real.valueOf(0.0);

        Assert.assertEquals(1, Types.dimensionOf(r));
    }

    @Test
    public void structureIsNotInsideBounds() {
        Vector vector = new Vector();
        Real r = Real.valueOf(-7.0, new Bounds(-5.0, 5.0));

        vector.add(r);

        Assert.assertFalse(Types.isInsideBounds(vector));
    }

    @Test
    public void structureInBounds() {
        Bounds bounds = new Bounds(-5.0, 5.0);
        Real r1 = Real.valueOf(-5.0, bounds);
        Real r2 = Real.valueOf(5.0, bounds);

        Vector vector = new Vector();
        vector.add(r1);
        vector.add(r2);

        Assert.assertTrue(Types.isInsideBounds(vector));
    }
}
