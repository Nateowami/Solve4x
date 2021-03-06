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

import com.github.nateowami.solve4x.Solve4x;

/**
 * Represents an AlgebraicParticle. Subclasses include Variable, Number, Root, Fraction, Fraction, MixedNumber, Term, and Expression.
 * @author Nateowami
 */
public abstract class AlgebraicParticle extends Algebra implements Cloneable {
	
	private int exponent = 1;
	private boolean sign = true;
	
	/**
	 * Constructs a new AlgebraicParticle (only usable by subclasses because AlgebraicParticle is abstract).
	 * @param sign The sign of the AlgebraicParticle.
	 * @param exponent The exponent of the AlgebraicParticle.
	 */
	protected AlgebraicParticle(boolean sign, int exponent) {
		this.sign = sign;
		this.exponent = exponent;
	}
	
	/**
	 * An empty constructor for AlgebraicParticle (only usable by subclasses because AlgebraicParticle is abstract).
	 */
	protected AlgebraicParticle(){};
	
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
	 * @param blacklist A "blacklisted" class that will not be used (directly) in initializing this algebraic particle.
	 * Will be ignored if s is parenthesized.
	 * @return An AlgebraicParticle representing s (may be left null).
	 * @throws ParsingException if s cannot be parsed as an algebraic particle.
	 */
	public static AlgebraicParticle getInstance(String s, Class<? extends AlgebraicParticle> blacklist) {		
		if(s.length() < 1) throw new ParsingException("Cannot construct AlgebraicParticle from empty string.");
		
		String original = s, withSignAndExponent, withExponent, bothRemoved;
		Boolean hadParsSecondTime = false;
		
		//remove parentheses if applicable
		boolean hadPars = false;
		s = Util.removePar(s);
		if(s.length() != original.length()) {
			hadPars = true;
			blacklist = null;
		}
		withSignAndExponent = s;
		
		//find the first char, set the sign to true/false, and remove the +/- sign
		char first = s.charAt(0);
		boolean sign = first != '-';
		if(first == '+' || first == '-') {
			withExponent = s.substring(1);
		}
		else withExponent = s;
		
		//remove the exponent
		String superscript = exponent(withExponent);
		int exponent = 1;
		//if superscript, set it and remove it
		if(!superscript.isEmpty()) {
			exponent = Integer.parseInt(superscript);
			bothRemoved = withExponent.substring(0, withExponent.length() - superscript.length());
		}
		else bothRemoved = withExponent;
		
		//if there weren't pars, remove now
		if(!hadPars) {
			String noPars = Util.removePar(bothRemoved);
			
			//if there were pars
			if(noPars.length() != bothRemoved.length()) {
				blacklist = null;
				hadPars = true;
				hadParsSecondTime = true;
				
				bothRemoved = noPars;
				withExponent = noPars;
				withSignAndExponent = noPars;
			}
		}
		
		AlgebraicParticle result = construct(withSignAndExponent, withExponent, bothRemoved, blacklist);
		
		//if we need to set sign or exponent (if hadPars, then for sure yes)
		if(hadParsSecondTime || !(result instanceof Expression)) {
			result.sign = sign;
			// Set the exponent if it's a variable or a number, or the whole thing was surrounded 
			// by pars. If it's a number (and not surrounded by pars)make sure it DOESN'T have a scientific notation exponent. 
			// For example, in 2.6*10⁹, the 9 is the exponent of the 10 in scientific notation, and 
			// doesn't belong to the whole number.
			if(hadParsSecondTime || result instanceof Variable || result instanceof Number 
					&& ((Number)result).getScientificNotationExponent() == null){
				result.exponent = exponent;
			}
		}
		return result;
	}
	
	/**
	 * Works like {@link #getInstance(String, Class<? extends AlgebraicParticle>)}, with c 
	 * (the class not to use) set to null.
	 * @param s The string to parse. 
	 * @return An algebraic particle representing s.
	 */
	public static AlgebraicParticle getInstance(String s){
		return getInstance(s, null);
	}
	
