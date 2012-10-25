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
package net.sourceforge.cilib.pso.velocityprovider;

import fj.P1;
import net.sourceforge.cilib.controlparameter.ConstantControlParameter;
import net.sourceforge.cilib.controlparameter.ControlParameter;
import net.sourceforge.cilib.entity.Particle;
import net.sourceforge.cilib.math.random.generator.MersenneTwister;
import net.sourceforge.cilib.math.random.generator.RandomProvider;
import net.sourceforge.cilib.type.types.Blackboard;
import net.sourceforge.cilib.type.types.container.Vector;
import net.sourceforge.cilib.util.Vectors;

/**
 * Implementation of A novel Binary Particle Swarm Optimization.
 * Maintains 2 velocities for each particle.
 * <p>
 * References:
 * </p>
 * <ul><li>
 * Mojtabe Ahmadieh Khanesar, Mohammed Teshnehlab and Mahdi Aliyari Shoorehdeli.,
 * "A Novel Binary Particle Swarm Optimization" (2007). Proceedings of the 15th
 * Mediterranear Conference on Control and Automation.
 * </li></ul>
 */
public final class BinaryMVVelocityProvider implements VelocityProvider {
    
    private static enum Velocity { V0, V1 };

    protected ControlParameter inertiaWeight;
    protected ControlParameter c1;
    protected ControlParameter c2;
    protected RandomProvider r1;
    protected RandomProvider r2;

    public BinaryMVVelocityProvider() {
        this(ConstantControlParameter.of(0.729844),
            ConstantControlParameter.of(1.496180),
            ConstantControlParameter.of(1.496180),
            new MersenneTwister(),
            new MersenneTwister());
    }

    public BinaryMVVelocityProvider(ControlParameter inertia, ControlParameter c1, ControlParameter c2,
            RandomProvider r1, RandomProvider r2) {
        this.inertiaWeight = inertia;
        this.c1 = c1;
        this.c2 = c2;
        this.r1 = r1;
        this.r2 = r2;
    }

    public BinaryMVVelocityProvider(BinaryMVVelocityProvider copy) {
        this.inertiaWeight = copy.inertiaWeight.getClone();
        this.c1 = copy.c1.getClone();
        this.c2 = copy.c2.getClone();
        this.r1 = copy.r1;
        this.r2 = copy.r2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BinaryMVVelocityProvider getClone() {
        return new BinaryMVVelocityProvider(this);
    }

    @Override
    public Vector get(Particle particle) {
        Blackboard properties = particle.getProperties();
        
        // initialise two velocities for each particle on first update
        if ((properties.get(Velocity.V0) == null) 
            || (properties.get(Velocity.V1) == null)) {

            particle.getProperties().put(Velocity.V0, particle.getPosition());
            particle.getProperties().put(Velocity.V1, particle.getPosition());
        }

        Vector v0 = (Vector) properties.get(Velocity.V0);
        Vector v1 = (Vector) properties.get(Velocity.V1);
        
        // get local and global bests
        Vector pbest = (Vector) particle.getLocalGuide();
        Vector gbest = (Vector) particle.getGlobalGuide();

        // update both velocities (v0 and v1)
        Vector.Builder dp0 = Vector.newBuilder();
        Vector.Builder dp1 = Vector.newBuilder();
        Vector.Builder dg0 = Vector.newBuilder();
        Vector.Builder dg1 = Vector.newBuilder();

        for(int i = 0; i < particle.getDimension(); i++) {
            dp0.add(pbest.booleanValueOf(i) ? -1 : 1);
            dp1.add(pbest.booleanValueOf(i) ? 1 : -1);
            dg0.add(gbest.booleanValueOf(i) ? -1 : 1);
            dg1.add(gbest.booleanValueOf(i) ? 1 : -1);
        }

        v0 = v0.multiply(inertiaWeight.getParameter())
            .plus(dp0.build().multiply(cp(c1)).multiply(random(r1)))
            .plus(dg0.build().multiply(cp(c2)).multiply(random(r2)));

        v1 = v1.multiply(inertiaWeight.getParameter())
            .plus(dp1.build().multiply(cp(c1)).multiply(random(r1)))
            .plus(dg1.build().multiply(cp(c2)).multiply(random(r2)));

        // update the particle's v0 and v1 velocities
        particle.getProperties().put(Velocity.V0, v0);
        particle.getProperties().put(Velocity.V1, v1);        

        // return combined velocity
        Vector.Builder combined = Vector.newBuilder();
        Vector position = (Vector) particle.getPosition();
        for (int i = 0; i < particle.getDimension(); i++) {
            if (position.booleanValueOf(i)) {
                combined.addWithin(v0.doubleValueOf(i), position.boundsOf(i));
            } else {
                combined.addWithin(v1.doubleValueOf(i), position.boundsOf(i));
            }
        }

        return combined.build();
    }
    
    private static P1<Number> random(final RandomProvider r) {
        return new P1<Number>() {
            @Override
            public Number _1() {
                return r.nextDouble();
            }
        };
    }
    
    private static P1<Number> cp(final ControlParameter r) {
        return new P1<Number>() {
            @Override
            public Number _1() {
                return r.getParameter();
            }
        };
    }

    /**
     * Get the <code>ControlParameter</code> representing the inertia weight of
     * the VelocityProvider.
     * @return Returns the inertia component <tt>ControlParameter</tt>.
     */
    public ControlParameter getInertiaWeight() {
        return inertiaWeight;
    }

    /**
     * Set the <tt>ControlParameter</tt> for the inertia weight of the velocity
     * update equation.
     * @param inertiaComponent The inertiaComponent to set.
     */
    public void setInertiaWeight(ControlParameter inertiaWeight) {
        this.inertiaWeight = inertiaWeight;
    }
    
    /**
     * Gets the <tt>ControlParameter</tt> representing c1 of
     * the velocity update equation
     * <code>VelocityProvider</code>.
     * @return Returns c1.
     */
    public ControlParameter c1() {
        return c1;
    }

    /**
     * Set the <tt>ControlParameter</tt> for c1.
     * @param c1 
     */
    public void c1(ControlParameter c1) {
        this.c1 = c1;
    }

    /**
     * Get the <tt>ControlParameter</tt> representing c2 of
     * the velocity update equation.
     * @return Returns the socialComponent.
     */
    public ControlParameter c2() {
        return c2;
    }

    /**
     * Set the <tt>ControlParameter</tt> for c2.
     * @param c2 
     */
    public void c2(ControlParameter c2) {
        this.c2 = c2;
    }

    public RandomProvider getR1() {
        return r1;
    }

    public void setR1(RandomProvider r1) {
        this.r1 = r1;
    }

    public RandomProvider getR2() {
        return r2;
    }

    public void setR2(RandomProvider r2) {
        this.r2 = r2;
    }
}
