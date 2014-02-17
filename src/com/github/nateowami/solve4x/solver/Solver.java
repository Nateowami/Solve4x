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
package com.github.nateowami.solve4x.solver;

import java.nio.charset.MalformedInputException;
import java.util.ArrayList;

import com.github.nateowami.solve4x.Solve4x;
import com.github.nateowami.solve4x.algorithm.CombineLikeTerms;

/**
 * Solves equations and simplifies expressions
 * @author Nateowami
 */

public class Solver {
	
	//the final solution
	private Solution finalSolution;
	//a list of solutions that may work. Whichever one is best will be used in the end
	ArrayList <Solution> solutions = new ArrayList<Solution>();
	//A list of algorithms that can be used for solving
	ArrayList <Algorithm> algor;
	
	
	/**
	 * Creates a new Solver so you can call solve and get the solution.
	 * @param equation The equation or expression to solve, simplify, 
	 * factor, multiply, etc
	 * @param selection The users selection. Do they want this to be 
	 * factored, solved, simplified, or what?
	 * @see com.github.nateowami.solve4x.solver.SolveFor
	 */
	public Solver(String equation, SolveFor selection) throws MalformedInputException{
		
		//reset the solution from any previous solving
		finalSolution = null;
		
		//remove spaces
		equation = equation.replaceAll(" ", "");
		//remove commas (they may be in numbers)
		equation = equation.replaceAll(",", "");
		
		//validate the equation
		//if there is an equals sign (=)
		if(equation.indexOf('=') != -1){
			//if it isn't valid as an equation
			if(!Validator.eqIsValid(equation)){
					throw new MalformedInputException(0);
			}
		}
		//it must be an expression
		else{
			//if it's not valid as an expression
			if(!Validator.exprIsValid(equation)){
				throw new MalformedInputException(0);
			}
		}
		
		Solve4x.debug("Equation successfully validated");
		
		//set the solving algorithms list based on what the user selected
		//if they wanted it solved
		if(selection == SolveFor.SOLVE){
			algor = getSolvingList();
		}
		//if the user wanted it simplified
		else if(selection == SolveFor.SIMPLIFY){
			algor = getSimplifyingList();
		}
		
		Solve4x.debug(algor.size()+" algorithms are going to be used"); 
		
		//OK, now to solve. Nateowami is scared
		//add an initial solution to the solution list
		solutions.add(new Solution(new Equation(equation)));
		
		//OK, now iterate through the solving algorithms AND the solutions 
		//(currently only 1 solution, but this could grow as we take forks in the road)
		//here goes...

		//as long as it's not solved/simplified
		//currently only loops 25 times
		for(int i = 0; whichIsFinished(solutions) == -1 && i<25; i++){
			Solve4x.debug("Solution iteration begun: " + i);
			//take out/copy all the solutions and remove them from the list
			ArrayList <Solution>copy = new ArrayList<Solution>(solutions);
			solutions = new ArrayList<Solution>();
			
			//loop through the solutions
			for(int a=0; a<copy.size(); a++){
				Solve4x.debug("Looping through the solutions: " + a);
				//now loop through the algorithms
				for(int b=0; b<algor.size(); b++){
					Solve4x.debug("Looping through the algorithms: " + b);
					//if this algorithm thinks it should be used in this situation
					if(algor.get(b).getSmarts(copy.get(a).getLastEquation()) >= 4){
						//use this Algorithm
						Solve4x.debug("Using an algorithm");
						//create a solution
						Solution solution = copy.get(a);
						//create a step to add to it
						Step step = algor.get(b).getStep(copy.get(a).getLastEquation());
						Solve4x.debug(step.toString());
						//add the step
						solution.addStep(step);
						//add it to the solution list
						solutions.add(solution);
					}
					else{
						Solve4x.debug("Algorithm not used");
					}
				}
			}
		}
		//set finalSolution
		finalSolution = getBestSolution(solutions);
	}
	
