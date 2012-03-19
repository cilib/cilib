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
package net.sourceforge.cilib.functions.continuous.dynamic.moo.fda1;

import net.sourceforge.cilib.functions.ContinuousFunction;
import net.sourceforge.cilib.type.types.container.Vector;
import net.sourceforge.cilib.problem.FunctionMinimisationProblem;

/**
 * This function is the h function of the FDA1 problem defined on page 428 in the following paper:
 * M.Farina, K.Deb, P.Amato. Dynamic multiobjective optimization problems: test cases, approximations
 * and applications, IEEE Transactions on Evolutionary Computation, 8(5): 425-442, 2003
 *
 */
public class FDA1_h implements ContinuousFunction {

    private static final long serialVersionUID = -539665464941830813L;
    //members
    private ContinuousFunction fda1_g;
    private ContinuousFunction fda1_f1;
    private FunctionMinimisationProblem fda1_f1_problem;
    private FunctionMinimisationProblem fda1_g_problem;

    /**
     * Default constructor
     */
    public FDA1_h() {
        super();
    }

    /**
     * Copy constructor
     */
    public FDA1_h(FDA1_h copy) {
//        this.setDomain(copy.getDomain());
        this.setFDA1_g(copy.getFDA1_g_problem());
        this.setFDA1_g(copy.getFDA1_g());
        this.setFDA1_f(copy.getFDA1_f_problem());
        this.setFDA1_f(copy.getFDA1_f());
    }

    /**
     * Sets the g function that is used in FDA1 problem
     * @param problem
     */
    public void setFDA1_g(FunctionMinimisationProblem problem) {
        this.fda1_g_problem = problem;
        this.fda1_g = (ContinuousFunction) problem.getFunction();
//        this.setDomain(fda1_g.getDomainRegistry().getDomainString());
    }

    /**
     * returns the problem used to set the g function
     * @return
     */
    public FunctionMinimisationProblem getFDA1_g_problem() {
        return this.fda1_g_problem;
    }

    /**
     * Sets the g function that is used in the FDA1 problem
     * @param fda1_g
     */
    public void setFDA1_g(ContinuousFunction fda1_g) {
        this.fda1_g = fda1_g;
//        this.setDomain(fda1_g.getDomainRegistry().getDomainString());
    }

    /**
     * Returns the g function that is used in the FDA1 problem
     * @return
     */
    public ContinuousFunction getFDA1_g() {
        return this.fda1_g;
    }

    /**
     * Sets the f1 function that is used in FDA1 problem
     * @param problem
     */
    public void setFDA1_f(FunctionMinimisationProblem problem) {
        this.fda1_f1_problem = problem;
        this.fda1_f1 = (ContinuousFunction) problem.getFunction();
//        this.setDomain(fda1_f1.getDomainRegistry().getDomainString());
    }

    /**
     * returns the problem used to set the f1 function
     * @return
     */
    public FunctionMinimisationProblem getFDA1_f_problem() {
        return this.fda1_f1_problem;
    }

    /**
     * Sets the f1 function that is used in the FDA1 problem
     * @param fda1_f1
     */
    public void setFDA1_f(ContinuousFunction fda1_f1) {
        this.fda1_f1 = fda1_f1;
//        this.setDomain(fda1_f1.getDomainRegistry().getDomainString());
    }

    /**
     * Gets the f1 function that is used in the FDA1 problem
     * @return
     */
    public ContinuousFunction getFDA1_f() {
        return this.fda1_f1;
    }

    /**
     * Evaluates the function
     * h(f_1, g) = 1-sqrt(f_1/g)
     */
    @Override
    public Double apply(Vector input) {

        //only the first element
        Vector y = input.copyOfRange(0, 1);
        //all the elements except the first element
        Vector z = input.copyOfRange(1, input.size());
        //evaluate the fda1_g function
        double g = this.fda1_g.apply(z);
        //evaluate the fda1_f1 function
        double f1 = this.fda1_f1.apply(y);

        double value = 1.0;
        value -= Math.sqrt(f1 / g);

        return value;
    }
}
