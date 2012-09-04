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
package net.sourceforge.cilib.entity.operators.crossover.parentprovider;

import net.sourceforge.cilib.util.Cloneable;
import net.sourceforge.cilib.entity.Entity;
import fj.F;
import java.util.List;

/**
 * Selects a parent to be used as the main parent for Crossover strategies that require
 * a variable number of parents.
 */
public abstract class ParentProvider extends F<List<Entity>, Entity> implements Cloneable {

	@Override
	public abstract ParentProvider getClone();
}
