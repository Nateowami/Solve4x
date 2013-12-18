package net.javaroma.Algebra;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Solver {
	//TODO (you want this one?) make sure there is one and only one = sign in equation
	//Sure, but it needs to be in the Algebra package to avoid compilation issues.
	public static boolean isValid(String equation){
		int numbOfEquals = 0;
		for (int i=0; i < equation.length(); i++){
			
			//Check for more than one = sign
			String[] equalsArray = equation.split("=");
			if (equalsArray.length > 2) {
				return false;
			} else {
				break;
			}
			
		}
		Pattern sepPattern = Pattern.compile("(b)");
		Matcher matcher = sepPattern.matcher(equation);
		matcher.start();
		
		System.out.println();
		new Algebra();
		return true; //for now to avoid the no return error
	}
}

