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
package net.sourceforge.cilib.games.random;

import java.util.ArrayList;
import java.util.List;
import net.sourceforge.cilib.math.random.generator.MersenneTwister;

/**
 * This class contains a list of seed values to use. When a seed is requested the
 * current index in the list is returned, and the index moves to the next item in the list.
 * When the index reaches the end of the list, it goes back to the beginning.
 */
public class ListSeedingStrategy extends GameSeedingStrategy {
    private static final long serialVersionUID = 7525722649417922902L;
    protected List<Long> seeds;
    protected int index;
    public ListSeedingStrategy() {
        seeds = new ArrayList<Long>();
        index = -1;
    }

    /**
     * @param other
     */
    public ListSeedingStrategy(ListSeedingStrategy other) {
        super(other);
        seeds = new ArrayList<Long>();
        for(Long seed: other.seeds){
            seeds.add(seed);
        }
        index = other.index;
    }

    /**
     * Change the index that is used in the list of seeds.
     *
     */
    protected void updateIndex(){
        ++index;
        if(index >= seeds.size())
            index = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void seedGenerator() {
        updateIndex();
        currentSeed = seeds.get(index);
        generator = new MersenneTwister(currentSeed);
    }

    /**
     * Set a seed value to use
     * @param seed The seed value
     */
    public void setSeed(long seed){
        seeds.add(seed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameSeedingStrategy getClone() {
        return new ListSeedingStrategy(this);
    }

}
