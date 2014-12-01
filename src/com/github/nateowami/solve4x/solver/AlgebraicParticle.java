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
	 * @param s The string to parse as an AlgebraicParticle.
	 * @param c A "blacklisted" class that will not be used (directly) in initializing this algebraic particle.
	 * @return An AlgebraicParticle representing s (may be left null).
	 * @throws ParsingException if s cannot be parsed as an algebraic particle.
	 */
	public static AlgebraicParticle getInstance(String s, Class<? extends AlgebraicParticle> c) {
		String original = s; //for debugging purposes
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
			s = rmPars;
			withExponent = rmPars;
		}
			
		//construct the algebraic particle and return
		AlgebraicParticle particle;
		try{
			particle = construct(withExponent, s, c);
			/*
			 * The exponent is not the one calculated if it was applied to a term or expression and that term or
			 * Expression wasn't surrounded with parentheses. For example, with 4x+6⁴ we calculate that the 
			 * exponent is 4, but in reality there is no exponent (same as exponent of 1). In this case, withExponent
			 * would equal 4x+6⁴, and s would be 4x+6. So we use exponent of 1.
			*/
			particle.exponent = !withExponent.equals(s) && (particle instanceof Term || particle instanceof Expression) ? 1 : exponent;
			particle.sign = sign;
			return particle;
		} catch (ParsingException e){
			throw new ParsingException("Cannot construct AlgebraicParticle from \"" + original +  "\". With sign, exponent, and parentheses removed it's \"" + s + "\".");
		}		
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
	 * @param c A "blacklisted" class that will not be used (directly) in checking 
	 * whether s can be parsed (may be left null).
	 * @return If s can be parsed as an AlgebraicParticle.
	 */
	public static boolean parseable(String s, Class<? extends AlgebraicParticle> c){
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
			s = rmPars;
			withExponent = rmPars;
		}
		if (s.length() < 1)return false;
		
		if(parseableBySubclasses(withExponent, s, c)) return true;
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
	 * Tells if any class can parse withExponent or exponentRemoved. exponentRemoved will be 
	 * used when checking classes Variable and Number, while withExponent will be used in all 
	 * other cases
	 * @param withExponent The string to test, with the
	 * exponent still remaining, only if after removing any exponent, it is not fully nested 
	 * in parentheses.
	 * @param exponentRemoved The string to test, with the exponent and any parentheses removed.
	 * @param classes The classes to consider (i.e. these classes will be checked to see if they
	 * can parse the given strings.
	 * @return If a class listed in classes can construct from withExponent or exponentRemoved, 
	 * depending on the situation (see above).
	 */
	static private boolean parseableBySubclasses(String withExponent, String exponentRemoved, Class<? extends AlgebraicParticle> bl){
		String n = bl == null ? "" : bl.getSimpleName();
		if(!n.equals("Variable")    && Variable   .parseable(exponentRemoved)) return true;
		if(!n.equals("Number")      && Number     .parseable(exponentRemoved)) return true;
		if(!n.equals("Root")        && Root       .parseable(withExponent)) return true;
		if(!n.equals("Fraction")    && Fraction   .parseable(withExponent)) return true;
		if(!n.equals("MixedNumber") && MixedNumber.parseable(withExponent)) return true;
		if(!n.equals("Term")        && Term       .parseable(withExponent)) return true;
		if(!n.equals("Expression")  && Expression .parseable(withExponent)) return true;
		return false;
	}
	
	/**
	 * Constructs a new algebraic particle from withExponent or exponentRemoved.
	 * exponentRemoved is used when attempting to construct Variable or Number, while in all 
	 * other cases withExponent will be used.
	 * @param withExponent The string from which to construct an AlgebraicParticle, with the
	 * exponent still remaining, only if after removing any exponent, it is not fully nested 
	 * in parentheses.
	 * @param exponentRemoved The string from which to construct an AlgebraicParticle, with 
	 * the exponent and any parentheses removed.
	 * @param classes A list of classes to consider in constructing an AlgebraicParticle.
	 * @return An AlgebraicParticle, initialized with withExponent or exponentRemoved, depending
	 * on the situation (see above).
	 */
	static private AlgebraicParticle construct(String withExponent, String exponentRemoved, Class<? extends AlgebraicParticle> bl){
		String n = bl == null ? "" : bl.getSimpleName();
		if(!n.equals("Variable")    && Variable   .parseable(exponentRemoved))return new Variable(exponentRemoved);
		if(!n.equals("Number")      && Number     .parseable(exponentRemoved))return new Number(exponentRemoved);
		if(!n.equals("Root")        && Root       .parseable(withExponent))   return new Root(withExponent);
		if(!n.equals("Fraction")    && Fraction   .parseable(withExponent))   return new Fraction(withExponent);
		if(!n.equals("MixedNumber") && MixedNumber.parseable(withExponent))   return new MixedNumber(withExponent);
		if(!n.equals("Term")        && Term       .parseable(withExponent))   return new Term(withExponent);
		if(!n.equals("Expression")  && Expression .parseable(withExponent))   return new Expression(withExponent);
		throw new ParsingException("Cannot parse " + withExponent + " (with exponent) or " + exponentRemoved + " (exponent removed) as an algebraic particle.");
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
