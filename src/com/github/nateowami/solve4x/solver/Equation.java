/*
    Solve4x - An algebra solver that shows its work
    Copyright (C) 2015  Nathaniel Paulus

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

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Represents an algebraic equation
 * @author Nateowami
 */
public class Equation implements Algebra {
	
	//exprs holds expressions (one if this equation is just representing an 
	//expression, two if it's representing an equation
	private final AlgebraicParticle a, b;
	
	/**
	 * Creates an equation by turning it into two algebraic expressions.
	 * @param eq The equation to parse
	 */
	public Equation(String eq) {
		int i = eq.indexOf('=');
		a = AlgebraicParticle.getInstance(eq.substring(0, i));
		b = AlgebraicParticle.getInstance(eq.substring(i+1));
	}
	
	/**
	 * Constructs a new Equation
	 * @param a The first expression (left side of equals sign)
	 * @param b The second expression (right side of equals sign)
	 */
	public Equation(AlgebraicParticle a, AlgebraicParticle b) {
		this.a = a;
		this.b = b;
	}
	
	/**
	 * @return The expression to the left of the equals sign.
	 */
	public AlgebraicParticle left() {
		return a;
	}
	
	/**
	 * @return The expression to the right of the equals sign.
	 */
	public AlgebraicParticle right() {
		return b;
	}
	
	/**
	 * Tells if eq can be parsed as an equation.
	 * @param eq The string to check.
	 * @return If eq can be parsed as an equation.
	 */
	public static boolean parsable(String eq){
		int i = eq.indexOf('=');
		return AlgebraicParticle.parsable(eq.substring(0, i)) && AlgebraicParticle.parsable(eq.substring(i+1));
	}
	
	/**
	 * Replaces out with in in this equation, even if out is nested deeply in an AlgebraicParticle.
	 * @param out The AlgebraicParticle to swap out.
	 * @param in The AlgebraicParticle to swap in.
	 * @return out swapped for in.
	 */
	public Equation replace(AlgebraicParticle out, AlgebraicParticle in){
		//if one side of the equation needs to be completely replaced
		if(this.a == out) return new Equation(in, b);
		if(this.b == out) return new Equation(a, in);
		//or one side is a term or expression and one of its decendents needs to be replaced
		if(a instanceof AlgebraicCollection) {
			AlgebraicCollection ac = (AlgebraicCollection) a;
			AlgebraicParticle replaced = ac.replace(out, in);
			if(replaced != null) return new Equation(replaced, b);
		}
		if(b instanceof AlgebraicCollection) {
			AlgebraicCollection ac = (AlgebraicCollection) b;
			AlgebraicParticle replaced = ac.replace(out, in);
			if(replaced != null) return new Equation(a, replaced);
		}
		throw new IllegalArgumentException("Nothing to replace.");
	}
	
	/**
	 * @return A String representation of this equation. Example: 2x+x=5
	 */
	public String render(){
		return a.render() + '=' + b.render();
	}
	
	/**
	 * Flattens the equation and AlgebraicParticles it contains, and then limits them to instances of 
	 * class c.
	 * @param c The class to limit objects to.
	 * @return this flattened and all objects not an instance of c removed.
	 */
	protected ArrayList<? extends AlgebraicParticle> flattenAndLimitByClass(Class<? extends AlgebraicParticle> c){
		ArrayList<AlgebraicParticle> out = new ArrayList<AlgebraicParticle>();
		
		//flatten this.a and this.b and add them to out
		if(this.a instanceof AlgebraicCollection) out.addAll(((AlgebraicCollection)a).flattenAndLimitByClass(c));
		else if(this.a.getClass().equals(c)) out.add(this.a);
		
		if(this.b instanceof AlgebraicCollection) out.addAll(((AlgebraicCollection)b).flattenAndLimitByClass(c));
		else if(this.b.getClass().equals(c)) out.add(this.b);
		
		return out;
	}
	
	/**
	 * @return All terms in the flattened version of this equation.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Term> terms(){
		return (ArrayList<Term>)flattenAndLimitByClass(Term.class);
	}
	
	/**
	 * @return All expressions in the flattened version of this equation.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Expression> expressions(){
		return (ArrayList<Expression>)flattenAndLimitByClass(Expression.class);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Equation [a=" + a + ", b=" + b + "]";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Equation other = (Equation) obj;
		if (a == null) {
			if (other.a != null)
				return false;
		} else if (!a.equals(other.a))
			return false;
		if (b == null) {
			if (other.b != null)
				return false;
		} else if (!b.equals(other.b))
			return false;
		return true;
	}
	
}