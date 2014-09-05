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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an AlgebraicParticle. Subclasses include Variable, Number, Root, Fraction, ConstantFraction, MixedNumber, Term, and Expression.
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
	 * Constructs a new AlgebraicParticle and returns it. May be a Variable Number, Root, Fraction, ConstantFraction, MixedNumber, Term, or Expression.
	 * @param s The string to parse as an AlgebraicParticle.
	 * @return An AlgebraicParticle representing s.
	 * @throws MalformedInputException If s cannot be parsed as an AlgebraicParticle.
	 */
	public static AlgebraicParticle getInstance(String s, Class[] c) throws MalformedInputException{
		System.out.println("ALGEBRAICPARTICLE GETINSTANCE: " + s);
		
		//necessary because expressions like "(4x)" need the parentheses stripped off
		s = Util.removePar(s);
		
		if(s.length() < 1){
			throw new MalformedInputException(0);
		}
		
		//temps
		boolean sign = true;
		int exponent = 1;//anything to the first power is itself, so 1 is default
		
		//take care of sign
		sign = !(s.charAt(0) == '-');
		if(s.charAt(0) == '-' || s.charAt(0) == '+') s = s.substring(1);
		
		//deal with exponent
		Matcher m = Pattern.compile(".([⁰-⁹]+)").matcher(s);
		//if there was an exponent
		if(m.find()){
			exponent = Util.superscriptToInt(m.group(1));
			//remove the superscript from s
			s = s.substring(0, s.length() - m.group(1).length());
		}
		
		//TODO remove sign and exponent checking from other classes
		
		//the particle we'll eventually return
		AlgebraicParticle partical;
		if(Variable.isVariable(s))
			partical = new Variable(s);
		else if(Number.isNumber(s))
			partical = new Number(s);
		else if (Root.isRoot(s))
			partical = new Root(s);
		else if (Fraction.isFraction(s))
			partical = new Fraction(s);
		else if (ConstantFraction.isConstantFraction(s))
			partical = new ConstantFraction(s);
		else if (MixedNumber.isMixedNumber(s))
			partical = new MixedNumber(s);
		else if (Term.isTerm(s))
			partical = new Term(s);
		else if (Expression.isExpression(s))
			partical = new Expression(s);
		else {System.out.println("ERROR!!! CANNOT CONSTRUCT ALGEBRAIC PARTICLE"); throw new MalformedInputException(s.length());}
		
		partical.exponent = exponent;
		partical.sign = sign;
		return partical;		
	}
		
	/**
	 * Tells if s can be parsed as an AlgebraicParticle.
	 * @param s The string to check.
	 * @return If s can be parsed as an AlgebraicParticle.
	 */
	public static boolean isAlgebraicParticle(String s, Class<AlgebraicParticle>[] c){
		System.out.println("ALGEBRAICPARTICLE ISALGEBRAIC PARTICLE: " + s);
		if(s.length() < 1){
			System.out.println("IS ALGEBRAIC PARTICLE RETURNS FALSE");
			return false;
		}
		
		//necessary because expressions like "(4x)" need the parentheses stripped off
		s = Util.removePar(s);
		
		//remove the sign
		if(s.charAt(0) == '-' || s.charAt(0) == '+') s = s.substring(1);

		//remove the exponent
		Matcher m = Pattern.compile(".([⁰-⁹]+)").matcher(s);
		//if there was an exponent
		if(m.find()){
			//remove the superscript from s
			s = s.substring(0, s.length() - m.group(1).length());
		}

		if(Variable.isVariable(s) || Number.isNumber(s) || Root.isRoot(s) || Fraction.isFraction(s) || ConstantFraction.isConstantFraction(s) 
				|| MixedNumber.isMixedNumber(s) || Term.isTerm(s) || Expression.isExpression(s)){
			System.out.println("IS ALGEBRAIC PARTICLE RETURNS TRUE");
			return true;
		}
		System.out.println("IS ALGEBRAIC PARTICLE RETURNS FALSE");
		return false;
	}
		
	/**
	 * @return The string form of the algebraic particle.
	 */
	public abstract String getAsString();
	
	public abstract String toString();
	
}
