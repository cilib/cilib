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
package net.sourceforge.cilib.clustering.entity;

import net.sourceforge.cilib.functions.continuous.unconstrained.Spherical;
import junit.framework.Assert;
import net.sourceforge.cilib.entity.EntityType;
import net.sourceforge.cilib.entity.Particle;
import net.sourceforge.cilib.entity.initialization.InitializationStrategy;
import net.sourceforge.cilib.problem.FunctionMinimisationProblem;
import net.sourceforge.cilib.problem.MinimisationFitness;
import net.sourceforge.cilib.type.types.container.CentroidHolder;
import net.sourceforge.cilib.type.types.container.ClusterCentroid;
import net.sourceforge.cilib.type.types.container.StructuredType;
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
public class ClusterParticleTest {
    
    public ClusterParticleTest() {
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
     * Test of getCandidateSolution method, of class ClusterParticle.
     */
    @Test
    public void testGetCandidateSolution() {
        System.out.println("getCandidateSolution");
        ClusterParticle instance = new ClusterParticle();
        CentroidHolder holder = new CentroidHolder();
        ClusterCentroid centroid = ClusterCentroid.of(1,2,3,4,5,6);
        holder.add(centroid);
        holder.add(centroid);
        holder.add(centroid);
        instance.setCandidateSolution(holder);
        
        Assert.assertEquals(instance.getCandidateSolution(), holder);
    }

    /**
     * Test of setCandidateSolution method, of class ClusterParticle.
     */
    @Test
    public void testSetCandidateSolution_CentroidHolder() {
        System.out.println("setCandidateSolution");
        ClusterParticle instance = new ClusterParticle();
        CentroidHolder holder = new CentroidHolder();
        ClusterCentroid centroid = ClusterCentroid.of(1,2,3,4,5,6);
        holder.add(centroid);
        holder.add(centroid);
        holder.add(centroid);
        instance.setCandidateSolution(holder);
        
        Assert.assertEquals(instance.getCandidateSolution(), holder);
    }

    /**
     * Test of calculateFitness method, of class ClusterParticle.
     */
    @Test
    public void testCalculateFitness() {
        System.out.println("calculateFitness");
        ClusterParticle instance = new ClusterParticle();
        CentroidHolder holder = new CentroidHolder();
        instance.getProperties().put(EntityType.FITNESS, new MinimisationFitness(12.0));
        instance.getProperties().put(EntityType.Particle.BEST_FITNESS, new MinimisationFitness(12.0));
        ClusterCentroid centroid = ClusterCentroid.of(1,2,3,4,5,6);
        centroid.setDataItemDistances(new double[]{1,2,3,4});
        holder.add(centroid);
        holder.add(centroid);
        holder.add(centroid);
        instance.setCandidateSolution(holder);
        instance.calculateFitness();
        
        Assert.assertEquals(instance.getFitness().getValue(), 2.5);
    }

    /**
     * Test of getBestFitness method, of class ClusterParticle.
     */
    @Test
    public void testGetBestFitness() {
        System.out.println("getBestFitness");
        ClusterParticle instance = new ClusterParticle();
        instance.getProperties().put(EntityType.Particle.BEST_FITNESS, new MinimisationFitness(6.0));
        
        Assert.assertEquals(instance.getBestFitness().getValue(), 6.0);
    }

    /**
     * Test of getDimension method, of class ClusterParticle.
     */
    @Test
    public void testGetDimension() {
        System.out.println("getDimension");
        ClusterParticle instance = new ClusterParticle();
        CentroidHolder holder = new CentroidHolder();
        ClusterCentroid centroid = ClusterCentroid.of(1,2,3,4,5,6);
        centroid.setDataItemDistances(new double[]{1,2,3,4});
        holder.add(centroid);
        holder.add(centroid);
        holder.add(centroid);
        instance.setCandidateSolution(holder);
        
        Assert.assertEquals(instance.getDimension(), 3);
    }

    /**
     * Test of getPosition method, of class ClusterParticle.
     */
    @Test
    public void testGetPosition() {
        System.out.println("getPosition");
        ClusterParticle instance = new ClusterParticle();
        CentroidHolder holder = new CentroidHolder();
        ClusterCentroid centroid = ClusterCentroid.of(1,2,3,4,5,6);
        centroid.setDataItemDistances(new double[]{1,2,3,4});
        holder.add(centroid);
        holder.add(centroid);
        holder.add(centroid);
        instance.setCandidateSolution(holder);
        
        Assert.assertEquals(instance.getPosition(), holder);
    }

    /**
     * Test of getBestPosition method, of class ClusterParticle.
     */
    @Test
    public void testGetBestPosition() {
        System.out.println("getBestPosition");
        ClusterParticle instance = new ClusterParticle();
        CentroidHolder holder = new CentroidHolder();
        ClusterCentroid centroid = ClusterCentroid.of(1,2,3,4,5,6);
        centroid.setDataItemDistances(new double[]{1,2,3,4});
        holder.add(centroid);
        holder.add(centroid);
        holder.add(centroid);
        instance.getProperties().put(EntityType.Particle.BEST_POSITION, holder);
        
        Assert.assertEquals(instance.getBestPosition(), holder);
    }

    /**
     * Test of getVelocity method, of class ClusterParticle.
     */
    @Test
    public void testGetVelocity() {
        System.out.println("getVelocity");
        ClusterParticle instance = new ClusterParticle();
        CentroidHolder holder = new CentroidHolder();
        ClusterCentroid centroid = ClusterCentroid.of(1,2,3,4,5,6);
        centroid.setDataItemDistances(new double[]{1,2,3,4});
        holder.add(centroid);
        holder.add(centroid);
        holder.add(centroid);
        instance.getProperties().put(EntityType.Particle.VELOCITY, holder);
        
        Assert.assertEquals(instance.getVelocity(), holder);
    }

    /**
     * Test of getNeighbourhoodBest method, of class ClusterParticle.
     */
    @Test
    public void testGetNeighbourhoodBest() {
        System.out.println("getNeighbourhoodBest");
        ClusterParticle instance = new ClusterParticle();
        ClusterParticle neighbour = new ClusterParticle();
        CentroidHolder holder = new CentroidHolder();
        ClusterCentroid centroid = ClusterCentroid.of(1,2,3,4,5,6);
        centroid.setDataItemDistances(new double[]{1,2,3,4});
        holder.add(centroid);
        holder.add(centroid);
        holder.add(centroid);
        neighbour.setCandidateSolution(holder);
        instance.setNeighbourhoodBest(neighbour);
        
        Assert.assertEquals(instance.getNeighbourhoodBest().getCandidateSolution(), holder);
    }

    /**
     * Test of updatePosition method, of class ClusterParticle.
     */
    @Test
    public void testUpdatePosition() {
        System.out.println("updatePosition");
        ClusterParticle instance = new ClusterParticle();
        CentroidHolder holder = new CentroidHolder();
        ClusterCentroid centroid = ClusterCentroid.of(1,2,3,4,5,6);
        centroid.setDataItemDistances(new double[]{1,2,3,4});
        holder.add(centroid);
        holder.add(centroid);
        holder.add(centroid);
        instance.setCandidateSolution(holder.getClone());
        instance.getProperties().put(EntityType.Particle.VELOCITY, holder);
        instance.getProperties().put(EntityType.Particle.BEST_POSITION, holder);
        instance.getProperties().put(EntityType.FITNESS, new MinimisationFitness(6.0));
        instance.getProperties().put(EntityType.Particle.BEST_FITNESS, new MinimisationFitness(6.0));
        instance.setNeighbourhoodBest(instance);
        instance.updatePosition();
        
        Assert.assertNotSame(instance.getCandidateSolution(), holder);
    }

    /**
     * Test of updateVelocity method, of class ClusterParticle.
     */
    @Test
    public void testUpdateVelocity() {
        System.out.println("updateVelocity");
        ClusterParticle instance = new ClusterParticle();
        CentroidHolder holder = new CentroidHolder();
        ClusterCentroid centroid = ClusterCentroid.of(1,2,3,4,5,6);
        centroid.setDataItemDistances(new double[]{1,2,3,4});
        holder.add(centroid);
        holder.add(centroid);
        holder.add(centroid);
        instance.setCandidateSolution(holder.getClone());
        instance.getProperties().put(EntityType.Particle.VELOCITY, holder);
        instance.getProperties().put(EntityType.Particle.BEST_POSITION, holder);
        instance.getProperties().put(EntityType.FITNESS, new MinimisationFitness(6.0));
        instance.getProperties().put(EntityType.Particle.BEST_FITNESS, new MinimisationFitness(6.0));
        instance.setNeighbourhoodBest(instance);
        instance.updateVelocity();
        
        Assert.assertNotSame(instance.getVelocity(), holder);
    }

    /**
     * Test of initialise method, of class ClusterParticle.
     */
    @Test
    public void testInitialise() {
        System.out.println("initialise");
        FunctionMinimisationProblem problem = new FunctionMinimisationProblem();
        problem.setDomain("R(-5.12:5.12)^5");
        problem.setFunction(new Spherical());
        
        ClusterParticle instance = new ClusterParticle();
        instance.setNumberOfCusters(2);
        instance.initialise(problem);
        
        Assert.assertEquals(instance.getDimension(), 2);
        Assert.assertTrue(((CentroidHolder) instance.getCandidateSolution()).get(0).size() == 5);
        
    }

    /**
     * Test of reinitialise method, of class ClusterParticle.
     */
    @Test
    public void testReinitialise() {
        System.out.println("reinitialise");
        ClusterParticle instance = new ClusterParticle();
        
        FunctionMinimisationProblem problem = new FunctionMinimisationProblem();
        problem.setDomain("R(-5.12:5.12)^5");
        problem.setFunction(new Spherical());
        
        instance.setNumberOfCusters(2);
        instance.initialise(problem);
        
        CentroidHolder holder = (CentroidHolder) instance.getCandidateSolution().getClone();
        
        instance.reinitialise();
        
        Assert.assertNotSame(holder, (CentroidHolder) instance.getCandidateSolution());
    }

    /**
     * Test of setNeighbourhoodBest method, of class ClusterParticle.
     */
    @Test
    public void testSetNeighbourhoodBest() {
        System.out.println("setNeighbourhoodBest");
        ClusterParticle instance = new ClusterParticle();
        ClusterParticle neighbourhoodBest = new ClusterParticle();
        
        CentroidHolder holder = new CentroidHolder();
        ClusterCentroid centroid = ClusterCentroid.of(1,2,3,4,5,6);
        centroid.setDataItemDistances(new double[]{1,2,3,4});
        holder.add(centroid);
        holder.add(centroid);
        holder.add(centroid);
        
        neighbourhoodBest.setCandidateSolution(holder);
        neighbourhoodBest.getProperties().put(EntityType.Particle.VELOCITY, holder);
        neighbourhoodBest.getProperties().put(EntityType.Particle.BEST_POSITION, holder);
        
        instance.setNeighbourhoodBest(neighbourhoodBest);
        
        Assert.assertEquals(instance.getNeighbourhoodBest(), neighbourhoodBest);
    }

   
}
