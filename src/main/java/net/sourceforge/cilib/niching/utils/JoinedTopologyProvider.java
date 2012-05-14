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
package net.sourceforge.cilib.niching.utils;

import fj.F;
import fj.data.List;
import net.sourceforge.cilib.entity.Entity;
import net.sourceforge.cilib.entity.EntityType;
import net.sourceforge.cilib.entity.Topology;
import net.sourceforge.cilib.niching.NichingSwarms;
import net.sourceforge.cilib.type.types.Int;
import net.sourceforge.cilib.util.functions.Algorithms;

public class JoinedTopologyProvider extends TopologyProvider {
    @Override
    public List<? extends Entity> f(NichingSwarms a) {
        return List.join(
            List.cons(a._1(), a._2())
                .map(Algorithms.getTopology())
                .map(new F<Topology<? extends Entity>, List<Entity>>() {
                    @Override
                    public List<Entity> f(Topology<? extends Entity> a) {
                        return List.iterableList((Topology<Entity>) a).filter(new F<Entity, Boolean>() {
                            @Override
                            public Boolean f(Entity a) {
                                return ((Int) a.getProperties().get(EntityType.Coevolution.POPULATION_ID)).intValue() == 0;
                            }                            
                        });
                    }
                })
            );
    }
}
