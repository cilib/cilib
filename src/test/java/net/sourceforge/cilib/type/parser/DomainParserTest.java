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
package net.sourceforge.cilib.type.parser;

import net.sourceforge.cilib.type.types.container.StructuredType;
import net.sourceforge.cilib.type.types.container.Vector;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests related to the parsing of domain strings.
 */
public class DomainParserTest {

    /**
     * Creation of {@code StringType}.
     */
    @Test
    public void stringType() {
        StructuredType vector = DomainParser.parse("T");

        Assert.assertEquals(1, vector.size());
    }

    /**
     * The default kind of domain string that would be quite common.
     */
    @Test
    public void dimensionRange() {
        Vector vector = (Vector) DomainParser.parse("R(-9.0:9.0)^6");

        Assert.assertEquals(6, vector.size());
    }

    @Test
    public void infiniteBounds() {
        Vector vector = (Vector) DomainParser.parse("R^6");

        Assert.assertEquals(6, vector.size());
    }

    @Test
    public void singleInfiniteBounds() {
        Vector vector = (Vector) DomainParser.parse("R");

        Assert.assertEquals(1, vector.size());
    }

    @Test
    @Ignore("Who even uses this feature?")
    public void value() {
        Vector vector = (Vector) DomainParser.parse("R(8.0)^6");

        Assert.assertEquals(6, vector.size());
        for (int i = 0; i < vector.size(); i++) {
            Assert.assertEquals(8.0, vector.doubleValueOf(0), 0.001);
        }
    }

    @Test
    @Ignore("Who even uses this feature?")
    public void singleValue() {
        Vector vector = (Vector) DomainParser.parse("R(8.0)");

        Assert.assertEquals(1, vector.size());
        Assert.assertEquals(8.0, vector.doubleValueOf(0), 0.001);
    }

    @Test
    @Ignore("Who even uses this feature?")
    public void complex() {
        Vector vector = (Vector) DomainParser.parse("R(-9.0:9.0),R^6,R(9.0),B,Z");

        Assert.assertEquals(10, vector.size());
    }

    @Test(expected=RuntimeException.class)
    public void invalidDomain() {
        DomainParser.parse("Y");
    }

    @Test(expected=RuntimeException.class)
    public void parseNotValid() {
        DomainParser.parse("R(-5, -4, -5)^-7");
    }

    @Test(expected=RuntimeException.class)
    public void negativeExponent() {
        DomainParser.parse("R^-9");
    }

    @Test(expected=RuntimeException.class)
    public void zeroExponent() {
        DomainParser.parse("R^0");
    }

    @Test
    public void integerBounds() {
        DomainParser.parse("R(1:3)");
        DomainParser.parse("R(-1:3)");
        DomainParser.parse("R(-3:-1)");
        DomainParser.parse("R(-3:-1)^9");
    }

    @Test(expected=RuntimeException.class)
    public void incorrectBoundsOrder() {
        DomainParser.parse("R(3:2)"); // Lower bound > Upper bound = WRONG!
    }

     @Test
    public void testParseReal() {
        DomainParser.parse("R(0.0:9.0)");
        DomainParser.parse("R");
        DomainParser.parse("R^6");
        DomainParser.parse("R(-9.0:9.0)");
        DomainParser.parse("R(-30.0:30.0)^6");
    }


    @Test
    public void testParseBit() {
        DomainParser.parse("B");
        DomainParser.parse("B^6");
    }


    @Test
    public void testParseInteger() {
        DomainParser.parse("Z");
        DomainParser.parse("Z(-1:0)");
//        Parser.parse("Z(1)");
        DomainParser.parse("Z(0:1)");
        DomainParser.parse("Z(-999:999)");
        DomainParser.parse("Z^8");
        DomainParser.parse("Z(0:1)^10");
    }


    @Test
    public void testParseString() {
        DomainParser.parse("T^5");
    }

    @Test
    public void vectorReturn() {
        StructuredType type = DomainParser.parse("R^5");
        Assert.assertThat(type, is(Vector.class));
    }

}
