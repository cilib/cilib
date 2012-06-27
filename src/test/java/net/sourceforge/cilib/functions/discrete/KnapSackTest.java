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
package net.sourceforge.cilib.functions.discrete;

import net.sourceforge.cilib.type.types.container.Vector;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class KnapSackTest {

    @Test
    public void testSimpleKnapSack() {
        KnapSack k = new KnapSack();
        k.setCapacity(5);
        k.setNumberOfObjects(5);
        k.setWeight("1,1,1,1,1");
        k.setValue("1,1,1,1,1");

        Vector.Builder x = Vector.newBuilder();
        x.add(true);
        x.add(true);
        x.add(true);
        x.add(true);
        x.add(true);
        assertEquals(5, Double.valueOf(k.apply(x.build())).intValue());
    }
}
