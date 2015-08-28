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
import java.awt.GridLayout;

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
	
	private static final Font font = new Font("SansSerif", Font.PLAIN, 14);

	public StepPanel(Step step) {
		this.setLayout(new GridLayout(2, 1));
		
		JPanel top = new JPanel();
		
		for(Object obj : step.getExplanation()) {
			if(obj instanceof String) {
				JLabel label = new JLabel((String) obj);
				label.setFont(font);
				top.add(label);
			}
			else top.add(new JLabel(new ImageIcon(GraphicalRenderer.render((Algebra)obj))));
		}
		
		this.add(top);
		this.add(new JLabel(new ImageIcon(GraphicalRenderer.render(step.getAlgebraicExpression()))));
	}
	
}
