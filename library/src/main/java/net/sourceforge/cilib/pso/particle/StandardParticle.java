/**           __  __
 *    _____ _/ /_/ /_    Computational Intelligence Library (CIlib)
 *   / ___/ / / / __ \   (c) CIRG @ UP
 *  / /__/ / / / /_/ /   http://cilib.net
 *  \___/_/_/_/_.___/
 */
package net.sourceforge.cilib.pso.particle;

import net.sourceforge.cilib.entity.Property;
import net.sourceforge.cilib.problem.Problem;
import net.sourceforge.cilib.problem.solution.Fitness;
import net.sourceforge.cilib.problem.solution.InferiorFitness;
import net.sourceforge.cilib.type.types.Int;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 *
 */
public class StandardParticle extends AbstractParticle {
    private static final long serialVersionUID = 2610843008637279845L;

    protected Particle neighbourhoodBest;

    /** Creates a new instance of StandardParticle. */
    public StandardParticle() {
        super();
        put(Property.BEST_POSITION, Vector.of());
        put(Property.VELOCITY, Vector.of());
    }

    /**
     * Create a copy of the provided instance.
     * @param copy The instance to copy.
     */
    public StandardParticle(StandardParticle copy) {
        super(copy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StandardParticle getClone() {
           return new StandardParticle(this);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if ((object == null) || (this.getClass() != object.getClass())) {
            return false;
        }

        StandardParticle other = (StandardParticle) object;
        return super.equals(object) &&
            (this.neighbourhoodBest == null ? true : this.neighbourhoodBest.equals(other.neighbourhoodBest));
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Fitness getBestFitness() {
        return (Fitness) get(Property.BEST_FITNESS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector getBestPosition() {
        return (Vector) get(Property.BEST_POSITION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Particle getNeighbourhoodBest() {
        return this.neighbourhoodBest;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector getPosition() {
        return (Vector) getCandidateSolution();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector getVelocity() {
        return (Vector) get(Property.VELOCITY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialise(Problem problem) {
        put(Property.CANDIDATE_SOLUTION, problem.getDomain().getBuiltRepresentation().getClone());
        put(Property.BEST_POSITION, Vector.copyOf(getPosition()));
        put(Property.VELOCITY, Vector.copyOf(getPosition()));

        this.positionInitialisationStrategy.initialise(Property.CANDIDATE_SOLUTION, this);
        this.personalBestInitialisationStrategy.initialise(Property.BEST_POSITION, this);
        this.velocityInitialisationStrategy.initialise(Property.VELOCITY, this);

        put(Property.FITNESS, InferiorFitness.instance());
        put(Property.BEST_FITNESS, InferiorFitness.instance());
        put(Property.PREVIOUS_FITNESS, InferiorFitness.instance());
        this.neighbourhoodBest = this;

        put(Property.PBEST_STAGNATION_COUNTER, Int.valueOf(0));
        put(Property.PREVIOUS_SOLUTION, getCandidateSolution());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updatePosition() {
        put(Property.PREVIOUS_SOLUTION, getCandidateSolution());
        put(Property.CANDIDATE_SOLUTION, this.behavior.getPositionProvider().get(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void calculateFitness() {
        super.calculateFitness();
        this.personalBestUpdateStrategy.updatePersonalBest(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNeighbourhoodBest(Particle particle) {
        neighbourhoodBest = particle;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateVelocity() {
        put(Property.VELOCITY, this.behavior.getVelocityProvider().get(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reinitialise() {
        this.positionInitialisationStrategy.initialise(Property.CANDIDATE_SOLUTION, this);
        this.velocityInitialisationStrategy.initialise(Property.VELOCITY, this);
    }
}
