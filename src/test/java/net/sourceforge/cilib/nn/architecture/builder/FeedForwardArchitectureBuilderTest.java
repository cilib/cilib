/**
 * Computational Intelligence Library (CIlib) Copyright (C) 2003 - 2010
 * Computational Intelligence Research Group (CIRG@UP) Department of Computer
 * Science University of Pretoria South Africa
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any
 * later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, see <http://www.gnu.org/licenses/>.
 */
package net.sourceforge.cilib.nn.architecture.builder;

import net.sourceforge.cilib.math.Maths;
import net.sourceforge.cilib.nn.NeuralNetwork;
import org.junit.Assert;
import org.junit.Test;

public class FeedForwardArchitectureBuilderTest {

    @Test
    public void testBuildArchitecture() {
        NeuralNetwork network = new NeuralNetwork();
        network.getArchitecture().getArchitectureBuilder().addLayer(new LayerConfiguration(5));
        network.getArchitecture().getArchitectureBuilder().addLayer(new LayerConfiguration(3));
        network.getArchitecture().getArchitectureBuilder().addLayer(new LayerConfiguration(3, false));
        network.getArchitecture().getArchitectureBuilder().addLayer(new LayerConfiguration(2));
        network.getArchitecture().getArchitectureBuilder().getLayerBuilder().setDomain("R(-3:3)");
        network.initialize();

        //assert num layers
        Assert.assertEquals(4, network.getArchitecture().getNumLayers());

        //assert fully connected
        Assert.assertEquals(36, network.getWeights().size());

        //assert biasses
        Assert.assertEquals(true, network.getArchitecture().getLayers().get(0).isBias());
        int layerSize = network.getArchitecture().getLayers().get(0).size();
        Assert.assertEquals(6, layerSize);
        Assert.assertEquals(-1, network.getArchitecture().getLayers().get(0).getNeuralInput(layerSize - 1), Maths.EPSILON);

        Assert.assertEquals(true, network.getArchitecture().getLayers().get(1).isBias());
        layerSize = network.getArchitecture().getLayers().get(1).size();
        Assert.assertEquals(4, layerSize);
        Assert.assertEquals(-1, network.getArchitecture().getLayers().get(1).getNeuralInput(layerSize - 1), Maths.EPSILON);

        Assert.assertEquals(false, network.getArchitecture().getLayers().get(2).isBias());
        layerSize = network.getArchitecture().getLayers().get(2).size();
        Assert.assertEquals(3, layerSize);

        Assert.assertEquals(false, network.getArchitecture().getLayers().get(3).isBias());
        layerSize = network.getArchitecture().getLayers().get(3).size();
        Assert.assertEquals(2, layerSize);
    }
}
