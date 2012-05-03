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
package net.sourceforge.cilib.functions.continuous.hybrid;

import net.sourceforge.cilib.functions.ContinuousFunction;
import net.sourceforge.cilib.functions.continuous.decorators.RotatedFunctionDecorator;
import net.sourceforge.cilib.type.types.container.Vector;
import net.sourceforge.cilib.util.Sequence;

/**
 * This is a container class to store information about individual functions used
 * in hybrid composite functions of the CEC2005 benchmark functions. Rotation and 
 * shifting is done through here rather than using separate decorator classes.
 * <p>
 * Parameters that must be set:
 * </p>
 * <ul>
 * <li>sigma</li>
 * <li>lambda: scaling factor</li>
 * <li>horizontalShift: shifting of the optimum</li>
 * <li>bias: vertical shifting</li>
 * <li>function: the optimization function that makes up the overall function</li>
 * </ul>
 * <p>
 * When adding functions to the HybridCompositionFunction make sure the horizontalShift
 * </p>
 * <p>
 * Reference:
 * </p>
 * <p>
 * Suganthan, P. N., Hansen, N., Liang, J. J., Deb, K., Chen, Y., Auger, A., and Tiwari, S. (2005).
 * Problem Definitions and Evaluation Criteria for the CEC 2005 Special Session on Real-Parameter Optimization.
 * Natural Computing, 1-50. Available at: http://vg.perso.eisti.fr/These/Papiers/Bibli2/CEC05.pdf.
 * </p>
 */
public class SingleFunction implements ContinuousFunction {
    private ContinuousFunction function;
    private RotatedFunctionDecorator rotationFunction;
    private double sigma;
    private double weight;
    private double lambda;
    private double horizontalShift;
    private double fmax;
    private double bias;
    private Vector shifted; //A temporary vector to hold the shifted input
    private boolean initialized;
    
    /**
     * Default constructor.
     */
    public SingleFunction() {
        this.initialized = false;
        this.rotationFunction = new RotatedFunctionDecorator();
        this.sigma = 1.0;
        this.lambda = 1.0;
        this.horizontalShift = 0.0;
        this.bias = 0.0;
    }

    /*
     * Getters and setters for the parameters
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }

    public double getSigma() {
        return sigma;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    public double getLambda() {
        return lambda;
    }

    public void setFunction(ContinuousFunction function) {
        this.function = function;
        this.rotationFunction.setFunction(function);
    }

    public ContinuousFunction getFunction() {
        return function;
    }

    public void setHorizontalShift(double horizontalShift) {
        this.horizontalShift = horizontalShift;
    }

    public double getHorizontalShift() {
        return horizontalShift;
    }

    public double getBias() {
        return bias;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public void setfMax(double fmax) {
        this.fmax = fmax;
    }

    public double getfMax() {
        return fmax;
    }    

    public void setShifted(Vector shifted) {
        this.shifted = shifted;
    }

    public Vector getShifted() {
        return shifted;
    }
    
    /**
     * Sets the rotation matrix type.
     * @param type Either "identity" or "orthonormal"
     */
    public void setMatrixType(String type) {
        rotationFunction.setMatrixType(type);
    }
    
    /**
     * Sets the condition for the linear transformation matrix if it's used.
     * @param condition The condition of the matrix.
     */
    public void setCondition(int condition) {
        rotationFunction.setCondition(condition);
    }
    
    /**
     * Shifts the input vector.
     * @param input 
     */
    public void shift(Vector input) {
        setShifted(input.subtract(Vector.copyOf(Sequence.repeat(horizontalShift, input.size()))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double apply(Vector input) {
        //need to get input's size to set fMax
        if (!initialized) {
            setfMax(Math.abs(rotationFunction.apply(Vector.copyOf(Sequence.repeat(5.0, input.size())).divide(lambda))));
            initialized = true;
        }

        return rotationFunction.apply(shifted.divide(lambda)) / getfMax();
    }
}
