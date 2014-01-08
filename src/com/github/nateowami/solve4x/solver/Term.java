/*
    Solve4x - A Java program to solve and explain algebra problems
    Copyright (C) 2013 Nathaniel Paulus

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
 * Provides a way to work with terms in equations.
 * @author Nateowami
 */
public class Term {
	
	//all properties are strings because there's no way to have an empty int
	//but you can have an empty string. Example: 2x has no coefficient. We can't 
	//represent that if we use an int.
	
	//the coefficient of the term (it's a String because the coefficient could be a fraction)
	private String coe = "";
	//the body of the term
	private String expr = "";
	//the exponent of the term
	private String expon = "";
	
	/**
	 * Creates a new term from a String
	 * @param expression The term to create
	 */
	public Term(String expression){
		//
		//TODO finish this class @Nateowami
	}
}
