/**
 * Computational Intelligence Library (CIlib) Copyright (C) 2003 - 2010
 * Computational Intelligence Research Group (CIRG@UP) Department of Computer
 * Science University of Pretoria South Africa
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, see <http://www.gnu.org/licenses/>.
 */
package net.sourceforge.cilib.functions.continuous.dynamic.moo.dmop1;

import net.sourceforge.cilib.algorithm.AbstractAlgorithm;
import net.sourceforge.cilib.functions.ContinuousFunction;
import net.sourceforge.cilib.problem.FunctionOptimisationProblem;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 * This function is the f2 function of the DMOP1 problem defined on page 119 in
 * the following article: C-K. Goh and K.C. Tan. A competitive-cooperative
 * coevolutionary paradigm for dynamic multiobjective optimization, IEEE
 * Transactions on Evolutionary Computation, 13(1): 103-127, 2009
 *
 * @author Marde Greeff
 */
public class DMOP1_f2 implements ContinuousFunction {

    private static final long serialVersionUID = 5927570367072540148L;
    //members
    ContinuousFunction dmop1_g;
    ContinuousFunction dmop1_h;
    FunctionOptimisationProblem dmop1_g_problem;
    FunctionOptimisationProblem dmop1_h_problem;

    //Domain = "R(0, 1)^10"
    
    /**
     * Sets the g function with a specified problem.
     * @param problem FunctionOptimisationProblem used for the g function.
     */
    public void setDMOP1_g(FunctionOptimisationProblem problem) {
        this.dmop1_g_problem = problem;
        this.dmop1_g = (ContinuousFunction) problem.getFunction();
    }

    /**
     * Returns the problem used to set the g function.
     * @return dmop1_g_problem FunctionOptimisationProblem used for the g
     * function.
     */
    public FunctionOptimisationProblem getDMOP1_g_problem() {
        return this.dmop1_g_problem;
    }

    /**
     * Sets the g function that is used in the DMOP1 problem without specifying
     * the problem.
     * @param dmop1_g ContinuousFunction used for the g function.
     */
    public void setDMOP1_g(ContinuousFunction dmop1_g) {
        this.dmop1_g = dmop1_g;
    }

    /**
     * Returns the g function that is used in the DMOP1 problem.
     * @return dmop1_g ContinuousFunction used for the g function.
     */
    public ContinuousFunction getDMOP1_g() {
        return this.dmop1_g;
    }

    /**
     * Sets the h function with a specified problem.
     * @param problem FunctionOptimisationProblem used for the h function.
     */
    public void setDMOP1_h(FunctionOptimisationProblem problem) {
        this.dmop1_h_problem = problem;
        this.dmop1_h = (ContinuousFunction) problem.getFunction();
    }

    /**
     * Returns the problem used to set the h function.
     * @return dmop1_h_problem FunctionOptimisationProblem used for the h
     * function.
     */
    public FunctionOptimisationProblem getDMOP1_h_problem() {
        return this.dmop1_h_problem;
    }

    /**
     * Sets the h function that is used in the DMOP1 problem without specifying
     * the problem.
     * @param dmop1_h ContinuousFunction used for the h function.
     */
    public void setDMOP1_h(ContinuousFunction dmop1_h) {
        this.dmop1_h = dmop1_h;
    }

    /**
     * Returns the h function that is used in the DMOP1 problem.
     * @return dmop1_h ContinuousFunction used for the h function.
     */
    public ContinuousFunction getDMOP1_h() {
        return this.dmop1_h;
    }

    /**
     * Evaluates the function.
     */
    @Override
    public Double apply(Vector x) {
        int it = AbstractAlgorithm.get().getIterations();
        return apply(it, x);
    }

    /**
     * Evaluates the function for a specific iteration.
     */
    public Double apply(int iteration, Vector x) {

        Vector y = x.copyOfRange(1, x.size());
        double g = this.dmop1_g.apply(y);
        double h = ((DMOP1_h) this.dmop1_h).apply(iteration, x);

        double value = g * h;

        return value;
    }
}
