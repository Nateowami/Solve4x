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

import java.lang.reflect.InvocationTargetException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an AlgebraicParticle. Subclasses include Variable, Number, Root, Fraction, Fraction, MixedNumber, Term, and Expression.
 * @author Nateowami
 */
public abstract class AlgebraicParticle {
	
	private int exponent = 1;
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
		return this.exponent;
	}
	
	/**
	 * Constructs a new AlgebraicParticle and returns it. May be a Variable Number, Root, Fraction, Fraction, MixedNumber, Term, or Expression.
	 * @param s The string to parse as an AlgebraicParticle.
	 * @return An AlgebraicParticle representing s.
	 * @ If s cannot be parsed as an AlgebraicParticle.
	 */
	public static AlgebraicParticle getInstance(String s, Class[] c) {
		System.out.println("ALGEBRAICPARTICLE GETINSTANCE: " + s);
		String original = s; //for debugging purposes
		
		//necessary because expressions like "(4x)" need the parentheses stripped off
		s = Util.removePar(s);
		
		if(s.length() < 1){
			throw new ParsingException("Cannot construct AlgebraicParticle with length < 1 (length < 1 is after removing any corresponding parentheses, which may affect length)");
		}
		
		//temps
		boolean sign = true;
		int exponent = 1;//anything to the first power is itself, so 1 is default
		
		//take care of sign
		sign = !(s.charAt(0) == '-');
		if(s.charAt(0) == '-' || s.charAt(0) == '+') s = s.substring(1);
		
		//deal with exponent
		Matcher m = Pattern.compile(".([¹-⁹][⁰-⁹]*)\\z").matcher(s);
		//if there was an exponent
		if(m.find()){
			exponent = Util.superscriptToInt(m.group(1));
			//remove the superscript from s
			s = s.substring(0, s.length() - m.group(1).length());
		}
		
		//the particle we'll eventually return
		AlgebraicParticle partical = null;

		for(Class i : c){
			String n = i.getSimpleName();
			     if(n.equals("Variable")    && Variable.parseable(s))    partical = new Variable(s);
			else if(n.equals("Number")      && Number.parseable(s))      partical = new Number(s);
			else if(n.equals("Root")        && Root.parseable(s))        partical = new Root(s);
			else if(n.equals("Fraction")    && Fraction.parseable(s))    partical = new Fraction(s);
			else if(n.equals("MixedNumber") && MixedNumber.parseable(s)) partical = new MixedNumber(s);
			else if(n.equals("Term")        && Term.parseable(s))        partical = new Term(s);
			else if(n.equals("Expression")  && Expression.parseable(s))  partical = new Expression(s);
		}
		//check if creating it was successful
		if(partical == null)throw new ParsingException("Failed to construct AlgebraicParticle \"" + original + "\"" 
				+ ". With sign, exponent, and parentheses removed, it's \"" + s + "\".");
		
		partical.exponent = exponent;
		partical.sign = sign;
		return partical;		
	}
		
	/**
	 * Tells if s can be parsed as an AlgebraicParticle.
	 * @param s The string to check.
	 * @return If s can be parsed as an AlgebraicParticle.
	 */
	public static boolean parseable(String s, Class[] c){
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
		Matcher m = Pattern.compile(".([¹-⁹][⁰-⁹]*)\\z").matcher(s);
		//if there was an exponent
		if(m.find()){
			//remove the superscript from s
			s = s.substring(0, s.length() - m.group(1).length());
		}
		
		for(Class i : c){
			String n = i.getSimpleName();
			if(n.equals("Variable")    && Variable   .parseable(s)) return true;
			if(n.equals("Number")      && Number     .parseable(s)) return true;
			if(n.equals("Root")        && Root       .parseable(s)) return true;
			if(n.equals("Fraction")    && Fraction   .parseable(s)) return true;
			if(n.equals("MixedNumber") && MixedNumber.parseable(s)) return true;
			if(n.equals("Term")        && Term       .parseable(s)) return true;
			if(n.equals("Expression")  && Expression .parseable(s)) return true;
		}
		
		/*
		 * There is a small chance we'll use this code again in a modified way
		if(Variable.parseable(s) || Number.parseable(s) || Root.parseable(s) || Fraction.parseable(s) || 
				MixedNumber.parseable(s) || Term.parseable(s) || Expression.parseable(s)){
			System.out.println("IS ALGEBRAIC PARTICLE RETURNS TRUE");
			return true;
		}*/
		
		System.out.println("IS ALGEBRAIC PARTICLE RETURNS FALSE");
		return false;
	}
		
	/**
	 * @return The string form of the algebraic particle.
	 */
	public abstract String getAsString();
	
	/**
	 * Wraps string s with the sign and exponent. The use case for this
	 * is in a subclass of AlgebraicParticle in its getAsString() method.
	 * The subclass should call this method to wrap a string with the sign
	 * and exponent. For example, the Number class could call 
	 * wrapWithSignAndExponent("3.14") which, depending on the values in 
	 * the fields sign and exponent, may return something along the lines 
	 * of "+3.14⁶". The sign will always be present, whether positive or 
	 * negative, but the superscript will only be present if it is not 1.
	 * While this functionality could be implemented directly in the 
	 * subclass, this will keep the code DRYer and be easier to modify.
	 * @param s The string to be wrapped with the sign and exponent.
	 * @return The wrapped version of s.
	 */
	protected String wrapWithSignAndExponent(String s){
		return (sign ? "" : '-') + s + (exponent == 1 ? "" : Util.intToSuperscript(exponent));
	}
	
	/**
	 * Every AlgebraicParticle needs to define its own toString() for debugging purposes.
	 */
	public abstract String toString();
	
}
