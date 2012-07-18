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
package net.sourceforge.cilib.entity;

import net.sourceforge.cilib.controlparameter.ControlParameter;
import net.sourceforge.cilib.entity.initialization.InitializationStrategy;
import net.sourceforge.cilib.pso.particle.ParticleBehavior;
import net.sourceforge.cilib.pso.pbestupdate.PersonalBestUpdateStrategy;
import net.sourceforge.cilib.pso.positionprovider.PositionProvider;
import net.sourceforge.cilib.pso.velocityprovider.VelocityProvider;
import net.sourceforge.cilib.type.types.Type;
import net.sourceforge.cilib.type.types.container.StructuredType;

/**
 * Definition of a particle.
 */
public interface Particle extends Entity, SocialEntity, MemoryBasedEntity {

    /**
     * {@inheritDoc}
     */
    @Override
    Particle getClone();

    /**
     * Get the current position of the {@linkplain Particle}.
     * @return The {@linkplain Type} representing the position.
     */
    StructuredType getPosition();

    /**
     * Get the current velocity of the {@linkplain Particle}.
     * @return The {@linkplain Type} representing the velocity.
     */
    StructuredType getVelocity();

    StructuredType getGlobalGuide();

    StructuredType getLocalGuide();

    /**
     * Get the current best particle within the {@linkplain Particle}s neighbourhood.
     * @return The neighbourhood best particle.
     */
    Particle getNeighbourhoodBest();

    /**
     * Set the current neighbourhood best particle within the current neighbourhood.
     * @param particle The particle to set as the neighbourhood best.
     */
    void setNeighbourhoodBest(Particle particle);

    /**
     * Update the current position of the {@linkplain Particle}.
     */
    void updatePosition();

    /**
     * Update the velocity of the {@linkplain Particle}.
     */
    void updateVelocity();

    /**
     * Get the strategy that will be used to update the velocity.
     * @return The current {@linkplain VelocityProvider}.
     */
    VelocityProvider getVelocityProvider();

    /**
     * Set the {@linkplain VelocityProvider} to be used during velocity updates.
     * @param velocityProvider the {@linkplain VelocityProvider} to use.
     */
    void setVelocityProvider(VelocityProvider velocityProvider);

    /**
     * Get the strategy for the intialisation of the velocity.
     * @return The {@linkplain VelocityInitialisationStrategy} that is currently set.
     */
    InitializationStrategy getVelocityInitializationStrategy();

    /**
     * Set the initialisation strategy for the {@linkplain Particle}s velocity.
     * @param velocityInitialisationStrategy The velocity initialisation strategy to set.
     */
    void setVelocityInitializationStrategy(InitializationStrategy velocityInitialisationStrategy);

    /**
     * Get the current {@linkplain PositionProvider} of the {@linkplain Particle}.
     * @return The current {@linkplain PositionProvider}.
     */
    PositionProvider getPositionProvider();

    /**
     * Set the {@linkplain PositionProvider} to use for particle position updates.
     * @param positionProvider The {@linkplain PositionProvider} to set.
     */
    void setPositionProvider(PositionProvider positionProvider);

    /**
     * Get the current strategy to perform personal best updates.
     * @return The current {@link PersonalBestUpdateStrategy}.
     */
    PersonalBestUpdateStrategy getPersonalBestUpdateStrategy();

    /**
     * Set the strategy to perform personal best updates.
     * @param personalBestUpdateStrategy The instance to set.
     */
    void setPersonalBestUpdateStrategy(PersonalBestUpdateStrategy personalBestUpdateStrategy);

    /**
     * Get the behavior associated with the current Particle.
     * @return The {@link ParticleBehavior} associated with the current Particle.
     */
    ParticleBehavior getParticleBehavior();

    /**
     * Set the behavior that the current particle should follow.
     * @param behavior The {@link ParticleBehavior} to use.
     */
    void setParticleBehavior(ParticleBehavior particleBehavior);

}
