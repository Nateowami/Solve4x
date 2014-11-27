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
	 * @throws ParsingException if s cannot be parsed as an algebraic particle.
	 * @param s The string to parse as an AlgebraicParticle.
	 * @param c A list of classes to from which to try construction.
	 * @return An AlgebraicParticle representing s.
	 */
	public static AlgebraicParticle getInstance(String s, Class<? extends AlgebraicParticle>[] c) {
		String original = s; //for debugging purposes
		if(s.length() < 1) throw new ParsingException("Cannot construct AlgebraicParticle from empty string.");
		
		//take care of sign
		boolean sign = !(s.charAt(0) == '-');
		if(s.charAt(0) == '-' || s.charAt(0) == '+') s = s.substring(1);
		
		if(s.length() < 1) throw new ParsingException("Cannot construct AlgebraicParticle from \"" + original + "\"." );
		
		//the exponent
		int exponent = 1;
		String ex = exponent(s);
		if(ex.length() != 0){
			exponent = Integer.parseInt(ex);
			s = s.substring(0, s.length() - ex.length());
		}
		
		if(s.length() < 1) throw new ParsingException("Cannot construct AlgebraicParticle from \"" + original +  "\".");
		
		//remove parentheses - necessary because expressions like "(4x)" need the parentheses stripped off
		s = Util.removePar(s);
			
		//construct the algebraic particle and return
		AlgebraicParticle partical;
		try{
			partical = construct(s, c);
			partical.exponent = exponent;
			partical.sign = sign;
			return partical;
		} catch (ParsingException e){
			throw new ParsingException("Cannot construct AlgebraicParticle from \"" + original +  "\". With sign, exponent, and parentheses removed it's \"" + s + "\".");
		}		
	}
	
	/**
	 * Tells if s can be parsed as an AlgebraicParticle.
	 * @param s The string to check.
	 * @param c A list of classes to check for being parseable
	 * @return If s can be parsed as an AlgebraicParticle.
	 */
	public static boolean parseable(String s, Class<? extends AlgebraicParticle>[] c){
		if (s.length() < 1)return false;
		//remove the sign
		if(s.charAt(0) == '-' || s.charAt(0) == '+') s = s.substring(1);
		if (s.length() < 1)return false;
		
		//remove pars - necessary because expressions like "(4x)" need the parentheses stripped off
		s = Util.removePar(s);
		if (s.length() < 1)return false;
		
		//remove the exponent
		s = s.substring(0, s.length() - exponent(s).length());
		if (s.length() < 1)return false;
		
		//deal with exponent
		/*int e = s.length()-1;
		while(e > 0 && Util.isSuperscript(Character.toString(s.charAt(e))))e--;
		e++;//because we decrement it before checking if it's a superscript
		s = s.substring(0, e);*/
		
		if(whichClass(s, c) != null) return true;
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
		return (sign ? "" : '-') + s + (exponent == 1 ? "" : Util.toSuperscript(Integer.toString(exponent)));
	}
	
	/**
	 * Every AlgebraicParticle needs to define its own toString() for debugging purposes.
	 */
	public abstract String toString();
	
	/**
	 * Tells which (if any) class can parse s.
	 * @param s The string for which to hunt a class that can parse it.
	 * @param classes A list of classes from which to check.
	 * @return The name of the class that can parse s. If none exist, it returns null.
	 */
	static private String whichClass(String s, Class[] classes){
		for(Class i : classes){
			String n = i.getSimpleName();
			if(n.equals("Variable")    && Variable   .parseable(s)) return "Variable";
			if(n.equals("Number")      && Number     .parseable(s)) return "Number";
			if(n.equals("Root")        && Root       .parseable(s)) return "Root";
			if(n.equals("Fraction")    && Fraction   .parseable(s)) return "Fraction";
			if(n.equals("MixedNumber") && MixedNumber.parseable(s)) return "MixedNumber";
			if(n.equals("Term")        && Term       .parseable(s)) return "Term";
			if(n.equals("Expression")  && Expression .parseable(s)) return "Expression";
		}
		return null;
	}
	
	/**
	 * Constructs a new algebraic particle from s.
	 * @param s The string from which to construct an algebraic particle.
	 * @param classes A list of classes to try to parse s.
	 * @return An algebraic particle that is the parsed version of s.
	 */
	static private AlgebraicParticle construct(String s, Class[] classes){
		for(Class i : classes){
			String n = i.getSimpleName();
			if(n.equals("Variable")    && Variable   .parseable(s)) return new Variable(s);
			if(n.equals("Number")      && Number     .parseable(s)) return new Number(s);
			if(n.equals("Root")        && Root       .parseable(s)) return new Root(s);
			if(n.equals("Fraction")    && Fraction   .parseable(s)) return new Fraction(s);
			if(n.equals("MixedNumber") && MixedNumber.parseable(s)) return new MixedNumber(s);
			if(n.equals("Term")        && Term       .parseable(s)) return new Term(s);
			if(n.equals("Expression")  && Expression .parseable(s)) return new Expression(s);
		}
		throw new ParsingException("Cannot parse " + s + " as an algebraic particle.");
	}
	
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
	 * @return The exponent, as an int.
	 */
	private static String exponent(String s){
		int i;
		for(i = s.length(); i > 1 && Util.isSuperscript(Character.toString(s.charAt(i-1))); i--);
		return i == s.length() ? "" : Integer.toString(Util.superscriptToInt(s.substring(i)));
	}
	
}
