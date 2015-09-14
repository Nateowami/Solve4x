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

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Scrollable;

import com.github.nateowami.solve4x.solver.Algebra;
import com.github.nateowami.solve4x.solver.Solution;
import com.github.nateowami.solve4x.solver.Step;
import com.github.nateowami.solve4x.visual.GraphicalRenderer;

/**
 * @author Nateowami
 */
public class SolutionPanel extends JPanel implements Scrollable {
	
	private static final Font font = new Font("SansSerif", Font.PLAIN, 14);
	
	public void setSolution(Solution solution) {
		
		//remove any elements that may be on the panel from a previous solutions
		this.removeAll();
		
		//grid layout with one column and any number of rows (0 -> any number of rows)
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel summary = null;
		//if there's a summary to show
		if(solution.getSummary() != null) {
			summary = new JPanel();
			JLabel summaryLabel = new JLabel(solution.getSummary());
			summaryLabel.setFont(font);
			summary.add(summaryLabel);
		}
		
		//add the summary at the top if it exists and belongs at the top
		if(summary != null && !solution.isSummaryLast()) this.add(summary);
		
		//add the original equation/algebra at the top
		JPanel original = new JPanel();
		JLabel originalLabel = new JLabel(new ImageIcon(GraphicalRenderer.render(solution.getOriginalAlgebraicExpression())));
		original.add(originalLabel);
		this.add(original);
		
		//add each step
		for(int i = 0; i < solution.length(); i++) {
			this.add(new StepPanel(solution.get(i), font));
		}
		
		//add the summary at the bottom if it exists and belongs at the bottom
		if(summary != null && solution.isSummaryLast()) this.add(summary);
		
		//revalidate and paint
		this.revalidate();
		this.repaint();
	}
	
	/*
	 * The following methods implement the Scrollable interface. See 
	 * http://docs.oracle.com/javase/7/docs/api/javax/swing/Scrollable.html for documentation. 
	 */
	
	@Override
	public boolean getScrollableTracksViewportWidth() {
        return true;
    }
	
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}
	
	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
		//TODO, not sure this method is really applicable, see http://stackoverflow.com/a/1252514/3714913
		return Math.max(visibleRect.height * 9 / 10, 1);
	}
	
	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}
	
	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
		//scroll 5 [pixels?] at a time
		return 5;
	}
	
}
