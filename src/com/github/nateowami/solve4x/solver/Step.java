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
 * Represents a single step in solving or simplifying an equation or expression.
 * Each step tracks difficulty, explanation, and an equation.
 * @author Nateowami
 */
public class Step {
	
	private int difficulty;
	String explanation;
	Equation eq;
	
	/**
	 * Constructs a new Step.
	 * @param eq The equation for this step.
	 * @param explanation An explanation for how this step works.
	 * @param difficulty The difficulty associated with this step.
	 */
	public Step(Equation eq, String explanation, int difficulty){
		this.explanation = explanation;
		this.difficulty = difficulty;
		this.explanation = explanation;
	}
	
	/**
	 * @return the explanation for this step
	 */
	public String getExplanation() {
		return explanation;
	}
	
	/**
	 * @return the Equation
	 */
	public Equation getEquation() {
		return eq;
	}
	
	/**
	 * @return the difficulty of this step (0-9)
	 */
	public int getDifficulty() {
		return difficulty;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Step [difficulty=" + difficulty + ", explanation="
				+ explanation + "]";
	}
	
}
