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

/**
 * All things algebra: equations and algebraic particles (i.e., terms, expressions, fractions, 
 * etc.).
 * @author Nateowami
 */
public abstract class Algebra {
	
	/**
	 * Flattens the type hierarchy and returns it as an ArrayList. The ArrayList will contain this 
	 * object, as well as any of its children and their children, etc. For example, if this method 
	 * was invoked on 2x(3+4y), flattening it would yield the following result:<br>
	 * [2, x, (3+4y), 3, 4y, 4, y]
	 * @return The hierarchy flattened.
	 */
	public ArrayList<Algebra> flatten() {
		ArrayList<Algebra> list = new ArrayList<Algebra>();
		list.add(this);
		if(this instanceof Equation) {
			list.addAll(((Equation)this).left().flatten());
			list.addAll(((Equation)this).right().flatten());
		}
		else if(this instanceof AlgebraicCollection) {
			AlgebraicCollection collection = (AlgebraicCollection) this;
			for(int i = 0; i < collection.length(); i++) list.addAll(collection.get(i).flatten());
		}
		else if(this instanceof Fraction) {
			list.add(((Fraction)this).getTop());
			list.add(((Fraction)this).getBottom());
		}
		else if(this instanceof MixedNumber) {
			list.add(((MixedNumber)this).getNumeral());
			list.add(((MixedNumber)this).getFraction());
		}
		else if(this instanceof Root) list.add(((Root)this).getExpr());
		return list;
	}
	
	public abstract String render();
	
	// Make sure subclasses implement these themselves
	
	public abstract String toString();
	
	public abstract boolean equals(Object o);
	
	public abstract int hashCode();
	
}
