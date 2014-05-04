/*
    Solve4x - An audio-visual algebra solver
    Copyright (C) 2014 Solve4x project

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
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

/**
 * Creates icons at runtime
 * @author Nateowami, Tribex
 */
public class Icon {
	
	/**
	 * Makes an icon that looks like a play button
	 * @return The icon
	 */
	public static BufferedImage getPlayIcon(){
		//create our image and Graphics2D
		BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();
		//set the drawing color to black
		g2d.setColor(new Color(0,0,0));
		//set rendering hints
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);
		//create a polygon to draw on-screen
		Polygon poly = new Polygon();
		poly.addPoint(8, 4);
		poly.addPoint(8, 27);
		poly.addPoint(27, 16);
		//draw the polygon
		g2d.fillPolygon(poly);
		//dispose of g2d
		g2d.dispose();
		//return the image
		return image;
	}
	
	/**
	 * Makes an icon that looks like a pause button
	 * @return The icon
	 */
	public static BufferedImage getPauseIcon(){
		//create our image and Graphics2D
		BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();
		//set the drawing color to black
		g2d.setColor(new Color(0,0,0));
		//set rendering hints
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);
		
		//create a polygon to draw on-screen
		Polygon poly = new Polygon();
		poly.addPoint(8, 4);
		poly.addPoint(8, 27);
		poly.addPoint(13, 27);
		poly.addPoint(13, 4);
		//draw the polygon
		g2d.fillPolygon(poly);
		
		//create another polygon to draw on-screen
		Polygon poly2 = new Polygon();
		poly2.addPoint(18, 4);
		poly2.addPoint(18, 27);
		poly2.addPoint(23, 27);
		poly2.addPoint(23, 4);
		//draw the polygon
		g2d.fillPolygon(poly2);
		//dispose of g2d
		g2d.dispose();
		//return the image
		return image;
	}
	
	/**
	 * Makes an icon that looks like a fast-forward button
	 * @return The icon
	 */
	//XXX this is not currently being used. Just putting a note so we easily remember. Remove this note if this changes
	public static BufferedImage getFastForwardIcon(){
		//create our image and Graphics2D
		BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();
		//set the drawing color to black
		g2d.setColor(new Color(0,0,0));
		//set rendering hints
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);
		
		//Create the left-most triangle polygon
		Polygon triangle1 = new Polygon();
		triangle1.addPoint(2, 4);
		triangle1.addPoint(2, 27);
		triangle1.addPoint(21, 16);
		
		//Create the right-most triangle polygon
		Polygon triangle2 = new Polygon();
		triangle2.addPoint(10, 4);
		triangle2.addPoint(10, 27);
		triangle2.addPoint(29, 16);

		//draw the polygons
		g2d.fillPolygon(triangle1);
		g2d.fillPolygon(triangle2);
		
		//Draw the bar at the end
		g2d.fillRect(26, 4, 3, 23);
		//dispose of g2d
		g2d.dispose();
		//return the image
		return image;
	}
	
	/**
	 * Makes an icon that looks like a fast-backward button
	 * @return The icon
	 */
	//XXX this is not currently being used. Just putting a note so we easily remember. Remove this note if this changes
	public static BufferedImage getFastBackwardIcon(){
		//create our image and Graphics2D
		BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();
		//set the drawing color to black
		g2d.setColor(new Color(0,0,0));
		//set rendering hints
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);
		
		//Create the left-most triangle polygon
		Polygon triangle1 = new Polygon();
		triangle1.addPoint(21, 4);
		triangle1.addPoint(21, 27);
		triangle1.addPoint(2, 16);
		
		//Create the right-most triangle polygon
		Polygon triangle2 = new Polygon();
		triangle2.addPoint(29, 4);
		triangle2.addPoint(29, 27);
		triangle2.addPoint(10, 16);

		//draw the polygons
		g2d.fillPolygon(triangle1);
		g2d.fillPolygon(triangle2);
		
		//Draw the bar at the end
		g2d.fillRect(2, 4, 3, 23);
		//dispose of g2d
		g2d.dispose();
		//return the image
		return image;
	}
	
	/**
	 * Draws an icon for a the volume control
	 * @param volume The volume to draw (0-3)
	 * @return The icon
	 */
	public static BufferedImage getVolumeIcon(int volume){
		//create our image and Graphics2D
		BufferedImage image = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();
		//set the drawing color to black
		g2d.setColor(new Color(0,0,0));
		//set rendering hints
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);

		//the starting position for the microphone we need to draw.
		//the starting position depends on the volume
		int start;
		
		//set the starting position
		switch(volume){
		case 0:
			start = 3;
			break;
		case 1:
			start = 7;
			break;
		case 2:
			start = 5;
			break;
		case 3:
			start = 2;
			break;
		default:
			start = 3;
		}
		
		//create the polygon for the microphone
		Polygon microphone = new Polygon();		
		microphone.addPoint(start, 12);
		microphone.addPoint(start, 20);
		microphone.addPoint(7+start, 20);
		//there's a problem with one of these, can't figure it out
		microphone.addPoint(13+start, 24);
		microphone.addPoint(13+start, 7);
		microphone.addPoint(7+start, 12);
		
		//draw the microphone
		g2d.fillPolygon(microphone);
		
		//set the line width
		g2d.setStroke(new BasicStroke(2));
		
		//draw the arcs for sound waves
		//check volume first though
		
		//if the volume is muted
		if(volume==0){
			//draw the first line of the X (\)
			g2d.draw(new Line2D.Double(18, 10.5, 28, 20.5));
			//draw the second line of the X (/)
			g2d.draw(new Line2D.Double(28, 10.5, 18, 20.5));
		}
		
		//if the volume is 1
		else if(volume == 1){
			g2d.draw(new Arc2D.Double(22, 9.5, 3, 12.5, 90, -180, Arc2D.OPEN));
		}
		
		//if the volume is 2
		else if(volume==2){
			g2d.draw(new Arc2D.Double(19, 9.5, 3, 12.5, 90, -180, Arc2D.OPEN));
			g2d.draw(new Arc2D.Double(23.5, 6.75, 3.5, 18, 90, -180, Arc2D.OPEN));
		}
		
		//if the volume is 3
		else if(volume==3){
			g2d.draw(new Arc2D.Double(17, 9.5, 3, 12, 90, -180, Arc2D.OPEN));
			g2d.draw(new Arc2D.Double(21, 6.5, 4, 18, 90, -180, Arc2D.OPEN));
			g2d.draw(new Arc2D.Double(25, 3.5, 5, 24, 90, -180, Arc2D.OPEN));
		}
		//dispose of g2d
		g2d.dispose();
		//return the image
		return image;
	}
}
