/**           __  __
 *    _____ _/ /_/ /_    Computational Intelligence Library (CIlib)
 *   / ___/ / / / __ \   (c) CIRG @ UP
 *  / /__/ / / / /_/ /   http://cilib.net
 *  \___/_/_/_/_.___/
 */
package net.cilib.functions.continuous.unconstrained;

import net.cilib.functions.ContinuousFunction;
import net.cilib.type.types.container.Vector;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class Bohachevsky1Test {

    private ContinuousFunction function;

    @Before
    public void instantiate() {
        this.function = new Bohachevsky1();
    }

    /**
     * Test of evaluate method, of class {@link Bohachevsky1}.
     */
    @Test
    public void testEvaluate() {
        Vector x = Vector.of(1.0, 2.0);
        assertEquals(9.6, function.f(x), 0.0);

        x.setReal(0, 0.0);
        x.setReal(1, 0.0);
        assertEquals(0.0, function.f(x), 0.0);
    }

    /**
     * Test argument with invalid dimension.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testInvalidEvaluate() {
        function.f(Vector.of(1.0, 2.0, 3.0));
    }
}
