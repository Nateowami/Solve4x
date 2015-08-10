/*
    Solve4x - An algebra solver that shows its work
    Copyright (C) 2015  Nathaniel Paulus

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
import java.util.Arrays;

/**
 * Represents an algebraic expression with positive and negative terms
 * @author Nateowami
 */
public class Expression extends AlgebraicCollection{
	
	//create an ArrayList for storing a list of terms
	private final ArrayList<AlgebraicParticle> termList;
	
	/**
	 * Creates a list of terms from the expression
	 * @param expr The expression to store as terms
	 */
	Expression(String expr) {
		String[] parts = Util.splitByNonNestedChars(expr, '+', '-');
		this.termList = new ArrayList<AlgebraicParticle>();
		for(String part : parts){
			termList.add(AlgebraicParticle.getInstance(part, Expression.class));
		}
	}
	
	/**
	 * Construct a new Expression from an array.
	 * @param array The array of terms for this expression.
	 */
	public Expression(boolean sign, AlgebraicParticle[] termList, int exponent) {
		super(sign, exponent);
		this.termList = new ArrayList<AlgebraicParticle>(Arrays.asList(termList));
	}
		
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Expression [termList=" + termList + ", sign()=" + sign()
				+ ", exponent()=" + exponent() + "]";
	}
	
	/**
	 * @return The number of terms in this expression
	 */
	public int length(){
		return termList.size();
	}
	
	/**
	 * @param i The index of the term you want
	 * @return The term at index i
	 */
	public AlgebraicParticle get(int i){
		return termList.get(i);
	}
	
	/**
	 * Fetches the expression in the form of a String.
	 * This should not be used in most situations.
	 * @return The expression in String form
	 */
	public String render(){
		String expr = "";
		for(int i = 0; i < termList.size(); i++){
			String term = termList.get(i).render();
			//if it's not the first term, and the term isn't negative, prepend a + sign
			expr += i != 0 && term.charAt(0) != '-' ? "+" + term : term;
		}
		return wrapWithSignParAndExponent(expr, true);
	}
	
	/**
	 * Tells if string s can be parsed as an expression.
	 * @param expr The string to check.
	 * @return If s can be parsed as an expression.
	 */
	public static boolean parsable(String expr) {
		String[] parts = Util.splitByNonNestedChars(expr, '+', '-');
		if(parts.length < 2) return false;
		for(String part : parts){
			if(!AlgebraicParticle.parsable(part, Expression.class))return false;
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.github.nateowami.solve4x.solver.AlgebraicParticle#cloneWithNewSign(java.lang.Boolean)
	 */
	@Override
	public Expression cloneWithNewSign(Boolean sign) {
		//construct a new expresion with the given sign (if given) and the current other fields
		return new Expression(sign == null ? this.sign() : sign, 
				this.termList.toArray(new AlgebraicParticle[this.termList.size()]),
				this.exponent()
				);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((termList == null) ? 0 : termList.hashCode());
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && almostEquals(obj);
	}
	
	public boolean almostEquals(Object obj){
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Expression other = (Expression) obj;
		if (termList == null) {
			if (other.termList != null)
				return false;
		} else if (!termList.equals(other.termList))
			return false;
		return true;
	}

	@Override
	public AlgebraicCollection cloneAndRemove(int i) {
		ArrayList<AlgebraicParticle> list = (ArrayList<AlgebraicParticle>) this.termList.clone();
		list.remove(i);
		return new Expression(this.sign(), list.toArray(new AlgebraicParticle[list.size()]), this.exponent());
	}
	
}
