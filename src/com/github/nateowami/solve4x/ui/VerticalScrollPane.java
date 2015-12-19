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
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.Scrollable;

/**
 * Implements methods of the Scrollable interface to facilitate vertical scrolling. This isn't a 
 * scrollpane, it's just a class that can be extended by JPanels that want to scroll at sensible 
 * increments, and <em>doesn't</em> want to allow vertical scrolling. This helps the JPanel's 
 * layout manager know that it shouldn't allow the components to take all the horizontal width they 
 * want. 
 * @author Nateowami
 */
public abstract class VerticalScrollPane extends JPanel implements Scrollable {
	
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
		//scroll 15 [pixels?] at a time
		return 15;
	}
	
}
