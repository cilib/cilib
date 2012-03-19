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
package net.sourceforge.cilib.coevolution;

import net.sourceforge.cilib.coevolution.score.EntityScoreboard;
import net.sourceforge.cilib.entity.Entity;
import net.sourceforge.cilib.entity.EntityType;
import net.sourceforge.cilib.problem.Fitness;
import net.sourceforge.cilib.problem.MaximisationFitness;

/**
 * example of implementation of an FitnessSharingStrategy.
 *
 */
public class StandardFitnessSharingStrategy extends FitnessSharingStrategy{

    /**
     *
     */
    private static final long serialVersionUID = -495910154501231712L;

    public StandardFitnessSharingStrategy() {
    }

    public StandardFitnessSharingStrategy(StandardFitnessSharingStrategy copy) {

    }

    public StandardFitnessSharingStrategy getClone(){
        return new StandardFitnessSharingStrategy(this);
    }

    /**
     * in this case, assign the number of victory to the fitness of the Entity.
     * @return the modified fitness.
     */
    public Fitness modifyFitness(CoevolutionAlgorithm ca, Entity ent){

        //System.out.println("pre fit: " + ent.getFitness().getValue());
        EntityScoreboard board = (EntityScoreboard) ent.getProperties().get(EntityType.Coevolution.BOARD);
        Fitness f = new MaximisationFitness(Integer.valueOf(board.getWinCount()).doubleValue());
                    //*
                    //((Real)( ent.getProperties().get("distance"))).getReal()
                    //0.0
        //System.out.println("dis: " + ((Real)( ent.getProperties().get("distance"))).getReal());
        //System.out.println("f: " + f.getValue());
        ent.getProperties().put(EntityType.FITNESS, f);
        return f;
    }

}
