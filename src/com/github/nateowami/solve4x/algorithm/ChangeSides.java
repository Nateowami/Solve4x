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
package com.github.nateowami.solve4x.algorithm;

import com.github.nateowami.solve4x.solver.*;
import com.github.nateowami.solve4x.solver.Number;

/**
 * @author Nateowami
 */
public class ChangeSides extends Algorithm {

	/**
	 * Constructs a new ChangeSides algorithm.
	 */
	public ChangeSides() {
		super(Equation.class); // declare that ChangeSides works on equations
	}

	@Override
	public Step execute(Equation eq) {
		int maxSmarts = 0, index = 0;
		for(int i = 0; i < eq.length(); i+=2){
			int smarts = smartsForTwoExprssions(eq.get(i), eq.get(i+1));
			if(smarts > maxSmarts){maxSmarts = smarts; index = i;}
			if(smarts >= 9)break;//no use continuing then
		}
		AlgebraicParticle first = eq.get(index), second = eq.get(index+1);
		Expression left = first instanceof Expression ? (Expression) first : new Expression(true, new AlgebraicParticle[]{first}, 1),
				right = second instanceof Expression ? (Expression) second : new Expression(true, new AlgebraicParticle[]{second}, 1);
		
		boolean[] map1 = constantnessMap(left), map2 = constantnessMap(right);
		//count how many are constant on each side
		int const1 = 0, const2 = 0;
		for(boolean bool : map1) if(bool) const1++;
		for(boolean bool : map2) if(bool) const2++;
		//subtract to find how many non constants that leaves
		int rconst1 = map1.length - const1, rconst2 = map2.length - const2;
		
		//calculate which side we should put constants to separate them from variables with as few moves as possible
		boolean putConstOnRight = const1 + rconst2 <= const2 + rconst1;
		boolean moveConstants;
		//if constants are all on one side
		if(const1 == 0 || const2 == 0){
			moveConstants = false;
		}
		//if variables area all on one side
		else if(rconst1 == 0 || rconst2 == 0){
			moveConstants = true;
		}
		//constants and variables are on both sides then
		else{
			//if we're aiming for the form of e.g. x=6
			if(putConstOnRight){
				//move constants if there are more variables on the right than constants on the left
				moveConstants = rconst2 > const1;
			}
			//then we're going for the form of 6=x
			else{
				//move constants if there are more variables on the left than constants on the right
				moveConstants = rconst1 > const2;
			}
		}
		
		//copy all the values above, as destination and source, so we don't have to keep thinking about which way to move stuff
		Expression source, dest;
		boolean[] sourceMap, destMap;
		int sourceConst, destConst;
		//if we're moving to the right -whether constants or variables
		if(putConstOnRight == moveConstants){
			source = left; dest = right; 
			sourceMap = map1; destMap = map2;
			sourceConst = const1; destConst = const2;
		}
		else {
			source = right; dest = left; 
			sourceMap = map2; destMap = map1;
			sourceConst = const2; destConst = const1;
		}
		
		//create arrays of stuff to keep and stuff to move. calculate lengths from number constant/not constant
		AlgebraicParticle[] move = new AlgebraicParticle[moveConstants ? sourceConst : sourceMap.length - sourceConst], 
				keep = new AlgebraicParticle[moveConstants ? sourceMap.length - sourceConst : sourceConst];
		//use these as pointers to keep track of where append the elements to the above arrays
		int moveIndex = 0, keepIndex = 0;
		for(int i = 0; i < source.length(); i++){
			//if this is a constant and we're moving constants, or this is not constant, and we're moving non constants
			if(sourceMap[i] == moveConstants){
				move[moveIndex++] = source.get(i);
			}
			else{
				keep[keepIndex++] = source.get(i);
			}
		}
		
		AlgebraicParticle removed = keep.length == 0 ? Number.ZERO : unwrap(new Expression(true, keep, 1));
		//concat move and dest to get the result
		AlgebraicParticle[] calculateAdded = new AlgebraicParticle[dest.length() + move.length];
		int i;
		for(i = 0; i < dest.length(); i++){
			calculateAdded[i] = dest.get(i);
		}
		for(int j = 0; j < move.length; j++){
			//change the sign when we copy the terms we're moving to the other side
			calculateAdded[i++] = move[j].cloneWithNewSign(!move[j].sign());
		}	
		AlgebraicParticle added = new Expression(true, calculateAdded, 1);
		
		//clone the equation, putting source and dest beside each other, in the proper order
		eq = eq.cloneWithNewExpression(putConstOnRight == moveConstants ? removed : added, index);
		eq = eq.cloneWithNewExpression(putConstOnRight != moveConstants ? removed : added, index+1);
		Step step = new Step(eq, 5/*TODO*/);
		return step.explain("We need to move ").list(move)
				.explain(" to the " + (putConstOnRight == moveConstants ? "right" : "left") + " and change the sign" + (move.length == 1 ? "" : "s") + ".");
	}

