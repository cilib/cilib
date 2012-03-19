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
package net.sourceforge.cilib.games.game.predatorprey;

import net.sourceforge.cilib.games.agent.neural.NeuralOutputInterpretationStrategy;
import net.sourceforge.cilib.games.agent.Agent;
import net.sourceforge.cilib.games.game.Game;
import net.sourceforge.cilib.games.items.GameToken;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 * This is a {@linkplain NeuralOutputInterpretationStrategy} that will interperet the output of a neural network to make a movement decision for a Predator agent.
 */
public class NeuralPredatorOutputStrategy extends NeuralOutputInterpretationStrategy {

    public NeuralPredatorOutputStrategy() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyOutputToState(Vector outputData, Agent currentPlayer, Game oldState) {
        if (!(oldState instanceof PredatorPreyGame)) {
            throw new RuntimeException("Invalid game for this agent");
        }
        if (!currentPlayer.getAgentToken().equals(GameToken.PredatorPrey.PREDATOR)) {
            throw new RuntimeException("This strategy can only be used on a prey player");
        }
        PredatorPreyGame game = (PredatorPreyGame) oldState;
        int x = 0;
        if (outputData.doubleValueOf(0) > 0.0) //move on x axis
        {
            if (outputData.doubleValueOf(1) > 0.0) {
                x = 1;
            } else {
                x = -1;
            }
        }
        int y = 0;
        if (outputData.doubleValueOf(2) > 0.0) //move on y axis
        {
            if (outputData.doubleValueOf(3) > 0.0) //move down
            {
                y = 1;
            } else {
                y = -1;
            }
        }

        game.movePlayer(currentPlayer.getPlayerID(), x, y);
    }

    /**
     * This strategy requires 4 outputs:
     * The first determines movement on the x axis
     * the second determines if that movement is left or right
     * The third determines movement on the y axes
     * The fourth determines if it is up or down
     */
    @Override
    public int getAmOutputs() {
        return 4;
    }
}
