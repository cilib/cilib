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
package net.sourceforge.cilib.functions.continuous.decorators;

import net.sourceforge.cilib.problem.Fitness;
import net.sourceforge.cilib.problem.FunctionOptimisationProblem;
import net.sourceforge.cilib.problem.OptimisationProblemAdapter;
import net.sourceforge.cilib.type.DomainRegistry;
import net.sourceforge.cilib.type.StringBasedDomainRegistry;
import net.sourceforge.cilib.type.types.Real;
import net.sourceforge.cilib.type.types.Type;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 * A Decorator pattern class to wrap a normal function to perform Angle Modulation.
 *
 * The deault values for angle modulation are:
 * <p>
 * <ul>
 *   <li>domain = "R(-1.0,1.0)^4"</li>
 *   <li>precision = 3</li>
 * </ul>
 * </p>
 *
 */
public class AngleModulation extends OptimisationProblemAdapter {

    private static final long serialVersionUID = -3492262439415251355L;
    private int precision;
    private int bitsPerDimension;
    private double lowerBound;
    private double upperBound;
    private FunctionOptimisationProblem delegate;
    private DomainRegistry domainRegistry;

    public AngleModulation() {
        precision = 3;
        bitsPerDimension = 0;
        domainRegistry = new StringBasedDomainRegistry();

        domainRegistry.setDomainString("R(-1.0:1.0)^4");
    }

    public AngleModulation(AngleModulation copy) {
//        setDomain(copy.getDomain());
        this.precision = copy.precision;
        this.bitsPerDimension = copy.bitsPerDimension;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AngleModulation getClone() {
        return new AngleModulation(this);
    }

//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Double evaluate(Vector input) {
//        String solution = generateBitString(input, bitsPerDimension);
//        Vector expandedVector = decodeBitString(solution, bitsPerDimension);
//        return function.evaluate(expandedVector).doubleValue();
//    }

    /**
     *
     * @return
     */
    public int getPrecision() {
        return this.precision;
    }

    /**
     *
     * @param precision
     */
    public void setPrecision(int precision) {
        if (precision < 0) {
            throw new ArithmeticException("Presicion values must be >= 0");
        }

        this.precision = precision;
    }

    /**
     *
     * @return
     */
//    public Function getFunction() {
//        return this.function;
//    }

    /**
     *
     * @param decoratedFunciton
     */
//    public void setFunction(Function<Vector, ? extends Number> decoratedFunciton) {
//        this.function = decoratedFunciton;
//        bitsPerDimension = getRequiredNumberOfBits(function.getDomainRegistry());
//    }

    public void setProblem(FunctionOptimisationProblem problem) {
        this.delegate = problem;
        bitsPerDimension = getRequiredNumberOfBits(delegate.getDomain());
    }

    public FunctionOptimisationProblem getProblem() {
        return delegate;
    }

    /**
     * @TODO: This needs to use an API for domain string manipulation
     * @param domain
     * @return
     */
    public int getRequiredNumberOfBits(DomainRegistry domain) {
        if (domain.getDomainString().contains("B")) {
            return 1;
        } else {
            String range = domain.getDomainString();

            // now remove all the irrelevant details from the domain provided
            range = range.substring(range.indexOf('(') + 1);
            range = range.substring(0, range.indexOf(')'));

            String[] bounds = range.split(":");
            lowerBound = Double.valueOf(bounds[0]).doubleValue();
            upperBound = Double.valueOf(bounds[1]).doubleValue();

            double greaterRange = Math.abs(lowerBound) + Math.abs(upperBound);
            double expandedRange = greaterRange * Math.pow(10, getPrecision());

            return Double.valueOf(Math.ceil(Math.log(expandedRange) / Math.log(2.0))).intValue();
        }

    }

    /**
     * @TODO: Change this to use something better than a string
     * @TODO: complete this method
     *
     * @param x
     * @param dimensionBitNumber 
     * @return
     */
    public String generateBitString(Vector x, int dimensionBitNumber) {
        double a = x.doubleValueOf(0);
        double b = x.doubleValueOf(1);
        double c = x.doubleValueOf(2);
        double d = x.doubleValueOf(3);

        StringBuilder str = new StringBuilder();

        for (int i = 0; i < dimensionBitNumber * delegate.getDomain().getDimension(); i++) {
            double result = Math.sin(2 * Math.PI * (i - a) * b * Math.cos(2 * Math.PI * c * (i - a))) + d;

            if (result > 0.0) {
                str.append('1');
            } else {
                str.append('0');
            }
        }

        return str.toString();
    }

    /**
     *
     * @param bits
     * @param dimensionBits
     * @return
     */
    public Vector decodeBitString(String bits, int dimensionBits) {
        Vector vector = new Vector();

        for (int i = 0; i < bits.length();) {
            double tmp = valueOf(bits, i, i + dimensionBits);
            tmp = transform(tmp);

            vector.add(Real.valueOf(tmp));
            i += dimensionBits;
        }

        return vector;
    }

    /**
     * Determine the numeric value of the given bitstring.
     *
     * TODO: Move this into a class that will make sense.
     *
     * @param bitString The bitsting as a string
     * @param startIndex The starting index
     * @param endIndex The ending index
     * @return The value of the bitstring
     */
    public double valueOf(String bitString, int startIndex, int endIndex) {
        double result = 0.0;

        String substring = bitString.substring(startIndex, endIndex);
        result = Integer.valueOf(substring, 2).intValue();

        return result;
    }

    public double valueOf(String bitString, int index) {
        String substring = bitString.substring(index);
        return Integer.valueOf(substring, 2).intValue();
    }

    public double valueOf(String bitString) {
        return Integer.valueOf(bitString, 2).intValue();
    }

    /**
     *
     * @param number
     * @return
     */
    private double transform(double number) {
        double result = number;

        int tmp = 1;
        tmp <<= this.bitsPerDimension - 1;
        result -= tmp;
        result /= Math.pow(10, getPrecision());

        return result;
    }

    @Override
    public DomainRegistry getDomain() {
        return domainRegistry;
    }

    @Override
    protected Fitness calculateFitness(Type solution) {
        String bitString = generateBitString((Vector) solution,bitsPerDimension);
        Vector expandedVector = decodeBitString(bitString, bitsPerDimension);
        return delegate.getFitness(expandedVector);
    }
}
