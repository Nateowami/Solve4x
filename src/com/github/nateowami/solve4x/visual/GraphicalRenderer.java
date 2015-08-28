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
package com.github.nateowami.solve4x.visual;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.github.nateowami.solve4x.Main;
import com.github.nateowami.solve4x.solver.*;
import com.github.nateowami.solve4x.solver.Number;

/**
 * @author Nateowami
 */
public class GraphicalRenderer {
	
	//keep a cache so we don't keep rendering the same algebra
	private static final HashMap<Algebra, BufferedImage> cache = new HashMap<Algebra, BufferedImage>();
	
	//keep commonly used fonts for simplicity and efficiency 
	private static Font font, superscriptFont, subscriptFont;
	
	//initialize the fonts
	static {
		try {
			//setup default font
			font = Font.createFont(Font.TRUETYPE_FONT, Main.class.getResourceAsStream("/fonts/KaTeX_Main-Regular.ttf")).deriveFont(18f);
			
			//setup superscript font
			HashMap<TextAttribute, Object> map = new HashMap<TextAttribute, Object>();
			map.put(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUPER);
			superscriptFont = font.deriveFont(map);
			
			//setup subscript font
			map.put(TextAttribute.SUPERSCRIPT, TextAttribute.SUPERSCRIPT_SUB);
			subscriptFont = font.deriveFont(map);
			
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Renders the given algebra.
	 * @param algebra The algebra rendered.
	 * @return A BufferedImage rendering of the given algebra.
	 */
	public static BufferedImage render(Algebra algebra) {
		//manage the cache
		if(cache.containsKey(algebra)) return cache.get(algebra);
		else {
			BufferedImage image = delegateRendering(algebra);
			cache.put(algebra, image);
			return image;
		}
	}
	
	/**
	 * Delegates rendering of the specified algebra to the appropriate methods and 
	 * returns the result.
	 * @param algebra The algebra to render.
	 * @return A BufferedImage representation of the given algebra.
	 */
	private static BufferedImage delegateRendering(Algebra algebra) {
		if(algebra instanceof Equation) return renderEquation((Equation)algebra);
		else {
			BufferedImage rendered = null;
			if(algebra instanceof Expression) rendered = renderExpression((Expression)algebra);
			else if(algebra instanceof Term) rendered = renderTerm((Term) algebra);
			else if(algebra instanceof Fraction) rendered = renderFraction((Fraction) algebra);
			else if(algebra instanceof Number) rendered = renderNumber((Number) algebra);
			else if(algebra instanceof MixedNumber) rendered = renderMixedNumber((MixedNumber) algebra);
			else if(algebra instanceof Root) rendered = renderRoot((Root)algebra);
			else rendered = renderVariable((Variable) algebra);
			return wrapWithSignAndExponent(rendered, (AlgebraicParticle) algebra);
		}
	}
	
	/*
	 * BEGIN MAIN SET OF RENDERING METHODS
	 * 
	 * The following methods are not JavaDoc'ed because they have essentially the same function, 
	 * but operate on different types. The each take an AlgebraicParticle and render it as a 
	 * BufferedImage. However, none of them render their sign, exponent, or parentheses, as this is 
	 * left for the caller to delegate.
	 */
	
	private static BufferedImage renderEquation(Equation equation) {
		BufferedImage left = render(equation.left()), right = render(equation.right());
		int width = left.getWidth() + right.getWidth() + dimensions("=").width;
		int height = Math.max(left.getHeight(), right.getHeight());
		BufferedImage image = image(width, height);
		Graphics2D g = image.createGraphics();
		
		render(g, image.getHeight(), left, 0);	
		render(g, image.getHeight(), render("="), left.getWidth());
		render(g, image.getHeight(), right, image.getWidth() - right.getWidth());
		
		return image;
	}
	
	private static BufferedImage renderExpression(Expression expr) {
		BufferedImage plus = render("+");
		ArrayList<BufferedImage> terms = new ArrayList<BufferedImage>();
		
		//total up the width of all parts of the expression, and find the height of the tallest one
		int width = 0, height = 0;
		for(int i = 0; i < expr.length(); i++) {
			AlgebraicParticle term = expr.get(i);
			BufferedImage image = render(term);
			terms.add(image);
			
			// Add the width of the term, and if it's positive (and not the first term), add width 
			// of + sign. If it's negative it would already have a - sign
			width += terms.get(i).getWidth();
			if(expr.get(i).sign() && i != 0) width += plus.getWidth();
			
			height = Math.max(height, image.getHeight());
		}
		
		//actually render the expression
		BufferedImage image = image(width, height);
		Graphics2D g = image.createGraphics();
		//keep track how far we've moved horizontally as we render
		int location = 0;
		for(int i = 0; i < expr.length(); i++) {
			AlgebraicParticle term = expr.get(i);
			//if it's positive, render the + sign and add its width to location variable
			if(i != 0  && term.sign()) {
				render(g, image.getHeight(), plus, location);
				location += plus.getWidth();
			}
			//render the term and move location forward its width
			render(g, image.getHeight(), terms.get(i), location);
			location += terms.get(i).getWidth();
		}
		
		return image;
	}
	
	private static BufferedImage renderTerm(Term term) {
		BufferedImage dot = render("⋅"); // OK, so it's actually an "interpunct." Whatever.
		ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
		int width = 0, height = 0;
		AlgebraicParticle previous = null;
		for(int i = 0; i < term.length(); i++) {			
			AlgebraicParticle element = term.get(i);
			
			// Check if we need to add a dot (multiplication symbol) between this and the previous 
			// element in order to keep it from being ambiguous.
			if(previous instanceof Number && 
					(element instanceof Number || element instanceof MixedNumber || element instanceof Fraction)) {
				width += dot.getWidth();
			}
			previous = element;
			
			BufferedImage image = render(element);
			//wrap with parentheses if it's an expression
			if(element instanceof Expression) image = parenthesize(image);
			images.add(image);
			width += image.getWidth();
			height = Math.max(height, image.getHeight());
		}
		
		BufferedImage image = image(width, height);
		Graphics2D g = image.createGraphics();
		
		previous = null;
		int location = 0;
		for(int i = 0; i < images.size(); i++) {
			AlgebraicParticle element = term.get(i);
			
			//if we need to add a dot (multiplication symbol) between this and the previous element
			if(previous instanceof Number && 
					(element instanceof Number || element instanceof MixedNumber || element instanceof Fraction)) {
				render(g, image.getHeight(), dot, location);
				location += dot.getWidth();
			}
			previous = element;
			
			render(g, image.getHeight(), images.get(i), location);
			location += images.get(i).getWidth();
		}
		
		return image;
	}
	
	private static BufferedImage renderFraction(Fraction frac) {
		BufferedImage top = render(frac.getTop()), bottom = render(frac.getBottom());
		int width = Math.max(top.getWidth(), bottom.getWidth());
		int height = top.getHeight() + bottom.getHeight();// + 4; // 4 pixels for the fraction bar
		
		BufferedImage image = image(width, height);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.BLACK);
		
		//draw it at the top in the center
		g.drawImage(top, null, (width - top.getWidth()) / 2, 0);
		//draw a bar in the middle
		g.drawLine(0, top.getHeight(), width, top.getHeight());
		//draw the bottom in the center
		g.drawImage(bottom, null, (width - bottom.getWidth()) / 2, image.getHeight() - bottom.getHeight());
		
		return image;
	}
	
	private static BufferedImage renderNumber(Number number) {
		BufferedImage integer = render(number.getInteger() == null ? "0" : number.getInteger());
		BufferedImage decimal = number.getDecimal() == null ? null : render("." + number.getDecimal());
		
		BufferedImage multiplication = null;
		if(number.getScientificNotationExponent() != null) {
			BufferedImage times10 = render("×10");
			BufferedImage exponent = render(String.valueOf(number.getScientificNotationExponent()), superscriptFont);
			
			multiplication = image(exponent.getWidth() + times10.getWidth(), times10.getHeight());
			Graphics2D g = multiplication.createGraphics();
			render(g, multiplication.getHeight(), times10, 0);
			render(g, multiplication.getHeight(), exponent, times10.getWidth());
		}
		
		ArrayList<BufferedImage> list = new ArrayList<BufferedImage>(3);
		list.add(integer);
		if(decimal != null) list.add(decimal);
		if(multiplication != null) list.add(multiplication);
		return render(list);
	}
	
	private static BufferedImage renderMixedNumber(MixedNumber mn) {
		ArrayList<BufferedImage> list = new ArrayList<BufferedImage>(2);
		list.add(render(mn.getNumeral()));
		list.add(render(mn.getFraction()));
		return render(list);
	}
	
	private static BufferedImage renderRoot(Root root) {
		BufferedImage degree = root.getNthRoot() == 2 ? null : renderSpecial(Integer.toString(root.getNthRoot()), subscriptFont, 0);
		BufferedImage radicand = render(root.getExpr());
		BufferedImage radicalSign = render("√", font.deriveFont(((float)radicand.getHeight()*0.9f)));
		
		int width = (degree == null ? 0 : degree.getWidth()) + radicalSign.getWidth() + radicand.getWidth();
		int height = Math.max(radicand.getHeight(), radicalSign.getHeight());
		
		BufferedImage image = image(width, height);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.BLACK);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.drawImage(radicalSign, null, degree == null ? 0 : degree.getWidth(), 0);
		//draw the degree right on top of the radical sign (if degree is null this has no effect)
		g.drawImage(degree, null, radicalSign.getWidth() / 2, radicalSign.getHeight() / 4);
		g.drawImage(radicand, null, image.getWidth() - radicand.getWidth(), 0);
		//draw the bar above the radicand
		int descentOfBar = (int) (image.getHeight() * 0.15);
		g.drawLine(image.getWidth() - radicand.getWidth(), descentOfBar, image.getWidth(), descentOfBar);
		
		return image;
	}
	
