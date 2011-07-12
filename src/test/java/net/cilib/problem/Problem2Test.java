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
package net.cilib.problem;

import fj.Function;
import fj.data.List;
import fj.data.Option;
import fj.function.Doubles;
import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 *
 * @author gpampara
 */
public class Problem2Test {

    @Test
    public void solutionEvaluation() {
        List<Double> list = List.list(1.0, 2.0, 3.0, 4.0);

        Problem2 mockProblem2 = new Problem2(Function.uncurryF2(Doubles.multiply));

        Option<Double> result = mockProblem2.eval(list);
        Assert.assertThat(result.some(), equalTo(14.0));
    }
}
