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
package net.sourceforge.cilib.clustering;

import java.util.ArrayList;
import junit.framework.Assert;
import net.sourceforge.cilib.algorithm.initialisation.DataDependantPopulationInitializationStrategy;
import net.sourceforge.cilib.algorithm.initialisation.PopulationInitialisationStrategy;
import net.sourceforge.cilib.clustering.entity.ClusterParticle;
import net.sourceforge.cilib.clustering.iterationstrategies.SinglePopulationDataClusteringIterationStrategy;
import net.sourceforge.cilib.measurement.generic.Iterations;
import net.sourceforge.cilib.problem.QuantizationErrorMinimizationProblem;
import net.sourceforge.cilib.stoppingcondition.Maximum;
import net.sourceforge.cilib.stoppingcondition.MeasuredStoppingCondition;
import net.sourceforge.cilib.algorithm.population.IterationStrategy;
import net.sourceforge.cilib.clustering.iterationstrategies.StandardDataClusteringIterationStrategy;
import net.sourceforge.cilib.entity.EntityType;
import net.sourceforge.cilib.problem.MinimisationFitness;
import net.sourceforge.cilib.problem.OptimisationSolution;
import net.sourceforge.cilib.problem.boundaryconstraint.CentroidBoundaryConstraint;
import net.sourceforge.cilib.problem.boundaryconstraint.RandomBoundaryConstraint;
import net.sourceforge.cilib.type.types.container.CentroidHolder;
import net.sourceforge.cilib.type.types.container.ClusterCentroid;
import net.sourceforge.cilib.type.types.container.Vector;
import net.sourceforge.cilib.util.EuclideanDistanceMeasure;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Kristina
 */
public class CooperativePSOTest {
    
    public CooperativePSOTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of algorithmIteration method, of class CooperativePSO.
     */
    @Test
    public void testAlgorithmIteration() {
        System.out.println("algorithmIteration");
        DataClusteringPSO instance = new DataClusteringPSO();
        
        QuantizationErrorMinimizationProblem problem = new QuantizationErrorMinimizationProblem();
        problem.setDomain("R(-5.12:5.12)");
        IterationStrategy strategy = new StandardDataClusteringIterationStrategy();
        CentroidBoundaryConstraint constraint = new CentroidBoundaryConstraint();
        constraint.setDelegate(new RandomBoundaryConstraint());
        strategy.setBoundaryConstraint(constraint);
        instance.setOptimisationProblem(problem);
        DataDependantPopulationInitializationStrategy init = new DataDependantPopulationInitializationStrategy<ClusterParticle>();
      
        init.setEntityType(new ClusterParticle());
        init.setEntityNumber(2);
        instance.setInitialisationStrategy(init);
        instance.setSourceURL("src\\test\\resources\\datasets\\iris2.arff");
        
        instance.setOptimisationProblem(problem);
        instance.addStoppingCondition(new MeasuredStoppingCondition());
        
        DataClusteringPSO instance2 = new DataClusteringPSO();
        
        QuantizationErrorMinimizationProblem problem2 = new QuantizationErrorMinimizationProblem();
        problem2.setDomain("R(-5.12:5.12)");
        IterationStrategy strategy2 = new StandardDataClusteringIterationStrategy();
        CentroidBoundaryConstraint constraint2 = new CentroidBoundaryConstraint();
        constraint2.setDelegate(new RandomBoundaryConstraint());
        strategy2.setBoundaryConstraint(constraint2);
        instance2.setOptimisationProblem(problem2);
        DataDependantPopulationInitializationStrategy init2 = new DataDependantPopulationInitializationStrategy<ClusterParticle>();
      
        init2.setEntityType(new ClusterParticle());
        init2.setEntityNumber(2);
        instance2.setInitialisationStrategy(init2);
        instance2.setSourceURL("src\\test\\resources\\datasets\\iris2.arff");
        
        instance2.setOptimisationProblem(problem2);
        instance2.addStoppingCondition(new MeasuredStoppingCondition());
        
        DataClusteringPSO instance3 = new DataClusteringPSO();
        
        QuantizationErrorMinimizationProblem problem3 = new QuantizationErrorMinimizationProblem();
        problem3.setDomain("R(-5.12:5.12)");
        IterationStrategy strategy3 = new StandardDataClusteringIterationStrategy();
        CentroidBoundaryConstraint constraint3 = new CentroidBoundaryConstraint();
        constraint3.setDelegate(new RandomBoundaryConstraint());
        strategy3.setBoundaryConstraint(constraint3);
        instance3.setOptimisationProblem(problem3);
        DataDependantPopulationInitializationStrategy init3 = new DataDependantPopulationInitializationStrategy<ClusterParticle>();
      
        init3.setEntityType(new ClusterParticle());
        init3.setEntityNumber(2);
        instance3.setInitialisationStrategy(init3);
        instance3.setSourceURL("src\\test\\resources\\datasets\\iris2.arff");
        
        instance3.setOptimisationProblem(problem3);
        instance3.addStoppingCondition(new MeasuredStoppingCondition());
        
        CooperativePSO cooperative = new CooperativePSO();
        cooperative.addStoppingCondition(new MeasuredStoppingCondition(new Iterations(), new Maximum(), 30));
        cooperative.addPopulationBasedAlgorithm(instance);
        cooperative.addPopulationBasedAlgorithm(instance2);
        cooperative.addPopulationBasedAlgorithm(instance3);
        cooperative.setOptimisationProblem(problem);
        
        cooperative.initialise();
        
        ClusterParticle particleBefore = instance.getTopology().get(0).getClone();
        
        cooperative.run();
        
        ClusterParticle particleAfter = instance.getTopology().get(0).getClone();
        
        Assert.assertFalse(particleAfter.getCandidateSolution().containsAll(particleBefore.getCandidateSolution()));
    }

