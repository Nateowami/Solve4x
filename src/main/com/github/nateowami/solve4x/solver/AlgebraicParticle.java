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
 * Represents an AlgebraicParticle. Subclasses include Number, Root, Fraction, ConstantFraction, MixedNumber, Term, and Expression.
 * @author Nateowami
 */
public abstract class AlgebraicParticle {
	
	private int exponent;
	private boolean sign = true;
	
	/**
	 * @return The sign of the AlgebraicParticle (true for +, false for -).
	 */
	public boolean sign(){
		return sign;
	}
		
	/**
	 * @return The exponent of the AlgebraicParticle.
	 */
	public int exponent(){
		return exponent;
	}
	
	/**
	 * Constructs a new AlgebraicParticle and returns it. May be a Number, Root, Fraction, ConstantFraction, MixedNumber, Term, or Expression.
	 * @param s The string to parse as an AlgebraicParticle.
	 * @return An AlgebraicParticle representing s.
	 * @throws MalformedInputException If s cannot be parsed as an AlgebraicParticle.
	 */
	
	public static AlgebraicParticle getInstance(String s) throws MalformedInputException{
		System.out.println("ALGEBRAICPARTICLE GETINSTANCE: " + s);
		if(s.length() < 1){
			throw new MalformedInputException(0);
		}
		//TODO check for sign
		//TODO check for exponent
		//TODO remove sign and exponent checking from other classes
		if(Number.isNumber(s))
			return new Number(s);
		else if (Root.isRoot(s))
			return new Root(s);
		else if (Fraction.isFraction(s))
			return new Fraction(s);
		else if (ConstantFraction.isConstantFraction(s))
			return new ConstantFraction(s);
		else if (MixedNumber.isMixedNumber(s))
			return new MixedNumber(s);
		else if (Term.isTerm(s))
			return new Term(s);
		else if (Expression.isExpression(s))
			return new Expression(s);
		else {System.out.println("ERROR!!!"); throw new MalformedInputException(s.length());}
		
	}
		
	/**
	 * Tells if s can be parsed as an AlgebraicParticle.
	 * @param s The string to check.
	 * @return If s can be parsed as an AlgebraicParticle.
	 */
	public static boolean isAlgebraicParticle(String s){
		//TODO remove exponent and sign
		if(Number.isNumber(s) || Root.isRoot(s) || Fraction.isFraction(s) || ConstantFraction.isConstantFraction(s) 
				|| MixedNumber.isMixedNumber(s) || Term.isTerm(s) || Expression.isExpression(s)){
			return true;
		}
		else return false;
	}
		
	/**
	 * @return The string form of the algebraic particle.
	 */
	public abstract String getAsString();
	
}
