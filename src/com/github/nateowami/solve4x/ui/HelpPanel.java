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

import java.awt.Component;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

/**
 * @author Nateowami
 */
public class HelpPanel extends VerticalScrollPane {
	
	//cache the one-and-only help panel so it doesn't have to keep getting constructed
	private static HelpPanel panel = null;
	
	private static String[] instructions = {
		"To enter an equation or expression, just type it out how you normally would. For example, "
		+ "try 2x+6y=83",
		
		"For fractions, put parentheses around the numbers on top and bottom. For example, (3)/(2)."
		+ " You can even do mixed numbers: 2(3)/(4).",
		
		"Multiply using an asterisk (e.g. 2*8) or just stick them next to eachother (e.g. 2x)."		
	};
	
	/**
	 * Creates a new HelpPanel, which is a JPanel with help information for Solve4x.
	 */
	private HelpPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		//add the intro
		JLabel intro = new JLabel("Enter a problem in the textbox and click Answer.");
		intro.setFont(new Font("SansSerif", Font.PLAIN, 18).deriveFont(Collections.singletonMap(
		        TextAttribute.WEIGHT, TextAttribute.WEIGHT_SEMIBOLD)));
		intro.setBorder(new EmptyBorder(10, 10, 10, 10));
		intro.setAlignmentX(Component.CENTER_ALIGNMENT);
		intro.setFont(intro.getFont().deriveFont(16f));
		this.add(intro);
		
		//add the instructions
		JTextArea text = new JTextArea();
		text.setFont(new Font("SansSerif", Font.PLAIN, 14));
		text.setEditable(false);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		for(String s : instructions) text.append(s + "\n\n");
		this.add(text);
	}
	
	/**
	 * Returns a help panel. If none exists it is created and returned. If one does exist it is 
	 * returned instead of creating a new one.
	 * @return A HelpPanel.
	 */
	public static HelpPanel getHelpPanel() {
		if(panel == null) panel = new HelpPanel();
		return panel;
	}
	
}
