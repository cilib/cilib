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
package net.sourceforge.cilib.clustering.iterationstrategies;

import net.sourceforge.cilib.algorithm.AbstractAlgorithm;
import net.sourceforge.cilib.clustering.DataClusteringPSO;
import net.sourceforge.cilib.util.changeDetection.ChangeDetectionStrategy;
import net.sourceforge.cilib.util.changeDetection.IterationBasedChangeDetectionStrategy;
import net.sourceforge.cilib.clustering.entity.ClusterParticle;
import net.sourceforge.cilib.entity.Topology;
import net.sourceforge.cilib.problem.boundaryconstraint.BoundaryConstraint;
import net.sourceforge.cilib.type.types.container.CentroidHolder;

/**
 *
 * This class performs 1 iteration of the StandardDataClusteringIterationStrategy followed by a check to see
 * if any changes have occurred in the dataset. If changes have occurred, part of the population 
 * (or the whole population) is re-initialized.
 */
public class ReinitializingDataClusteringIterationStrategy extends SinglePopulationDataClusteringIterationStrategy{
    private SinglePopulationDataClusteringIterationStrategy delegate;
    ChangeDetectionStrategy changeDetectionStrategy;
    
    /*
     * Default constructor for ReinitializingDataClusteringIterationStrategy
     */
    public ReinitializingDataClusteringIterationStrategy() {
        super();
        delegate = new StandardDataClusteringIterationStrategy();
        changeDetectionStrategy = new IterationBasedChangeDetectionStrategy();
    }
    
    /*
     * Copy constructor for ReinitializingDataClusteringIterationStrategy
     */
    public ReinitializingDataClusteringIterationStrategy(ReinitializingDataClusteringIterationStrategy copy) {
        super(copy);
        delegate = copy.delegate;
        changeDetectionStrategy = copy.changeDetectionStrategy;
    }
    
    /*
     * Clone method for ReinitializingDataClusteringIterationStrategy
     */
    @Override
    public ReinitializingDataClusteringIterationStrategy getClone() {
        return new ReinitializingDataClusteringIterationStrategy(this);
    }

    /*
     * Performs an iteration of it's delegate iteration startegy (by default the StandardDataClusteringIterationStrategy).
     * Reinitializes part of, or the whole, population if a change has taken place.
     * @param algorithm The algorithm using this iteration strategy
     */
    @Override
    public void performIteration(DataClusteringPSO algorithm) {
        if(changeDetectionStrategy.detectChange()) {
            reinitializePosition(algorithm.getTopology());
            reinitialized = true;
        }
        
        delegate.setWindow(this.window);
        delegate.performIteration(algorithm);
        
    }
    
    /*
     * Returns the delegate iteration strategy
     * @return delegate The delegate iteration strategy
     */
    public SinglePopulationDataClusteringIterationStrategy getDelegate() {
        return delegate;
    }
    
    /*
     * Sets teh delegate iteration strategy to the one received as a parameter
     * @param newDelegate The new delegate iteration strategy
     */
    public void setDelegate(SinglePopulationDataClusteringIterationStrategy newDelegate){
        delegate = newDelegate;
    }
    
    /*
     * Reinitializes part of, or the whole, population
     * @param topology The population to be reinitialised
     */
    private void reinitializePosition(Topology<ClusterParticle> topology) {
        int index = 0;
        for(int i = index; i < topology.size(); i+=reinitialisationInterval) {
                ((ClusterParticle) topology.get(i)).reinitialise();
                assignDataPatternsToParticle(((CentroidHolder)((ClusterParticle) topology.get(i)).getCandidateSolution()), dataset);
        }
        
    }
    
    /*
     * Sets the boundary constraint of the re-initialization strategy as well as that of its delegate
     * @param boundaryConstraint The constraint to be given to this strategy as well as its delegate
     */
    @Override
    public void setBoundaryConstraint(BoundaryConstraint boundaryConstraint) {
        this.boundaryConstraint = boundaryConstraint;
        delegate.setBoundaryConstraint(boundaryConstraint);
    }
    
    /*
     * Sets the change detection strategy to be used
     * @param changeStrategy The new changeDetectionStrategy
     */
    public void setChangeDetectionStrategy(ChangeDetectionStrategy changeStrategy) {
        changeDetectionStrategy = changeStrategy;
    }
    
    /*
     * Returns the change detection strategy being used
     * @return cahngeDetectionStrategy The current change detection strategy
     */
    public ChangeDetectionStrategy getChangeDetectionStrategy() {
        return changeDetectionStrategy;
    }
}
