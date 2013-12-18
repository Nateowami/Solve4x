package net.javaroma.Solve4x;
import net.javaroma.Solve4x.Main;
/**
 * Checks for the validity of an equation.
 * @param equation The equation to validate.
 * @return if the if the equation is valid
 */
public class Solver {
	public static boolean eqIsValid(String equation){
		//XXX debugging
		System.out.println("eqIsValid()" + equation);
		Main.debugMsg("Testing Equation Validity: "+equation);
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
			return (exprIsValid(equation.substring(0, indexOfEquals)) 
					&& exprIsValid(equation.substring(indexOfEquals + 1, equation.length())));
		} 
		else {
			return false;
		}
	}

	/**
	 *Evaluates the algebraic validity of a give expression.
	 *@param expr The expression to check
	 *@return if the expression is valid
	 */
	public static boolean exprIsValid(String expr) {
		//XXX debugging
		System.out.println("exprIsValid()" + expr);
		/*
		 * This method is VERY complex if it ever works. What we need to do is 
		 * chop up the expression into terms, which can be thought of as 
		 * sub-expressions. For example, in the expression 3x2 + 4 + 3(23 + x), 
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

		//remove parentheses if the entire expression is enclosed by them
		expr = removePar(expr);

		for(int i=0; i < expr.length(); i++){
			//([{ are all valid parentheses in algebra. They mean the same thing, 
			//but they help give clarity when they're nested. '<' has about the same meaning
			//within this program.
			if(expr.charAt(i) == '(' || expr.charAt(i) == '[' 
					|| expr.charAt(i) == '{' || expr.charAt(i) == '<'){
				parDepth++;
			}
			else if(expr.charAt(i) == ')' || expr.charAt(i) == ']' 
					|| expr.charAt(i) == '}' || expr.charAt(i) == '>'){
				parDepth--;
			}

			//if the parDepth is 0 we're at the lowest level of operators, so we can dice things up
			if((expr.charAt(i) == '+' || expr.charAt(i) == '-') && parDepth == 0){
				if(!exprIsValid(expr.substring(lastCut, i))){
					valid = false;
				}
				lastCut = i+1;
				cut = true;
			}

			//if we've gotten to the end and we've cut part off, we need to take care of the remaining part
			else if(i == expr.length() && cut == true){
				if(!exprIsValid(expr.substring(lastCut, expr.length()))){
					valid = false;
				}
			}

		}

		//if the for loop above has failed to cut the expression, this next one
		//will try with multiplication

		//using the parDepth variable again so reseting
		parDepth = 0;
		lastCut = 0;
		if(!cut){
			for(int i=0; i < expr.length(); i++){
				//check to see if parDepth needs to change
				//in this program '<' has about the same meaning as parentheses 
				if(expr.charAt(i) == '(' || expr.charAt(i) == '[' 
						|| expr.charAt(i) == '{' || expr.charAt(i) == '<'){
					parDepth++;
				}
				else if(expr.charAt(i) == ')' || expr.charAt(i) == ']' 
						|| expr.charAt(i) == '}' || expr.charAt(i)== '>'){
					parDepth--;
				}

				//if we're at the place of a multiplication
				if(parDepth == 0 && (expr.charAt(i) == ')' || expr.charAt(i) == ']' 
						|| expr.charAt(i) == '}') || (getNextRealChar(expr, i + 1) == '(' 
						|| getNextRealChar(expr, i+1) == '[' || getNextRealChar(expr, i+1) == '{')){
					//if it's an opening parentheses 
					if(expr.charAt(i) == '(' || expr.charAt(i) == '[' || expr.charAt(i) == '{'){
						if(!exprIsValid(expr.substring(lastCut, i))){
							valid = false;
						}
						lastCut = i+1;
						cut = true;
					}
					//it must be a closing parentheses then
					else{
						//if the next char is a numeral, then we must have a power here
						if(isNumeral(expr.charAt(i+1))){
							//figure out where the power ends so we can cut it right
							int cutAt = getNextNonNumeral(expr, i) - 1;
							//cut it after the end of the power
							//XXX Nate was doing debugging of String.substring() calls and got this far
							if(!exprIsValid(expr.substring(lastCut, cutAt))){
								valid = false;
							}
							lastCut = cutAt + 1;
							cut = true;
						}
					}
				}
				//if we've gotten to the end and we've cut part off, we need to take care of the remaining part
				else if(i == expr.length() && cut == true){
					if(!exprIsValid(expr.substring(lastCut, i))){
						valid = false;
					}
				}
			}

			//again, division
			//using the parDepth variable again so reseting
			parDepth = 0;
			lastCut = 0;
			for(int i=0; i < expr.length(); i++){

				//check to see if parDepth needs to change
				//In this program < and > are similar to parentheses
				if(expr.charAt(i) == '(' || expr.charAt(i) == '[' 
						|| expr.charAt(i) == '{' || expr.charAt(i) == '<'){
					parDepth++;
				}
				else if(expr.charAt(i) == ')' || expr.charAt(i) == ']' 
						|| expr.charAt(i) == '}' || expr.charAt(i) == '>'){
					parDepth--;
				}

				//if we're at the place of a division 
				if(parDepth == 0 && expr.charAt(i) == '/'){
					if(!exprIsValid(expr.substring(lastCut, i))){
						valid = false;
					}
					lastCut = i+1;
					cut = true;
				}
				//if we've gotten to the end and we've cut part off, we need to take care of the remaining part
				else if(i == expr.length() && cut == true){
					if(!exprIsValid(expr.substring(lastCut, i))){
						valid = false;
					}
				}
			}
		}


		//if it still can't be cut, it must be a term and will be set for validation
		if(!cut){
			if (!termIsValid(expr)){
				valid = false;
			}
		}
		return valid;
	}

	/**
	 * Evaluates the algebraic validity of the given term
	 * @param term The term to be evaluated
	 * @return If the term is valid
	 */
	public static boolean termIsValid(String term) {
		//XXX
		System.out.println("termIsValid()" + term);
		//remove all numerals at beginning
		while(term.length() > 0 && isNumeral(term.charAt(0))){
			term = term.substring(1, term.length());
		}
		//remove all numerals at end
		while(term.length() > 0 && isNumeral(term.charAt(term.length()-1))){
			term = term.substring(0, term.length() - 2);//-2 (one for array indices start at 0, one for cutting extra char
		}
		//remove all letters
		while(term.length() > 0 && isLetter(term.charAt(0))){
			term = term.substring(1, term.length());
		}
		if(term.length() == 0){
			return true;
		}
		else return false;
	}

	/**
	 * Evaluates a char to see if it is a numeral
	 * @param c The char to evaluate
	 * @return If the char is a numeral
	 */
	public static boolean isNumeral(char c){
		//XXX
		System.out.println("eqNumeral()"+ c);
		if(c <= 9 && c >= 0){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Evaluates a char to see if it is between a-z or A-Z
	 * @param c The char to evaluate
	 * @return If the char is a a-z or A-Z
	 */
	public static boolean isLetter(char c){
		//XXX
		System.out.println("isLetter()" + c);
		if(c <= 'z' && c >= 'a' || c <= 'Z' && c >= 'A'){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * Evaluates a given string to determine if it has an = sign
	 * @param The string to evaluate
	 * @return If the equation has an equals sign
	 */
	public static boolean isEq(String str){
		//XXX
		System.out.println("isEq()");
		boolean hasEqualsSign = false;
		for(int i=0; i < str.length(); i++){
			if(str.charAt(i) == '='){
				hasEqualsSign = true;
			}
		}
		return hasEqualsSign;
	}

	/**
	 * Checks to see if the entire expression is surrounded by parentheses,
	 * in which case they will be removed for easier parsing
	 */
	public static String removePar(String expr){ 
		//XXX
		System.out.println("removePar()" + expr);
		//FIXME: Strings are sent with length 0

		//remove any spaces at beginning and end
		expr.trim();


		//check to see if it starts and ends with parentheses
		if((expr.length() >= 1) && (expr.charAt(0) == '(' || expr.charAt(0) == '[' 
				|| expr.charAt(0) == '{' || expr.charAt(0) == '<') 
				&& (expr.charAt(expr.length()-1) == ')' || expr.charAt(expr.length()-1) == ']' 
				|| expr.charAt(expr.length()-1) == '}' || expr.charAt(expr.length()-1) == '>')){

			int parDepth = 1; //how deep we are into parentheses nesting
			boolean parDepthReached0 = false;//if parDepth ever reaches 0 we're outside all parentheses
			//check to see if the entire expr is enclosed with parentheses
			for (int i = 1; i < expr.length() - 1; i++){
				if(expr.charAt(i) == '(' || expr.charAt(i) == '[' 
						|| expr.charAt(i) == '{' || expr.charAt(i) == '<'){
					parDepth++;
				}
				else if(expr.charAt(i) == ')' || expr.charAt(i) == ']' 
						|| expr.charAt(i) == '}' || expr.charAt(i) == '>'){
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

	/**
	 * Finds the next char (that is not a space) after the given index in the given string.
	 * @param str The string to search through
	 * @param index The index to start searching from
	 * @return The first non-space char in str after index
	 */
	public static char getNextRealChar(String str, int index){
		//XXX
		System.out.println("getNextRealChar) String: "+str+"index:" + index);
		char firstChar = ' ';
		for(int i = index; i < str.length(); i++){
			if(str.charAt(i) != ' '){
				firstChar = str.charAt(i);
				break;
			}
		}
		return firstChar;

	}

	/**
	 * Finds the index of the first char following the given index that is not a numeral
	 * @param str The string to search in
	 * @param index The index to start the search at
	 * @return The first non-numerical char after the given index
	 */
	public static int getNextNonNumeral(String str, int index){
		//XXX
		System.out.println("getNextNonNumeral" + "str:"+str+"index:"+index);
		int answer = str.length();//if there is no answer, default is the last index
		for(int i = 0; i < str.length(); i++){
			if(str.charAt(i) >= '0' && str.charAt(i) <= '9'){
				answer = i;
				break;
			}
		}
		return answer;
	}
}

