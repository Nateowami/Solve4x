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

package rsc.uiClasses;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
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
    public void paintListBackground(SynthContext context, Graphics g, int x, int y, int w, int h) {
        try {
            // load image XXX what image is this??? What list is it painting for?
            BufferedImage bim = ImageIO.read(getClass().getResourceAsStream("/img/duke.jpg"));
            // create image to use for painting
            BufferedImage used = new BufferedImage(bim.getWidth(), bim.getHeight(), BufferedImage.TRANSLUCENT);
            // create graphics for panting from used image
            Graphics2D g2 = used.createGraphics();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.1f));
            //draw the image
            g2.drawImage(bim, null, 0, 0);
            g2.dispose();
            // now, draw transparent image
            g2 = (Graphics2D) g;
            g2.drawImage(used.getScaledInstance(w, h, Image.SCALE_FAST), 0, 0, null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

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

    
    @Override//XXX is this method still used?
    public void paintButtonBorder(SynthContext context, Graphics g, int x, int y, int w, int h) {
    	//create a round rectangle
        RoundRectangle2D.Double rect = new RoundRectangle2D.Double();
        rect.setRoundRect(x, y, w-5, h-5, 10, 10);
        Graphics2D g2 = (Graphics2D)g;
        //set the color to black
        g2.setColor(Color.BLACK);
        //set the rendering hints
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //set the stroke for painting the border
        g2.setStroke(new BasicStroke(2));
        //draw the rectangle border
        g2.draw(rect);
        
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
    
    //XXX is this method still used?
    public void paintTextFieldBorder(SynthContext context, Graphics g, int x, int y, int w, int h) {
    	//create a round rectangle
        RoundRectangle2D.Double rect = new RoundRectangle2D.Double();
        rect.setRoundRect(x, y, w-5, h-5, 10, 10);
        Graphics2D g2 = (Graphics2D)g;
        //set the color to black
        g2.setColor(Color.BLACK);
        //set rendering hints
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //set the stroke to use for painting
        g2.setStroke(new BasicStroke(2));
        //draw the rectangle border
        g2.draw(rect);
        
    }
    
}