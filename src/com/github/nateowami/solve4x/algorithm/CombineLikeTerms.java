/*
    Solve4x - An audio-visual algebra solver
    Copyright (C) 2014 Nathaniel Paulus

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
package com.github.nateowami.solve4x.algorithm;

import com.github.nateowami.solve4x.solver.Algorithm;
import com.github.nateowami.solve4x.solver.Equation;
import com.github.nateowami.solve4x.solver.Expression;
import com.github.nateowami.solve4x.solver.Step;
import com.github.nateowami.solve4x.solver.Term;

/**
 * @author Nateowami
 */
public class CombineLikeTerms extends Algorithm{

	/**
	 * Combines like terms in a given equation
	 * @param equation The equation to combine terms in
	 * @return A Step with this algorithm applied
	 */
	@Override
	public Step getStep(Equation equation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Tells how smart it is to use this algorithm to the specified equation
	 * @param equation The equation to check
	 * @return On a scale 0-9 how smart it is to use this algorithm on this equation
	 */
	@Override
	public int getSmarts(Equation equation) {
		//find which expression needs simplifying the most
		int num = 0;
		for(int i=0; i<equation.getSize(); i++){
			if(howManyLike(equation.getExpression(i)) > num){
				num = howManyLike(equation.getExpression(i));
			}
		}
		//figure out the smartness based on number of like terms
		if(num == 2){
			return 4;
		}
		else if(num == 3){
			return 6;
		}
		else if(num == 4){
			return 8;
		}
		else if(num > 4){
			return 9;
		}
		else{
			return 0;
		}
	}
	
	/**
	 * Tells how many terms in a given expression are alike
	 * For example 3x+4x+5x+5x4+2x4 will return 3
	 * @param expr The expression to check
	 * @return The number of like terms
	 */
	private int howManyLike(Expression expr){
		int num = 0;
		for(int i = 0; i< expr.numbOfTerms(); i++){
			//update num
			if(num < numLikeThis(expr, expr.termAt(i))){
				num = numLikeThis(expr, expr.termAt(i));
			}
		}
		return num;
	}
	
	/**
	 * Tells how many terms in a given expression are the same type as the specified term
	 * @param expr The expression
	 * @param term The term
	 * @return The number of terms in expr that are the same type as term
	 */
	private int numLikeThis(Expression expr, Term term){
		int num = 0;
		//check all terms in the expression for being like term
		for(int i =0;  i< expr.numbOfTerms(); i++){
			if(expr.termAt(i).getBody().equals(term.getBody())){
				i++;
			}
		}
		return num;
	}
}
