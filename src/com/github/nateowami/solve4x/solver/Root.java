/*
    Solve4x - An audio-visual algebra solver
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
public class Root {
	
	//the root (e.g. 4th root, 2nd root (square root))
	private int root;
	private Expression expr;
	
	public Root(String root){
		for(int i = 0; i < root.length() && isSubscript(root.charAt(i)); i++){
			
		}
		//TODO
	}
	
	/**
	 * Tells if sub is a subscript from 0 to 9.
	 * @param sub The char to check.
	 * @return If sub is a valid subscript.
	 */
	//TODO make non-public. currently public for unit tests.
	public boolean isSubscript(char sub) {
		return sub >= '\u2080' && sub <= '\u2089';
	}

	/**
	 * Tells if root is a valid root, e.g. ₄√(4x+6). Parentheses are only necessary for more than one term.
	 * @param root The root to check.
	 * @return If root is a valid root.
	 */
	public boolean isRoot(String root){
		return false; //TODO
	}

	/**
	 * @return The root (e.g. 2 for a square root, 4 for a fourth root).
	 */
	public int getRoot() {
		return root;
	}

	/**
	 * @return The expression under the radical sign.
	 */
	public Expression getExpr() {
		return expr;
	}
	
}
