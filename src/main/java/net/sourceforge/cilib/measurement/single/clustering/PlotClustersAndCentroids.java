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

import java.util.ArrayList;

import net.sourceforge.cilib.algorithm.Algorithm;
import net.sourceforge.cilib.functions.clustering.ClusteringFunctions;
import net.sourceforge.cilib.io.DataTable;
import net.sourceforge.cilib.io.pattern.StandardPattern;
import net.sourceforge.cilib.measurement.Measurement;
import net.sourceforge.cilib.problem.clustering.ClusteringProblem;
import net.sourceforge.cilib.type.types.Bounds;
import net.sourceforge.cilib.type.types.Int;
import net.sourceforge.cilib.type.types.container.Cluster;
import net.sourceforge.cilib.type.types.container.TypeList;
import net.sourceforge.cilib.type.types.container.Vector;
import net.sourceforge.cilib.util.DistanceMeasure;
import net.sourceforge.cilib.util.Vectors;

/**
 * This measurement is handy when debugging a clustering in R^2 space using GNUplot. Logging should be disabled and no
 * other output should be written to standard out. To try it, start GNUplot, execute:<br/>
 * load "&lt;./simulator.sh path/to/your/cilib.config.file.xml -noprogress"<br/>
 * and enjoy.
 *
 * @author Theuns Cloete
 */
public class PlotClustersAndCentroids implements Measurement<Int> {
    private static final long serialVersionUID = -3463144715668304866L;

    @Override
    public Measurement getClone() {
        return this;
    }

    @Override
    public String getDomain() {
        return "Z";
    }

    /**
     * TODO: When we start using Guice, this method should be refactored
     */
    @Override
    public Int getValue(Algorithm algorithm) {
        ClusteringProblem problem = (ClusteringProblem) algorithm.getOptimisationProblem();
        int numberOfClusters = problem.getNumberOfClusters();
        ArrayList<Vector> centroids = ClusteringFunctions.disassembleCentroids((Vector) algorithm.getBestSolution().getPosition(), numberOfClusters);
        DataTable<StandardPattern, TypeList> dataTable = problem.getDataTable();
        DistanceMeasure distanceMeasure = problem.getDistanceMeasure();
        ArrayList<Cluster> clusters = ClusteringFunctions.cluster(centroids, dataTable, distanceMeasure, numberOfClusters);

//        System.out.println("reset");
//        System.out.println("set term jpeg medium");
//        System.out.println("set output \"iteration." + String.format("%04d", Algorithm.get().getIterations()) + ".jpg\"");
//        System.out.print("plot [-0.5:10][-5:5] sin(x) - 0.5, 0.5 - sin(x), ");
        System.out.println("set title '" + problem.getClusteringFunction().getClass().getSimpleName() + ": " + algorithm.getIterations() + " iterations'");
        System.out.print("plot ");

        for (int i = 0, n = clusters.size(); i < n; ++i) {
            if (!clusters.get(i).isEmpty()) {
                System.out.print("'-' title 'cluster" + i + "', ");
            }
        }

        for (int i = 0, n = clusters.size(); i < n; ++i) {
            System.out.print("'-' title 'centroid" + i + "'");

            if (i < n - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();

        for (Cluster cluster : clusters) {
            if (!cluster.isEmpty()) {
                for (StandardPattern pattern : cluster) {
                    System.out.println(Vectors.toString(pattern.getVector(), "", "", "\t"));
                }
                System.out.println("e");
            }
        }

        for (Cluster cluster : clusters) {
            System.out.println(Vectors.toString(cluster.getCentroid(), "", "", "\t"));
            System.out.println("e");
        }
        return Int.valueOf(0, new Bounds(0, 0));
    }
}
