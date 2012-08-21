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
package net.sourceforge.cilib.problem.boundaryconstraint;

import net.sourceforge.cilib.entity.Entity;
import net.sourceforge.cilib.entity.EntityType;
import net.sourceforge.cilib.pso.particle.StandardParticle;
import net.sourceforge.cilib.type.types.container.CentroidHolder;
import net.sourceforge.cilib.type.types.container.ClusterCentroid;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 * This is a wrapper class. It enforces the Boundary Constraint chosen by the user on each 
 * ClusterCentroid held by a CentroidHolder
 */
public class CentroidBoundaryConstraint implements BoundaryConstraint{

    BoundaryConstraint delegate;
    
    /*
     * Default constructor for CentroidBoundaryConstraint
     */
    public CentroidBoundaryConstraint() {
        delegate = new UnconstrainedBoundary();
    }
    
    /*
     * Copy constructor for CentroidBoundaryConstraint
     */
    public CentroidBoundaryConstraint(CentroidBoundaryConstraint copy) {
        delegate = copy.delegate;
    }
    
    /*
     * Clone method for CentroidBoundaryConstraint
     * @return new instance of the CentroidBoundaryConstraint
     */
    @Override
    public BoundaryConstraint getClone() {
        return new CentroidBoundaryConstraint(this);
    }

    /*
     * Enforces the delegate's boundary constraint on each ClusterCentroid held by the CentoifHolder of teh entity
     * @param entity The entity to be bound constrainded
     */
    @Override
    public void enforce(Entity entity) {
        //System.out.println("Class: " + entity.getCandidateSolution().getClass().toString() + ", " + entity.getCandidateSolution());
        CentroidHolder holder = (CentroidHolder) entity.getCandidateSolution().getClone();
        CentroidHolder velocity = (CentroidHolder) entity.getProperties().get(EntityType.Particle.VELOCITY).getClone();
        CentroidHolder bestPosition = (CentroidHolder) entity.getProperties().get(EntityType.Particle.BEST_POSITION).getClone();
        CentroidHolder newSolution = new CentroidHolder();
        
        int index = 0;
        for(ClusterCentroid centroid : holder) {
            StandardParticle newParticle = new StandardParticle();
            newParticle.setCandidateSolution(centroid.toVector());
            newParticle.getProperties().put(EntityType.Particle.VELOCITY, velocity.get(index).toVector());
            newParticle.getProperties().put(EntityType.Particle.BEST_POSITION, bestPosition.get(index).toVector());
            
            delegate.enforce(newParticle);
            ClusterCentroid centr = new ClusterCentroid();
            centr.copy((Vector) newParticle.getCandidateSolution());
            newSolution.add(centr);
            index++;
        }
        entity.setCandidateSolution(newSolution);
    }
    
    /*
     * Sets teh delagate BoundaryConstraint
     * @param constraint The BoundaryConstraint to be enforced
     */
    public void setDelegate(BoundaryConstraint constraint) {
        delegate = constraint;
    }
    
}
