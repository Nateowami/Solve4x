package net.javaroma.Algebra;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import net.javaroma.Algebra.Solver;

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
		//get the equation
		
		//XXX: Old Terminal Code :XXX\\
		/*Scanner in = new Scanner(System.in);
		System.out.println("Enter an Equation: ");
		String equation = in.nextLine();
		
		Run the equation and check the validity of it.
		Boolean isValid = Solver.isValid(equation);
		
		if (!isValid) {
			System.out.println("Error: malformed expression.");
		}*/
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
	    
	    //EvaluateButton
	    JButton btnEvaluate = new JButton("Evaluate");
	    btnEvaluate.addActionListener(new ActionListener(){
	        public void actionPerformed(ActionEvent e) {
	        	String equation = equationField.getText();
	             Boolean isValid = Solver.isValid(equation);
	             lblEnterEquation.setText("Equation Evaluation Status: "+isValid.toString());
	        }
	    });
	    buttonPanel.add(btnEvaluate);
	    btnEvaluate.setHorizontalAlignment(SwingConstants.LEFT);
	    evaluateFrame.pack();
	    evaluateFrame.setVisible(true);
	}
}