    /**
     * Test of getSolutions method, of class CooperativePSO.
     */
    @Test
    public void testGetSolutions() {
        DataClusteringPSO standard  = new DataClusteringPSO();
        ClusterParticle particle = new ClusterParticle();
        CentroidHolder holder = new CentroidHolder();
        holder.add(ClusterCentroid.of(1,2,3,4,5));
        holder.add(ClusterCentroid.of(5,4,3,2,1));
        particle.setCandidateSolution(holder);
        particle.getProperties().put(EntityType.FITNESS, new MinimisationFitness(2.0));
        particle.getProperties().put(EntityType.Particle.BEST_FITNESS, new MinimisationFitness(2.0));
        particle.getProperties().put(EntityType.Particle.BEST_POSITION, particle.getCandidateSolution());
        particle.setNeighbourhoodBest(particle);
        standard.getTopology().add(particle);
        
        ClusterParticle otherParticle = new ClusterParticle();
        CentroidHolder otherHolder = new CentroidHolder();
        otherHolder.add(ClusterCentroid.of(6,7,8,9,10));
        otherHolder.add(ClusterCentroid.of(10,9,8,7,6));
        otherParticle.setCandidateSolution(otherHolder);
        otherParticle.getProperties().put(EntityType.FITNESS, new MinimisationFitness(1.0));
        otherParticle.getProperties().put(EntityType.Particle.BEST_FITNESS, new MinimisationFitness(1.0));
        otherParticle.getProperties().put(EntityType.Particle.BEST_POSITION, otherParticle.getCandidateSolution());
        otherParticle.setNeighbourhoodBest(otherParticle);
        standard.getTopology().add(otherParticle);
        
        DataClusteringPSO standard2  = new DataClusteringPSO();
        ClusterParticle particle2 = new ClusterParticle();
        CentroidHolder otherHolder2 = new CentroidHolder();
        otherHolder2.add(ClusterCentroid.of(3,2,3,4,5));
        otherHolder2.add(ClusterCentroid.of(5,10,3,7,1));
        particle2.setCandidateSolution(holder);
        particle2.getProperties().put(EntityType.FITNESS, new MinimisationFitness(2.1));
        particle2.getProperties().put(EntityType.Particle.BEST_FITNESS, new MinimisationFitness(2.1));
        particle2.getProperties().put(EntityType.Particle.BEST_POSITION, particle2.getCandidateSolution());
        particle2.setNeighbourhoodBest(particle2);
        standard2.getTopology().add(particle2);
        
        ClusterParticle otherParticle2 = new ClusterParticle();
        CentroidHolder holder2 = new CentroidHolder();
        holder2.add(ClusterCentroid.of(9,7,2,9,10));
        holder2.add(ClusterCentroid.of(11,9,5,7,6));
        otherParticle2.setCandidateSolution(holder2);
        otherParticle2.getProperties().put(EntityType.FITNESS, new MinimisationFitness(3.0));
        otherParticle2.getProperties().put(EntityType.Particle.BEST_FITNESS, new MinimisationFitness(3.0));
        otherParticle2.getProperties().put(EntityType.Particle.BEST_POSITION, otherParticle.getCandidateSolution());
        otherParticle2.setNeighbourhoodBest(otherParticle2);
        standard2.getTopology().add(otherParticle2);
        
        CooperativePSO cooperative = new CooperativePSO();
        cooperative.addPopulationBasedAlgorithm(standard);
        cooperative.addPopulationBasedAlgorithm(standard2);
        
        ArrayList<OptimisationSolution> list = (ArrayList<OptimisationSolution>) cooperative.getSolutions();
        ArrayList<CentroidHolder> holders = new ArrayList<CentroidHolder>();
        for(OptimisationSolution solution : list) {
            holders.add((CentroidHolder) solution.getPosition());
        }
        
        Assert.assertTrue(!list.isEmpty());
        boolean contains = false;
        for(CentroidHolder centroidHolder : holders) {
            if(centroidHolder.containsAll((CentroidHolder) otherParticle.getCandidateSolution())){
                contains = true;
            }
        }
        
        Assert.assertTrue(contains);
        
        contains = false;
        for(CentroidHolder centroidHolder : holders) {
            if(centroidHolder.containsAll((CentroidHolder) particle2.getCandidateSolution())){
                contains = true;
            }
        }
        
        Assert.assertTrue(contains);
        
    }
    

