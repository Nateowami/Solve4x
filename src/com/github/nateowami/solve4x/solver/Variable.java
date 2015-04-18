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
 * Represents a variable, A-Z or a-z
 * @author Nateowami
 */
public class Variable extends AlgebraicParticle{
	private final char var;
	
	/**
	 * Constructs a new Variable.
	 * @param s The string to parse.
	 * @throws If s is null, doesn't contain exactly one char, or the one char is not A-Z or a-z.
	 */
	protected Variable(String s) {
		if(s == null || s.length() != 1 || !Util.areAllLetters(Character.toString(s.charAt(0)))){
			throw new ParsingException("Cannot parse \"" + s + "\" as a variable. Expected one alphabetic character (a-z or A-Z).");
		}
		else var = s.charAt(0);
	}
	
	/**
	 * Constructs a new variable.
	 * @param sign The sign of the variable.
	 * @param var The variable itself (e.g. 'x').
	 * @param exponent The exponent of the variable.
	 */
	public Variable(boolean sign, char var, int exponent){
		super(sign, exponent);
		this.var = var;
	}
	
	/**
	 * @return The variables character (e.g. 'x').
	 */
	public char getVar(){
		return var;
	}
	
	/* (non-Javadoc)
	 * @see com.github.nateowami.solve4x.solver.AlgebraicParticle#getAsString()
	 */
	@Override
	public String getAsString() {
		return wrapWithSignParAndExponent(var + "", false);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Variable [var=" + var + ", sign()=" + sign() + ", exponent()="
				+ exponent() + "]";
	}
	
	/**
	 * Tells if s is parseable as a variable. Must contain one char, a-z or A-Z.
	 * @param s The string to check.
	 * @return If s is a variable.
	 */
	public static boolean parseable(String s) {
		if(s == null || s.length() != 1 || !Util.areAllLetters(Character.toString(s.charAt(0)))){
			return false;
		}
		else return true;
	}
	
	/* (non-Javadoc)
	 * @see com.github.nateowami.solve4x.solver.AlgebraicParticle#cloneWithNewSign(java.lang.Boolean)
	 */
	@Override
	public AlgebraicParticle cloneWithNewSign(Boolean sign) {
		return new Variable(sign == null ? this.sign() : sign,
				this.var,
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
		result = prime * result + var;
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return almostEquals(obj) && super.equals(obj);
	}
	
	public boolean almostEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Variable other = (Variable) obj;
		if (var != other.var)
			return false;
		return true;
	}
	
}
