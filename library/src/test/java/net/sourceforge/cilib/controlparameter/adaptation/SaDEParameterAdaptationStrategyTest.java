/**           __  __
 *    _____ _/ /_/ /_    Computational Intelligence Library (CIlib)
 *   / ___/ / / / __ \   (c) CIRG @ UP
 *  / /__/ / / / /_/ /   http://cilib.net
 *  \___/_/_/_/_.___/
 */
package net.cilib.controlparameter.adaptation;

import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;
import net.cilib.controlparameter.adaptation.SaDEParameterAdaptationStrategy;
import net.cilib.controlparameter.SettableControlParameter;
import net.cilib.controlparameter.StandardUpdatableControlParameter;
import net.cilib.math.random.GaussianDistribution;
import net.cilib.controlparameter.initialisation.RandomBoundedParameterInitialisationStrategy;
import net.cilib.ec.ParameterisedIndividual;

public class SaDEParameterAdaptationStrategyTest {

    @Test
    public void changeTest() {
        SaDEParameterAdaptationStrategy strategy = new SaDEParameterAdaptationStrategy();
        SettableControlParameter parameter = new StandardUpdatableControlParameter();
        parameter.setParameter(5.2);
        strategy.change(parameter);

        Assert.assertFalse(parameter.getParameter() == 5.2);
    }

    @Test
    public void recalculateAdaptiveVariablesTest() {
        ArrayList<Double> learningExperience = new ArrayList<Double>();
        learningExperience.add(2.1);
        learningExperience.add(1.4);
        learningExperience.add(6.3);
        learningExperience.add(3.0);

        SaDEParameterAdaptationStrategy strategy = new SaDEParameterAdaptationStrategy();
        strategy.setLearningExperience(learningExperience);

        strategy.recalculateAdaptiveVariables();

        Assert.assertTrue(strategy.getMean() == 3.2);
    }

    @Test
    public void acceptedTest() {
        SaDEParameterAdaptationStrategy strategy = new SaDEParameterAdaptationStrategy();
        SettableControlParameter parameter = new StandardUpdatableControlParameter();
        parameter.setParameter(5.2);
        strategy.accepted(parameter,new ParameterisedIndividual(), true);
        double result = strategy.getLearningExperience().get(0);

        Assert.assertTrue(5.2 == result);
    }

    @Test
    public void getLearningExperienceTest() {
        ArrayList<Double> learningExperience = new ArrayList<Double>();
        learningExperience.add(2.1);
        learningExperience.add(1.4);
        learningExperience.add(6.3);
        learningExperience.add(3.0);

        SaDEParameterAdaptationStrategy strategy = new SaDEParameterAdaptationStrategy();
        strategy.setLearningExperience(learningExperience);

        Assert.assertEquals(learningExperience, strategy.getLearningExperience());

    }

    @Test
    public void setLearningExperienceTest() {
        ArrayList<Double> learningExperience = new ArrayList<Double>();
        learningExperience.add(2.1);
        learningExperience.add(1.4);
        learningExperience.add(6.3);
        learningExperience.add(3.0);

        SaDEParameterAdaptationStrategy strategy = new SaDEParameterAdaptationStrategy();
        strategy.setLearningExperience(learningExperience);

        Assert.assertEquals(learningExperience, strategy.getLearningExperience());
    }

    @Test
    public void getMean() {
       SaDEParameterAdaptationStrategy strategy = new SaDEParameterAdaptationStrategy();
       strategy.setMean(3.0);

       Assert.assertTrue(strategy.getMean() == 3.0);
    }

    @Test
    public void setMean() {
       SaDEParameterAdaptationStrategy strategy = new SaDEParameterAdaptationStrategy();
       strategy.setMean(3.0);

       Assert.assertTrue(strategy.getMean() == 3.0);
    }

    @Test
    public void getRandom() {
       SaDEParameterAdaptationStrategy strategy = new SaDEParameterAdaptationStrategy();
       GaussianDistribution distribution = new GaussianDistribution();
       strategy.setRandom(distribution);

       Assert.assertEquals(distribution, strategy.getRandom());
    }

    @Test
    public void setRandom() {
        SaDEParameterAdaptationStrategy strategy = new SaDEParameterAdaptationStrategy();
        GaussianDistribution distribution = new GaussianDistribution();
        strategy.setRandom(distribution);

        Assert.assertEquals(distribution, strategy.getRandom());
    }

    @Test
    public void getInitialisationStrategy() {
       SaDEParameterAdaptationStrategy strategy = new SaDEParameterAdaptationStrategy();
       RandomBoundedParameterInitialisationStrategy initStrategy = new RandomBoundedParameterInitialisationStrategy();
       strategy.setInitialisationStrategy(initStrategy);

       Assert.assertEquals(initStrategy, strategy.getInitialisationStrategy());
    }

    @Test
    public void setInitialisationStrategy() {
       SaDEParameterAdaptationStrategy strategy = new SaDEParameterAdaptationStrategy();
       RandomBoundedParameterInitialisationStrategy initStrategy = new RandomBoundedParameterInitialisationStrategy();
       strategy.setInitialisationStrategy(initStrategy);

       Assert.assertEquals(initStrategy, strategy.getInitialisationStrategy());
    }
}
