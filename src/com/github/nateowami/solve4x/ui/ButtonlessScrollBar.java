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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * Basic scroll bar without buttons (just the thumb).
 * @author Nateowami
 */
public class ButtonlessScrollBar extends BasicScrollBarUI {
	
	public ButtonlessScrollBar() {
		
	}
	
	/**
	 * @return A JButton that that is 0 by 0 so it will never render.
	 */
	private JButton zeroButton() {
		JButton button = new JButton();
		Dimension zero = new Dimension(0, 0);
		button.setPreferredSize(zero);
		button.setMinimumSize(zero);
		button.setMaximumSize(zero);
		return button;
	}
	
	/*
	 * The following two methods are required, otherwise a NullPointerException will be thrown 
	 * when BasicScrollBarUI is doing a layout. 
	 */
	
	@Override
	protected Dimension getMinimumThumbSize() {
		return new Dimension(5, 100);
	}
	
	@Override
	protected Dimension getMaximumThumbSize() {
		return new Dimension(5, 1000);
	}
	
	/*
	 * The following two methods are what "remove" the buttons from the scrollbar by setting their 
	 * size to 0 by 0.
	 */
	
	@Override
	protected JButton createDecreaseButton(int orientation) {
		return zeroButton();
	}
	
	@Override
	protected JButton createIncreaseButton(int orientation) {
		return zeroButton();
	}
	
	/*
	 * The following method paints the thumb of the scrollbar. No track is needed so 
	 * paintTrack() is not overridden.
	 */
    
    @Override
    protected void paintThumb(Graphics graphics, JComponent c, Rectangle thumbBounds) {
    	Graphics2D g = (Graphics2D) graphics;
    	g.setColor(Color.GRAY);
    	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	g.fillRoundRect(thumbBounds.x+2, thumbBounds.y, thumbBounds.width-3, thumbBounds.height, 8, 8);
    }
	
}
