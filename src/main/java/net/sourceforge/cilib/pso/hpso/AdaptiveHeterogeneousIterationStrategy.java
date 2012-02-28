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
package net.sourceforge.cilib.pso.hpso;

import static com.google.common.base.Preconditions.checkState;

import java.util.ArrayList;
import java.util.List;
import net.sourceforge.cilib.algorithm.initialisation.HeterogeneousPopulationInitialisationStrategy;
import net.sourceforge.cilib.algorithm.population.IterationStrategy;
import net.sourceforge.cilib.controlparameter.ConstantControlParameter;
import net.sourceforge.cilib.entity.Entity;
import net.sourceforge.cilib.entity.Particle;
import net.sourceforge.cilib.problem.boundaryconstraint.BoundaryConstraint;
import net.sourceforge.cilib.pso.PSO;
import net.sourceforge.cilib.pso.hpso.detectionstrategies.PersonalBestStagnationDetectionStrategy;
import net.sourceforge.cilib.pso.hpso.detectionstrategies.BehaviorChangeTriggerDetectionStrategy;
import net.sourceforge.cilib.pso.iterationstrategies.SynchronousIterationStrategy;
import net.sourceforge.cilib.pso.particle.ParticleBehavior;
import net.sourceforge.cilib.util.selection.recipes.Selector;
import net.sourceforge.cilib.util.selection.recipes.TournamentSelector;

/**
 * Iteration strategy for adaptive dynamic heterogeneous particle swarms (HPSO).
 *
 * This class is similar to the {@link DynamicHeterogeneousIterationStrategy} class.
 * The difference is that particles probabilistically choose new behaviors from
 * the behavior pool, rather than randomly. In this way, behaviors that perform
 * well in early iterations are favored over the other behaviors in subsequent
 * iterations.
 *
 * TODO: add reference
 *
 * @author Bennie Leonard
 */
public class AdaptiveHeterogeneousIterationStrategy implements IterationStrategy<PSO> {
    private IterationStrategy<PSO> iterationStrategy;
    private BehaviorChangeTriggerDetectionStrategy<Particle> detectionStrategy;
    private TournamentSelector<ParticleBehavior> behaviorSelectionRecipe;
    private List<ParticleBehavior> behaviorPool;

    /**
     * Create a new instance of {@linkplain HeterogeneousIterationStrategy}.
     */
    public AdaptiveHeterogeneousIterationStrategy() {
        this.iterationStrategy = new SynchronousIterationStrategy();
        this.detectionStrategy = new PersonalBestStagnationDetectionStrategy();
        this.behaviorSelectionRecipe = new TournamentSelector<ParticleBehavior>();
        this.behaviorPool = new ArrayList<ParticleBehavior>();

        this.behaviorSelectionRecipe.setTournamentSize(ConstantControlParameter.of(0.2));
    }

    /**
     * Create a copy of the provided instance.
     * @param copy The instance to copy.
     */
    public AdaptiveHeterogeneousIterationStrategy(AdaptiveHeterogeneousIterationStrategy copy) {
        this.iterationStrategy = copy.iterationStrategy.getClone();
        this.detectionStrategy = copy.detectionStrategy.getClone();
        this.behaviorSelectionRecipe = copy.behaviorSelectionRecipe;
        this.behaviorPool = new ArrayList<ParticleBehavior>(copy.behaviorPool);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AdaptiveHeterogeneousIterationStrategy getClone() {
        return new AdaptiveHeterogeneousIterationStrategy(this);
    }

    /**
     * Structure of Dynamic Heterogeneous iteration strategy:
     *
     * <ol>
     *   <li>For each particle:</li>
     *   <li>Check if particle must change its behavior</li>
     *   <li>If particle must change its behavior:</li>
     *   <ol>
     *     <li>Select a new behavior to the particle from the behavior pool</li>
     *   </ol>
     *   <li>Perform normal iteration</li>
     * </ol>
     *
     * @see net.sourceforge.cilib.pso.iterationstrategies.SynchronousIterationStrategy#performIteration()
     */
    @Override
    public void performIteration(PSO algorithm) {
        checkState(behaviorPool.size() > 0, "You must add particle behaviors to the behavior pool first.");

        ParticleBehavior behavior;
        for(Entity e : algorithm.getTopology()) {
            Particle p = (Particle)e;

            if (detectionStrategy.detect(p)) {
                behavior = behaviorSelectionRecipe.on(behaviorPool).select();
                behavior.incrementSelectedCounter();
                p.setParticleBehavior(behavior);
            }
        }

        iterationStrategy.performIteration(algorithm);
    }

    /**
     * Get the current {@linkplain IterationStrategy}.
     * @return The current {@linkplain IterationStrategy}.
     */
    public IterationStrategy<PSO> getIterationStrategy() {
        return iterationStrategy;
    }

    /**
     * Set the {@linkplain IterationStrategy} to be used.
     * @param iterationStrategy The value to set.
     */
    public void setIterationStrategy(IterationStrategy<PSO> iterationStrategy) {
        this.iterationStrategy = iterationStrategy;
    }

    /**
     * Get the currently defined {@linkplain StagnationDetectionStrategy}.
     * @return The current {@linkplain StagnationDetectionStrategy}.
     */
    public BehaviorChangeTriggerDetectionStrategy<Particle> getStagnationDetectionStrategy() {
        return detectionStrategy;
    }

    /**
     * Set the {@linkplain StagnationDetectionStrategy} to be used.
     * @param strategy The {@linkplain StagnationDetectionStrategy} to set.
     */
    public void setStagnationDetectionStrategy(BehaviorChangeTriggerDetectionStrategy<Particle> strategy) {
        this.detectionStrategy = strategy;
    }

    /**
     * Get the currently defined {@linkplain Selector},
     * @return The current {@linkplain Selector}.
     */
    public Selector<ParticleBehavior> getSelectionRecipe() {
        return behaviorSelectionRecipe;
    }

    /**
     * Set the current {@linkplain Selector} to use.
     * @param strategy The {@linkplain Selector} to set.
     */
    public void setSelectionRecipe(TournamentSelector<ParticleBehavior> recipe) {
        this.behaviorSelectionRecipe = recipe;
    }

    /**
     * Add a {@link ParticleBehavior} to the behavior pool.
     * @param behavior The {@link ParticleBehavior} to add to the behavior pool.
     */
    public void addBehavior(ParticleBehavior behavior) {
        behaviorPool.add(behavior);
    }

    /**
     * Set the {@link ParticleBehavior} pool.
     * @param pool A {@link List} of {@link ParticleBehavior} objects.
     */
    public void setBehaviorPool(List<ParticleBehavior> pool) {
        behaviorPool = pool;
    }

    /**
     * Get the current behavior pool.
     * @return The current {@link List} of {@link ParticleBehavior} objects.
     */
    public List<ParticleBehavior> getBehaviorPool() {
        return behaviorPool;
    }

    @Override
    public BoundaryConstraint getBoundaryConstraint() {
        return this.iterationStrategy.getBoundaryConstraint();
    }

    @Override
    public void setBoundaryConstraint(BoundaryConstraint boundaryConstraint) {
        this.iterationStrategy.setBoundaryConstraint(boundaryConstraint);
    }
}
