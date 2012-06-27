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

import net.sourceforge.cilib.games.agent.NeuralAgent;
import net.sourceforge.cilib.games.agent.neural.NeuralStateInputStrategy;
import net.sourceforge.cilib.games.game.Game;
import net.sourceforge.cilib.games.items.GameToken;
import net.sourceforge.cilib.games.items.GridLocation;
import net.sourceforge.cilib.games.states.ListGameState;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 * This is the neural input strategy for a Predator or Prey agent. 
 * It determines how the game is described to the {@linkplain NeuralAgent}
 */
public class PredatorPreyNeuralInputStrategy extends NeuralStateInputStrategy {

    public PredatorPreyNeuralInputStrategy() {
    }

    /**
     * This {@linkplain NeuralStateInputStrategy} requires 4 input units. 
     * The first two is the position of the Predator agent, and the 2nd two is the position of the Prey agent.
     */
    @Override
    public int amountInputs() {
        return 4;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector getNeuralInputArray(NeuralAgent currentPlayer, Game state) {
        if (!(state instanceof PredatorPreyGame)) {
            throw new RuntimeException("Invalid game for this agent");
        }
        
        ListGameState lstate = (ListGameState) state.getDecisionState();
        Vector predPos = null, preyPos = null;
        
        for (int i = 0; i < lstate.getSize(); i++) {
            if (lstate.getItem(i).getToken().equals(GameToken.PredatorPrey.PREDATOR)) {
                predPos = ((GridLocation) lstate.getItem(i).getLocation()).getLocation();
            } else {
                preyPos = ((GridLocation) lstate.getItem(i).getLocation()).getLocation();
            }
        }
        
        Vector.Builder inputvector = Vector.newBuilder();
        inputvector.add(currentPlayer.getScaledInput((double) predPos.intValueOf(0), 0, ((PredatorPreyGame) state).getBoardWidth()));
        inputvector.add(currentPlayer.getScaledInput((double) predPos.intValueOf(1), 0, ((PredatorPreyGame) state).getBoardHeight()));
        inputvector.add(currentPlayer.getScaledInput((double) preyPos.intValueOf(0), 0, ((PredatorPreyGame) state).getBoardWidth()));
        inputvector.add(currentPlayer.getScaledInput((double) preyPos.intValueOf(1), 0, ((PredatorPreyGame) state).getBoardHeight()));
        
        return inputvector.build();
    }
}
