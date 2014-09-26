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

import java.nio.charset.MalformedInputException;

/**
 * An abstract type for all solving strategies to extend. Basically it's a template
 * for classes that will define a particular way to solve (e.g. simplifying). Doing it 
 * this way should allow us to loop through solving strategies. If you can think of a 
 * better name let me know.
 * @author Nateowami
 */
public abstract interface Algorithm {
	
	/**
	 * Creates a Step object for performing this algorithm on
	 * a given expression or equation
	 * @param equation The expression or equation to do a bit of solving on
	 * @return The Step for solving this little bit
	 * @throws MalformedInputException 
	 */
	public abstract Step getStep(Equation equation) throws MalformedInputException;
	
	/**
	 * To return the approximate smartness of performing the solving technique
	 * on the given Step
	 * @param equation The Step to evaluate
	 */
	public abstract int getSmarts(Equation equation);
	

}
