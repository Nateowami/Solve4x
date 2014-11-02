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

import java.util.ArrayList;

import com.github.nateowami.solve4x.Solve4x;
import com.github.nateowami.solve4x.algorithm.CombineLikeTerms;

/**
 * Solves equations and simplifies expressions
 * @author Nateowami
 */

public class Solver {
	
	/**
	 * Represents the possible different ways to solve. For example, maybe the user wants
	 * the expression simplified, or maybe they want it put in a certain form. Or maybe they
	 * only need to factor the expression. The user's selection is represented by this enum.
	 * @author Nateowami
	 */
	public enum SolveFor {
		SOLVE, SIMPLIFY, FACTOR
	}
	
	//the final solution
	private Solution finalSolution;
	//a list of solutions that may work. Whichever one is best will be used in the end
	private ArrayList <Solution> solutions = new ArrayList<Solution>();
	//A list of algorithms that can be used for solving
	private final ArrayList <Algorithm> algor;
	
	
	/**
	 * Creates a new Solver so you can call getSolution().
	 * @param equation The equation or expression to solve, simplify, 
	 * factor, multiply, etc
	 * @param selection The user's selection. Do they want this to be 
	 * factored, solved, simplified, or what? See {@link com.github.natewoami.solve4x.solver.Solver.SolveFor}.
	 */
	public Solver(String equation, SolveFor selection) {
		
		//remove spaces
		equation = equation.replaceAll(" ", "");
		//remove any commas that may be in numbers
		equation = equation.replaceAll(",", "");
		
		//validate the equation
		if(!Validator.isEqValid(equation)){
			throw new ParsingException("Validator says equation \"" + equation + "\" is invalid.");
		}
		
		this.algor = getAlgorithms(selection);
		
		//OK, now to solve
		//add an initial solution to the solution list
		solutions.add(new Solution(new Equation(equation)));
		
		//OK, now iterate through the solving algorithms AND the solutions 
		//(currently only 1 solution, but this could grow as we take forks in the road)

		//as long as it's not solved/simplified
		//currently loops max 25 times
		for(int i = 0; whichIsFinished(solutions) == -1 && i<25; i++){
			//take out/copy all the solutions and remove them from the list
			ArrayList <Solution>copy = new ArrayList<Solution>(solutions);
			solutions = new ArrayList<Solution>();
			
			//loop through the solutions
			for(int a=0; a<copy.size(); a++){
				//now loop through the algorithms
				for(int b=0; b<algor.size(); b++){
					//if this algorithm thinks it should be used in this situation
					if(algor.get(b).getSmarts(copy.get(a).getLastStep().getEquation()) >= 4){
						//use this Algorithm
						//create a solution
						Solution solution = copy.get(a);
						//create a step to add to it
						Step step = algor.get(b).getStep(copy.get(a).getLastStep().getEquation());
						solution.addStep(step);
						//add it to the solution list
						solutions.add(solution);
					}
				}
			}
		}
		//set finalSolution
		finalSolution = getBestSolution(solutions);
	}
	

	/**
	 * @return A Solution object that contains all the steps for solving
	 */
	public Solution getSolution(){
		return finalSolution;
	}
			
	/**
	 * Tells if we're done solving. It looks through the list of solutions and finds one that is 
	 * solved, simplified, or whatever needs to be done to it. Returns positive int if it finds one, -1
	 * if there is no solution yet
	 * @param solList The list of Solutions to check
	 * @return The index of a Solution that is fully solved
	 */
	private int whichIsFinished(ArrayList<Solution> solList){
		Solve4x.debug("checking if we're done. the list of solutions has the following length: " + solList.size());
		//if there aren't any solutions return -1
		if(solList.size() == 0) return -1;
		//check all solutions
		for(int i=0; i<solList.size(); i++){
			
			//check the first and second expressions
			if(isSimplified(solList.get(i).getLastStep().getEquation().getExpression(0))
					&& isSimplified(solList.get(i).getLastStep().getEquation().getExpression(0))){
				Solve4x.debug("Returns " + i);
				return i;
			}
		}
		Solve4x.debug("Returns -1");
		return -1;
	}
	
	/**
	 * Tells if an expression is fully simplified. Examples: 23(2)/(3)
	 * Works best for fractions. If it's complicated it's unreliable and will return false
	 * @param expr The expression to check
	 * @return If the expr is fully simplified
	 */
	private boolean isSimplified(AlgebraicParticle a){
		if(a.exponent() != 1)return false;
		else if(a instanceof Number) return true;
		else if(a instanceof Variable) return true;
		else if(a instanceof Fraction && ((Fraction)a).isSimplified()) return true;
		else if(a instanceof MixedNumber && ((MixedNumber)a).isSimplified()) return true;
		else return false;
	}
	
	/**
	 * Tells which Solution is the best
	 * @param solutions An ArrayList of Solutions to search
	 * @return The Solution that is solved the best, or null if no solutions are given.
	 */
	private Solution getBestSolution(ArrayList<Solution> solutions) {
		//the index of the best solution found so far
		int index = -1;
		//how "difficult" that solution is
		int difficulty = -1;
		//now iterate over the solutions and find the best one
		for(int i = 0; i< solutions.size(); i++){
			//if the difficulty of the current Solution is less than that of any Solution found so far
			//or it's the first solution we've looked at 
			if(solutions.get(i).difficulty() < difficulty || i == 0){
				index = i;
			}
		}
		//index is now the the index of the best Solution or is -1, if there is no solution
		
		//if index is 0 or positive
		if(index >= 0){
			//return the solution that was found
			return solutions.get(index);
		}
		//there is no Solution; return null
		else return null;
	}
	
	/**
	 * Returns the applicable solving algorithms for the selection, e.g.,
	 * algorithms for solving, simplifying, factoring, etc.
	 * @param selection The user's selection.
	 * @return An ArrayList of algorithms applicable for the selection.
	 */
	private ArrayList<Algorithm> getAlgorithms(SolveFor selection) {
		ArrayList<Algorithm> algorList = new ArrayList<Algorithm>();
		switch (selection){
		case SOLVE:
		case FACTOR:
		case SIMPLIFY:
			algorList.add(new CombineLikeTerms());
		}
		return algorList;
	}
	
}
