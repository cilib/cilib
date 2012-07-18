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
package net.sourceforge.cilib.entity.initialization;

import net.sourceforge.cilib.entity.Entity;
import net.sourceforge.cilib.entity.EntityType;
import net.sourceforge.cilib.type.types.Type;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 * TODO: this class should be refactored to use the RandomInitialVelocityStrategy or to be a compound
 * operation where the velocity is first randomised and then scaled by a percentage.
 * @param <E> The entity type.
 */
public class DomainPercentageInitializationStrategy<E extends Entity> implements
        InitializationStrategy<E> {

    private static final long serialVersionUID = -7178323673738508287L;
    private InitializationStrategy velocityInitialisationStrategy;
    private double percentage;

    public DomainPercentageInitializationStrategy() {
        this.velocityInitialisationStrategy = new RandomInitializationStrategy();
        this.percentage = 0.1;
    }

    public DomainPercentageInitializationStrategy(DomainPercentageInitializationStrategy copy) {
        this.velocityInitialisationStrategy = copy.velocityInitialisationStrategy.getClone();
        this.percentage = copy.percentage;
    }

    @Override
    public DomainPercentageInitializationStrategy getClone() {
        return new DomainPercentageInitializationStrategy(this);
    }

    @Override
    public void initialize(Enum<?> key, E entity) {
        this.velocityInitialisationStrategy.initialize(EntityType.Particle.VELOCITY, entity);
        Type type = entity.getProperties().get(key);
        Vector vector = (Vector) type;

        for (int i = 0; i < vector.size(); ++i) {
            vector.setReal(i, vector.doubleValueOf(i) * percentage);
        }
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public InitializationStrategy getVelocityInitialisationStrategy() {
        return velocityInitialisationStrategy;
    }

    public void setVelocityInitialisationStrategy(InitializationStrategy velocityInitialisationStrategy) {
        this.velocityInitialisationStrategy = velocityInitialisationStrategy;
    }
}
