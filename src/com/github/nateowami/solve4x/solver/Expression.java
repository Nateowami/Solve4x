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

import com.github.nateowami.solve4x.Solve4x;

/**
 * Represents an algebraic expression with positive and negative terms
 * @author Nateowami
 */
public class Expression extends AlgebraicParticle{
	
	//create an ArrayList for storing a list of terms
	protected ArrayList <AlgebraicParticle>termList = new ArrayList<AlgebraicParticle>();
	
	/**
	 * Creates a list of terms from the expression
	 * @param expr The expression to store as terms
	 */
	Expression(String expr) {
		String[] parts = Util.splitByNonNestedChars(expr, '+', '-');
		for(String part : parts){
			termList.add(AlgebraicParticle.getInstance(part, Expression.class));
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Expression ["
				+ (termList != null ? "termList=" + termList : "") + "]";
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
	public AlgebraicParticle termAt(int i){
		return termList.get(i);
	}
	
	/**
	 * Fetches the expression in the form of a String.
	 * This should not be used in most situations.
	 * @return The expression in String form
	 */
	public String getAsString(){
		String expr = "";
		for(int i = 0; i < termList.size(); i++){
			String term = termList.get(i).getAsString();
			//if it's not the first term, and the term isn't negative, prepend a + sign
			expr += i != 0 && term.charAt(0) != '-' ? "+" + term : term;
		}
		return expr;
	}

	/**
	 * Tells if string s can be parsed as an expression.
	 * @param expr The string to check.
	 * @return If s can be parsed as an expression.
	 */
	public static boolean parseable(String expr) {
		String[] parts = Util.splitByNonNestedChars(expr, '+', '-');
		if(parts.length < 2) return false;
		for(String part : parts){
			if(!AlgebraicParticle.parseable(part, Expression.class))return false;
		}
		return true;
	}

	/**
	 * Combines like terms into ArrayLists.
	 * Given an expression like this:
	 * 2x    4    x/3    6    x
	 * Terms (or AlgebraicParticles) are added to ArrayLists to form a 2d array:
	 * 2x	x
	 * 4	6
	 * x/3
	 * Now it can easily be seen that there are three types of terms. The first two 
	 * lines can then be combined to make a simpler expression:
	 * 3x    10    x/3
	 * @return A 2d ArrayList (ArrayList of ArrayList) containing like terms. Each row
	 * contains terms that are alike.
	 */
	public ArrayList<ArrayList<AlgebraicParticle>> likeTerms(){
		/*
		 * What we're doing is basically taking an expression like this:
		 * 2x    4    x/3    6    x
		 * And putting them in an arraylist of arraylists, like this:
		 * 2x	x
		 * 4	6
		 * x/3
		 * Now you can see there are three types of terms. The first two lines can then
		 * easily be combined to make a simpler expression:
		 * 3x    10    x/3
		 */
		ArrayList<ArrayList<AlgebraicParticle>> list = new ArrayList<ArrayList<AlgebraicParticle>>(this.length());
		//loop through the terms
		bigloop:
		for(AlgebraicParticle t : termList){
			//place this term in the correct ArrayList
			for(ArrayList<AlgebraicParticle> array : list){
				if(likeTerms(array.get(0), t)){
					array.add(t);
					continue bigloop;
				}
			}
			//there is no like term, we must add it to a new arraylist
			ArrayList<AlgebraicParticle> tmp = new ArrayList<AlgebraicParticle>(1);
			tmp.add(t);
			list.add(tmp);
		}
		return list;
	}
	
	/**
	 * Tells if a and b are like terms (i.e., they are both numbers, mixed numbers, or fractions with
	 * numbers on top and bottom, or they are the same variables).
	 * @param a The first algebraic particle.
	 * @param b The second algebraic particle.
	 * @return If a and b are like terms.
	 */
	public static boolean likeTerms(AlgebraicParticle a, AlgebraicParticle b){
		//if they're numbers, mixed numbers, or fractions with numbers on top and bottom
		if((a instanceof Number || a instanceof MixedNumber || a instanceof Fraction && ((Fraction)a).constant())
				&& (b instanceof Number || b instanceof MixedNumber || b instanceof Fraction && ((Fraction)b).constant())) return true;
		//if they're identical variables
		else if(a instanceof Variable && b instanceof Variable && ((Variable)a).getVar() == ((Variable)b).getVar()) return true;
		//if they're terms, and they're like
		else if(a instanceof Term && b instanceof Term){
			Term first = (Term) a, second = (Term) b;
			if(first.length() == second.length() && first.getPartAt(0) instanceof Number && second.getPartAt(0) instanceof Number){
				for(int i = 1; i < first.length()/*same as second.length()*/; i++){
					if(!first.getPartAt(i).equals(second.getPartAt(i)))return false;
				}
				return true;
			}
			else return false;
		}
		else return false;
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
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
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
	
}
