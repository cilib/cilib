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

import java.util.Collection;

import net.sourceforge.cilib.problem.dataset.ClusterableDataSet.Pattern;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 * This is the Davies-Bouldin Validity Index.
 *
 * Index is given in
 * @Article{ daviesbouldin1979vi, title = "A Cluster Seperation Measure", author = "David L. Davies
 *           and Donald W. Bouldin", journal = "IEEE Transactions on Pattern Analysis and Machine
 *           Intelligence", volume = "1", number = "2", year = "1979", pages = "224--227", month =
 *           apr, issn = "0162-8828" }
 * NOTE: By default, the cluster center refers to the cluster centroid. See {@link ClusterCenterStrategy}.
 */
public class DaviesBouldinIndex extends ScatterSeperationRatio {
    private static final long serialVersionUID = -5167494843653998358L;

    public DaviesBouldinIndex() {
        super();
    }

    @Override
    public double calculateFitness() {
        double db = 0.0, max = -Double.MAX_VALUE;

        cacheWithinClusterScatter();
        cacheBetweenClusterSeperation();

        for (int i = 0; i < clustersFormed; i++) {
            double withinScatterLeft = getWithinClusterScatter(i);
            for (int j = 0; j < clustersFormed; j++) {
                if (i != j) {
                    double withinScatterRight = getWithinClusterScatter(j);
                    double betweenSeperation = getBetweenClusterSeperation(i, j);
                    max = Math.max(max, (withinScatterLeft + withinScatterRight) / betweenSeperation);
                }
            }
            db += max;
            max = -Double.MAX_VALUE;
        }
        return db / clustersFormed;
    }

    @Override
    protected double calculateWithinClusterScatter(int k) {
        double withinClusterScatter = 0.0;
        Collection<Pattern> cluster = arrangedClusters.get(k).values();
        Vector center = clusterCenterStrategy.getCenter(k);

        for (Pattern pattern : cluster) {
            withinClusterScatter += helper.calculateDistance(pattern.data, center);
        }
        return withinClusterScatter / cluster.size();
    }

    /**
     * The <i>alpha</i> value of the distance measure should correspond to the <i>p</i> value of the
     * Davies-Bouldin Validity Index.
     */
    @Override
    protected double calculateBetweenClusterSeperation(int i, int j) {
        return helper.calculateDistance(clusterCenterStrategy.getCenter(i), clusterCenterStrategy.getCenter(j));
    }
}