	@Override
	public int smarts(Equation equation) {
		int max = 0;
		for(int i = 0; i < equation.length(); i+=2){
			int smarts = smartsForTwoExprssions(equation.get(i), equation.get(i+1));
			if(smarts > 7) return smarts;
			else if(max < smarts) max = smarts;
		}
		return max;
	}

	/**
	 * Calculates the smarts of moving terms between a and b. Neither needs to be an Expressions; if 
	 * one of them is not, it will be treated as a one-term Expression.
	 * @param a An algebraicParticle to compare with b.
	 * @param b An algebraicParticle to compare with a.
	 * @return An approximate "smartness" of moving terms between a and b.
	 */
	private int smartsForTwoExprssions(AlgebraicParticle a, AlgebraicParticle b){
		if(a.exponent() != 1) return 0;//because if an expression has an exponent, you can't move terms nicely
		//make a "map" of how which expressions are constant
		boolean[] map1 = constantnessMap(a), map2 = constantnessMap(b);
		//count how many are not constant on each side
		int count1 = 0, count2 = 0;
		for(boolean bool : map1) if(!bool) count1++;
		for(boolean bool : map2) if(!bool) count2++;

		int movable;//count how many terms should be moved
		//if non-constants are all on one side
		if(count1 == 0 || count2 == 0){
			//if variables are on the left
			if(count1 > 0) movable = map1.length - count1;
			else movable = map2.length - count2;
		}
		else{
			//the lesser of two values
			movable = count1 > count2 ? count2 : count1;
		}
		if(movable == 1) return 5;
		if(movable == 2) return 7;
		else if (movable > 2) return 9;
		else return 0;
	}
	
	/**
	 * Returns a "map" of which terms are constant. For example, given the equation 2x+6-1.8, the 
	 * return value would be {false, true, true}. This is intended as a way to cache the results 
	 * of finding which terms are constant. If a is not an Expression, an array of length 1 will 
	 * be returned, indicating whether a is constant.
	 * @param a An AlgebraicParticle for which you'd like a map.
	 * @return
	 */
	private boolean[] constantnessMap(AlgebraicParticle a){
		if(a instanceof Expression){
			Expression e = (Expression) a;
			boolean[] map = new boolean[e.length()];
			for(int i = 0; i < map.length; i++){
				map[i] = !hasVariable(e.get(i));
			}
			return map;
		}
		else return new boolean[]{!hasVariable(a)};
	}
	
	/**
	 * Tells if any object of type Variable is found in a, anywhere in the algebraic 
	 * hierarchy.
	 * @param a An object to check for variables.
	 * @return True if a has a variable, otherwise false.
	 */
	private boolean hasVariable(AlgebraicParticle a){
		if(a instanceof Variable) return true;
		else if(a instanceof AlgebraicCollection){
			AlgebraicCollection c = (AlgebraicCollection) a;
			for(int i = 0; i < c.length(); i++){
				if(hasVariable(c.get(i))) return true;
			}
			return false;
		}
		else if(a instanceof Fraction){
			Fraction f = (Fraction) a;
			return hasVariable(f.getTop()) || hasVariable(f.getBottom()); 
		}
		else return false;
	}
	
}