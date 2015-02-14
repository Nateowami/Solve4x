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

/**
 * Represents a solution for a given equation or expression by 
 * holding a Step ArrayList.
 * @author Nateowami
 */
public class Solution {
	
	ArrayList <Step>steps = new ArrayList<Step>();//a list of steps
	
	/**
	 * Creates a new Solution from another Solution
	 * @param solution The Solution to copy
	 */
	public Solution(Solution solution){
		this.steps = solution.getList();
	}
	
	/**
	 * Creates a new Solution
	 * @param equation The equation that is being solved
	 */
	public Solution(Equation equation){
		Step firstStep = new Step(equation, 0);
		firstStep.explain("Here's the original equation.");
		this.steps.add(firstStep);
	}
	
	/**
	 * Gets the list of steps for solving
	 * @return The list of steps for solving
	 */
	private ArrayList<Step> getList() {
		return this.steps;
	}
	
	/**
	 * Adds a Step to the current Solution
	 * @param step
	 */
	public void addStep(Step step) {
		this.steps.add(step);
	}
	
	/**
	 * Finds the number of steps in this Solution
	 * @return The current number of steps
	 */
	public int numOfSteps(){
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
	 * Tells the difficulty of this Solution
	 * @return The sum of the difficulties of the individual steps.
	 */
	public int difficulty() {
		int difficulty = 0;
		for(Step s : this.steps){
			difficulty += s.getDifficulty();
		}
		return difficulty;
	}

	/**
	 * @return The last step of the solution (equivalent to Solution.getStepAt(Solution.numOfSteps()-1).
	 */
	public Step getLastStep() {
		return this.steps.get(this.steps.size()-1);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Solution [steps=" + steps + ", difficulty()=" + difficulty()
				+ "]";
	}
	
}