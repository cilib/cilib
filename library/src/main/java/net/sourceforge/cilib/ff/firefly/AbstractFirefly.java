/**           __  __
 *    _____ _/ /_/ /_    Computational Intelligence Library (CIlib)
 *   / ___/ / / / __ \   (c) CIRG @ UP
 *  / /__/ / / / /_/ /   http://cilib.net
 *  \___/_/_/_/_.___/
 */
package net.sourceforge.cilib.ff.firefly;

import net.sourceforge.cilib.ff.positionupdatestrategies.FireflyPositionUpdateStrategy;
import net.sourceforge.cilib.ff.positionupdatestrategies.StandardFireflyPositionUpdateStrategy;
import net.sourceforge.cilib.entity.AbstractEntity;
import net.sourceforge.cilib.entity.Entity;
import net.sourceforge.cilib.entity.EntityType;
import net.sourceforge.cilib.entity.initialization.InitializationStrategy;
import net.sourceforge.cilib.entity.initialization.RandomInitializationStrategy;
import net.sourceforge.cilib.problem.Problem;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 * The abstract entity class for the Firefly algorithm that represents the fireflies.
 *
 */
public abstract class AbstractFirefly extends AbstractEntity implements Firefly {

    protected FireflyPositionUpdateStrategy positionUpdateStrategy;
    protected InitializationStrategy<Firefly> positionInitializationStrategy;

    /**
     * Default constructor. Defines reasonable defaults for common members.
     */
    public AbstractFirefly() {
        this.positionUpdateStrategy = new StandardFireflyPositionUpdateStrategy();
        this.positionInitializationStrategy = new RandomInitializationStrategy<Firefly>();
    }

    /**
     * Copy constructor. Create a copy of the provided instance.
     * @param copy the reference of the bee that is deep copied.
     */
    public AbstractFirefly(AbstractFirefly copy) {
        super(copy);
        this.positionUpdateStrategy = copy.positionUpdateStrategy.getClone();
        this.positionInitializationStrategy = copy.positionInitializationStrategy.getClone();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract AbstractFirefly getClone();

    /**
     * {@inheritDoc}
     */
    @Override
    public FireflyPositionUpdateStrategy getPositionUpdateStrategy() {
        return this.positionUpdateStrategy;
    }

    /**
     * Sets the position update strategy of the firefly.
     * @param positionUpdateStrategy the new position update strategy.
     */
    public void setPositionUpdateStrategy(FireflyPositionUpdateStrategy positionUpdateStrategy) {
        this.positionUpdateStrategy = positionUpdateStrategy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void updatePosition(Firefly other);

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void calculateFitness();

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(Entity o) {
        return getFitness().compareTo(o.getFitness());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareIntensity(Firefly other) {
        return compareTo(other);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBrighter(Firefly other) {
        return this.compareIntensity(other) > 0;
    }

    /**
     * {@inheritDoc}
     */
    public Vector getPosition() {
        return (Vector) this.getCandidateSolution();
    }

    /**
     * {@inheritDoc}
     */
    public void setPosition(Vector position) {
        this.setCandidateSolution(position);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract int getDimension();

    /**
     * Get the current {@linkplain PositionInitialisationStrategy}.
     * @return The current {@linkplain PositionInitialisationStrategy}.
     */
    public InitializationStrategy<Firefly> getPositionInitialisationStrategy() {
        return this.positionInitializationStrategy;
    }

    /**
     * Set the {@linkplain PositionInitialisationStrategy} to be used.
     * @param positionInitializationStrategy The value to set.
     */
    public void setPositionInitialisationStrategy(InitializationStrategy positionInitializationStrategy) {
        this.positionInitializationStrategy = positionInitializationStrategy;
    }
}
