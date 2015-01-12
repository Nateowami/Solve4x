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

import java.util.ArrayList;

/**
 * Represents a single step in solving or simplifying an equation or expression.
 * Each step tracks difficulty, explanation, and an equation.
 * @author Nateowami
 */
public class Step {
	
	private int difficulty;
	ArrayList<Object> explanation = new ArrayList<Object>();
	Equation eq;
	
	/**
	 * Constructs a new Step.
	 * @param eq The equation for this step.
	 * @param explanation An explanation for how this step works.
	 * @param difficulty The difficulty associated with this step.
	 */
	public Step(Equation eq, int difficulty){
		this.eq = eq;
		this.difficulty = difficulty;
	}
	
	/**
	 * Returns an explanation for the step, in an ArrayList&lt;Object&gt;. Each object is
	 * guaranteed to be of type String or AlgebraicParticle. That way strings and algebra
	 * can be rendered together later (algebra can't always be rendered nicely as strings).
	 * @return the explanation for this step.
	 */
	public ArrayList<Object> getExplanation() {
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
	
	/**
	 * Appends the given string s to the explanation.
	 * @param s The string to append to the explanation.
	 * @return Returns this so you can chain methods.
	 */
	public Step explain(String s){
		this.explanation.add(s);
		return this;
	}
	
	/**
	 * Appends the given AlgebraicParticle a to the explanation. This is useful, if, for 
	 * example, you need an explanation such as "combine 2x(3+4) and 2y". That way, algebra 
	 * can be rendered however later. 
	 * @param a The AlgebraicParticle to append.
	 * @return Returns this so you can chain methods.
	 */
	public Step explain(AlgebraicParticle a){
		this.explanation.add(a);
		return this;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Step [difficulty=" + difficulty + ", explanation="
				+ explanation + ", eq=" + eq + "]";
	}

	
	/**
	 * Turns a list of AlgebraicParticles into an English list and adds it to the explanation. 
	 * For example, an array of [2x, 4y, 2+6] would become "2x, 4y, and 2+6". If the length of
	 * the given array is 1, it is simply added to the explanation. If the list has a length 
	 * of two, it is added in the form of "2x and 4y"
	 * @param list The list to add to the explanation.
	 * @return Returns this so you can chain methods.
	 */
	public Step list(ArrayList<AlgebraicParticle> list) {
		if(list.size() == 1) explain(list.get(0));
		else if (list.size() == 2) explain(list.get(0)).explain(" and ").explain(list.get(1));
		else {
			for (int i = 0; i < list.size()-1; i++)	explain(list.get(i)).explain(", ");
			explain("and ").explain(list.get(list.size() - 1));	
		}
		return this;
	}
	
}
