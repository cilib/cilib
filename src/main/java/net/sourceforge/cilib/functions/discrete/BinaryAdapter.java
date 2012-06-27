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
package net.sourceforge.cilib.functions.discrete;

import net.sourceforge.cilib.functions.ContinuousFunction;
import net.sourceforge.cilib.functions.Function;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 * Class to convert a binary vector into a continuous vector for optimisation of
 * continous problems by a binary optimiser.
 *
 * This still needs some experimental work though, to verify that it is working
 *
 * @TODO: This doesn't actually make sense...... should rather be a problem that
 * does the needed mapping between spaces.
 *
 */
public class BinaryAdapter implements ContinuousFunction {
    private static final long serialVersionUID = -329657439970469569L;

    private Function<Vector, Number> function;
    private int bitsPerDimension;
    private int precision;

    /**
     * Constructor.
     */
    public BinaryAdapter() {
        bitsPerDimension = 1;
        precision = 0;
    }

    /**
     * Evaluate the {@see net.sourceforge.cilib.type.types.Vector} by
     * decoding the binary vector into a continuous vector and evaluate the results
     * by feeding the result into the wrapped funtion.
     *
     * @param input The {@see net.sourceforge.cilib.type.types.Bit} vector to evaluate
     */
    @Override
    public Double apply(Vector input) {
//        System.out.println("vector: " + input);
        Vector decodedVector = this.decodeBitString(input);
//        System.out.println("decoded: " + decodedVector + " " + decodedVector.size());

        return function.apply(decodedVector).doubleValue();
    }

//    /**
//     *
//     */
//    @Override
//    public Double getMinimum() {
//        return function.getMinimum().doubleValue();
//    }
//
//    /**
//     *
//     */
//    @Override
//    public Double getMaximum() {
//        return function.getMaximum().doubleValue();
//    }

    /**
     * @return Returns the bitsPerDimension.
     */
    public int getBitsPerDimension() {
        return bitsPerDimension;
    }

    /**
     * @param bitsPerDimension The bitsPerDimension to set.
     */
    public void setBitsPerDimension(int bitsPerDimension) {
        if (bitsPerDimension < 0)
            throw new RuntimeException("Cannot set the amount of bits in BinaryAdapter to anything < 0");

        this.bitsPerDimension = bitsPerDimension;
    }


    /**
     * @return Returns the precision.
     */
    public int getPrecision() {
        return precision;
    }


    /**
     * @param precision The precision to set.
     */
    public void setPrecision(int precision) {
        this.precision = precision;
    }


    /**
     * @return Returns the function.
     */
    public Function<Vector, Number> getFunction() {
        return function;
    }

    /**
     * @param function The function to set.
     */
    public void setFunction(Function<Vector, ? extends Number> function) {
        this.function = (Function<Vector, Number>) function;
    }


    /**
     *
     * @param bits
     * @return
     */
    public Vector decodeBitString(Vector bits) {
        Vector.Builder vector = Vector.newBuilder();

        for (int i = 0; i < bits.size();) {
            double tmp = valueOf(bits, i, i+this.bitsPerDimension);
            tmp = transform(tmp);

            vector.add(tmp);
            i += this.bitsPerDimension;
        }

        return vector.build();
    }


    /**
     *
     * @param vector
     * @param i
     * @param j
     * @return
     */
    public double valueOf(Vector vector, int i, int j) {
        double result = 0.0;
        int n = 1;

        for (int counter = j-1; counter >= i; counter--) {
            if (vector.booleanValueOf(counter)) {
                result += n;
            }

            n = n*2;
        }

        return result;
    }

    private double transform(double number) {
        double result = number;

        if (this.precision > 0) {
            int tmp = 1;
            tmp <<= this.bitsPerDimension-1;
            result -= tmp;
            result /= Math.pow(10, this.precision);
        }

        return result;
    }

//    @Override
//    public void setDomain(String representation) {
//        if (!representation.matches("^B\\^.*"))
//            throw new RuntimeException("BinaryAdapter can only accept domain strings in the form: B^?\nWhere ? is the size of the dimension");
//
//        super.setDomain(representation);
//    }
}
