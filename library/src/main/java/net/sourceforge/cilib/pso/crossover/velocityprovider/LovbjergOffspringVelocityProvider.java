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
package net.sourceforge.cilib.pso.crossover.velocityprovider;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import java.util.List;
import net.sourceforge.cilib.entity.Particle;
import net.sourceforge.cilib.type.types.container.StructuredType;
import net.sourceforge.cilib.type.types.container.Vector;
import net.sourceforge.cilib.util.Vectors;

/**
 * This OffspringVelocityProvider calculates an offspring's velocity according to
 * Lovbjerg et al's hybrid PSO:
 * <p>
 * @INPROCEEDINGS{Løvbjerg01hybridparticle,
 *   author = {Morten Løvbjerg and Thomas Kiel Rasmussen and Thiemo Krink},
 *   title = {Hybrid Particle Swarm Optimiser with Breeding and Subpopulations},
 *   booktitle = {Proceedings of the Genetic and Evolutionary Computation Conference (GECCO-2001},
 *   year = {2001},
 *   pages = {469--476},
 *   publisher = {Morgan Kaufmann}
 * }
 * </p>
 */
public class LovbjergOffspringVelocityProvider extends OffspringVelocityProvider {
    @Override
    public StructuredType f(List<Particle> parent, Particle offspring) {
        Vector velocity = (Vector) offspring.getVelocity();
        
        return Vectors.sumOf(Lists.transform(parent, new Function<Particle, Vector>() {
            @Override
            public Vector apply(Particle f) {
                return (Vector) f.getVelocity();
            }            
        })).normalize().multiply(velocity.length());
    }
}
