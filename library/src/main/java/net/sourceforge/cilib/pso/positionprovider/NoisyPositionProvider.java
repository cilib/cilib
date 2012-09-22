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
package net.sourceforge.cilib.pso.positionprovider;

import net.sourceforge.cilib.math.random.ProbabilityDistributionFunction;
import net.sourceforge.cilib.type.types.container.Vector;
import net.sourceforge.cilib.entity.Particle;
import net.sourceforge.cilib.math.random.GaussianDistribution;
import net.sourceforge.cilib.util.Vectors;

/**
 * Decorates a {@link PositionUpdateVisitor} or a {@link VelocityUpdateVisitor}
 * with random noise from any probability distribution function.
 *
 */
public class NoisyPositionProvider implements PositionProvider {

    private static final long serialVersionUID = -2665293187543545962L;
    private ProbabilityDistributionFunction distribution;
    private PositionProvider delegate;

    public NoisyPositionProvider() {
        this.distribution = new GaussianDistribution();
        this.delegate = new StandardPositionProvider();
    }

    public NoisyPositionProvider(NoisyPositionProvider rhs) {
        this.distribution = rhs.distribution;
        this.delegate = rhs.delegate.getClone();
    }

    @Override
    public Vector get(Particle particle) {
        Vector position = this.delegate.get(particle);
        Vector.Builder builder = Vector.newBuilder();
        for (int i = 0; i < particle.getDimension(); i++) {
            builder.add(this.distribution.getRandomNumber());
        }
        return Vectors.sumOf(position, builder.build());
    }

    @Override
    public NoisyPositionProvider getClone() {
        return new NoisyPositionProvider(this);
    }

    public PositionProvider getDelegate() {
        return this.delegate;
    }

    public void setDelegate(PositionProvider delegate) {
        this.delegate = delegate;
    }

    public ProbabilityDistributionFunction getDistribution() {
        return this.distribution;
    }

    public void setDistribution(ProbabilityDistributionFunction distribution) {
        this.distribution = distribution;
    }
}
