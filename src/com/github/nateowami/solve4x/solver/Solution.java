/*
    Solve4x - An algebra solver that shows its work
    Copyright (C) 2015  Nathaniel Paulus

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
	
	private ArrayList <Step>steps = new ArrayList<Step>();//a list of steps
	private Algebra original;
	private String summary;
	private boolean summaryLast = true;
	
	/**
	 * Creates a new Solution from another Solution
	 * @param solution The Solution to copy
	 */
	public Solution(Solution solution){
		this.original = solution.original;
		this.steps.addAll(solution.getList());
	}
	
	/**
	 * Creates a new Solution
	 * @param equation The equation that is being solved, in it's original form.
	 */
	public Solution(Algebra algebra){
		this.original = algebra;
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
	public int length(){
		return this.steps.size();
	}
	
	/**
	 * Gets the Step at the specified index
	 * @param index The index of the Step you want
	 * @return The Step specified
	 */
	public Step get(int index){
		return this.steps.get(index);
	}
	
	/**
	 * @return The latest state of the equation or algebraic expression being solved in the 
	 * solution. This is either the result of the last solving step, or the original expression if 
	 * no solving steps have occurred.
	 */
	public Algebra getLastAlgebraicExpression(){
		return this.steps.size() > 0 ? this.steps.get(this.steps.size() - 1).getAlgebraicExpression() : this.original;
	}
	
	/**
	 * @return The solutions's summary (may be null);
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Sets the solution's summary.
	 * @param summary The summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	/**
	 * Tells whether the summary is to be placed after the solution.
	 * @return True if the summary should come after the solution, or false if it should come first.
	 */
	public boolean isSummaryLast() {
		return summaryLast;
	}

	/**
	 * Sets whether the summary should come last, that is, after the main part of the solution. Set 
	 * to true if you want the summary at the end, otherwise false.
	 * @param summaryLast True for a summary at the end, or false to place it at the beginning.
	 */
	public void setSummaryLast(boolean summaryLast) {
		this.summaryLast = summaryLast;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Solution [steps=" + steps + ", original=" + original + "]";
	}
	
	/**
	 * @return The original algebraic expression, before any solver steps were performed.
	 */
	public Algebra getOriginalAlgebraicExpression() {
		return original;
	}

		
}