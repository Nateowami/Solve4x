package net.javaroma.Algebra;
import java.util.Scanner;
import net.javaroma.Algebra.Solver;
import javax.swing.JFrame;

public class Algebra {
	Algebra(){
		//TODO create GUI (later) 
		
		//get the equation
		Scanner in = new Scanner(System.in);
		System.out.println("Enter an Equation: ");
		String equation = in.nextLine();
		
		//Run the equation and check the validity of it.
		Boolean isValid = Solver.isValid(equation);
		
		if (isValid == false) {
			System.out.println("Error: malformed expression.");
		}
	}
}
