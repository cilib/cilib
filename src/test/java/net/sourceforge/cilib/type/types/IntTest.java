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

import net.sourceforge.cilib.math.random.generator.MersenneTwister;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 *
 */
public class IntTest {

    @Test
    public void testClone() {
        Int i = Int.valueOf(-10);
        Int clone = i.getClone();

        assertEquals(i.intValue(), clone.intValue());
        assertNotSame(i, clone);
    }

    @Test
    public void testEquals() {
        Int i1 = Int.valueOf(10);
        Int i2 = Int.valueOf(10);
        Int i3 = Int.valueOf(-5);

        assertTrue(i1.equals(i1));
        assertTrue(i2.equals(i2));
        assertTrue(i3.equals(i3));

        assertTrue(i1.equals(i2));
        assertFalse(i1.equals(i3));
        assertTrue(i2.equals(i1));
        assertFalse(i2.equals(i3));
    }

    @Test
    public void testCompareTo() {
        Int i1 = Int.valueOf(15, new Bounds(0, 30));
        Int i2 = Int.valueOf(-15, new Bounds(-30, 0));

        assertEquals(0, i1.compareTo(i1));
        assertEquals(0, i2.compareTo(i2));
        assertEquals(1, i1.compareTo(i2));
        assertEquals(-1, i2.compareTo(i1));
    }

    @Test
    public void testRandomize() {
        Int i1 = Int.valueOf(0, new Bounds(-300, 300));
        Int i2 = i1.getClone();

        assertTrue(i1.intValue() == i2.intValue());
        i1.randomize(new MersenneTwister());
        assertTrue(i1.intValue() != i2.intValue());
    }
}
