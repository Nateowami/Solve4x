package net.javaroma.Algebra;

/*
 * Checks for the validity of an equation.
 * @param equation The equation to validate.
 * @return if the if the equation is valid
 */
public class Solver {
	public static boolean eqIsValid(String equation){

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
			return (exprIsValid(equation.substring(0, indexOfEquals - 1)) 
					&& exprIsValid(equation.substring(numbOfEquals + 1, equation.length())));
		} 
		else {
			return false;
		}
	}
	
	/*
	 *Evaluates the algebraic validity of a give expression.
	 *@param expr The expression to check
	 *@return if the expression is valid
	 */
	public static boolean exprIsValid(String expr) {
		/*
		 * This method is VERY complex if it ever works. What we need to do is 
		 * chop up the expression into terms, which can be thought of as 
		 * sub-expressions. For example, in the expression 3x^2 + 4 + 3(23 + x), 
		 * 3(23 + x) needs to be dealt with as its own expression. To do this,
		 * This method will call itself. In order to determine where to cut it, this
		 * method will keep track of how deep into a parentheses it gets. For example,
		 * in ((2x + 4) + 3), it would quickly reach a depth of two. It won't cut
		 * expressions at a + sign unless the parentheses depth is 0. 
		 */
		int lastCut = 0; //we're cutting strings up into little little
		int parDepth = 0; //the depth we've gone into parentheses 
		boolean valid = true; //if the expression is valid
		boolean cut = false; //if the expression was cut into smaller pieces in this method.
		//if it hasn't been cut into small pieces, it is ready to be sent to the 
		//termIsValid() method.
		
		for(int i=0; i < expr.length(); i++){
			//([{ are all valid parentheses in algebra. They mean the same thing, 
			//but they help give clarity when they're nested
			if(expr.charAt(i) == '(' || expr.charAt(i) == '[' || expr.charAt(i) == '{'){
				parDepth++;
			}
			else if(expr.charAt(i) == ')' || expr.charAt(i) == ']' || expr.charAt(i) == '}'){
				parDepth--;
			}
			
			//if the parDepth is 0 we're at the lowest level of operators, so we can dice things up
			if((expr.charAt(i) == '+' || expr.charAt(i) == '-') && parDepth == 0){
				if(!exprIsValid(expr.substring(lastCut, i-1))){
					valid = false;
				}
				lastCut = i+1;
				cut = true;
			}
			
		}
		
		//if the for loop above has failed to cut the expression, this next one
		//will try with division.
		if(!cut){
			for(int i=0; i < expr.length(); i++){
				
			}
		}
		
		//if it still can't be cut, it must be a term and will be set for validation
		if(!cut){
			if (!termIsValid(expr)){
				valid = false;
			}
		}
		return true;//TODO fix later
	}
	
	public static boolean termIsValid(String expr) {
		
		
		return true;//TODO fix later
	}

	/*
	 * Evaluates a given string to determine if it has an = sign
	 * @param The string to evaluate
	 * @return If the equation has an equals sign
	 */
	public static boolean isEq(String str){
		boolean hasEqualsSign = false;
		for(int i=0; i < str.length(); i++){
			if(str.charAt(i) == '='){
				hasEqualsSign = true;
			}
		}
		return hasEqualsSign;
	}
	
	/*
	 * Checks to see if the entire expression is surrounded by parentheses,
	 * in which case they will be removed for easier parsing
	 */
	public static String removePar(String expr){
		//remove any spaces at beginning...
		while(expr.charAt(0) == ' '){
			expr = expr.substring(1, expr.length());
		}
		//...and the end
		while(expr.charAt(expr.length()) == ' '){
			expr = expr.substring(0, expr.length());
		}
		
		//check to see if it starts and ends with parentheses
		if((expr.charAt(0) == '(' || expr.charAt(0) == '[' || expr.charAt(0) == '{') && 
				(expr.charAt(expr.length()) == ')' || expr.charAt(expr.length()) == ']' || expr.charAt(expr.length()) == '}')){
			
			int parDepth = 1; //how deep we are into parentheses nesting
			boolean parDepthReached0 = false;//if parDepth ever reaches 0 we're outside all parentheses
			//check to see if the entire expr is enclosed with parentheses
			for (int i = 1; i < expr.length() - 1; i++){
				if(expr.charAt(i) == '(' || expr.charAt(i) == '[' || expr.charAt(i) == '{'){
					parDepth++;
				}
				else if(expr.charAt(i) == ')' || expr.charAt(i) == ']' || expr.charAt(i) == '}'){
					parDepth--;
				}
				if (parDepth == 0){
					parDepthReached0 = true;
				}
			}
			
			if(!parDepthReached0){
				return expr.substring(1, expr.length() - 1);
			}
			else{
				return expr;
			}
		}
		else{
			return expr;
		}
	}
}

