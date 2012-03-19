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

import java.io.File;
import java.util.List;
import net.sourceforge.cilib.io.exception.CIlibIOException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
public class CSVFileWriterTest {

    private static String testFilePath;

    @BeforeClass
    public static void setTestFilePath() {
        testFilePath = "src/test/resources/datasets/iris.data";
    }

    @Test
    public void testWrite() throws CIlibIOException {
        DataTableBuilder dataTableBuilder = new DataTableBuilder(new DelimitedTextFileReader());
        dataTableBuilder.getDataReader().setSourceURL(testFilePath);
        dataTableBuilder.buildDataTable();
        DataTable<List<String>,List<String>> dataTable = dataTableBuilder.getDataTable();

        CSVFileWriter writer = new CSVFileWriter();
        writer.setDestinationURL("./test.csv");
        writer.open();
        writer.write(dataTable);
        writer.close();

        File file = new File("./test.csv");
        Assert.assertTrue(file.length() > 0);

        testFilePath = "./test.csv";
        dataTableBuilder = new DataTableBuilder(new DelimitedTextFileReader());
        dataTableBuilder.getDataReader().setSourceURL(testFilePath);
        dataTableBuilder.buildDataTable();
        dataTable = dataTableBuilder.getDataTable();

        Assert.assertEquals(150, dataTable.getNumRows());
        Assert.assertEquals(5, dataTable.getNumColums());

        file.delete();
    }
}
