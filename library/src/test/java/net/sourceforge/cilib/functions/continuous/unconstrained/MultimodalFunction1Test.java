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
import org.junit.Test;
import org.junit.Before;

public class MultimodalFunction1Test {
    private ContinuousFunction function;

    @Before
    public void instantiate() {
        this.function = new MultimodalFunction1();
    }

    /**
     * Test of evaluate method, of class {@link MultimodalFunction1}.
     */
    @Test
    public void testApply() {
        Vector x = Vector.of(0.0, 0.0);

        //test minimum
        assertEquals(0.0, function.f(x), 0.0);

        //test another point
        x.setReal(0, 0.1);
        x.setReal(1, 0.5);
        assertEquals(2.0, function.f(x), 0.0);
    }
}
