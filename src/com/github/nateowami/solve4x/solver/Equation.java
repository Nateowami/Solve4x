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

import java.util.Arrays;

import com.github.nateowami.solve4x.Solve4x;

/**
 * Represents an algebraic equation
 * @author Nateowami
 */
public class Equation {
	
	//exprs holds expressions (one if this equation is just representing an 
	//expression, two if it's representing an equation
	private final AlgebraicParticle exprs[];
	
	/**
	 * Creates an equation by turning it into two expressions
	 * @param eq The equation (or expression) to turn into an
	 * equation, which can be just an expression if necessary
	 */
	public Equation(String eq) {
		int i = eq.indexOf('=');
		if (i > 0) exprs = new AlgebraicParticle[]{AlgebraicParticle.getInstance(eq.substring(0, i)), AlgebraicParticle.getInstance(eq.substring(i+1))};
		else exprs = new AlgebraicParticle[]{AlgebraicParticle.getInstance(eq)};
	}
	
	/**
	 * Constructs a new equation. The expressions passed may be of length 1, or any even number.
	 * @param exprs The expressions of the equation.
	 */
	public Equation(AlgebraicParticle[] exprs) {
		this.exprs = exprs;
	}
	
	/**
	 * @param i The expression you want in this equation.
	 *Needs to be 0 or 1.
	 * @return The expression at index i
	 */
	public AlgebraicParticle getPartAt(int i){
		return exprs[i];
	}
	
	/**
	 * Tells the length/number of expressions in this Equation
	 * This seems unnecessary (there would usually be two) but there could be only
	 * one if Equation holds only one expression, or more if it represents a system
	 * of equations.
	 * @return The number of expressions in this Equation
	 */
	public int getSize(){
		return exprs.length;
	}
	
	/**
	 * Tells if eq can be parsed as an equation/expression. An equation is really just
	 * a wrapper around one or two expressions, so if passed a single expression this 
	 * will still return true.
	 * @param eq The string to check.
	 * @return If eq can be parsed as an equation.
	 */
	public static boolean parseable(String eq){
		int i = eq.indexOf('=');
		return i == -1 && AlgebraicParticle.parseable(eq) || AlgebraicParticle.parseable(eq.substring(0, i)) && AlgebraicParticle.parseable(eq.substring(i+1));
	}
	
	/**
	 * Clones the expression, replacing the expression at index "index" with expr in the clone.
	 * @param algebraicParticle expr The expression to replace with.
	 * @param index The index of the expression to swap out for expr.
	 * @return A new equation, identical to current one, except that the expression at index 
	 * is set to expr.
	 */
	public Equation cloneWithNewExpression(AlgebraicParticle algebraicParticle, int index) {
		AlgebraicParticle[] newExprs = Arrays.copyOf(this.exprs, this.exprs.length);
		newExprs[index] = algebraicParticle;
		return new Equation(newExprs);
	}
	
	/**
	 * @return A String representation of this equation. Example: 2x+x=5
	 */
	public String getAsString(){
		String eq= "";
		for(int i = 0; i < exprs.length; i++){
			//if i is odd append '='
			eq += i % 2 == 0 ? "" : "=";
			eq += exprs[i].getAsString();
		}
		return eq;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Equation [exprs=" + Arrays.toString(exprs) + "]";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(exprs);
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equation other = (Equation) obj;
		if (!Arrays.equals(exprs, other.exprs))
			return false;
		return true;
	}
	
}