	/**
	 * Tells if s can be parsed as an AlgebraicParticle.
	 * @param s The string to check.
	 * @param blacklist A "blacklisted" class that will not be used (directly) in checking. Will be ignored if
	 * s is parenthesized.
	 * @return If s can be parsed as an AlgebraicParticle.
	 */
	public static boolean parsable(String s, Class<? extends AlgebraicParticle> blacklist){
		if(s.length() < 1)return false;
		String original = s;
		
		String withExponent, bothRemoved;
		boolean hadPars = false;
		
		s = Util.removePar(s);
		if(s.length() != original.length()) {
			hadPars = true;
			blacklist = null;
		}
				
		//remove sign
		if(s.charAt(0) == '+' || s.charAt(0) == '-') {
			withExponent = s.substring(1);
		}
		else withExponent = s;
		
		//remove exponent
		String exponent = exponent(withExponent);
		if(!exponent.isEmpty()) {
			bothRemoved = withExponent.substring(0, withExponent.length() - exponent.length());
		}
		else bothRemoved = withExponent;
		
		//remove pars if not removed already
		if(!hadPars) {
			String parsRemoved = Util.removePar(bothRemoved);
			
			//if there were pars to remove
			if(parsRemoved.length() != bothRemoved.length()) {
				withExponent = parsRemoved;
				bothRemoved = parsRemoved;
				hadPars = true;
				blacklist = null;
			}
		}
		
		return parsableBySubclasses(hadPars ? withExponent : s, withExponent, bothRemoved, blacklist);
	}
	
	/**
	 * Works like {@link #parsable(String, Class<? extends AlgebraicParticle>)} with 
	 * c (the class not to use) set to null.
	 * @param s The string to test.
	 * @return If s can be parsed as an algebraic particle.
	 */
	public static boolean parsable(String s){
		return parsable(s, null);
	}
	
	/**
	 * @return The string form of the algebraic particle.
	 */
	public abstract String render();
	
	/**
	 * Wraps string s with the sign and exponent. The use case for this
	 * is in a subclass of AlgebraicParticle in its render() method.
	 * The subclass should call this method to wrap a string with the sign
	 * and exponent. For example, the Number class could call 
	 * wrapWithSignAndExponent("3.14") which, depending on the values in 
	 * the fields sign and exponent, may return something along the lines 
	 * of "+3.14⁶". The sign will always be present, whether positive or 
	 * negative, but the superscript will only be present if it is not 1.
	 * While this functionality could be implemented directly in the 
	 * subclass, this will keep the code DRYer and be easier to modify.
	 * @param s The string to be wrapped with the sign and exponent.
	 * @param pars Tells if parentheses should also be added, if the 
	 * exponent is not 1.
	 * @return The wrapped version of s.
	 */
	protected String wrapWithSignParAndExponent(String s, boolean pars){
		//the sign
		return (sign ? "" : '-') 
				//the term and parentheses (if needed)
				+ (exponent != 1 && pars ? "(" + s + ")" : s )
				//the exponent
				+ (exponent == 1 ? "" : Util.toSuperscript(Integer.toString(exponent)));
	}
	
	/**
	 * Every AlgebraicParticle needs to define its own toString() for debugging purposes.
	 */
	public abstract String toString();
	
	/**
	 * Tells if any class can parse withSign, withExponent or allRemoved. exponentRemoved will be 
	 * used when checking classes Variable and Number, while withExponent will be used in all 
	 * other cases, except for expressions, which will use withSign, and sometimes in the case of 
	 * Number, when allRemoved contains *10, in which case withExponent will be passed to it.
	 * @param withSign The string to test, with the sign and exponent still remaining, only if 
	 * after removing any sign and exponent, it is not fully nested in parentheses.
	 * @param withExponent The string to test, with the exponent still remaining, only if after
	 * removing any exponent, it is not fully nested in parentheses.
	 * @param allRemoved The string to test, with the exponent and any parentheses removed.
	 * @param blacklist A blacklisted class that will not be used in checking if parsing is possible.
	 * @return If a class listed in classes can construct from withExponent or exponentRemoved, 
	 * depending on the situation (see above).
	 */
	static private boolean parsableBySubclasses(String withSign, String withExponent, String allRemoved, Class<? extends AlgebraicParticle> blacklist){
		String n = blacklist == null ? "" : blacklist.getSimpleName();
		String forNumber = allRemoved.indexOf("*10") == -1 ? allRemoved : withExponent;
		if(!n.equals("Variable")    && Variable   .parsable(allRemoved)) return true;
		if(!n.equals("Number")      && Number     .parsable(forNumber)) return true;
		if(!n.equals("Root")        && Root       .parsable(withExponent)) return true;
		if(!n.equals("Fraction")    && Fraction   .parsable(withExponent)) return true;
		if(!n.equals("MixedNumber") && MixedNumber.parsable(withExponent)) return true;
		if(!n.equals("Term")        && Term       .parsable(withExponent)) return true;
		if(!n.equals("Expression")  && Expression .parsable(withSign)) return true;
		return false;
	}
	
