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

import com.github.nateowami.solve4x.Solve4x;

/**
 * Represents a single step in solving or simplifying an equation or expression
 * @author Nateowami
 */
public class Step {
	
	//list of stages in a step. Normally length will be 1; however cancelling factors
	//can cause there to be 2 stages in a step; possibly more in the future.
	private String stages[];
	private ArrayList <String> explanation = new ArrayList<String>();
	private int difficulty;//the difficulty of the step
	
	/**
	 * Constructs a Step object
	 * @param step an ArrayList <String> containing the different stages of this solving step. 
	 * Usually the length will be one; it can be more if we are cancelling like terms.
	 * @param explain The explanation for this step. It needs to be written in a particular 
	 * syntax.
	 */
	public Step(String step[], String explanation, int difficulty){
		Solve4x.debug("Creating a Step from the following stage: " + step[0]);
		Thread.currentThread().dumpStack();
		//init the stages ArrayList
		this.stages = step;
		//init the explanation for this step
		this.explanation.add(explanation);
		//init the difficulty
		this.difficulty = difficulty;
	}
	
	/**
	 * Finds the length of the step. This will often be just one.
	 * @return The length of the step
	 */
	public int getStepLength(){
		return this.stages.length;
	}
	
	/**
	 * Finds the stage of the step at the specified index
	 * @param index The stage of the step to find. For example, if there are 
	 * Three stages to this step, sending an index of two will find the last
	 * stage of the solving step.
	 * @return The part of the step at index. For example, it may return 5x=6 if 
	 * that's the first stage of this solving step.
	 */
	public String getStageAt(int index){
		return this.stages[index];
	}
	
	/**
	 * Returns the explanation for a particular stage of this solving step
	 * @param i The stage of the step you want explained
	 * @return The explanation
	 */
	public String getExplanation(int i){
		return this.explanation.get(i);
	}

	/**
	 * @return the difficulty of this step (0-9)
	 */
	public int getDifficulty() {
		return difficulty;
	}

	/**
	 * @param difficulty the difficulty to set for this step (0-9)
	 */
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * @return Returns the last stage of this Step
	 */
	public String getLastStage() {
		return getStageAt(this.stages.length - 1);
	}
	
}
