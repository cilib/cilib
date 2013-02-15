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
package net.sourceforge.cilib.functions.continuous.dynamic.moo.fda3mod_camara;

import net.sourceforge.cilib.algorithm.AbstractAlgorithm;
import net.sourceforge.cilib.functions.ContinuousFunction;
import net.sourceforge.cilib.problem.FunctionOptimisationProblem;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 * This function is the h function of the FDA3_mod problem defined in the
 * following paper: M. Camara, J. Ortega and F. de Toro. Approaching dynamic
 * multi-objective optimization problems by using parallel evolutionary
 * algorithms, Advances in Multi-Objective Nature Inspired Computing, Studies in
 * Computational Intelligence, vol. 272, pp. 63-86, Springer Berlin/Heidelberg,
 * 2010.
 *
 * @author Marde Greeff
 */
public class FDA3_h implements ContinuousFunction {

    private static final long serialVersionUID = 3845365231540108577L;
    //members
    ContinuousFunction fda3_f;
    ContinuousFunction fda3_g;
    FunctionOptimisationProblem fda3_f_problem;
    FunctionOptimisationProblem fda3_g_problem;

    //setDomain("R(-1, 1)^30")
    
    /**
     * Sets the f1 function with a specified problem.
     * @param problem FunctionOptimisationProblem used for the f1 function.
     */
    public void setFDA3_f(FunctionOptimisationProblem problem) {
        this.fda3_f_problem = problem;
        this.fda3_f = (ContinuousFunction) problem.getFunction();
    }

    /**
     * Returns the problem used to set the f1 function.
     * @return fda3_f_problem FunctionOptimisationProblem used for the f1
     * function.
     */
    public FunctionOptimisationProblem getFDA3_f_problem() {
        return this.fda3_f_problem;
    }

    /**
     * Sets the f1 function that is used in the FDA3 problem without specifying
     * the problem.
     * @param fda3_f ContinuousFunction used for the f1 function.
     */
    public void setFDA3_f(ContinuousFunction fda3_f) {
        this.fda3_f = fda3_f;
    }

    /**
     * Returns the f1 function that is used in the FDA3 problem.
     * @return fda3_f ContinuousFunction used for the f1 function.
     */
    public ContinuousFunction getFDA3_f() {
        return this.fda3_f;
    }

    /**
     * Sets the g function with a specified problem.
     * @param problem FunctionOptimisationProblem used for the g function.
     */
    public void setFDA3_g(FunctionOptimisationProblem problem) {
        this.fda3_g_problem = problem;
        this.fda3_g = (ContinuousFunction) problem.getFunction();
    }

    /**
     * Returns the problem used to set the g function.
     * @return fda3_g_problem FunctionOptimisationProblem used for the g
     * function.
     */
    public FunctionOptimisationProblem getFDA3_g_problem() {
        return this.fda3_g_problem;
    }

    /**
     * Sets the g function that is used in the FDA3 problem without specifying
     * the problem.
     * @param fda3_g ContinuousFunction used for the g function.
     */
    public void setFDA3_g(ContinuousFunction fda3_g) {
        this.fda3_g = fda3_g;
    }

    /**
     * Returns the g function that is used in the FDA3 problem.
     * @return fda3_g ContinuousFunction used for the g function.
     */
    public ContinuousFunction getFDA3_g() {
        return this.fda3_g;
    }

    /**
     * Evaluates the function.
     */
    @Override
    public Double apply(Vector x) {
        int iteration = AbstractAlgorithm.get().getIterations();
        return this.apply(iteration, x);
    }

    /**
     * Evaluates the function for a specific iteration.
     */
    public Double apply(int iteration, Vector x) {
        Vector y = x;
        Vector z = x;

        if (x.size() > 1) {
            y = x.copyOfRange(1, x.size());
            z = x.copyOfRange(0, 1);
        }

        double f = ((FDA3_f1) this.fda3_f).apply(z);
        double g = ((FDA3_g) this.fda3_g).apply(iteration, y);

        double value = 1.0;
        value -= Math.sqrt((double) f / (double) g);

        return value;
    }
}