	private static BufferedImage renderVariable(Variable v) {
		return render(Character.toString(v.getVar()));
	}
	
	/*
	 * BEGIN HELPER METHODS
	 */
	
	/**
	 * Wraps the given image with parentheses, sign, and exponent, if they are applicable. If the 
	 * sign is positive, no sign will be rendered. Additionally, if the specified AlgebraicParticle 
	 * <code>a</code> has an exponent of 1, no exponent will be rendered. In all cases except when 
	 * <code>a</code> is a Variable or Number, when an exponent is rendered the image is first 
	 * wrapped with parentheses, thus keeping with the order of operations rules. 
	 * @param image The image which is the rendered version of <code>a</code>
	 * @param a The AlgebraicParticle whose rendered form is to be wrapped with parentheses, sign, 
	 * and exponent, as needed.
	 * @return The given image with the necessary operations applied to it.
	 */
	private static BufferedImage wrapWithSignAndExponent(BufferedImage image, AlgebraicParticle a) {
		//don't need pars if it's a number or variable (or if no exponent)
		boolean pars = a.exponent() != 1 && !(a instanceof Variable || a instanceof Number);
		
		//pars may need to be big to wrap around the expression
		Font parsFont = font.deriveFont((float) image.getHeight() * 0.8f);
		
		ArrayList<BufferedImage> images = new ArrayList<BufferedImage>(5);
		if(!a.sign()) images.add(render("-"));
		if(pars) images.add(render("(", parsFont));
		images.add(image);
		if(pars) images.add(render(")", parsFont));
		if(a.exponent() != 1) images.add(renderSpecial(Integer.toString(a.exponent()), superscriptFont, 0));
		
		return render(images);
	}
	
