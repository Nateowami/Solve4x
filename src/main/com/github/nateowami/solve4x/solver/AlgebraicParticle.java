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
 * @author Nateowami
 */
public abstract class AlgebraicParticle {
	
	private int exponent;
	private boolean sign = true;
	
	public boolean sign(){
		return sign;
	}
	
	public int exponent(){
		return exponent;
	}
	
	public static AlgebraicParticle getInstance(String s) throws MalformedInputException{
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
		else throw new MalformedInputException(s.length());
		
	}
	
	public static boolean isAlgebraicParticle(String s){
		//TODO remove exponent and sign
		if(Number.isNumber(s) || Root.isRoot(s) || Fraction.isFraction(s) || ConstantFraction.isConstantFraction(s) 
				|| MixedNumber.isMixedNumber(s) || Term.isTerm(s) || Expression.isExpression(s)){
			return true;
		}
		else return false;
	}
			
	public abstract String getAsString();
	
}
