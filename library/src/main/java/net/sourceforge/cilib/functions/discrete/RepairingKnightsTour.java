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
package net.sourceforge.cilib.functions.discrete;

import net.sourceforge.cilib.functions.DiscreteFunction;
import net.sourceforge.cilib.type.types.container.Vector;

/**
 * An implementation of the Knight's Tour problem function. It is important to note
 * that this implementation implements a repair to the evaluation of the potential
 * solution. The reasoning behind this is that the knight all too often during the
 * testing phase would jump off the board and remain off the board.
 *
 * The repair is a simple in-order operation that determines the position where the
 * error occoured and tries to replace the error move with one of the possible 7
 * moves remaining. Once the tour can continue, the move is replaced and evaluation
 * continues.
 *
 */
public class RepairingKnightsTour implements DiscreteFunction {
    private static final long serialVersionUID = 6961834833997387285L;
    private int boardSize;
    private boolean cyclic;
    private String startingPos = null;
    private int startX;
    private int startY;
    private static final int[] MOVEMENT_X = {-2, -1,  1,  2, 2, 1, -1, -2};
    private static final int[] MOVEMENT_Y = {-1, -2, -2, -1, 1, 2, -2, -1};

    /**
     *
     */
    public RepairingKnightsTour() {
        this.boardSize = 8;
        this.cyclic = false;
        this.startX = 0;
        this.startY = 0;
//        setDomain("B^"+boardSize*boardSize*3);
    }

    /**
     *
     */
    @Override
    public Integer apply(Vector input) {
        int fitness = 0;

        int row = startX;
        int col = startY;

        boolean[][] visited = new boolean[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                visited[i][j] = false;
            }
        }

        int move = 0;
        while (true) {
            if (0 <= row && row < boardSize && 0 <= col && col < boardSize) {
                int moveNum = decode(input.booleanValueOf(move * 3), input.booleanValueOf(move * 3 + 1), input.booleanValueOf(move * 3 + 2));
                if (!visited[row][col]) {
                    fitness++;
                    move++;
//                    if (move == getMaximum() - 1) {
//                        break;
//                    }

                    visited[row][col] = true;
                }

                row += MOVEMENT_X[moveNum];
                col += MOVEMENT_Y[moveNum];

                if (0 <= row && row < boardSize && 0 <= col && col < boardSize && !visited[row][col]) {
                    // Nothing to do :) No need to repair
                } else {
                    row -= MOVEMENT_X[moveNum];
                    col -= MOVEMENT_Y[moveNum];

                    int test = 0;

                    for (int k = 0; k < boardSize; k++) {
                        if (k == moveNum) {
                            // Nothing to do :)
                        } else {
                            int newX = row + MOVEMENT_X[k];
                            int newY = col + MOVEMENT_Y[k];

                            if (0 <= newX && newX < boardSize && 0 <= newY && newY < boardSize && !visited[newX][newY]) {
                                row = newX;
                                col = newY;

                                test++;

                                modifyBits(input, move, k);

                                break;
                            }
                        }
                    }
                    if (test == 0) {
                        break;
                    }
                }

            } else {
                break;
            }
        }

        return fitness;
    }

    /**
     *
     * @param b1
     * @param b2
     * @param b3
     * @return
     */
    private int decode(boolean b1, boolean b2, boolean b3) {
        int i0 = b1 ? 1 : 0;
        int i1 = b2 ? 1 : 0;
        int i2 = b3 ? 1 : 0;
        return 4*i0 + 2*i1 + i2;
    }

    /**
     *
     * @param x
     * @param move
     * @param k
     */
    private void modifyBits(Vector x, int move, int k) {
        x.setInt(move * 3, (k >> 2) & 1);
        x.setInt(move * 3, (k >> 1) & 1);
        x.setInt(move * 3, k & 1);
    }

    /**
     * @return Returns the boardSize.
     */
    public int getBoardSize() {
        return boardSize;
    }

    /**
     * @param boardSize The boardSize to set.
     */
    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
//        setDomain("B^"+boardSize*boardSize*3);
    }

    /**
     * @return Returns the cyclic.
     */
    public boolean isCyclic() {
        return cyclic;
    }

    /**
     * @param cyclic The cyclic to set.
     */
    public void setCyclic(boolean cyclic) {
        this.cyclic = cyclic;
    }

    /**
     * @return Returns the startingPos.
     */
    public String getStartingPos() {
        return startingPos;
    }

    /**
     * Set the starting position on the board. Following the normal chess convention
     * the rows are described by letters (A-H) on the 8x8 board and columns (0-7).
     * These row and column values can obviously be adjusted to the currenly set board
     * size.
     *
     * @param startingPos The startingPos to set.
     */
    public void setStartingPos(String startingPos) {
        this.startingPos = startingPos;
    }
//
//    @Override
//    public String getDomain() {
//        return "B^"+boardSize*boardSize*3;
//    }
}
