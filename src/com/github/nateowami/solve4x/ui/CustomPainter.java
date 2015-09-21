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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.SwingConstants;
import javax.swing.plaf.synth.ColorType;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthPainter;

/**
 * A custom painter for a synth look and feel
 * @author Tribex
 */
public class CustomPainter extends SynthPainter {
	
    @Override
    public void paintPanelBackground(SynthContext context, Graphics g, int x, int y, int w, int h) {
        //create a round rectangle
        RoundRectangle2D.Double rect = new RoundRectangle2D.Double();
        rect.setRoundRect(x, y, w-5, h-5, 15, 15);
        //find the color needed
        Color col = context.getStyle().getColor(context, ColorType.BACKGROUND);
        Graphics2D g2 = (Graphics2D)g;
        //set rendering hints
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //set the color to the color found above
        g2.setColor(col);
        //draw/fill the rect
        g2.fill(rect);
    }
    
    @Override
    public void paintButtonBackground(SynthContext context, Graphics g, int x, int y, int w, int h) {
        //create a round rectangle
        RoundRectangle2D.Double rect = new RoundRectangle2D.Double();
        rect.setRoundRect(x, y, w-5, h-5, 10, 10);
        //find the needed color
        Color col = context.getStyle().getColor(context, ColorType.BACKGROUND);
        Graphics2D g2 = (Graphics2D)g;
        //set the color to the one found above
        g2.setColor(col);
        //set the rendering hints
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //draw/fill the round rectangle
        g2.fill(rect);
    }
    
    @Override
    public void paintTextFieldBackground(SynthContext context, Graphics g, int x, int y, int w, int h) {
        //create a round rectangle
        RoundRectangle2D.Double rect = new RoundRectangle2D.Double();
        rect.setRoundRect(x, y, w-5, h-5, 10, 10);
        //get the color we need to use
        Color col = context.getStyle().getColor(context, ColorType.BACKGROUND);
        Graphics2D g2 = (Graphics2D)g;
        //set the color from the one found above
        g2.setColor(col);
        //set rendering hints
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //draw/fill the round rectangle
        g2.fill(rect);
    }
    
    @Override
    public void paintScrollBarThumbBackground(SynthContext context, Graphics g, int x, int y, int w, int h, int orientation) {
    	g.setColor(context.getStyle().getColor(context, ColorType.BACKGROUND));
    	g.fillRect(x, y, w, h);
    }
    
    @Override
    public void paintArrowButtonForeground(SynthContext context, Graphics graphics, int x, int y, int w, int h, int direction) {
    	Graphics2D g = (Graphics2D) graphics;
    	g.setColor(context.getStyle().getColor(context, ColorType.FOREGROUND));
    	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	g.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
    	
    	//if the arrow is pointing upwards
    	boolean up = direction == SwingConstants.NORTH;
    	int b /*buffer width*/ = 3;
    	
    	Path2D path = new Path2D.Double();
    	path.moveTo(x+b, up ? y+h-b : y+b);
    	path.lineTo(w / 2.0 + x, up ? y+b : y+h-b);
    	path.lineTo(x+w-b, up ? y+h-b : y+b);
    	g.draw(path);
    }
    
    @Override
    public void paintArrowButtonBackground(SynthContext context, Graphics g, int x, int y, int w, int h) {
    	g.setColor(context.getStyle().getColor(context, ColorType.BACKGROUND));
    	g.fillRect(x, y, w, h);
    }
    
}
