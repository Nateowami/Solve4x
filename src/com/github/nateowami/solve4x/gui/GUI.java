/*
    Solve4x - A Java-based program to solve and explain algebra problems
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
package com.github.nateowami.solve4x.gui;

import com.github.nateowami.solve4x.solver.Solver;
import com.github.nateowami.solve4x.solver.Validator;
import com.github.nateowami.solve4x.gui.GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.MalformedInputException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


/**
 * A temporary GUI
 * @autor Tribex, Nateowami
 */
public class GUI {

	public GUI(){
		startGUI();
	}

	private void startGUI() {
		//Define GUI elements
		JFrame evaluateFrame = new JFrame("Evaluate Algebraic Expression");
		JPanel mainPanel = new JPanel();
		final JLabel lblEnterEquation = new JLabel("Enter Equation:");
		final JTextField equationField;
		JPanel buttonPanel = new JPanel();

		//GUI layout, I don't even dare comment it.
		evaluateFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		evaluateFrame.getContentPane().add(mainPanel, BorderLayout.NORTH);
		mainPanel.setLayout(new GridLayout(3, 3, 0, 3));
		mainPanel.add(lblEnterEquation);

		equationField = new JTextField();
		mainPanel.add(equationField);
		equationField.setColumns(30);
		mainPanel.add(buttonPanel);

		//EvaluateButton lets equations evaluate.
		JButton btnEvaluate = new JButton("Evaluate");
		btnEvaluate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				lblEnterEquation.setText("");
				String equation = equationField.getText();
				Boolean isValid;

				//commented out for testing
				/*if(REGEX)
    			Validator.generateOrderOfOperations(equation);
    		//End testing
				 * 
				 */

				//Check to see if the input was an equation or an expression

				try {
					Solver.solve(equation);
				} catch (MalformedInputException err) {
					// TODO After GUI is done, make this do something useful
					err.printStackTrace();
					lblEnterEquation.setText("Equation Evaluation Status: "+ false);
				}

				lblEnterEquation.setText("Equation Evaluation Status: "+ true);
			}
		});
		buttonPanel.add(btnEvaluate);
		btnEvaluate.setHorizontalAlignment(SwingConstants.LEFT);
		evaluateFrame.pack();
		evaluateFrame.setVisible(true);
	}
}
