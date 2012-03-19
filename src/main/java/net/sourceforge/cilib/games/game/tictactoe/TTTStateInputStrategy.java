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
package net.sourceforge.cilib.games.game.tictactoe;

import net.sourceforge.cilib.games.agent.NeuralAgent;
import net.sourceforge.cilib.games.agent.neural.NeuralStateInputStrategy;
import net.sourceforge.cilib.games.game.Game;
import net.sourceforge.cilib.games.game.GridGame;
import net.sourceforge.cilib.games.items.GameItem;
import net.sourceforge.cilib.games.items.PlayerItem;
import net.sourceforge.cilib.games.states.GridGameState;
import net.sourceforge.cilib.type.types.Real;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 * This class is used to give a Neural Network a Tic Tac Toe game.
 * For this {@linkplain NeuralStateInputStrategy} the Neural Network needs to have a number of inputs equals to the number of cells in the game.
 * Each input is then given to the network depending if the cell is occupied by the current player, the opponent player or if its empty.
 *
 */
public class TTTStateInputStrategy extends NeuralStateInputStrategy {

    public TTTStateInputStrategy() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int amountInputs() {
        return 9; //need to find a better way of doing this, hardcoding the value is bad mkay.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vector getNeuralInputArray(NeuralAgent currentPlayer, Game state) {
        Vector input = new Vector();
        int Width = ((GridGame)state).getWidth();
        int Height = ((GridGame)state).getHeight();
        GridGameState gstate = (GridGameState)state.getDecisionState();

        for(int j = 0; j < Height; ++j){
            for(int i = 0; i < Width; ++i){
                GameItem item =gstate.getItem(i, j);
                if(item != null){
                    if(((PlayerItem)item).getPlayerID() == currentPlayer.getPlayerID())
                        input.add(Real.valueOf(currentPlayer.getScaledInput(1, -1, 1))); //it is me
                    else
                        input.add(Real.valueOf(currentPlayer.getScaledInput(-1, -1, 1))); //it is not me
                }
                else
                    input.add(Real.valueOf(currentPlayer.getScaledInput(0, -1, 1))); //it is nobody
            }
        }
        return input;
    }

}