	/**
	 * Constructs a new algebraic particle from withSign, withExponent, or allRemoved.
	 * allRemoved is used when attempting to construct Variable or Number, while in all 
	 * other cases withExponent will be used, except in the case of expressions, which always
	 * use withSign, and sometimes in the case of Number, when allRemoved contains *10, in which 
	 * case withExponent will be passed to it.
	 * @param withSign The string from which to construct an AlgebraicParticle, with the sign 
	 * and exponent still remaining, only if after removing any sign and exponent, it is not 
	 * fully nested in parentheses.
	 * @param withExponent The string from which to construct an AlgebraicParticle, with the
	 * exponent still remaining, only if after removing any exponent, it is not fully nested 
	 * in parentheses.
	 * @param allRemoved The string from which to construct an AlgebraicParticle, with 
	 * the exponent and any parentheses removed.
	 * @param blacklist A blacklisted class that will not be used in parsing.
	 * @return An AlgebraicParticle, initialized with withExponent or exponentRemoved, depending
	 * on the situation (see above).
	 */
	static private AlgebraicParticle construct(String withSign, String withExponent, String allRemoved, Class<? extends AlgebraicParticle> classes){
		String n = classes == null ? "" : classes.getSimpleName();
		String forNumber = allRemoved.indexOf("*10") == -1 ? allRemoved : withExponent;
		if(!n.equals("Variable")    && Variable   .parsable(allRemoved))return new Variable(allRemoved);
		if(!n.equals("Number")      && Number     .parsable(forNumber))return new Number(forNumber);
		if(!n.equals("Root")        && Root       .parsable(withExponent))   return new Root(withExponent);
		if(!n.equals("Fraction")    && Fraction   .parsable(withExponent))   return new Fraction(withExponent);
		if(!n.equals("MixedNumber") && MixedNumber.parsable(withExponent))   return new MixedNumber(withExponent); // XXX shouldn't this use allRemoved?
		if(!n.equals("Term")        && Term       .parsable(withExponent))   return new Term(withExponent);
		if(!n.equals("Expression")  && Expression .parsable(withSign))   return new Expression(withSign);
		throw new ParsingException("Cannot parse "+withSign+" (with sign) or "+withExponent
				+" (with exponent) "+allRemoved+" (exponent removed) or "+forNumber
				+" (for parsing as a number) as an algebraic particle.");
	}
	
	/**
	 * Clones the AlgebraicParticle with a new sign. If the sign already equals the specified sign, 
	 * implementations should simply return this instance rather than clone.
	 * @param sign The sign for the new AlgebraicParticle.
	 * @return An pseudo-clone of this AlgebraicParticle.
	 */
	public abstract AlgebraicParticle cloneWithNewSign(boolean sign);
	
	/**
	 * Tells if a equals this, without regard for signs or powers.
	 * @param a An AlgebraicParticle to compare to this.
	 * @return If a almost equals this.
	 */
	public abstract boolean almostEquals(Object a);
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + exponent;
		result = prime * result + (sign ? 1231 : 1237);
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
		AlgebraicParticle other = (AlgebraicParticle) obj;
		if (exponent != other.exponent)
			return false;
		if (sign != other.sign)
			return false;
		return true;
	}
	
	/**
	 * Extracts a superscript/exponent from the end of string <code>s</code> and returns it.
	 * @param s The string from which to extract an exponent.
	 * @return The exponent, converted to an int and then string.
	 */
	private static String exponent(String s){
		int i;
		for(i = s.length(); i > 1 && Util.isSuperscript(Character.toString(s.charAt(i-1))); i--);
		return i == s.length() ? "" : Integer.toString(Util.superscriptToInt(s.substring(i)));
	}
	
	/**
	 * Makes a clone of this object and sets the sign and exponent of the clone to the specified 
	 * sign and exponent. If the sign and exponent are already at the specified values, no clone 
	 * will occur, and this instance will simply be returned.
	 * @param sign The sign of the returned object (if null, defaults to the sign of this).
	 * @param exponent The exponent of the returned object (if null, defaults to the exponent of this).
	 * @return A pseudo-clone of this object
	 */
	public AlgebraicParticle cloneWithNewSignAndExponent(Boolean sign, Integer exponent){
		//checks for null necessary otherwise auto unboxing will cause NullPointerException
		if((sign == null || this.sign == sign) && (exponent  == null || this.exponent == exponent)) return this;
		try {
			AlgebraicParticle copy = (AlgebraicParticle) this.clone();
			copy.sign = sign == null ? this.sign : sign;
			copy.exponent = exponent == null ? this.exponent : exponent;
			return copy;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
}
