/*
    Solve4x - An algebra solver that shows its work
    Copyright (C) 2014 Solve4x project

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

import java.nio.charset.MalformedInputException;

/**
 * Holds a root for any expression, e.g √(4x+6)
 * @author Nateowami
 */
public class Root extends AlgebraicParticle{
	
	//the root (e.g. 4th root, 2nd root (square root), nth root)
	private int nthRoot;
	private AlgebraicParticle expr;
	private boolean isConstant;
	
	/**
	 * Constructs a new Root
	 * @param root The root to parse. Should be in the form of <i>subscript</i>√<i>expression</i>
	 * @throws MalformedInputException If the root in the wrong syntax.
	 */
	protected Root(String root) throws MalformedInputException{
		//figure out how many subscript chars there are
		int i = 0;
		for(; i < root.length(); i++){
			//if it's not a subscript
			if(root.charAt(i) < '\u2080' || root.charAt(i) > '\u2089') break;
		}
		//now cut that part out of root
		String subscript = root.substring(0,i);
		//subscript after conversion
		String subscriptNormalized = "";
		root = root.substring(i,root.length());
		//turn the subscript to regular numeral chars
		for(int j = 0; j < subscript.length(); j++){
			//convert from subscript to regular
			subscriptNormalized+=(char)(subscript.charAt(j)-8272);
		}
		//parse subscriptNormalized to int. If it's "", use 2 as the default (square root)
		if("".equals(subscriptNormalized)) this.nthRoot = 2;
		else this.nthRoot = Integer.parseInt(subscriptNormalized);
		//parse the √ symbol
		if(root.length() < 1 || root.charAt(0) != '√'){
			throw new MalformedInputException(0);
		}
		else{
			//remove the √ sign
			root = root.substring(1, root.length());
		}
		//we're now safe to parse the expression
		this.expr = new Expression(Util.removePar(root));
		//if expr is a number
		isConstant = Number.isNumber(Util.removePar(root));
	}
	
	/**
	 * Tells if root is a valid root, in the form of <i>subscript</i>√<i>expression</i>. 
	 * e.g. ₄√(4x+6). Parentheses are only necessary for more than one term.
	 * @param root The root to check.
	 * @return If root is a valid root.
	 * @throws MalformedInputException 
	 */
	static public boolean isRoot(String root) throws MalformedInputException{
		//first check for the subscript and radical sign
		int i = 0;
		for(; i < root.length() && root.charAt(i) >= '\u2080' && root.charAt(i) <= '\u2089'; i++);
		//remove the subscript
		root = root.substring(i,root.length());
		//remove the radical sign, making sure not to throw and exception
		if(root.length() < 1 || root.charAt(0) != '√') return false;
		//remove the radical sign
		root = root.substring(1);
		//now make sure the rest is an expression that either has only one term
		//or has parentheses around it
		//if it's not the same after removing parentheses
		boolean hasPar = !Util.removePar(root).equals(root);
		Expression expr;
		//if it can't be parsed as and expression return false
		try{
			expr = new Expression(root);
		}
		catch(ArrayIndexOutOfBoundsException e){
			return false;
		}
		//if the expression has more than one term and it doesn't have parentheses
		if(expr.numbOfTerms() != 1 && !hasPar) return false;
		//well then it must be valid
		return true; 
	}

	/**
	 * @return The nth root (e.g. 2 for a square root, 4 for a fourth root).
	 */
	public int getNthRoot() {
		return this.nthRoot;
	}

	/**
	 * @return The expression under the radical sign.
	 */
	public AlgebraicParticle getExpr() {
		return this.expr;
	}
	
	/**
	 * Tells whether the Root is a constant value (without variables). If the 
	 * expression under the radical is a number (as defined by 
	 * Number.isNumber(String)) the Root is constant.
	 * @return If the Root is constant.
	 */
	public boolean isConstant(){
		return isConstant;
	}

	/* (non-Javadoc)
	 * @see com.github.nateowami.solve4x.solver.AlgebraicParticle#getAsString()
	 */
	@Override
	public String getAsString() {
		return 
				//watch out for square root; don't render it if it exists
				(this.nthRoot == 2 ? "" : this.nthRoot)
				+ expr.getAsString();
	}
	
}
