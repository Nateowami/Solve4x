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
public abstract class AlgebraicParticle implements Cloneable {
	
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
	 * @param c A "blacklisted" class that will not be used (directly) in initializing this algebraic particle.
	 * Will be ignored if s is parenthesized.
	 * @return An AlgebraicParticle representing s (may be left null).
	 * @throws ParsingException if s cannot be parsed as an algebraic particle.
	 */
	public static AlgebraicParticle getInstance(String s, Class<? extends AlgebraicParticle> c) {
		String original = s;
		boolean pars = false;
		if(s.length() < 1) throw new ParsingException("Cannot construct AlgebraicParticle from empty string.");
		
		//take care of sign
		boolean sign = !(s.charAt(0) == '-');
		if(s.charAt(0) == '-' || s.charAt(0) == '+') s = s.substring(1);
		
		if(s.length() < 1) throw new ParsingException("Cannot construct AlgebraicParticle from \"" + original + "\"." );
		
		/*
		 * We need to distinguish between exponents like x(4+y)⁴ and stuff like (4x+6)⁴. With the latter,
		 * the whole expression is raised to the fourth power. For the former, just the 4+y part is raised
		 * to the fourth power. withExponent will be used for the former case, so that terms and expressions
		 * that don't have parentheses around them will not get their exponents set. See 
		 * https://github.com/Nateowami/Solve4x/issues/23 for more info.
		 */
		String withExponent = s;//save for later
		
		//the exponent
		int exponent = 1;
		String ex = exponent(s);
		if(ex.length() != 0){
			exponent = Integer.parseInt(ex);
			s = s.substring(0, s.length() - ex.length());
		}
		
		if(s.length() < 1) throw new ParsingException("Cannot construct AlgebraicParticle from \"" + original +  "\".");
		
		//remove parentheses - necessary because expressions like "(4x)" need the parentheses stripped off
		String rmPars = Util.removePar(s);
		//if there are parentheses to remove, then withExponent should be set to rmPars too
		if(rmPars.length() != s.length()){
			pars = true;
			s = rmPars;
			withExponent = rmPars;
			c = null;
		}
			
		//construct the algebraic particle and return
		AlgebraicParticle particle;
		particle = construct(pars ? s : original, withExponent, s, c);
		/*
		 * The exponent is not the one calculated if it was applied to a term or expression and that term or
		 * Expression wasn't surrounded with parentheses. For example, with 4x+6⁴ we calculate that the 
		 * exponent is 4, but in reality there is no exponent (same as exponent of 1). In this case, withExponent
		 * would equal 4x+6⁴, and s would be 4x+6. So we use exponent of 1.
		 */
		particle.exponent = !withExponent.equals(s) && (particle instanceof Term || particle instanceof Expression) ? 1 : exponent;
		particle.sign = withExponent.equals(s) && particle instanceof Expression ? true : sign;
		return particle;		
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
	 * @param c A "blacklisted" class that will not be used (directly) in checking. Will be ignored if
	 * s is parenthesized.
	 * whether s can be parsed (may be left null).
	 * @return If s can be parsed as an AlgebraicParticle.
	 */
	public static boolean parseable(String s, Class<? extends AlgebraicParticle> c){
		String original = s;
		boolean pars = false;
		if (s.length() < 1)return false;
		//remove the sign
		if(s.charAt(0) == '-' || s.charAt(0) == '+') s = s.substring(1);
		if (s.length() < 1)return false;
		
		String withExponent = s;//temporarily hold it with exponent and pars intact
		
		//remove the exponent
		s = s.substring(0, s.length() - exponent(s).length());
		if (s.length() < 1)return false;
		
		//remove pars - necessary because expressions like "(4x)" need the parentheses stripped off
		String rmPars = Util.removePar(s);
		if(rmPars.length() != s.length()){
			pars = true;
			s = rmPars;
			withExponent = rmPars;
			c = null;
		}
		if (s.length() < 1)return false;
		
		if(parseableBySubclasses(pars ? s : original, withExponent, s, c)) return true;
		return false;
	}
	
	/**
	 * Works like {@link #parseable(String, Class<? extends AlgebraicParticle>)} with 
	 * c (the class not to use) set to null.
	 * @param s The string to test.
	 * @return If s can be parsed as an algebraic particle.
	 */
	public static boolean parseable(String s){
		return parseable(s, null);
	}
	
	/**
	 * @return The string form of the algebraic particle.
	 */
	public abstract String render();
	
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
	 * other cases, except for expressions, which will use withSign.
	 * @param withSign The string to test, with the sign and exponent still remaining, only if 
	 * after removing any sign and exponent, it is stilly fully nested in parentheses.
	 * @param withExponent The string to test, with the exponent still remaining, only if after
	 * removing any exponent, it is not fully nested in parentheses.
	 * @param allRemoved The string to test, with the exponent and any parentheses removed.
	 * @param classes The classes to consider (i.e. these classes will be checked to see if they
	 * can parse the given strings.
	 * @return If a class listed in classes can construct from withExponent or exponentRemoved, 
	 * depending on the situation (see above).
	 */
	static private boolean parseableBySubclasses(String withSign, String withExponent, String allRemoved, Class<? extends AlgebraicParticle> bl){
		String n = bl == null ? "" : bl.getSimpleName();
		if(!n.equals("Variable")    && Variable   .parseable(allRemoved)) return true;
		if(!n.equals("Number")      && Number     .parseable(allRemoved)) return true;
		if(!n.equals("Root")        && Root       .parseable(withExponent)) return true;
		if(!n.equals("Fraction")    && Fraction   .parseable(withExponent)) return true;
		if(!n.equals("MixedNumber") && MixedNumber.parseable(withExponent)) return true;
		if(!n.equals("Term")        && Term       .parseable(withExponent)) return true;
		if(!n.equals("Expression")  && Expression .parseable(withSign)) return true;
		return false;
	}
	
	/**
	 * Constructs a new algebraic particle from withSign, withExponent, or allRemoved.
	 * allRemoved is used when attempting to construct Variable or Number, while in all 
	 * other cases withExponent will be used, except in the case of expressions, which always
	 * use withSign.
	 * @param withSign The string from which to construct an AlgebraicParticle, with the sign 
	 * and exponent still remaining, only if after removing any sign and exponent, it is not 
	 * fully nested in parentheses.
	 * @param withExponent The string from which to construct an AlgebraicParticle, with the
	 * exponent still remaining, only if after removing any exponent, it is not fully nested 
	 * in parentheses.
	 * @param allRemoved The string from which to construct an AlgebraicParticle, with 
	 * the exponent and any parentheses removed.
	 * @param classes A list of classes to consider in constructing an AlgebraicParticle.
	 * @return An AlgebraicParticle, initialized with withExponent or exponentRemoved, depending
	 * on the situation (see above).
	 */
	static private AlgebraicParticle construct(String withSign, String withExponent, String allRemoved, Class<? extends AlgebraicParticle> bl){
		String n = bl == null ? "" : bl.getSimpleName();
		if(!n.equals("Variable")    && Variable   .parseable(allRemoved))return new Variable(allRemoved);
		if(!n.equals("Number")      && Number     .parseable(allRemoved))return new Number(allRemoved);
		if(!n.equals("Root")        && Root       .parseable(withExponent))   return new Root(withExponent);
		if(!n.equals("Fraction")    && Fraction   .parseable(withExponent))   return new Fraction(withExponent);
		if(!n.equals("MixedNumber") && MixedNumber.parseable(withExponent))   return new MixedNumber(withExponent);
		if(!n.equals("Term")        && Term       .parseable(withExponent))   return new Term(withExponent);
		if(!n.equals("Expression")  && Expression .parseable(withSign))   return new Expression(withSign);
		throw new ParsingException("Cannot parse " + withSign + " (with sign) or "+ withExponent + " (with exponent) or " + allRemoved + " (exponent removed) as an algebraic particle.");
	}
	
	/**
	 * Clones the AlgebraicParticle with a new sign.
	 * @param sign The sign for the new AlgebraicParticle. May be null, in which case the current sign will be used.
	 * @return An almost-clone of AlgebraicParticle.
	 * TODO this doesn't need to be defined in subclasses; see cloneWithNewSignAndExponent(boolean,sign)
	 */
	public abstract AlgebraicParticle cloneWithNewSign(Boolean sign);
	
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
	 * @return The exponent, as an int.
	 */
	private static String exponent(String s){
		int i;
		for(i = s.length(); i > 1 && Util.isSuperscript(Character.toString(s.charAt(i-1))); i--);
		return i == s.length() ? "" : Integer.toString(Util.superscriptToInt(s.substring(i)));
	}
	
	/**
	 * Makes a psudo-clone this object (psudo in that a variable or two may be changed, though not 
	 * necessarily), optionally modifying two variables, sign and exponent.
	 * @param sign The sign of the returned object (if null, defaults to the sign of this).
	 * @param exponent The exponent of the returned object (if null, defaults to the exponent of this).
	 * @return A psudo-clone of this object
	 */
	public AlgebraicParticle cloneWithNewSignAndExponent(Boolean sign, Integer exponent){
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
