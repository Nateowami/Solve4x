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

/**
 * Holds a root for any expression, e.g √(4x+6)
 * @author Nateowami
 */
public class Root extends AlgebraicParticle{
	
	//the root (e.g. 4th root, 2nd root (square root), nth root)
	private final int nthRoot;
	private final AlgebraicParticle expr;
	
	/**
	 * Constructs a new Root
	 * @param root The root to parse. Should be in the form of <i>subscript</i>√<i>expression</i>
	 */
	protected Root(String root) {
		String original = root; //for debugging purposes
		//figure out how many subscript chars there are
		int i = 0;
		for(; i < root.length(); i++){
			//if it's not a subscript
			if(!Util.isSubscript(Character.toString(root.charAt(i)))) break;
		}
		this.nthRoot = root.substring(0,i).equals("") ? 2 : Util.subscriptToInt(root.substring(0,i));
		root = root.substring(i,root.length());
		
		//parse the √ symbol
		if(root.length() < 1 || root.charAt(0) != '√'){
			throw new ParsingException("Invalid root \"" + original + "\". Subscript is " + this.nthRoot 
					+ " and the remaining part is \"" + root + "\".");
		}
		else{
			//remove the √ sign
			root = root.substring(1, root.length());
		}
		//we're now safe to parse the expression
		this.expr = AlgebraicParticle.getInstance(Util.removePar(root), Root.class);
	}
	
	/**
	 * Constructs a new Root.
	 * @param sign The sign of the root.
	 * @param nthRoot The number outside the radical (e.g. 3 in a cubic root, 2 in a square root).
	 * @param expr The expression under the radical sign.
	 * @param exponent The power to which the root is raised.
	 */
	public Root(boolean sign, int nthRoot, AlgebraicParticle expr, int exponent) {
		super(sign, exponent);
		this.nthRoot = nthRoot;
		this.expr = expr;
	}
	



	/**
	 * Tells if root is a valid root, in the form of <i>subscript</i>√<i>expression</i>. 
	 * e.g. ₄√(4x+6). Parentheses are only necessary for more than one term.
	 * @param root The root to check.
	 * @return If root is a valid root.
	 */
	static public boolean parseable(String root){
		//first check for the subscript and radical sign
		int i = 0;
		while(i < root.length() && Util.isSubscript(Character.toString(root.charAt(i)))) i++;
		//remove the subscript
		root = root.substring(i,root.length());
		//remove the radical sign, making sure not to throw and exception
		if(root.length() < 1 || root.charAt(0) != '√') return false;
		//remove the radical sign
		root = root.substring(1);
		//now make sure the rest is an expression that either has only one term
		//or has parentheses around it
		//if it's not the same after removing parentheses
		if (Util.removePar(root).equals(root)){
			return false;
		}
		//it has parentheses
		else {
			//if it's an algebraic particle
			return AlgebraicParticle.parseable(root, Root.class);
		}
		
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
		return Util.constant(this.expr);
	}
	
	/* (non-Javadoc)
	 * @see com.github.nateowami.solve4x.solver.AlgebraicParticle#getAsString()
	 */
	@Override
	public String getAsString() {
		return wrapWithSignParAndExponent(
				//don't show nth root if it's 2 (default)
				this.nthRoot == 2 ? "" : Util.toSubscript(Integer.toString(this.nthRoot))
				+ "√(" + expr.getAsString() + ")",
				true);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Root [nthRoot=" + nthRoot + ", expr=" + expr + ", sign()="
				+ sign() + ", exponent()=" + exponent() + "]";
	}
	
	/* (non-Javadoc)
	 * @see com.github.nateowami.solve4x.solver.AlgebraicParticle#cloneWithNewSign(java.lang.Boolean)
	 */
	@Override
	public AlgebraicParticle cloneWithNewSign(Boolean sign) {
		return new Root(sign == null ? this.sign() : sign,
				this.nthRoot,
				this.expr,
				this.exponent()
				);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((expr == null) ? 0 : expr.hashCode());
		result = prime * result + nthRoot;
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return almostEquals(obj) && super.equals(obj);
	}
	
	public boolean almostEquals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Root other = (Root) obj;
		if (expr == null) {
			if (other.expr != null)
				return false;
		} else if (!expr.equals(other.expr))
			return false;
		if (nthRoot != other.nthRoot)
			return false;
		return true;
	}
	
}
