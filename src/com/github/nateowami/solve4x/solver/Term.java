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
 * Provides a way to work with terms in equations.
 * Terms are represented as follows: A Term (optionally) contains a coefficient (instance of class Number),
 * and two list: one of variables, the other of Expressions. For example, the term 2&lt;3&gt;/&lt;4&gt;xy2(2x+4y)(x+y)2
 * would be represented as follows:
 * 2&lt;3&gt;/&lt;4&gt; (Number)
 * variable list:
 * 		x
 * 		y (variable with power of 2) 
 * Expression list:
 * 		(2x+4y)
 * 		(x+y)2 (Expression with power of two)
 * You can access these values with methods such as hasCoe(), getCoe(), numOfVars(), varAt(int), numOfExprs(), and exprAt(int).
 * Variables and Expressions are held in two separate arrays.
 * @author Nateowami
 */
public class Term extends AlgebraicParticle{
	
	//the list of variables in this term
	private ArrayList<AlgebraicParticle> parts = new ArrayList<AlgebraicParticle>();
	
	private static final Class[] subParts = {Variable.class, MixedNumber.class, Number.class, Fraction.class, Root.class, Expression.class};
	
	/**
	 * Creates a new term from a String
	 * @param s The string from which to construct the term.
	 */
	protected Term(String s) {
		String original = s; //for debugging purposes
		Solve4x.debug("Creating term: "+s);
		//loop backwards to find a match
		int i;
		//-1 from s.length() because we should not try to parse the whole thing the first time
		for(i = s.length()-1; i >=0; i--){
			if(AlgebraicParticle.parseable(s.substring(0, i), subParts)){
				Solve4x.debug("Creating algebraic particle: " + s.substring(0, i));
				parts.add(AlgebraicParticle.getInstance(s.substring(0, i), subParts));
				//reset the loop
				s = s.substring(i);
				i = s.length()+1;//+1 because it is about to be subtracted when the loop continues
			}
		}
		//if the whole thing wasn't parsed
		if(s.length() > 0){
			throw new ParsingException("Unable to parse \"" + original + "\" as a term. The following failed to be parsed: \" " + s + "\".");
		}
	}
	
	/**
	 * Tells the number of AlgebraicParticles in this term. For example,
	 * 2xy(2x+6)+2 would return 2.
	 * @return The number of Expressions in this term.
	 * @see com.github.nateowami.solve4x.solver.Term#getPartAt(int)
	 */
	public int length(){
		return this.parts.size();
	}
	
	/**
	 * @param i The index of the Expression you want.
	 * @return The Expression at index i.
	 * @see com.github.nateowami.solve4x.solver.Term#length()
	 */
	public AlgebraicParticle getPartAt(int i){
		return this.parts.get(i);
	}
		
	/**
	 * Returns a String representation of this Term in the form of an algebraic term, not the 
	 * traditional toString().
	 * @return This term rendered as plain text.
	 */
	public String getAsString(){
		String s = "";
		//keep track of whether the previous part of the term was wrapped with parentheses
		//this is so we can distinguish between 62 and 6(2)
		boolean prevHadPar = true;
		String prevClass = "";//the name of the class of the previous part of the term
		for(AlgebraicParticle p : this.parts){
			String curClass = p.getClass().getSimpleName();
			boolean needsPar = false;
			
			if(curClass.equals("Expression") || curClass.equals("Fraction")) needsPar = true;
			//MixedNumber and Number should only be wrapped with pars if the previous part was a number and it didn't have pars
			else if((curClass.equals("MixedNumber") || curClass.equals("Number")) && prevClass.equals("Number")) needsPar = !prevHadPar;
			s += needsPar ? "(" + p.getAsString() + ")"	: p.getAsString();
			
			prevClass = curClass;
			prevHadPar = needsPar;
		}
		return wrapWithSignAndExponent(s);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Term [" + (parts != null ? "parts=" + parts : "") + "]";
	}
	
	/** 
	 * Tells if a specified string s may be parsed as a term
	 * @param s The string to check.
	 * @return If s is parseable as a term.
	 */
	public static boolean parseable(String s){
		int numParsed = 0; //make sure we're not doing someone else's job 
		//for example, we shouldn't say "1" is a term, because it should be considered a number
		
		//make sure it's a list of algebraic particles
		//don't subtract 1 from s.length() because we need to make sure it won't be able to parse the whole thing at once
		for(int i = s.length(); i >= 0; i--){
			//make sure it doesn't have a sign
			if(s.length() > 0 && s.charAt(0) != '+' && s.charAt(0) != '-' && AlgebraicParticle.parseable(s.substring(0, i), subParts)){
				numParsed++;
				//reset the loop
				s = s.substring(i);
				i = s.length()+1;//+1 because it's about to be subtracted 1
			}
		}
		//if the whole thing was parseable
		if(s.length() == 0 && numParsed > 1){
			return true;
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
		result = prime * result + ((parts == null) ? 0 : parts.hashCode());
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
		Term other = (Term) obj;
		if (parts == null) {
			if (other.parts != null)
				return false;
		} else if (!parts.equals(other.parts))
			return false;
		return true;
	}
	
}