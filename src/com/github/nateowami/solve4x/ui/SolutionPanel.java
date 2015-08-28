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
package com.github.nateowami.solve4x.ui;

import java.awt.GridLayout;

import javax.swing.JPanel;

import com.github.nateowami.solve4x.solver.Solution;
import com.github.nateowami.solve4x.solver.Step;

/**
 * @author Nateowami
 */
public class SolutionPanel extends JPanel{
	
	public void setSolution(Solution solution) {
		
		this.setLayout(new GridLayout(0,1));
		
		this.removeAll();
		
		Step step = new Step(solution.getOriginalAlgebraicExpression());
		step.setAlgebraicExpression(solution.getOriginalAlgebraicExpression());
		this.add(new StepPanel(step));
		for(int i = 0; i < solution.length(); i++) {
			this.add(new StepPanel(solution.get(i)));
		}
		this.validate();
		this.repaint();
	}
	
}
