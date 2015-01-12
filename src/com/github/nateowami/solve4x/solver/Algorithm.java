/*
    Solve4x - An algebra solver that shows its work
    Copyright (C) 2014 Solve4x project

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.nateowami.solve4x.solver;

/**
 * The interface for Algorithms used by the solver (which will be in package 
 * com.github.nateowami.solve4x.algorithm). 
 * @author Nateowami
 */
public abstract interface Algorithm {
	
	/**
	 * Applies the algorithm on the given equation and returns the step.
	 * @param equation The equation to work on.
	 * @return The step for solving.
	 */
	public abstract Step execute(Equation equation);
	
	/**
	 * To return the approximate smartness of performing the solving technique
	 * on the given equation.
	 * @param equation The equation to evaluate
	 */
	public abstract int smarts(Equation equation);
	
}
