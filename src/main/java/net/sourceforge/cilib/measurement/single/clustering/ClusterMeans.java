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
package net.sourceforge.cilib.measurement.single.clustering;

import java.util.List;

import net.sourceforge.cilib.algorithm.Algorithm;
import net.sourceforge.cilib.functions.clustering.ClusteringFunctions;
import net.sourceforge.cilib.io.DataTable;
import net.sourceforge.cilib.io.pattern.StandardPattern;
import net.sourceforge.cilib.measurement.Measurement;
import net.sourceforge.cilib.problem.clustering.ClusteringProblem;
import net.sourceforge.cilib.type.types.Type;
import net.sourceforge.cilib.type.types.container.Cluster;
import net.sourceforge.cilib.type.types.container.TypeList;
import net.sourceforge.cilib.type.types.container.Vector;
import net.sourceforge.cilib.util.DistanceMeasure;

/**
 * Combines and measures the mean vectors of the clusters optimised by the given algorithm.
 *
 * @author Theuns Cloete
 */
public class ClusterMeans implements Measurement {
    private static final long serialVersionUID = -3232035478177927100L;

    @Override
    public Measurement getClone() {
        return this;
    }

    @Override
    public String getDomain() {
        return "(R^?)^?";
    }

    /**
     * TODO: When we start using Guice, this method should be refactored
     */
    @Override
    public Type getValue(Algorithm algorithm) {
        ClusteringProblem problem = (ClusteringProblem) algorithm.getOptimisationProblem();
        int numberOfClusters = problem.getNumberOfClusters();
        List<Vector> centroids = ClusteringFunctions.disassembleCentroids((Vector) algorithm.getBestSolution().getPosition(), numberOfClusters);
        DataTable<StandardPattern, TypeList> dataTable = problem.getDataTable();
        DistanceMeasure distanceMeasure = problem.getDistanceMeasure();
        List<Cluster> clusters = ClusteringFunctions.cluster(centroids, dataTable, distanceMeasure, numberOfClusters);
        Vector.Builder combined = Vector.newBuilder();

        for (Cluster cluster : clusters) {
            combined.copyOf(cluster.getMean());
        }
        return combined.build();
    }
}