	/**
	 * Wraps the specified image with appropriately size parentheses, and opening one on the left, 
	 * and a closing one on the right.
	 * @param image The image to wrap with parentheses.
	 * @return The specified image wrapped with parentheses.
	 */
	private static BufferedImage parenthesize(BufferedImage image) {
		Font parFont = font.deriveFont((float)image.getHeight());
		BufferedImage parLeft = render("(", parFont), parRight = render(")", parFont);
		ArrayList<BufferedImage> list = new ArrayList<BufferedImage>(3);
		list.add(parLeft);
		list.add(image);
		list.add(parRight);
		return render(list);
	}
	
	/**
	 * Renders an ArrayList of BufferedImages's onto one image. Each image is rendered immediately 
	 * to the right of the previous, in a horizontal row. The height of the resulting image is 
	 * equal to the height of the tallest image being rendered. Each image is centralized 
	 * vertically in the resulting image.
	 * @param images The list of images to render.
	 * @return The specified images rendered horizontally in order.
	 */
	private static BufferedImage render(ArrayList<BufferedImage> images) {
		int width = 0, height = 0;
		for(BufferedImage image : images) {
			width += image.getWidth();
			height = Math.max(height, image.getHeight());
		}
		
		BufferedImage render = image(width, height);
		Graphics2D g = render.createGraphics();
		
		int location = 0;
		for(BufferedImage image : images) {
			render(g, render.getHeight(), image, location);
			location += image.getWidth();
		}
		
		return render;
	}
	
