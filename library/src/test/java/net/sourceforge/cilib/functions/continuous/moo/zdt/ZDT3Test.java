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
package net.sourceforge.cilib.functions.continuous.moo.zdt;

import net.sourceforge.cilib.problem.MOFitness;
import net.sourceforge.cilib.type.types.Real;
import net.sourceforge.cilib.type.types.container.Vector;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 *
 */
public class ZDT3Test {

    @Test
    public void testEvaluate01() {
        Vector.Builder builder = Vector.newBuilder();
        for (int i = 0; i < 30; ++i) {
            builder.add(Real.valueOf(0.0));
        }
        ZDT3 t3 = new ZDT3();
        MOFitness fitness = t3.getFitness(builder.build());

        assertThat(fitness.getFitness(0).getValue(), is(equalTo(0.0)));
        assertThat(fitness.getFitness(1).getValue(), is(equalTo(1.0)));
    }

    @Test
    public void testEvaluate02() {
        Vector.Builder builder = Vector.newBuilder();
        builder.add(Real.valueOf(-1.0));
        for (int i = 0; i < 29; ++i) {
            builder.add(Real.valueOf(0.0));
        }
        ZDT3 t3 = new ZDT3();
        MOFitness fitness = t3.getFitness(builder.build());

        assertThat(fitness.getFitness(0).getValue(), is(equalTo(-1.0)));
        assertThat(fitness.getFitness(1).getValue(), is(equalTo(Double.NaN)));
    }

    @Test
    public void testEvaluate03() {
        Vector.Builder builder = Vector.newBuilder();
        builder.add(Real.valueOf(0.0));
        builder.add(Real.valueOf(-29.0 / 9.0));
        for (int i = 0; i < 28; ++i) {
            builder.add(Real.valueOf(0.0));
        }
        ZDT3 t3 = new ZDT3();
        MOFitness fitness = t3.getFitness(builder.build());

        assertThat(fitness.getFitness(0).getValue(), is(equalTo(0.0)));
        assertThat(fitness.getFitness(1).getValue(), is(equalTo(Double.NaN)));
    }

    @Test
    public void testEvaluate04() {
        Vector.Builder builder = Vector.newBuilder();
        builder.add(Real.valueOf(1.0));
        for (int i = 0; i < 29; ++i) {
            builder.add(Real.valueOf(0.0));
        }
        ZDT3 t3 = new ZDT3();
        MOFitness fitness = t3.getFitness(builder.build());

        assertThat(fitness.getFitness(0).getValue(), is(equalTo(1.0)));
        assertEquals(fitness.getFitness(1).getValue(), 0.0, 0.00001);
    }
}
