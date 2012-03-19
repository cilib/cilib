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
package net.sourceforge.cilib.games.items;
/**
 * This enum defines descriptors for any game items.
 *
 */
public enum GameToken implements GameEnum {
    DEFAULT;
    /**
     * {@inheritDoc}
     */
    public String getDescription(){
        switch(this){
            case DEFAULT:
                return "Default";
        }
        return "Unkown";
    }
    /**
     * Predator Prey tokens
     */
    public enum PredatorPrey implements GameEnum{
        PREDATOR,
        PREY;
        /**
         * {@inheritDoc}
         */
        public String getDescription(){
            switch(this){
                case PREDATOR:
                    return "P";
                case PREY:
                    return "Y";
            }
            return "Unkown";
        }
    }
    /**
     * Tick Tack Toe tokens
     *
     */
    public enum TicTacToe implements GameEnum{
        NOUGHT,
        CROSS;
        /**
         * {@inheritDoc}
         */
        public String getDescription(){
            switch(this){
                case NOUGHT:
                    return "O";
                case CROSS:
                    return "X";
            }
            return "UNKOWN";
        }
    }
    /**
     * Tetris tokens
     */
    public enum Tetris implements GameEnum{
        BOX,
        LSHAPE,
        RLSHAPE,
        LINE,
        T,
        ZIGZAG,
        RZIGZAG;
        /**
         * {@inheritDoc}
         */
        public String getDescription(){ //finish?!
            switch(this){
                case BOX:
                    return "B";
                case LSHAPE:
                    return "[";
                case RLSHAPE:
                    return "]";
                case LINE:
                    return "|";
                case T:
                    return "T";
                case ZIGZAG:
                    return "/";
                case RZIGZAG:
                    return "\\";
            }
            return "UNKOWN";
        }
    }
}
