/*
    Solve4x - A Java program to solve and explain algebra problems
    Copyright (C) 2013 Nathaniel Paulus

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

/**
 * Solves equations and simplifies expressions
 * @author Nateowami
 */
public class Solver {
	
	//the expression or equation to solve ore simplify
	String eq;
	//a list of solutions that may work. Whichever one is best will be used in the end
	ArrayList <Solution> list = new ArrayList<Solution>();
	//A list of strategies that can be used for solving
	ArrayList <Strategy> strat;
	
	
	/**
	 * Creates a new Solver so you can call solve and get the solution.
	 * @param equation The equation or expression to solve, simplify, 
	 * factor, multiply, etc
	 * @param selection The users selection. Do they want this to be 
	 * factored, solved, simplified, or what?
	 * @see com.github.nateowami.solve4x.solver.SolveFor
	 */
	public Solver(String equation, SolveFor selection){
		
		//set the variables
		this.eq = equation;
		
		//set the solving strategies list based on what the user selected
		//if they wanted it solved
		if(selection == SolveFor.SOLVE){
			strat = getSolvingList();
		}
		//if the user wanted it simplified
		else if(selection == SolveFor.SIMPLIFY){
			strat = getSimplifyingList();
		}
		
		//TODO solve
		
	}
	
	/**
	 * Solves the given equation or simplifies it if it is an expression
	 * @param expr The expression or equation to solve or simplify
	 * @return A Solution object that contains the steps for solving
	 * @throws MalformedInputException 
	 */
	public Solution solve() throws MalformedInputException{
		
		//if it's an equation
		if(Util.isEq(this.eq)){
			//make sure the equation is valid
			if(!Validator.eqIsValid(this.eq)){
				//if it's not, throw an exception
				throw new MalformedInputException(this.eq.length());
			}
			else{
				//TODO solve it
			}
		}
		//then it's not an equation; maybe an expression
		else{
			//make sure the expression is valid
			if(!Validator.exprIsValid(this.eq)){
				throw new MalformedInputException(this.eq.length());
			}
			//TODO simplify it
		}
		return null;//XXX fix this later
	}
	
	/**
	 * Makes a list of solving strategies
	 * @return An ArrayList of strategies for solving
	 * @see getSimplifyingList()
	 */
	private static <Strategy>ArrayList getSolvingList(){
		
		//the list of strategies
		ArrayList <Strategy>stratList = new ArrayList<Strategy>();
		
		//add the list of simplifying strategies (all simplifying strategies are also for solving)
		stratList.addAll(getSimplifyingList());
		
		//TODO add more stuff to stratList
		
		
		return stratList;
		
	}
	
	/**
	 * Makes a list of strategies for simplifying
	 * @return An ArrayList of strategies for simplifying
	 * @see getSolvingList()
	 */
	private static <Strategy>ArrayList getSimplifyingList(){
		
		//make the list of strategies
		ArrayList <Strategy>stratList = new ArrayList<Strategy>();
		//add stuff to the stratList
		
		return stratList;
	}
	
}
