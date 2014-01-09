/*
    Solve4x - A Java program to solve and explain algebra problems
    Copyright (C) 2013 Nathaniel Paulus

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
package com.github.nateowami.solve4x.av.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * Creates icons at runtime
 * @author Nateowami
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
		
		//return the image
		return image;
	}
	
	/**
	 * Makes an icon that looks like a fast-forward button
	 * @return The icon
	 */
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
		//return the image
		return image;
	}
	
	/**
	 * Makes an icon that looks like a fast-backward button
	 * @return The icon
	 */
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
		//return the image
		return image;
	}
}
