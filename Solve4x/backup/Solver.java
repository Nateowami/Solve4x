package net.javaroma.Algebra;
import java.util.ArrayList;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Checks for the validity of an equation.
 * @param equation The equation to validate.
 */
public class Solver {
	public static boolean isValid(String equation){

		//for (int i=0; i < equation.length(); i++){
		//Check for more than one = sign
		//String[] equalsArray = equation.split("=");
		//if (equalsArray.length > 2) {
		//We can change this back if you like. The reason I did this is because
		//it kills two birds with one stone. We determine how many = signs there
		//are and split it at the same time.
		int numbOfEquals = 0;
		int indexOfEquals = 0;
		for(int i = 0; i < equation.length(); i++){
			if (equation.charAt(i) == '='){
				numbOfEquals++;
				indexOfEquals = i;
			}
		}
		if(numbOfEquals == 1){
			//check the validity of the
			return (evaluateExpr(equation.substring(0, indexOfEquals - 1)) && evaluateExpr(equation.substring(numbOfEquals + 1, equation.length())));
		} 
		else {
			return false;
		}
	}
	
	/*
	 *Evaluates the algebraic validity of a give expression.
	 *@param expr The expression to check
	 */
	private static boolean evaluateExpr(String expr) {
		int lastCut = 0; //we're cutting strings up into little little
		int numbCut = 0; //the number taken so far
		ArrayList<String> cutStrings = new ArrayList<String>();
		
		//What exactly is the structure of the array going to look like?
		
		//it's a list of terms.

		for(int i=0; i < expr.length(); i++){
			if(expr.charAt(i) == '+' || expr.charAt(i) == '-'){
				cutStrings.add(expr.substring(lastCut, i - 1));
				System.out.println(cutStrings.toString());
			}
		}
		return true;//fix later
	}
}