	/**
	 * Solves the given equation or simplifies it if it is an expression
	 * @param expr The expression or equation to solve or simplify
	 * @return A Solution object that contains the steps for solving
	 */
	public Solution getSolution(){
		
		return finalSolution;
	}
	
	/**
	 * Makes a list of solving algorithms
	 * @return An ArrayList of algorithms for solving
	 * @see getSimplifyingList()
	 */
	private static <Algorithm>ArrayList getSolvingList(){
		
		//the list of algorithms
		ArrayList <Algorithm>algorList = new ArrayList<Algorithm>();
		
		//add the list of simplifying algorithms (all simplifying algorithms are also for solving)
		algorList.addAll(getSimplifyingList());
		
		//TODO add more stuff to algorList
		
		
		return algorList;
		
	}
	
	/**
	 * Makes a list of algorithms for simplifying
	 * @return An ArrayList of algorithms for simplifying
	 * @see getSolvingList()
	 */
	private static <Algorithm>ArrayList getSimplifyingList(){
		
		//make the list of algorithms
		ArrayList <Algorithm>algorList = new ArrayList<Algorithm>();
		algorList.add((Algorithm) new CombineLikeTerms());
		//TODO more add stuff to the algorList
		
		return algorList;
	}
	
	/**
	 * Tells if an equation is solved 
	 * TODO tell is an expression is fully simplified
	 * @return If the equation is solved
	 */
	private boolean isSolved(String equation){
		//XXX We're assuming this is an equation, which currently is
		//true, but we need to support more late. Possibly in another method
		
		//create a new equation
		Equation eq = new Equation(equation);
		
		//check for an identity (i.e. 1=1, 3/4=3/4)
		//in other words, see if they're the same on both sides of the = sign
		if(eq.getExpression(0).getAsString().equals(eq.getExpression(1).getAsString())){
			return true; //if it's an identity it's SOLVED (technically)
		}
		
		//if the first is a variable and the second is a number
		else if(Util.isLetter(eq.getExpression(0).getAsString()) && Util.isNumber(eq.getExpression(1).getAsString())){
			return true;
		}
		
		//if the second is a variable and the first is a number
		else if(Util.isLetter(eq.getExpression(1).getAsString()) && Util.isNumber(eq.getExpression(0).getAsString())){
			return true;
		}
		
		//At this time the above three are all that I can think of. If there are more
		//we will need to talk about them and add them.
		else return false;
		
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
		System.out.println(solList.get(0).getLastStep().getStageAt(0));
		//check all solutions
		for(int i=0; i<solList.size(); i++){
			
			//check the first and second expressions
			if(isSimplified(solList.get(i).getEquation().getExpression(0).getAsString())
					&& isSimplified(solList.get(i).getEquation().getExpression(1).getAsString())){
				Solve4x.debug("Returns " + i);
				return i;
			}
		}
		Solve4x.debug("Returns -1");
		return -1;
	}
	
	/**
	 * Tells if an expression is fully simplified. Examples: 23&lt;2&gt;/&lt;3&gt;
	 * Works best for fractions. If it's complicated it's unreliable and will return false
	 * @param expr
	 * @return
	 */
	private boolean isSimplified(String expr){
		Solve4x.debug("Checking if it's simplified: " + expr);
		//if it's a number
		if(Util.isNumber(expr)){
			Solve4x.debug("It's a number alright");
			//AND it's fully simplified (TODO)
			if(Util.isFullySimplified(expr)){
				Solve4x.debug("Returning true");
				return true;
			}
			//not fully simplified
			else{
				Solve4x.debug("Returning false");
				return false;
			}
		}
		//if it's a variable
		else if(expr.length() == 1 && Util.isLetter(expr.charAt(0))){
			Solve4x.debug("Returning true");
			return true;
		}
		//I can't think of any other way for it to be fully simplified
		else {
			Solve4x.debug("Returning false");
			return false;
		}
	}
	
	/**
	 * Tells which Solution is the best
	 * @param solutions An ArrayList of Solutions to search
	 * @return The Solution that is solved the best
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
	
}
