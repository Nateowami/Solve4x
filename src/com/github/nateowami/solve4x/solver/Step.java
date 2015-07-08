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
	
	private final ArrayList<Object> explanation = new ArrayList<Object>();
	private Algebra algebraicExpression;
	private final Algebra change;
	
	/**
	 * Constructs a new Step.
	 * @param change The algebra that changes in this step.
	 */
	public Step(Algebra change){
		this.change = change;
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
	 * Sets the equation or expression (i.e. the state of the equation or expression after it has 
	 * been modified by this step in the solving process).
	 * @param eq The equation to set it to.
	 * @throws IllegalArgumentException if the equation has already been set (it should only be set 
	 * once).
	 */
	public void setAlgebraicExpression(Algebra algebra) {
		if(this.algebraicExpression != null) throw new IllegalArgumentException("Algebraic expression has already been set.");
		else this.algebraicExpression = algebra;
	}
	
	/**
	 * @return the Equation
	 */
	public Algebra getAlgebraicExpression() {
		return this.algebraicExpression;
	}
	
	/**
	 * @return the AlgebraicParticel or Equation that was changed in this step.
	 */
	public Algebra getChange() {
		return this.change;
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
		return "Step [explanation=" + explanation + ", algebraicExpression="
				+ algebraicExpression + ", change=" + change + "]";
	}
	
	/**
	 * Turns an array of AlgebraicParticles into an English list and adds it to the explanation. 
	 * For example, an array of [2x, 4y, 2+6] would become "2x, 4y, and 2+6". If the length of
	 * the given array is 1, it is simply added to the explanation. If the list has a length 
	 * of two, it is added in the form of "2x and 4y"
	 * @param list The list to add to the explanation.
	 * @return Returns this so you can chain methods.
	 */
	public Step list(ArrayList<AlgebraicParticle> list) {
		return this.list(list.toArray(new AlgebraicParticle[list.size()]));
	}
	
	/**
	 * Turns an array of AlgebraicParticles into an English list and adds it to the explanation. 
	 * For example, an array of [2x, 4y, 2+6] would become "2x, 4y, and 2+6". If the length of
	 * the given array is 1, it is simply added to the explanation. If the list has a length 
	 * of two, it is added in the form of "2x and 4y"
	 * @param list The list to add to the explanation.
	 * @return Returns this so you can chain methods.
	 */
	public Step list(AlgebraicParticle[] list) {
		if(list.length == 1) explain(list[0]);
		else if (list.length == 2) explain(list[0]).explain(" and ").explain(list[1]);
		else {
			for (int i = 0; i < list.length-1; i++)	explain(list[i]).explain(", ");
			explain("and ").explain(list[list.length - 1]);	
		}
		return this;
	}
	
}
