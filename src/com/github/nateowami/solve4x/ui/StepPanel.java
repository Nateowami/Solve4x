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

import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.github.nateowami.solve4x.solver.Algebra;
import com.github.nateowami.solve4x.solver.Step;
import com.github.nateowami.solve4x.visual.GraphicalRenderer;

/**
 * @author Nateowami
 */
public class StepPanel extends JPanel {
	
	public StepPanel(Step step, Font font) {
		//set the layout to have two rows and one column
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//use a wrap layout for the explanation that goes in the top row
		JPanel top = new JPanel();
		top.setLayout(new WrapLayout());
		
		//iterate over the explanation and add text and rendered images to the top row
		for(Object obj : step.getExplanation()) {
			if(obj instanceof String) {
				JLabel label = new JLabel((String) obj);
				label.setFont(font);
				top.add(label);
			}
			else top.add(new JLabel(new ImageIcon(GraphicalRenderer.render((Algebra)obj))));
		}
		
		//add the top row
		this.add(top);
		
		//show what the equation looks like after it goes through this step
		JLabel bottom = new JLabel(new ImageIcon(GraphicalRenderer.render(step.getAlgebraicExpression())));
		bottom.setAlignmentX(CENTER_ALIGNMENT);
		this.add(bottom);
		
		this.revalidate();
		this.repaint();
	}
	
}
