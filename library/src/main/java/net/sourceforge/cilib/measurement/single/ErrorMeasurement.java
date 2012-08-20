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
package net.sourceforge.cilib.measurement.single;

import net.sourceforge.cilib.algorithm.Algorithm;
import net.sourceforge.cilib.measurement.Measurement;
import net.sourceforge.cilib.problem.OptimisationProblem;
import net.sourceforge.cilib.type.types.Real;
import net.sourceforge.cilib.type.types.Type;

/**
 * Calculate the error between the current best value of the swarm and the
 * global optimum of the function.
 *
 */
public class ErrorMeasurement implements Measurement {

    private static final long serialVersionUID = 2632671785674388015L;
    private double target;

    @Override
    public String getDomain() {
        return "R";
    }

    public void setTarget(double value) {
        this.target = value;
    }

    @Override
    public Type getValue(Algorithm algorithm) {
        OptimisationProblem problem = algorithm.getOptimisationProblem();
        double error = problem.getFitness(algorithm.getBestSolution().getPosition()).getValue() - target;
        return Real.valueOf(error);
    }

    @Override
    public Measurement getClone() {
        return this;
    }
}
