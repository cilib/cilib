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

import net.sourceforge.cilib.functions.ContinuousFunction;
import net.sourceforge.cilib.problem.FunctionMinimisationProblem;
import net.sourceforge.cilib.problem.MOOptimisationProblem;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 * <p>Zitzler-Thiele-Deb Test Function 4</p>
 *
 * Characteristics:
 * <ul>
 * <li>Convex Pareto-optimal front.</li>
 * <li>Multimodal - Several local pareto-optimal fronts.</li>
 * </ul>
 *
 * <p>
 * This function contains 21^9 local Pareto-optimal fronts and, therefore,
 * tests for an EA's ability to deal with multimodality.
 * </p>
 *
 * <p>
 * The global Pareto-optimal front is formed with g(x) = 1, the best local
 * Pareto-optimal front with g(x) = 1.25. Note that not all local Pareto-optimal
 * sets are distinguishable in the objective space.
 * </p>
 *
 * <p>
 * References:
 * </p>
 * <p>
 * <ul>
 * <li>
 * E. Zitzler, K. Deb and L. Thiele, "Comparison of multiobjective
 * evolutionary algorithms: Empirical results", in Evolutionary Computation,
 * vol 8, no 2, pp. 173-195, 2000.
 * </li>
 * </ul>
 * </p>
 *
 * @author Wiehann Matthysen
 */
public final class ZDT4 extends MOOptimisationProblem {

    private static final long serialVersionUID = 6807423144357771198L;
    private static final String DOMAIN = "R(0, 1)^1, R(-5,5)^9";

    private static class ZDT4_g implements ContinuousFunction {

        private static final long serialVersionUID = -4693394582794280778L;

        @Override
        public Double apply(Vector input) {
            double sum = 0.0;
            for (int i = 1; i < input.size(); ++i) {
                sum += input.doubleValueOf(i) * input.doubleValueOf(i) - 10.0 * Math.cos(4.0 * Math.PI * input.doubleValueOf(i));
            }
            return 1 + 10.0 * (input.size() - 1.0) + sum;
        }
    }

    private static class ZDT4_h implements ContinuousFunction {

        private static final long serialVersionUID = 3672916606445089134L;
        private final ZDT_f1 f1;
        private final ZDT4_g g;

        public ZDT4_h() {
            this.f1 = new ZDT_f1();
            this.g = new ZDT4_g();
        }

        @Override
        public Double apply(Vector input) {
            return 1.0 - Math.sqrt(this.f1.apply(input) / this.g.apply(input));
        }
    }

    private static class ZDT4_f2 implements ContinuousFunction {

        private static final long serialVersionUID = -4303326355255421549L;
        private final ZDT4_g g;
        private final ZDT4_h h;

        public ZDT4_f2() {
            this.g = new ZDT4_g();
            this.h = new ZDT4_h();
        }

        @Override
        public Double apply(Vector input) {
            return this.g.apply(input) * this.h.apply(input);
        }
    }

    public ZDT4() {
        FunctionMinimisationProblem zdt4_f1 = new FunctionMinimisationProblem();
        zdt4_f1.setFunction(new ZDT_f1());
        zdt4_f1.setDomain(DOMAIN);
        add(zdt4_f1);

        FunctionMinimisationProblem zdt4_f2 = new FunctionMinimisationProblem();
        zdt4_f2.setFunction(new ZDT4_f2());
        zdt4_f2.setDomain(DOMAIN);
        add(zdt4_f2);
    }

    public ZDT4(ZDT4 copy) {
        super(copy);
    }

    @Override
    public ZDT4 getClone() {
        return new ZDT4(this);
    }
}
