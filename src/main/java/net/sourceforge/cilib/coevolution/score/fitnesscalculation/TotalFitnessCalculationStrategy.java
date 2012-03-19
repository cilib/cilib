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
package net.sourceforge.cilib.coevolution.score.fitnesscalculation;

import java.util.ArrayList;

import net.sourceforge.cilib.coevolution.score.EntityScoreboard;
import net.sourceforge.cilib.problem.Fitness;

/**
 * the fitness is the total Fitness over all the fitness values attained in each round
 */
public class TotalFitnessCalculationStrategy extends FitnessCalculationStrategy {

	/**
	 *
	 */
	private static final long serialVersionUID = -7283990410867858837L;

	/**
	 *
	 */
	public TotalFitnessCalculationStrategy() {
	}


	public TotalFitnessCalculationStrategy(FitnessCalculationStrategy other) {
		super(other);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Fitness calculateFitnessFromScoreBoard(EntityScoreboard score,
			int currentRound) {
		ArrayList<Fitness> values = new ArrayList<Fitness>();
		values.addAll(score.getScores(currentRound));
		//get the ave
		double total = 0.0;
		for(Fitness val: values){
			total += val.getValue().doubleValue();
		}
		//set the value to the new fitness
		return values.get(0).newInstance(new Double(total));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getClone() {
		return new TotalFitnessCalculationStrategy(this);
	}

}
