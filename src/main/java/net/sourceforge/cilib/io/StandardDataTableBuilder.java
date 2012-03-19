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
package net.sourceforge.cilib.io;

/**
 * A class that builds a new instance of a DataTable object. By changing the type
 * of {@link #dataTable dataTable} and {@link #dataReader dataReader} that is used,
 * the behaviour changes. The default behaviour is to read a text file from the local
 * machine to a text datatable.
 */
public class StandardDataTableBuilder {}//implements DataTableBuilder {
//
//    private DataTable dataTable;
//    private DataReader dataReader;
//    private List<DataOperator> operatorPipeline;
//
//    /**
//     * Default empty constructor
//     */
//    public StandardDataTableBuilder() {
//        dataReader = new DelimitedTextFileReader();
//        dataTable = new StandardDataTable();
//        operatorPipeline = new LinkedList<DataOperator>();
//    }
//
//    /**
//     * Default constructor. Initializes dataTable to a new TextDataTable and
//     * dataReader to be a new TextDataReader.
//     * @param reader
//     */
//    public StandardDataTableBuilder(DataReader reader) {
//        dataReader = reader;
//        dataTable = new StandardDataTable();
//        operatorPipeline = new LinkedList<DataOperator>();
//    }
//
//    /**
//     * This method reads all rows from the {@link #dataReader DataReader} object and
//     * adds them into the {@link #dataTable DataTable} object. If the default
//     * behaviour is not sufficient or desired, method should be overriden.
//     * @return the constructed datatable.
//     * @throws CIlibIOException wraps another Exception that might occur during IO
//     */
//    public DataTable buildDataTable() throws IOException {
//        for (Object object : dataReader) {
//            dataTable.addRow(object);
//        }
//        dataTable.setColumnNames(dataReader.getColumnNames());
//        dataReader.close();
//        for (DataOperator operator : operatorPipeline) {
//            this.setDataTable(operator.operate(this.getDataTable()));
//        }
//        return (DataTable) this.dataTable.getClone();
//    }
//
//    /**
//     * Adds a DataOperator to the pipeline.
//     * @param dataOperator a new DataOperator.
//     */
//    public void addDataOperator(DataOperator dataOperator) {
//        operatorPipeline.add(dataOperator);
//    }
//
//    /**
//     * Get the DataReader object.
//     * @return the data reader.
//     */
//    public DataReader getDataReader() {
//        return dataReader;
//    }
//
//    /**
//     * Set the DataReader object.
//     * @param dataReader the data reader object.
//     */
//    public void setDataReader(DataReader dataReader) {
//        this.dataReader = dataReader;
//    }
//
//    /**
//     * Get the DataTable object.
//     * @return the data table.
//     */
//    public DataTable getDataTable() {
//        return (DataTable) this.dataTable.getClone();
//    }
//
//    /**
//     * Sets the DataTable object.
//     * @param dataTable the data table.
//     */
//    public void setDataTable(DataTable dataTable) {
//        this.dataTable = dataTable;
//    }
//
//    /**
//     * Gets the operator pipeline.
//     * @return the operator pipeline.
//     */
//    public List<DataOperator> getOperatorPipeline() {
//        return operatorPipeline;
//    }
//
//    /**
//     * Sets the operator pipeline.
//     * @param operatorPipeline the new operator pipeline.
//     */
//    public void setOperatorPipeline(List<DataOperator> operatorPipeline) {
//        this.operatorPipeline = operatorPipeline;
//    }
//
//    /**
//     * Convenience method for getting the source URL that the datatable is built
//     * from. Delegates to: {@link #dataReader dataReader} object.
//     * @return the source URL.
//     */
//    public String getSourceURL() {
//        return this.dataReader.getSourceURL();
//    }
//
//    /**
//     * Convenience method for setting the source URL that the datatable is built
//     * from. Delegates to: {@link #dataReader dataReader} object.
//     * @param sourceURL the new source URL.
//     */
//    public void setSourceURL(String sourceURL) {
//        this.dataReader.setSourceURL(sourceURL);
//    }
//}
