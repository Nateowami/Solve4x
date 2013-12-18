package net.javaroma.Solve4x;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import net.javaroma.Solve4x.Solver;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Algebra {
	Algebra(){
		//TODO Clean GUI code
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
	        	String equation = equationField.getText();
	        	Boolean isValid;
	        	//Check to see if the input was an equation or an expression
	        	if(Solver.isEq(equation)){
	        		isValid = Solver.eqIsValid(equation);
	        	}
	        	else{
	        		isValid = Solver.exprIsValid(equation);
	        	}
	            
	            lblEnterEquation.setText("Equation Evaluation Status: "+ isValid.toString());
	        }
	    });
	    buttonPanel.add(btnEvaluate);
	    btnEvaluate.setHorizontalAlignment(SwingConstants.LEFT);
	    evaluateFrame.pack();
	    evaluateFrame.setVisible(true);
	}
}
