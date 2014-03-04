/*
    Solve4x - An audio-visual algebra solver
    Copyright (C) 2014 Nathaniel Paulus

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
package com.github.nateowami.solve4x.solver;

import com.github.nateowami.solve4x.Solve4x;

/**
 * Provides a way to work with terms in equations.
 * @author Nateowami
 */
public class Term {
	
	//the coefficient of the term
	private Number coe;
	//The expression part of the term (if applicable) for example, 2x(34+4y) would use expr, while 2xy2 
	//would not. It would use body instead (for the xy2 part).
	private Expression expr;
	private String body;
	//the power the expression is raised to, if any
	private int exprPower;
	
	/**
	 * Creates a new term from a String
	 * @param term The term to create
	 */
	public Term(String term){
		//make the coefficient
		//i needed outside loop
		int i;
		for(i = 0; i < term.length(); i++){
			//if up to this point it's not a number
			if(!Number.isNumber(term.substring(0, i+1))){
				//subtract one to push i back into the "it's still a number" range
				i--;
				break;
			}
		}
		//i is now index of the last char of the coefficient, or -1 if there is no coefficient
		if(i > 0){
			//set the coefficient
			coe = new Number(term.substring(0, i+1));
			//delete the coefficient from term for further parsing
			term = term.substring(i+1, term.length());
		}
		
		Solve4x.debug("Coefficent removed from term. The following is left: " + term);
		
		//now figure out if we need to have a body or an Expression
		//if it's alpha-numeric it should use body instead of expr
		boolean an = true;//alpha-numeric
		for(int b = 0; b < term.length(); b++){
			//if the current char isn't a numeral or a letter
			if(!Util.isNumeral(term.charAt(b)) && !Util.isLetter(term.charAt(b))){
				//it's not alpha-numeric
				an = false;
				break;
			}
		}
		//if it's alpha-numeric, put term into body
		if(an){
			body = term;
		}
		//it's not alpha-numeric; it must be an Expression
		else{
			//TODO this wouldn't necessarily work. It could be something like 2(3x+4y)2
			expr = new Expression(term);
		}

	}
	
	/**
	 * Tells if the Term has a coefficient. Example:
	 * xy2 would be false. There is no coefficient.
	 * 2(xy2+9) would be true. 2 is the coefficient.
	 * &lt;1&gt;/&lt;2&gt; would be true. It has a fraction for a coefficient.
	 * @return If the Term has a coefficient
	 */
	public boolean hasCoe(){
		return coe == null;
	}
	
	/**
	 * Gives the coefficient of the Term if it has one, otherwise null
	 * @return the coefficient of the Term.
	 * @see hascoe();
	 */
	public Number getCoe(){
		return coe;
	}
	
	/**
	 * Tells if the Term has a body part. Example:
	 * 2xy2 would be true. xy2 is the body.
	 * 2(xy2+9) would be false. It uses an expression for the (xy2+9) part.
	 * 2&lt;1&gt;/&lt;2&gt; would be false. It has no body part, or expression.
	 * @return If the term has a body.
	 */
	public boolean hasBody(){
		return expr == null;
	}
	
	/**
	 * Gives the body of the term only if the body is not an expression, in which case it returns null.
	 * @return The body of the term. For example, if the term is "4x" this would return "x".
	 * @see hasBody()
	 */
	public String getBody() {
		return body;
	}

	/**
	 * Tells if the Term has an expression. Example:
	 * 2xy2 would be false. xy2 is held in the term's body.
	 * 2(xy2+9) would be true. The Expression is (xy2+9).
	 * 2&lt;1&gt;/&lt;2&gt; would be false. The fraction is in the coefficient.
	 * @return
	 */
	public boolean hasExpr(){
		return expr == null;
	}

	/**
	 * Gives the Expression part of the term if it exists, otherwise null.
	 * @return The Expression part of the term.
	 * @see hasExpr();
	 */
	public Expression getExpr(){
		return expr;
	}
	
	/**
	 * @return The Term as a String.
	 */
	public String getAsString() {
		return 
				//the coefficient
				coe.getAsString()
				//the expression if it exists
				+expr != null ? expr.getAsString() : ""
				//the body if it exists
				+body != null ? body : "";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		//any of the three could be null
		return 
				//the coefficient if it exists
				"Term [coe=" + (coe != null? coe.getAsString() : "null" ) + 
				//the expression part if it exists
				", expr=" + (expr != null? expr.getAsString() : "null") + 
				//the body of the term if it exists
				", body=" + (body != null? body : "null") + "]";
	}
	
}
