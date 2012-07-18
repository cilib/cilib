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
package net.sourceforge.cilib.util.selection.recipes;

import com.google.common.collect.Lists;
import java.util.List;
import net.sourceforge.cilib.ec.Individual;
import net.sourceforge.cilib.entity.EntityType;
import net.sourceforge.cilib.entity.Topology;
import net.sourceforge.cilib.entity.topologies.GBestTopology;
import net.sourceforge.cilib.problem.MaximisationFitness;
import net.sourceforge.cilib.problem.MinimisationFitness;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.Matchers.hasItem;

/**
 *
 */
public class ElitistSelectorTest {

    @Test(expected = IllegalArgumentException.class)
    public void selectEmpty() {
        List<Integer> elements = Lists.newArrayList();
        ElitistSelector<Integer> selection = new ElitistSelector<Integer>();
        selection.on(elements).select();
    }

    @Test
    public void selectSingle() {
        List<Integer> elements = Lists.newArrayList(1);
        ElitistSelector<Integer> selection = new ElitistSelector<Integer>();
        int selected = selection.on(elements).select();
        Assert.assertThat(selected, is(1));
    }

    private static Topology<Individual> createDummyTopology() {
        Topology<Individual> topology = new GBestTopology<Individual>();
        Individual individual1 = new Individual();
        Individual individual2 = new Individual();
        Individual individual3 = new Individual();
        topology.add(individual1);
        topology.add(individual2);
        topology.add(individual3);
        return topology;
    }

    @Test
    public void minimizationSelection() {
        Topology<Individual> topology = createDummyTopology();
        topology.get(0).getProperties().put(EntityType.FITNESS, new MinimisationFitness(99.0));
        topology.get(1).getProperties().put(EntityType.FITNESS, new MinimisationFitness(8.0));
        topology.get(2).getProperties().put(EntityType.FITNESS, new MinimisationFitness(9.0));

        ElitistSelector<Individual> selection = new ElitistSelector<Individual>();
        Individual selected = selection.on(topology).select();

        Assert.assertThat(selected, is(notNullValue()));
        Assert.assertThat(topology, hasItem(selected));
        Assert.assertThat(selected, is(topology.get(1)));
    }

    @Test
    public void maximizationSelection() {
        Topology<Individual> topology = createDummyTopology();
        topology.get(0).getProperties().put(EntityType.FITNESS, new MaximisationFitness(99.0));
        topology.get(1).getProperties().put(EntityType.FITNESS, new MaximisationFitness(8.0));
        topology.get(2).getProperties().put(EntityType.FITNESS, new MaximisationFitness(9.0));

        ElitistSelector<Individual> selection = new ElitistSelector<Individual>();
        Individual selected = selection.on(topology).select();

        Assert.assertThat(selected, is(notNullValue()));
        Assert.assertThat(topology, hasItem(selected));
        Assert.assertThat(selected, is(topology.get(0)));
    }

    @Test
    public void elitistSelection() {
        List<Integer> elements = Lists.newArrayList(9, 8, 7, 6, 5, 4, 3, 2, 1);
        ElitistSelector<Integer> selection = new ElitistSelector<Integer>();
        int selected = selection.on(elements).select();
        Assert.assertThat(selected, is(9));
    }
}
