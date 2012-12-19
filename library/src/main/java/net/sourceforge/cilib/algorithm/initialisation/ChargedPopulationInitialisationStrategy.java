/**           __  __
 *    _____ _/ /_/ /_    Computational Intelligence Library (CIlib)
 *   / ___/ / / / __ \   (c) CIRG @ UP
 *  / /__/ / / / /_/ /   http://cilib.net
 *  \___/_/_/_/_.___/
 */
package net.sourceforge.cilib.algorithm.initialisation;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import net.sourceforge.cilib.entity.Entity;
import net.sourceforge.cilib.math.random.generator.Rand;
import net.sourceforge.cilib.problem.Problem;
import net.sourceforge.cilib.pso.dynamic.ChargedParticle;

/**
 * Create a collection of {@linkplain net.sourceforge.cilib.entity.Entity entities}
 * by cloning the given prototype {@link net.sourceforge.cilib.entity.Entity}.
 * The Entity have to be ChargedParticle and their charged are set during the
 * initialization process.
 *
 * @param <E> The {@code Entity} type.
 */
public class ChargedPopulationInitialisationStrategy<E extends Entity>
        implements PopulationInitialisationStrategy<E> {

    private ChargedParticle prototypeEntity;
    private int entityNumber;
    private double chargedRatio; // determines the percentage of the swarm that is to be charged
    private double chargeMagnitude; // charge magnitude

    /**
     * Create an instance of the {@code ChargedPopulationInitialisationStrategy}.
     */
    public ChargedPopulationInitialisationStrategy() {
        entityNumber = 20;
        prototypeEntity = null; // This has to be manually set as Individuals are used in PSO etc...
        chargedRatio = 0.5;    // one half of the swarm is charged => Atomic swarm
        chargeMagnitude = 16; // the obscure value 16 comes from the article where the chraged PSO was analysed for the 1st time by its creators
    }

    /**
     * Copy constructor. Create a copy of the given instance.
     * @param copy The instance to copy.
     */
    public ChargedPopulationInitialisationStrategy(ChargedPopulationInitialisationStrategy<E> copy) {
        this.entityNumber = copy.entityNumber;
        this.prototypeEntity = copy.prototypeEntity.getClone();
        this.chargedRatio = copy.chargedRatio;
        this.chargeMagnitude = copy.chargeMagnitude;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ChargedPopulationInitialisationStrategy<E> getClone() {
        return new ChargedPopulationInitialisationStrategy<E>(this);
    }

    /**
     * Perform the required initialization, using the provided <tt>Topology</tt> and
     * <tt>Problem</tt>.
     * @param problem The <tt>Problem</tt> to use in the initialization of the topology.
     * @return An {@code Iterable<E>} of cloned instances.
     * @throws InitialisationException if the initialization cannot take place.
     */
    @Override
    public Iterable<E> initialise(Problem problem) {
        Preconditions.checkNotNull(problem, "No problem has been specified");
        Preconditions.checkNotNull(prototypeEntity, "No prototype Entity object has been defined for the clone operation in the entity constrution process.");

        List<E> clones = new ArrayList<E>();
        int chargedCounter = 0;
        int neutralCounter = 0;

        for (int i = 0; i < entityNumber; ++i) {
            E entity = (E) prototypeEntity.getClone();
            double rand = Rand.nextDouble();

            // makes sure the charged particles are randomly positioned across the topology
            if (chargedCounter < Math.floor(entityNumber * chargedRatio) && rand < chargedRatio) {
                ((ChargedParticle) entity).setCharge(chargeMagnitude);
                ++chargedCounter;
            } else if (neutralCounter >= Math.floor(entityNumber * (1.0 - chargedRatio))) {
                ((ChargedParticle) entity).setCharge(chargeMagnitude);
                ++chargedCounter;
            } else {
                ((ChargedParticle) entity).setCharge(0);
                ++neutralCounter;
            }
            entity.initialise(problem);
            clones.add(entity);
        }

        return clones;
    }

    /**
     * Set the prototype {@linkplain net.sourceforge.cilib.entity.Entity entity} for the copy process.
     * @param entityType The {@code Entity} to use for the cloning process. This must be a ChargedParticle.
     */
    @Override
    public void setEntityType(Entity entityType) {
        try {
            this.prototypeEntity = (ChargedParticle) entityType;
        } catch (ClassCastException e) {
            throw new UnsupportedOperationException("The entityType of a ChargedPopulationInitialisationStrategy must be a ChargedParticle", e);
        }
    }

    /**
     * Get the {@linkplain net.sourceforge.cilib.entity.Entity entity} that has been defined as
     * the prototype to for the copies.
     * @see ChargedPopulationInitialisationStrategy#getPrototypeEntity()
     * @return The prototype {@code Entity}.
     */
    @Override
    public Entity getEntityType() {
        return this.prototypeEntity;
    }

    /**
     * Get the defined number of {@code Entity} instances to create.
     * @return The number of {@code Entity} instances.
     */
    @Override
    public int getEntityNumber() {
        return this.entityNumber;
    }

    /**
     * Set the number of {@code Entity} instances to clone.
     * @param entityNumber The number to clone.
     */
    @Override
    public void setEntityNumber(int entityNumber) {
        this.entityNumber = entityNumber;
    }

    /**
     * @return the chargedRatio
     */
    public double getChargedRatio() {
        return chargedRatio;
    }

    /**
     * @param chargedRatio the chargedRatio to set
     */
    public void setChargedRatio(double chargedRatio) {
        this.chargedRatio = chargedRatio;
    }

    /**
     * @return the chargeMagnitude
     */
    public double getChargeMagnitude() {
        return chargeMagnitude;
    }

    /**
     * @param chargeMagnitude the chargeMagnitude to set
     */
    public void setChargeMagnitude(double chargeMagnitude) {
        this.chargeMagnitude = chargeMagnitude;
    }
}
