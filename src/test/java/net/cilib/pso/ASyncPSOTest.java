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
package net.cilib.pso;

import org.junit.Ignore;
import fj.data.List;
import net.cilib.entity.FitnessProvider;
import net.cilib.entity.FitnessComparator;
import net.cilib.entity.ParticleProvider;
import fj.data.Option;
import net.cilib.entity.Particle;
import net.cilib.collection.Topology;
import net.cilib.collection.TopologyBuffer;
import net.cilib.collection.immutable.ImmutableGBestTopology;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static net.cilib.predef.Predef.*;

/**
 *
 * @author gpampara
 */
@Ignore("Pending")
public class ASyncPSOTest {

    @Test
    public void partiallyBuiltTopologyUsedAsProvidedTopologyForGuideSelection() {
        final PositionProvider p = mock(PositionProvider.class);
        final VelocityProvider v = mock(VelocityProvider.class);
        final FitnessProvider f = mock(FitnessProvider.class);
        final Topology<Particle> topology = mock(Topology.class);

        when(p.f(any(List.class), any(List.class))).thenReturn(solution(1.0));
        when(v.f(any(Particle.class), any(Topology.class))).thenReturn(velocity(0.0));
        when(f.evaluate(any(List.class))).thenReturn(Option.some(1.0));

        final ParticleProvider provider = new ParticleProvider(p, v, f, FitnessComparator.MAX);
        final ASyncPSO asp = new ASyncPSO(provider);

        final Topology<Particle> t = mock(Topology.class);
        final TopologyBuffer buffer = mock(TopologyBuffer.class);

        when(t.newBuffer()).thenReturn(buffer);
        when(buffer.build()).thenReturn(ImmutableGBestTopology.of());

//        Topology<Particle> t2 = asp.f(t);

        // A portion of the old topology must be dropped to create the new
        // partial topology
        verify(topology, times(1)).drop(anyInt());
    }
}