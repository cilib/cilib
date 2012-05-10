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

import net.sourceforge.cilib.type.types.container.Vector;
import net.sourceforge.cilib.functions.continuous.unconstrained.Spherical;
import net.sourceforge.cilib.problem.FunctionMinimisationProblem;
import junit.framework.Assert;
import net.sourceforge.cilib.algorithm.initialisation.ClonedPopulationInitialisationStrategy;
import net.sourceforge.cilib.algorithm.initialisation.PopulationInitialisationStrategy;
import net.sourceforge.cilib.clustering.entity.ClusterParticle;
import net.sourceforge.cilib.coevolution.cooperative.contributionselection.ContributionSelectionStrategy;
import net.sourceforge.cilib.coevolution.cooperative.contributionselection.TopologyBestContributionSelectionStrategy;
import net.sourceforge.cilib.entity.Entity;
import net.sourceforge.cilib.entity.EntityType;
import net.sourceforge.cilib.entity.Particle;
import net.sourceforge.cilib.entity.Topology;
import net.sourceforge.cilib.entity.topologies.GBestTopology;
import net.sourceforge.cilib.io.DataTableBuilder;
import net.sourceforge.cilib.io.transform.DataOperator;
import net.sourceforge.cilib.io.transform.TypeConversionOperator;
import net.sourceforge.cilib.problem.MinimisationFitness;
import net.sourceforge.cilib.type.types.container.CentroidHolder;
import net.sourceforge.cilib.type.types.container.ClusterCentroid;
import net.sourceforge.cilib.util.DistanceMeasure;
import net.sourceforge.cilib.util.EuclideanDistanceMeasure;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Kristina
 */
public class PSOClusteringAlgorithmTest {
    
