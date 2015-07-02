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
 * All things algebra: equations and algebraic particles (i.e., terms, expressions, fractions, 
 * etc.).
 * @author Nateowami
 */
public abstract class Algebra {
	
	public abstract String render();
	
	// Make sure subclasses implement these themselves
	
	public abstract String toString();
	
	public abstract boolean equals(Object o);
	
	public abstract int hashCode();
	
}
