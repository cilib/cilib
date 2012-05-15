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
package net.sourceforge.cilib.pso.iterationstrategies;

import com.google.common.base.Supplier;
import com.google.common.collect.Lists;
import java.util.List;
import net.sourceforge.cilib.algorithm.population.AbstractIterationStrategy;
import net.sourceforge.cilib.controlparameter.ConstantControlParameter;
import net.sourceforge.cilib.controlparameter.ControlParameter;
import net.sourceforge.cilib.entity.EntityType;
import net.sourceforge.cilib.entity.Particle;
import net.sourceforge.cilib.entity.Topologies;
import net.sourceforge.cilib.entity.Topology;
import net.sourceforge.cilib.entity.comparator.SocialBestFitnessComparator;
import net.sourceforge.cilib.entity.operators.crossover.CrossoverStrategy;
import net.sourceforge.cilib.entity.operators.crossover.MultiParentCrossoverStrategy;
import net.sourceforge.cilib.math.random.CauchyDistribution;
import net.sourceforge.cilib.math.random.ProbabilityDistributionFuction;
import net.sourceforge.cilib.pso.PSO;
import net.sourceforge.cilib.type.types.Bounds;
import net.sourceforge.cilib.type.types.Numeric;
import net.sourceforge.cilib.type.types.container.Vector;
import net.sourceforge.cilib.util.selection.Samples;

/**
 * 
 * @INPROCEEDINGS{4668059,
 * author={Hui Wang and Zhijian Wu and Yong Liu and Sanyou Zeng},
 * booktitle={Natural Computation, 2008. ICNC '08. Fourth International Conference on}, title={Particle Swarm Optimization with a Novel Multi-Parent Crossover Operator},
 * year={2008},
 * month={oct.},
 * volume={7},
 * number={},
 * pages={664 -668},
 * keywords={advanced evolutionary computation techniques;evolutionary algorithms;hybrid PSO algorithm;multi-parent crossover operator;multimodal optimization problems;particle swarm optimization;self-adaptive Cauchy mutation operator;evolutionary computation;particle swarm optimisation;},
 * doi={10.1109/ICNC.2008.643},
 * ISSN={},}
 */
public class MultiParentCrossoverIterationStrategy extends AbstractIterationStrategy<PSO> {
    
    private CrossoverStrategy crossoverStrategy;
    private ControlParameter vMax;
    private ProbabilityDistributionFuction distribution;
    
    public MultiParentCrossoverIterationStrategy() {
        this.crossoverStrategy = new MultiParentCrossoverStrategy();
        this.crossoverStrategy.setCrossoverProbability(ConstantControlParameter.of(0.8));
        
        this.vMax = ConstantControlParameter.of(1.0);        
        this.distribution = new CauchyDistribution();
    }
    
    public MultiParentCrossoverIterationStrategy(MultiParentCrossoverIterationStrategy copy) {
        this.crossoverStrategy = copy.crossoverStrategy.getClone();
        this.distribution = copy.distribution;
        this.vMax = copy.vMax.getClone();
    }

    @Override
    public AbstractIterationStrategy<PSO> getClone() {
        return new MultiParentCrossoverIterationStrategy(this);
    }

    @Override
    public void performIteration(PSO algorithm) {
        Topology<Particle> topology = algorithm.getTopology();
        
        // pos/vel update
        for (Particle current : topology) {
            current.updateVelocity();
            current.updatePosition();

            boundaryConstraint.enforce(current);
            
            current.calculateFitness();
        }

        // crossover
        for (int i = 0; i < topology.size(); i++) {
            Particle p = topology.get(i);
            if (crossoverStrategy.getRandomDistribution().getRandomNumber() < crossoverStrategy.getCrossoverProbability().getParameter()) {
                List<Particle> parents = crossoverStrategy.getSelectionStrategy().on(topology).select(Samples.first(3));
                parents.add(p);
                parents = Lists.reverse(parents);
                Particle offspring = (Particle) crossoverStrategy.crossover(parents).get(0);
                offspring.calculateFitness();
                offspring.setNeighbourhoodBest(p.getNeighbourhoodBest());
                
                if (offspring.compareTo(p) > 0) {
                    topology.set(i, offspring);
                }
            }
        }
        
        // update neighbourhood
        for (Particle p : topology) {
            Particle nBest = Topologies.getNeighbourhoodBest(topology, p, new SocialBestFitnessComparator());
            p.setNeighbourhoodBest(nBest);
        }
        
        // calculate vAvg
        Vector avgV = (Vector) topology.get(0).getVelocity();
        for (int i = 1; i < topology.size(); i++) {
            Vector v = (Vector) topology.get(1).getVelocity();
            avgV.plus(v);
        }
        
        avgV.divide(topology.size());
        
        Vector.Builder builder = Vector.newBuilder();
        for (Numeric n : avgV) {
            if (Math.abs(n.doubleValue()) > vMax.getParameter()) {
                builder.add(vMax.getParameter());
            } else {
                builder.add(n);
            }
        }
        
        avgV = builder.build();
        
        // mutation
        Particle gBest = Topologies.getBestEntity(topology, new SocialBestFitnessComparator());
        Particle mutated = gBest.getClone();
        Vector pos = (Vector) gBest.getBestPosition();
        final Bounds bounds = pos.boundsOf(0);
        
        pos = pos.plus(avgV.multiply(new Supplier<Number>() {
            @Override
            public Number get() {
                return distribution.getRandomNumber()*bounds.getRange() + bounds.getLowerBound();
            }
        }));
        
        mutated.setCandidateSolution(pos);
        mutated.calculateFitness();
        
        if (gBest.getBestFitness().compareTo(mutated.getFitness()) < 0) {
            gBest.getProperties().put(EntityType.Particle.BEST_FITNESS, mutated.getBestFitness());
            gBest.getProperties().put(EntityType.Particle.BEST_POSITION, mutated.getBestPosition());
        }
    }

    public void setVMax(ControlParameter vMax) {
        this.vMax = vMax;
    }

    public ControlParameter getVMax() {
        return vMax;
    }

    public void setDistribution(ProbabilityDistributionFuction distribution) {
        this.distribution = distribution;
    }

    public ProbabilityDistributionFuction getDistribution() {
        return distribution;
    }

    public void setCrossoverStrategy(CrossoverStrategy crossoverStrategy) {
        this.crossoverStrategy = crossoverStrategy;
    }

    public CrossoverStrategy getCrossoverStrategy() {
        return crossoverStrategy;
    }    
}