    public PSOClusteringAlgorithmTest() {
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
     * Test of algorithmIteration method, of class PSOClusteringAlgorithm.
     */
    @Test
    public void testAlgorithmIteration() {
        System.out.println("algorithmIteration");
        PSOClusteringAlgorithm instance = new PSOClusteringAlgorithm();
        FunctionMinimisationProblem problem = new FunctionMinimisationProblem();
        problem.setDomain("R(-5.12:5.12)^4");
        problem.setFunction(new Spherical());
        instance.setOptimisationProblem(problem);
        PopulationInitialisationStrategy init = new ClonedPopulationInitialisationStrategy<ClusterParticle>();
        init.setEntityType(new ClusterParticle());
        init.setEntityNumber(2);
        instance.setInitialisationStrategy(init);
        instance.setSourceURL("src\\test\\resources\\datasets\\iris2.arff");
        instance.performInitialisation();
        
        ClusterParticle previousParticle1 = (ClusterParticle) instance.getTopology().get(0).getClone();
        ClusterParticle previousParticle2 = (ClusterParticle) instance.getTopology().get(1).getClone();
        
        instance.algorithmIteration();
        
         ClusterParticle laterParticle1 = (ClusterParticle) instance.getTopology().get(0).getClone();
         ClusterParticle laterParticle2 = (ClusterParticle) instance.getTopology().get(1).getClone();
         
         ///Assert.assertFalse(laterParticle1.getCandidateSolution().containsAll(previousParticle1.getCandidateSolution()));
         Assert.assertNotSame(previousParticle1, laterParticle1);
         Assert.assertNotSame(previousParticle2, laterParticle2);
        
    }

    /**
     * Test of getTopology method, of class PSOClusteringAlgorithm.
     */
    @Test
    public void testGetTopology() {
        System.out.println("getTopology");
        PSOClusteringAlgorithm instance = new PSOClusteringAlgorithm();
        Particle p = new ClusterParticle();
        CentroidHolder holder = new CentroidHolder();
        holder.add(ClusterCentroid.of(1,2,3,4,5));
        holder.add(ClusterCentroid.of(1,3,5,9,8));
        p.setCandidateSolution(holder);
        Topology topology = new GBestTopology();
        topology.add(p);
        
        instance.setTopology(topology);
        
        Assert.assertEquals(topology, instance.getTopology());
    }

    /**
     * Test of setTopology method, of class PSOClusteringAlgorithm.
     */
    @Test
    public void testSetTopology() {
        System.out.println("setTopology");
        PSOClusteringAlgorithm instance = new PSOClusteringAlgorithm();
        Particle p = new ClusterParticle();
        CentroidHolder holder = new CentroidHolder();
        holder.add(ClusterCentroid.of(1,2,3,4,5));
        p.setCandidateSolution(holder);
        Topology topology = new GBestTopology();
        topology.add(p);
        
        instance.setTopology(topology);
        
        Assert.assertEquals(topology, instance.getTopology());
    }

    /**
     * Test of performInitialisation method, of class PSOClusteringAlgorithm.
     */
    @Test
    public void testPerformInitialisation() {
        System.out.println("performInitialisation");
        PSOClusteringAlgorithm instance = new PSOClusteringAlgorithm();
        FunctionMinimisationProblem problem = new FunctionMinimisationProblem();
        problem.setDomain("R(-5.12:5.12)^30");
        problem.setFunction(new Spherical());
        instance.setOptimisationProblem(problem);
        PopulationInitialisationStrategy init = new ClonedPopulationInitialisationStrategy<ClusterParticle>();
        init.setEntityType(new ClusterParticle());
        instance.setInitialisationStrategy(init);
        instance.setSourceURL("src\\test\\resources\\datasets\\iris.arff");
        instance.performInitialisation();
        
        Assert.assertTrue(instance.getDataset().size() > 0);
        Assert.assertTrue(!instance.getTopology().isEmpty());
    }

    /**
     * Test of getContributionSelectionStrategy method, of class PSOClusteringAlgorithm.
     */
    @Test
    public void testGetContributionSelectionStrategy() {
       System.out.println("getContributionSelectionStrategy");
       PSOClusteringAlgorithm instance = new PSOClusteringAlgorithm();
       instance.setContributionSelectionStrategy(new TopologyBestContributionSelectionStrategy());
       
       Assert.assertTrue(instance.getContributionSelectionStrategy() instanceof TopologyBestContributionSelectionStrategy);
    }

    /**
     * Test of setContributionSelectionStrategy method, of class PSOClusteringAlgorithm.
     */
    @Test
    public void testSetContributionSelectionStrategy() {
        System.out.println("setContributionSelectionStrategy");
        PSOClusteringAlgorithm instance = new PSOClusteringAlgorithm();
       instance.setContributionSelectionStrategy(new TopologyBestContributionSelectionStrategy());
       
       Assert.assertTrue(instance.getContributionSelectionStrategy() instanceof TopologyBestContributionSelectionStrategy);
    }

    /**
     * Test of getDataTableBuilder method, of class PSOClusteringAlgorithm.
     */
    @Test
    public void testGetDataTableBuilder() {
        System.out.println("getDataTableBuilder");
        PSOClusteringAlgorithm instance = new PSOClusteringAlgorithm();
        instance.setDataTableBuilder(new DataTableBuilder());
       
        Assert.assertTrue(instance.getDataTableBuilder() instanceof DataTableBuilder);
    }

    /**
     * Test of setDataTableBuilder method, of class PSOClusteringAlgorithm.
     */
    @Test
    public void testSetDataTableBuilder() {
        System.out.println("setDataTableBuilder");
        DataTableBuilder dataTableBuilder = null;
        PSOClusteringAlgorithm instance = new PSOClusteringAlgorithm();
        instance.setDataTableBuilder(new DataTableBuilder());
       
        Assert.assertTrue(instance.getDataTableBuilder() instanceof DataTableBuilder);
    }

    /**
     * Test of getSourceURL method, of class PSOClusteringAlgorithm.
     */
    @Test
    public void testGetSourceURL() {
        System.out.println("getSourceURL");
        PSOClusteringAlgorithm instance = new PSOClusteringAlgorithm();
        instance.setSourceURL("c:\\sbc");
       
        Assert.assertEquals(instance.getSourceURL(), "c:\\sbc");
    }

    /**
     * Test of setSourceURL method, of class PSOClusteringAlgorithm.
     */
    @Test
    public void testSetSourceURL() {
        System.out.println("setSourceURL");
        PSOClusteringAlgorithm instance = new PSOClusteringAlgorithm();
        instance.setSourceURL("c:\\sbc");
       
        Assert.assertEquals(instance.getSourceURL(), "c:\\sbc");
    }

    /**
     * Test of getPatternConversionOperator method, of class PSOClusteringAlgorithm.
     */
    @Test
    public void testGetPatternConversionOperator() {
        System.out.println("getPatternConversionOperator");
        PSOClusteringAlgorithm instance = new PSOClusteringAlgorithm();
        instance.setPatternConversionOperator(new TypeConversionOperator());
       
        Assert.assertTrue(instance.getPatternConversionOperator() instanceof TypeConversionOperator);
    }

    /**
     * Test of setPatternConversionOperator method, of class PSOClusteringAlgorithm.
     */
    @Test
    public void testSetPatternConversionOperator() {
        System.out.println("setPatternConversionOperator");
        PSOClusteringAlgorithm instance = new PSOClusteringAlgorithm();
        instance.setPatternConversionOperator(new TypeConversionOperator());
       
        Assert.assertTrue(instance.getPatternConversionOperator() instanceof TypeConversionOperator);
    }

    /**
     * Test of getDistanceMeasure method, of class PSOClusteringAlgorithm.
     */
    @Test
    public void testGetDistanceMeasure() {
        System.out.println("getDistanceMeasure");
        PSOClusteringAlgorithm instance = new PSOClusteringAlgorithm();
        
        Assert.assertTrue(instance.getDistanceMeasure() instanceof EuclideanDistanceMeasure);
    }
}