    /**
     * Test of performInitialisation method, of class CooperativePSO.
     */
    @Test
    public void testPerformInitialisation() {
        System.out.println("performInitialisation");
        DataClusteringPSO standard = new DataClusteringPSO();
        QuantizationErrorMinimizationProblem problem = new QuantizationErrorMinimizationProblem();
        problem.setDomain("R(-5.12:5.12)");
        standard.setOptimisationProblem(problem);
        standard.addStoppingCondition(new MeasuredStoppingCondition(new Iterations(), new Maximum(), 1));
        PopulationInitialisationStrategy init = new DataDependantPopulationInitializationStrategy<ClusterParticle>();
        init.setEntityType(new ClusterParticle());
        standard.setInitialisationStrategy(init);
        standard.setSourceURL("src\\test\\resources\\datasets\\iris2.arff");
        standard.initialise();
        
        CooperativePSO instance = new CooperativePSO();
        instance.addPopulationBasedAlgorithm(standard);
        
        Assert.assertTrue(((SinglePopulationDataClusteringIterationStrategy)((DataClusteringPSO) instance.getPopulations().get(0)).getIterationStrategy()).getDataset().size() > 0);
        Assert.assertTrue(!instance.getPopulations().isEmpty());
        Assert.assertTrue(!instance.getPopulations().get(0).getTopology().isEmpty());
    }

    /**
     * Test of setIterationStrategy method, of class CooperativePSO.
     */
    @Test
    public void testSetIterationStrategy() {
       System.out.println("setIterationStrategy");
       CooperativePSO cooperative = new CooperativePSO();
       IterationStrategy strategy = new StandardDataClusteringIterationStrategy();
       
       cooperative.setIterationStrategy(strategy);
       
       Assert.assertEquals(strategy, cooperative.getIterationStrategy());
    }

    /**
     * Test of getIterationStrategy method, of class CooperativePSO.
     */
    @Test
    public void testGetIterationStrategy() {
       System.out.println("getIterationStrategy");
       CooperativePSO cooperative = new CooperativePSO();
       IterationStrategy strategy = new StandardDataClusteringIterationStrategy();

       cooperative.setIterationStrategy(strategy);

       Assert.assertEquals(strategy, cooperative.getIterationStrategy());
    }
}
