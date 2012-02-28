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
package net.sourceforge.cilib.functions.clustering.validityindices;

import java.util.ArrayList;
import java.util.Collection;

import net.sourceforge.cilib.controlparameter.ConstantControlParameter;
import net.sourceforge.cilib.controlparameter.ControlParameter;
import net.sourceforge.cilib.functions.clustering.ClusteringFitnessFunction;
import net.sourceforge.cilib.functions.clustering.clustercenterstrategies.ClusterMeanStrategy;
import net.sourceforge.cilib.math.Stats;
import net.sourceforge.cilib.problem.dataset.ClusterableDataSet.Pattern;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 * This is the Veenman-Reinders-Backer Validity Index.
 *
 * The description is given in Section 1 of<br/>
 * <pre>
 * {@literal @}Article{ 628823, author = "Cor J. Veenman and Marcel J. T. Reinders and Eric Backer", title = "A
 *           Maximum Variance Cluster Algorithm", journal = "IEEE Transactions on Pattern Analysis
 *           and Machine Intelligence", volume = "24", number = "9", year = "2002", issn =
 *           "0162-8828", pages = "1273--1280", doi =
 *           "http://dx.doi.org/10.1109/TPAMI.2002.1033218", publisher = "IEEE Computer Society",
 *           address = "Washington, DC, USA", }
 * </pre>
 * NOTE: By default, the cluster center refers to the cluster mean. See {@link ClusterCenterStrategy}.
 * @author Theuns Cloete
 */
public class VeenmanReindersBackerIndex extends ClusteringFitnessFunction {
    private static final long serialVersionUID = 5683593481233814465L;
    /** The best value for the varianceLimit should be determined empirically */
    private ControlParameter maximumVariance = null;

    public VeenmanReindersBackerIndex() {
        super();
        clusterCenterStrategy = new ClusterMeanStrategy();
        maximumVariance = ConstantControlParameter.of(1.0);    // default variance limit is 1.0
    }

    @Override
    public double calculateFitness() {
        if (!holdsConstraint())
            return worstFitness();

        double sumOfSquaredError = 0.0;

        for (int i = 0; i < arrangedClusters.size(); i++) {
            Collection<Pattern> cluster = arrangedClusters.get(i).values();
            Vector center = clusterCenterStrategy.getCenter(i);

            // H(Y) in the paper refers to the homogeneity of Y (not variance, because we do not divide by |Y|)
            for (Pattern pattern : cluster) {
                sumOfSquaredError += Math.pow(helper.calculateDistance(pattern.data, center), 2);
            }
        }
        return sumOfSquaredError / (double) helper.getNumberOfPatternsInDataSet();
    }

    private boolean holdsConstraint() {
        for (int i = 0; i < clustersFormed - 1; i++) {
            for (int j = i + 1; j < clustersFormed; j++) {
                Collection<Pattern> union = new ArrayList<Pattern>();
                union.addAll(arrangedClusters.get(i).values());
                union.addAll(arrangedClusters.get(j).values());

                if (Stats.variance(union, helper.getDataSetMean()) < getMaximumVariance()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setMaximumVariance(ControlParameter cpus) {
        maximumVariance = cpus;
    }

    private double getMaximumVariance() {
        return maximumVariance.getParameter();
    }

    public void updateControlParameters() {
        maximumVariance.updateParameter();
    }
}