	/**
	 * Renders a <code>image</code> onto <code>target</code>, vertically centralized and with 
	 * <code>location</code> pixels to its left. Height is used to specify the height of the image 
	 * the graphics context relates to, so the image can be vertically centralized.
	 * @param target The graphics context onto which to paint the specified image.
	 * @param image The image to paint.
	 * @param location The amount of space (in pixels) to allow to the left of the image.
	 */
	private static void render(Graphics2D target, int height, BufferedImage image, int location) {
		//render it vertically centralized 
		target.drawImage(image, null, location, (height - image.getHeight()) / 2);
	}
	
	/**
	 * Renders a string with the given font, with the baseline of the string positioned 
	 * <code>shift</code> pixels above the bottom of the image. If <code>shift</code> is negative 
	 * this has the effect of lowering the string's baseline.
	 * @param s The string to render.
	 * @param font The font with which to render the string.
	 * @param shift The number of pixels to shift the baseline of the text up from the bottom of 
	 * the image.
	 * @return <code>s</code> rendered with the given font and laid out <code>shift<code> pixels 
	 * up from the bottom of the baseline.
	 */
	private static BufferedImage renderSpecial(String s, Font font, int shift) {
		Dimension d = dimensions(s, superscriptFont);
		BufferedImage image = image(d);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.BLACK);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(superscriptFont);
		//this method is special because it doesn't render a bit higher for the sake of the descender line
		//this keeps the exponent from getting cut off at the top
		g.drawString(s, 0, d.height - shift);
		return image;
	}
	
	/**
	 * Renders the given string using this class's default font and returns the resulting 
	 * image. The baseline of the text will be aligned 15% of the height of the string from the 
	 * bottom of the image.
	 * @param s The string to render.
	 * @return <code>s</code> rendered with this class's default font.
	 */
	private static BufferedImage render(String s) {
		return render(s, font);
	}
	
	/**
	 * Renders the given string <code>s</code> using the given font, and returns the resulting 
	 * image. The baseline of the text will be aligned 15% of the height of the string from the 
	 * bottom of the image.
	 * @param s The string to render.
	 * @param font The font with which to render the string.
	 * @return <code>s</code> rendered with the given font.
	 */
	private static BufferedImage render(String s, Font font) {
		Dimension dimensions = dimensions(s, font);
		BufferedImage image = image(dimensions);
		Graphics2D g = image.createGraphics();

		g.setColor(Color.BLACK);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(font);

		g.drawString(s, 0, dimensions.height - (dimensions.height * 0.15f));
		return image;
	}
	
	/**
	 * Calculates the dimensions for a given string when laid out using the class's default font.
	 * @param string The string to calculate the size of.
	 * @return The dimensions (in pixels) of the given string when rendered using this class's 
	 * default font. 
	 */
	private static Dimension dimensions(String string) {
		return dimensions(string, font);
	}
	
	/**
	 * Calculates the dimensions needed to render a string with a given font. The dimensions are 
	 * baseline relative.
	 * @param string The string to calculate the dimensions of.
	 * @param font The font to use with the string.
	 * @return The dimensions (in pixels) of the specified string when rendered with the specified 
	 * font.
	 */
	private static Dimension dimensions(String string, Font font) {
		Rectangle2D rect = new Canvas().getFontMetrics(font).getStringBounds(string, null);
		return new Dimension((int)Math.round(rect.getWidth()), (int)Math.ceil(rect.getHeight()));
	}
	
	/**
	 * Constructs a new BufferedImage with the specified dimensions.
	 * @param dimensions The dimensions for the new image.
	 * @return A new image with the specified dimensions.
	 */
	private static BufferedImage image(Dimension dimensions) {
		return image(dimensions.width, dimensions.height);
	}
	
	/**
	 * Constructs a new BufferedImage with the specified width and height and returns it. 
	 * @param width The width of the image.
	 * @param height The height of the image.
	 * @return A BufferedImage with the specified width and height.
	 */
	private static BufferedImage image(int width, int height) {
		return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}
	
}
