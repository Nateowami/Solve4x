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

import java.util.ArrayList;

/**
 * Represents a solution for a given equation or expression by 
 * holding a Step ArrayList.
 * @author Nateowami
 */
public class Solution {
	
	private Equation equation;//the original equation or expression
	ArrayList <Step>steps = new ArrayList<Step>();//a list of steps
	//holds the latest equation (which would also be in the form of a string in Step)
	Equation lastEquation;
	
	/**
	 * Creates a new Solution from another Solution
	 * @param solution The Solution to copy
	 */
	public Solution(Solution solution){
		this.equation = solution.getEquation();
		this.steps = solution.getList();
		this.lastEquation = solution.getLastEquation();
	}
	
	/**
	 * Creates a new Solution
	 * @param equation The equation that is being solved
	 */
	public Solution(Equation equation){
		this.equation = equation;
		//the first and the last are the same
		this.lastEquation = equation;
	}
	
	/**
	 * Gets the list of steps for solving
	 * @return The list of steps for solving
	 */
	private ArrayList<Step> getList() {
		return this.steps;
	}

	/**
	 * Gets the original equation that the user entered
	 * @return The original equation
	 */
	public Equation getEquation(){
		return this.equation;
	}
	
	/**
	 * Adds a Step to the current Solution
	 * @param step
	 */
	public void addStep(Step step){
		this.steps.add(step);
		this.lastEquation = new Equation(step.getLastStage());
	}
	
	/**
	 * Finds the number of steps in this Solution
	 * @return The current number of steps
	 */
	public int getNumbOfSteps(){
		return this.steps.size();
	}
	
	/**
	 * Gets the Step at the specified index
	 * @param index The index of the Step you want
	 * @return The Step specified
	 */
	public Step getStepAt(int index){
		return this.steps.get(index);
	}
	
	/**
	 * @return The last Step currently in this Solution
	 */
	public Step getLastStep(){
		return this.steps.get(steps.size()-1);
	}

	/**
	 * @return The last equation of this solution
	 */
	public Equation getLastEquation() {
		return lastEquation;
	}
}
