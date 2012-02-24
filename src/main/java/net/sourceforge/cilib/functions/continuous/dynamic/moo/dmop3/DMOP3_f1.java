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
package net.sourceforge.cilib.functions.continuous.dynamic.moo.dmop3;

import net.sourceforge.cilib.algorithm.AbstractAlgorithm;
import net.sourceforge.cilib.functions.ContinuousFunction;
import net.sourceforge.cilib.math.random.generator.MersenneTwister;
import net.sourceforge.cilib.math.random.generator.RandomProvider;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 * This function is the f1 function of the DMOP3 problem defined on page 119 in the following article:
 * C-K. Goh and K.C. Tan. A competitive-cooperative coevolutionary paradigm for dynamic multiobjective 
 * optimization, IEEE Transactions on Evolutionary Computation, 13(1): 103-127, 2009
 * 
 * @author Marde Greeff
 */

public class DMOP3_f1 implements ContinuousFunction {

	private static final long serialVersionUID = 4525717945098304120L;
	
	//members
	//index of member to select - index starts at 0
	private static int r;
	RandomProvider randomiser;
	//number of generations for which t remains fixed 
	private int tau_t;
	private static int rIteration;

	/**
	 * Creates a new instance of DMOP3_f1.
	 */
	public DMOP3_f1 () {
		DMOP3_f1.r = -1;
                DMOP3_f1.rIteration = -1;
		randomiser = new MersenneTwister();
                tau_t = 5;
	}
	
	
	/**
	 * Sets which x to set as f1's value.
	 * @param r Index of x value to select.
	 */
	public static void setR(int r){
		DMOP3_f1.r = r;
	}
	
	/**
	 * Returns which x to set as f1's value.
	 * @return r Index of x value to select.
	 */
	public static int getR() {
		return DMOP3_f1.r;
	}
	
    /**
     * Sets the iteration when r was set.
     * @param rIteration Iteration when r was last randmomly selected.
     */
    public static void setRIteration(int r){
        DMOP3_f1.rIteration = r;
    }

    /**
     * Returns the iteration when r was set.
     * @return rIteration Iteration when r was set.
     */
    public static int getRIteration() {
        return DMOP3_f1.rIteration;
    }

	/**
	 * Returns the random number generator.
	 * @return randomiser Random number generator.
	 */
	public RandomProvider getRandomiser() {
		return this.randomiser;
	}
	
	/**
	 * Sets the random number generator.
	 * @param random Random number generator.
	 */
	public void setRandomiser(RandomProvider random) {
		this.randomiser = random;
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
	 * Evaluates the function.
	 */
    @Override
	public Double apply(Vector x) {
		int iteration  = AbstractAlgorithm.get().getIterations();
		return this.apply(iteration, x);
	}
	
	/**
	 * Evaluates the function for a specific iteration.
	 */
	public Double apply(int iteration, Vector x) {
		/*System.out.println("iteration = " + iteration);
        System.out.println("tau-t = " + this.tau_t);
        System.out.println("r before = " + DMOP3_f1.r);*/

            
            if ( ((iteration % this.tau_t) == 0) && (DMOP3_f1.rIteration != iteration)) {
			int indeks = this.getRandomiser().nextInt(x.size()-1);
			DMOP3_f1.setR(indeks);
                        DMOP3_f1.setRIteration(iteration);
            //System.out.println("new r = " + DMOP3_f1.r + " at iteration " + DMOP3_f1.rIteration);
		}
            //sets r to a random value
            else if(DMOP3_f1.r == -1)  {
			int indeks = this.getRandomiser().nextInt(x.size()-1);
			DMOP3_f1.setR(indeks);
                        DMOP3_f1.setRIteration(iteration);
            //System.out.println("first r = " + DMOP3_f1.r + " at iteration " + DMOP3_f1.rIteration);
		}
		double value = Math.abs(x.doubleValueOf(DMOP3_f1.r));
		return value;
		
	}
}
