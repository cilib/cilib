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
package net.sourceforge.cilib.functions.continuous.dynamic.moo.dmop2;

import net.sourceforge.cilib.algorithm.AbstractAlgorithm;
import net.sourceforge.cilib.functions.ContinuousFunction;
import net.sourceforge.cilib.problem.FunctionMinimisationProblem;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 * This function is the h function of the DMOP2 problem defined on page 119 in the following article:
 * C-K. Goh and K.C. Tan. A competitive-cooperative coevolutionary paradigm for dynamic multiobjective 
 * optimization, IEEE Transactions on Evolutionary Computation, 13(1): 103-127, 2009
 * 
 * @author Marde Greeff
 */

public class DMOP2_h implements ContinuousFunction {

	private static final long serialVersionUID = -1000902144834651314L;
	
	//members
	//number of generations for which t remains fixed 
	private int tau_t;
	//generation counter
	private int tau;
	//number of distinct steps in t
	private int n_t;
	//functions
	private ContinuousFunction dmop2_g;
	private ContinuousFunction dmop2_f1;
	private FunctionMinimisationProblem dmop2_f1_problem;
	private FunctionMinimisationProblem dmop2_g_problem;
			
	/**
	 * Creates a new instance of DMOP2_h
	 */
	public DMOP2_h() {
		//initialize the members
		this.tau_t =  5;
		this.tau = 1;
		this.n_t = 10;
	}		
	
	/**
	 * Sets the iteration number.
	 * @param tau Iteration number.
	 */
	public void setTau(int tau) {
		this.tau = tau;
	}
	
	/**
	 * Returns the iteration number.
	 * @return tau Iteration number.
	 */
	public int getTau() {
		return this.tau;
	}
	
	/**
	 * Sets the frequency of change.
	 * @param tau Change frequency.
	 */
	public void setTau_t(int tau_t) {
		this.tau_t = tau_t;
	}
	
	/**
	 * Returns the frequency of change.
	 * @return tau_t Change frequency.
	 */
	public int getTau_t() {
		return this.tau_t;
	}
	
	/**
	 * Sets the severity of change.
	 * @param n_t Change severity.
	 */
	public void setN_t(int n_t) {
		this.n_t = n_t;
	}
	
	/**
	 * Returns the severity of change.
	 * @return n_t Change severity.
	 */
	public int getN_t() {
		return this.n_t;
	}
	
	/**
	 * Sets the g function with a specified problem.
	 * @param problem FunctionMinimisationProblem used for the g function.
	 */
	public void setDMOP2_g(FunctionMinimisationProblem problem) {
		this.dmop2_g_problem = problem;
		this.dmop2_g = (ContinuousFunction)problem.getFunction();
	}
	
	/**
	 * Returns the problem used to set the g function.
	 * @return dmop2_g_problem FunctionMinimisationProblem used for the g function.
	 */
	public FunctionMinimisationProblem getDMOP2_g_problem() {
		return this.dmop2_g_problem;
	}
	
	/**
	 * Sets the g function that is used in the DMOP2 problem without specifying the problem.
	 * @param dmop2_g ContinuousFunction used for the g function.
	 */
	public void setDMOP2_g(ContinuousFunction dmop2_g) {
		this.dmop2_g = dmop2_g;
	}
	
	/**
	 * Returns the g function that is used in the DMOP2 problem.
	 * @return dmop2_g ContinuousFunction used for the g function.
	 */
	public ContinuousFunction getDMOP2_g() {
		return this.dmop2_g;
	}
	
	/**
	 * Sets the f1 function with a specified problem.
	 * @param problem FunctionMinimisationProblem used for the f1 function.
	 */
	public void setDMOP2_f(FunctionMinimisationProblem problem) {
		this.dmop2_f1_problem = problem;
		this.dmop2_f1 = (ContinuousFunction)problem.getFunction();
	}
	
	/**
	 * Returns the problem used to set the f1 function.
	 * @return dmop2_f1_problem FunctionMinimisationProblem used for the f1 function.
	 */
	public FunctionMinimisationProblem getDMOP2_f_problem() {
		return this.dmop2_f1_problem;
	}
	
	/**
	 * Sets the f1 function that is used in the DMOP2 problem without specifying the problem.
	 * @param dmop2_f1 ContinuousFunction used for the f1 function.
	 */
	public void setDMOP2_f(ContinuousFunction dmop2_f1) {
		this.dmop2_f1 = dmop2_f1;
	}
	
	/**
	 * Returns the f1 function that is used in the DMOP2 problem.
	 * @return dmop2_f1 ContinuousFunction used for the f1 function.
	 */
	public ContinuousFunction getDMOP2_f() {
		return this.dmop2_f1;
	}
	
	/**
	 * Evaluates the function.
	 */
	public Double apply(Vector x) {
		this.tau = AbstractAlgorithm.get().getIterations();
		return apply(this.tau, x);
	}
	
	/**
	 * Evaluates the function for a specific iteration.
	 */
	public Double apply(int iteration, Vector x) {
		double t = (1.0/(double)n_t)*Math.floor((double)iteration/(double)this.tau_t); 
		double H = 0.75*Math.sin(0.5*Math.PI*t)+1.25;
		
		//only the first element
		Vector y = x.copyOfRange(0, 1);
		//all the elements except the first element
		Vector z = x.copyOfRange(1, x.size());
		//evaluate the fda1_g function
		double g = ((DMOP2_g)this.dmop2_g).apply(iteration, z);
		//evaluate the fda1_f1 function
		double f1 = this.dmop2_f1.apply(y);
		
		double sum = 1.0;
		sum -= Math.pow(((double)f1 / (double)g), H);
		
		return sum;
	}
}
