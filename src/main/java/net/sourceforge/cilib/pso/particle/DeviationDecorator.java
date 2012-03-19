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
package net.sourceforge.cilib.pso.particle;

import net.sourceforge.cilib.entity.Entity;
import net.sourceforge.cilib.entity.Particle;
import net.sourceforge.cilib.problem.Fitness;
import net.sourceforge.cilib.problem.OptimisationProblem;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 *
 *
 */
public class DeviationDecorator extends ParticleDecorator implements Cloneable {
    private static final long serialVersionUID = -7919347953251165107L;

    private double[][] positions;
    private Fitness[] fitnesses;
    private int index;
    private int observations;
    private boolean enoughObservations;

    public DeviationDecorator(Particle target, int observations) {
        super(target);
        index = 0;
        enoughObservations = false;
        this.observations = observations;
    }

    /**
     * {@inheritDoc}
     */
    public DeviationDecorator getClone() {
        return null;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;

        if ((object == null) || (this.getClass() != object.getClass()))
            return false;

        throw new UnsupportedOperationException("This method is not implemented.");
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("This method is not implemented.");
    }

    /**
     * The initialise method overrides the parent class initialise method. The
     * fitness[] and positions[][] instance fields are instantiated.
     *
     * @param problem
     * @param i
     */
    public void initialise(OptimisationProblem problem) {
        //super.initialise(problem, i);
        index = 0;
        enoughObservations = false;
        fitnesses = new Fitness[observations];
        positions = new double[observations][problem.getDomain().getDimension()];
    }

    /**
     * {@inheritDoc}
     */
    public void updatePosition() {
        ++index;

        if (index == observations) {
            index = 0;
            enoughObservations = true;
        }

        fitnesses[index] = super.getFitness();
        //double[] position = super.getPosition();
        Vector position = (Vector) super.getPosition();

        for (int i = 0; i < super.getDimension(); ++i) {
            //positions[index][i] = position[i];
            positions[index][i] = position.doubleValueOf(i);
        }

        super.updatePosition();
    }

    public double getFitnessDeviation() {
        if (!enoughObservations) {
            return Double.MAX_VALUE;
        }

        double sum = 0;
        double sumsq = 0;

        for (int i = 0; i < observations; ++i) {
            sum += fitnesses[i].getValue().doubleValue();
            double tmp = fitnesses[i].getValue().doubleValue();
            sumsq += tmp * tmp;
        }

        return Math.sqrt((sumsq - (sum * sum) / observations) / (observations - 1));
    }

    public double getPositionDeviation() {
        if (!enoughObservations) {
            return Double.MAX_VALUE;
        }

        double deviation = 0;

        for (int i = 0; i < super.getDimension(); ++i) {
            double sum = 0;
            double sumsq = 0;

            for (int j = 0; j < observations; ++j) {
                sum += positions[j][i];
                sumsq += positions[j][i] * positions[j][i];
            }

            deviation += Math.sqrt((sumsq - (sum * sum) / observations)    / (observations - 1));
        }

        return deviation;
    }

    public void reset() {
        index = 0;
        enoughObservations = false;
        fitnesses = new Fitness[observations];
        positions = new double[observations][getDimension()];
    }

    @Deprecated
    public static DeviationDecorator extract(Particle particle) {
        throw new UnsupportedOperationException("Method has been removed");
    }

    public int compareTo(Entity o) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void calculateFitness() {
        // TODO Auto-generated method stub
    }

}
