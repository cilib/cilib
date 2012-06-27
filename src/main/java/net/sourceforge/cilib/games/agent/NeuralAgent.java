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
package net.sourceforge.cilib.games.agent;


import java.util.List;
import net.sourceforge.cilib.functions.activation.ActivationFunction;
import net.sourceforge.cilib.games.agent.neural.NeuralOutputInterpretationStrategy;
import net.sourceforge.cilib.games.agent.neural.NeuralStateInputStrategy;
import net.sourceforge.cilib.games.game.Game;
import net.sourceforge.cilib.games.states.GameState;
import net.sourceforge.cilib.io.pattern.StandardPattern;
import net.sourceforge.cilib.nn.NeuralNetwork;
import net.sourceforge.cilib.nn.architecture.builder.LayerConfiguration;
import net.sourceforge.cilib.type.DomainRegistry;
import net.sourceforge.cilib.type.StringBasedDomainRegistry;
import net.sourceforge.cilib.type.types.Type;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 * An agent that uses a Neural Network to make decisions
 */
public class NeuralAgent extends Agent {
    /**
     *
     */
    private static final long serialVersionUID = 5910765539852468020L;
    //this determines how the game state is given to the Neural Network as input
    protected NeuralStateInputStrategy stateInputStrategy;
    //this determines how the output of the Neural Network alters the game state
    protected NeuralOutputInterpretationStrategy outputInterpretationStrategy;
    //the neural network
    protected NeuralNetwork neuralNetwork;
    /**
     * @param playerNo
     */
    public NeuralAgent() {
        super();
    }

    /**
     * @param other
     */
    public NeuralAgent(NeuralAgent other) {
        super(other);
        stateInputStrategy = other.stateInputStrategy;
        outputInterpretationStrategy = other.outputInterpretationStrategy;
        neuralNetwork = other.neuralNetwork; //CLONE?
    }

    private void initializeNeuralNetwork(){
        List<LayerConfiguration> layerConfigs = neuralNetwork.getArchitecture().getArchitectureBuilder().getLayerConfigurations();
        //set input layer, first layer
        layerConfigs.get(0).setSize(stateInputStrategy.amountInputs());
        //set output layer, last layer
        layerConfigs.get(layerConfigs.size() - 1).setSize(outputInterpretationStrategy.getAmOutputs());

        neuralNetwork.initialize();
    }

    public void setStateInputStrategy(NeuralStateInputStrategy stateInputStrategy){
        this.stateInputStrategy = stateInputStrategy;
        if(stateInputStrategy != null && outputInterpretationStrategy != null)
            initializeNeuralNetwork();
    }

    public void setOutputInterpretationStrategy(NeuralOutputInterpretationStrategy outputInterpretationStrategy){
        this.outputInterpretationStrategy = outputInterpretationStrategy;
        if(stateInputStrategy != null && outputInterpretationStrategy != null)
            initializeNeuralNetwork();
    }

    public void setWeights(Vector weights){
        //set the NN weights to the specified weight vector
        neuralNetwork.setWeights(weights);
    }
    /**
     * Scale the input value from the specified range to the active range of the input nodes for the NN
     * @param val the value to scale
     * @param min the lower bound
     * @param max the upper bound
     * @return
     */
    public double getScaledInput(double val, double min, double max){
         //scale to active range of activation function. need to get this from neural network object
        ActivationFunction function = neuralNetwork.getArchitecture().getLayers().get(1).get(0).getActivationFunction();

        double scaledMax = function.getUpperActiveRange();
        double scaledMin = function.getLowerActiveRange();
        return (((val - min) * (scaledMax - scaledMin)) / (max - min)) + scaledMin;
        //return val;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Agent getClone() {
        return new NeuralAgent(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move(Game<GameState> game) {
        //set the input of the neural network to
        Vector input = stateInputStrategy.getNeuralInputArray(this, game);
        StandardPattern pattern = new StandardPattern(input, Vector.of());
        //get the output vector
        Vector NNOutput = neuralNetwork.evaluatePattern(pattern);//perform NN iteration, get output
        outputInterpretationStrategy.applyOutputToState(NNOutput, this, game);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializeAgent(Type agentData) {
        if(!(agentData instanceof Vector))
            throw new RuntimeException("agentData is not a weight vector, unable to initialize Neural Agent");
        setWeights((Vector)agentData);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DomainRegistry getAgentDomain() {
        DomainRegistry agentDomain = new StringBasedDomainRegistry();
        double activationRange = 1.0;//1 / Math.sqrt(inputStrategy.amountInputs());
        String representation = "R(-" + activationRange + ":" + activationRange + ")^" + neuralNetwork.getWeights().size(); //need to get min and max as well?!?
        agentDomain.setDomainString(representation);
        return agentDomain;
    }

    public NeuralOutputInterpretationStrategy getOutputInterpretationStrategy() {
        return outputInterpretationStrategy;
    }

    public NeuralStateInputStrategy getStateInputStrategy() {
        return stateInputStrategy;
    }

    public NeuralNetwork getNeuralNetwork() {
        return neuralNetwork;
    }

    public void setNeuralNetwork(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
    }

}
