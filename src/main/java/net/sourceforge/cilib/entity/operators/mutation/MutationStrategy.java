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
package net.sourceforge.cilib.entity.operators.mutation;

import java.util.List;

import net.sourceforge.cilib.controlparameter.ConstantControlParameter;
import net.sourceforge.cilib.controlparameter.ControlParameter;
import net.sourceforge.cilib.entity.Entity;
import net.sourceforge.cilib.entity.operators.Operator;
import net.sourceforge.cilib.math.ArithmeticOperator;
import net.sourceforge.cilib.math.random.ProbabilityDistributionFuction;
import net.sourceforge.cilib.math.random.UniformDistribution;

/**
 *
 * @author Andries Engelbrecht
 * @author Gary Pampara
 */
public abstract class MutationStrategy implements Operator {

    private static final long serialVersionUID = 6670947597280440404L;
    private ControlParameter mutationProbability;
    private ProbabilityDistributionFuction randomDistribution;
    private String operator;
    private ArithmeticOperator operatorStrategy;

    public MutationStrategy() {
        this.setOperator("+");

        mutationProbability = ConstantControlParameter.of(0.3);
        randomDistribution = new UniformDistribution();
    }

    public MutationStrategy(MutationStrategy copy) {
        this.operator = copy.operator;
        this.operatorStrategy = copy.operatorStrategy;
        this.mutationProbability = copy.mutationProbability.getClone();
        this.randomDistribution = copy.randomDistribution;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract MutationStrategy getClone();

    /**
     * Perform the mutation operation on the provided list of offspring individuals.
     * @param offspringList The list of {@linkplain Entity} instances to perform a
     *                      mutation on.
     */
    public abstract void mutate(List<? extends Entity> offspringList);

    /**
     *
     * @return
     */
    public ControlParameter getMutationProbability() {
        return mutationProbability;
    }

    /**
     *
     * @param mutationProbability
     */
    public void setMutationProbability(ControlParameter mutationProbability) {
        this.mutationProbability = mutationProbability;
    }

    public ProbabilityDistributionFuction getRandomDistribution() {
        return randomDistribution;
    }

    public void setRandomDistribution(ProbabilityDistributionFuction randomNumber) {
        this.randomDistribution = randomNumber;
    }

    public String getOperator() {
        return operator;
    }

    /**
     * This sets the operator to be used within the mutation strategy.
     * The mutation can be multiplicative or additive.
     * Valid values for the operator are defined in the list below.<br>
     * <p>
     * <table border="1">
     * <tr><td>Multiplicative:</td><td>Additive:</td></tr>
     * <tr><td>*</td><td>+</td></tr>
     * <tr><td>times</td><td>plus, add</td></tr>
     * <tr><td>multiplicative</td><td>additive</td></tr>
     * </table>
     *
     * @param operator A {@link java.lang.String} defining the desired operation
     */
    public void setOperator(String operator) {
        this.operator = operator;
        this.operatorStrategy = MutationOperatorFactory.getOperatorStrategy(operator);
    }

    public ArithmeticOperator getOperatorStrategy() {
        return this.operatorStrategy;
    }
}
