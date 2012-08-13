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
package net.sourceforge.cilib.nn.architecture.visitors;

import net.sourceforge.cilib.functions.activation.Linear;
import net.sourceforge.cilib.io.pattern.StandardPattern;
import net.sourceforge.cilib.math.Maths;
import net.sourceforge.cilib.nn.NeuralNetwork;
import net.sourceforge.cilib.nn.architecture.builder.CascadeArchitectureBuilder;
import net.sourceforge.cilib.nn.architecture.builder.LayerConfiguration;
import net.sourceforge.cilib.type.types.container.Vector;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class CascadeVisitorTest {

    @Test
    public void testVisit() {
        Vector input = Vector.of(0.1, 0.2);
        StandardPattern pattern = new StandardPattern(input, input);
		
        NeuralNetwork network = new NeuralNetwork();
		network.getArchitecture().setArchitectureBuilder(new CascadeArchitectureBuilder());
        network.getArchitecture().getArchitectureBuilder().addLayer(new LayerConfiguration(2, new Linear()));
        network.getArchitecture().getArchitectureBuilder().addLayer(new LayerConfiguration(3, new Linear()));
        network.getArchitecture().getArchitectureBuilder().addLayer(new LayerConfiguration(2, new Linear()));
        network.getArchitecture().getArchitectureBuilder().getLayerBuilder().setDomain("R(-3:3)");
        network.initialize();

		Vector weights = Vector.of(0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0,1.1,1.2,
		                           1.3,1.4,1.5,1.6,1.7,1.8,1.9,2.0,2.1,2.2,2.3,2.4);
        network.setWeights(weights);

        ArchitectureOperationVisitor cascadeVisitor = new CascadeVisitor();
        cascadeVisitor.setInput(pattern);
        cascadeVisitor.visit(network.getArchitecture());

		//hidden neurons
		double hn1 = 0.1*0.1 + 0.2*0.2 + 0.3*-1;
		double hn2 = 0.4*0.1 + 0.5*0.2 + 0.6*-1 + 0.7*hn1;
		double hn3 = 0.8*0.1 + 0.9*0.2 + 1.0*-1 + 1.1*hn1 + 1.2*hn2;

		//assert outputs
		Assert.assertEquals(1.3*0.1 + 1.4*0.2 + 1.5*-1 + 1.6*hn1 + 1.7*hn2 + 1.8*hn3,
                            cascadeVisitor.getOutput().get(0).doubleValue(),
                            Maths.EPSILON);
		Assert.assertEquals(1.9*0.1 + 2.0*0.2 + 2.1*-1 + 2.2*hn1 + 2.3*hn2 + 2.4*hn3,
                            cascadeVisitor.getOutput().get(1).doubleValue(),
                            Maths.EPSILON);
    }

}
