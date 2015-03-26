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

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Nateowami
 */
public abstract class AlgebraicCollection extends AlgebraicParticle {
	
	protected AlgebraicCollection(boolean sign, int exponent){
		super(sign, exponent);
	}
	
	protected AlgebraicCollection(){};
	
	/**
	 * @return The element at the specified index i.
	 */
	public abstract AlgebraicParticle get(int i);
	
	/**
	 * @return The length of the collection;
	 */
	public abstract int length();
	
	/**
	 * Clones the collection, with the exception that the element at index index is replaced with element.
	 * @param index The index of the element to replace.
	 * @param element The new element to replace the one currently at index index;
	 * @return A partial clone of the collection, with element at index swapped for element.
	 */
	public AlgebraicCollection cloneWithNewElement(int index, AlgebraicParticle element){
		AlgebraicParticle[] terms = new AlgebraicParticle[this.length()];
		for(int i = 0; i < terms.length; i++){
			if(i == index) terms[i] = element;
			else terms[i] = this.get(i); 
		}
		if(this instanceof Expression) return new Expression(this.sign(), terms, this.exponent());
		else if(this instanceof Term) return new Term(this.sign(), new ArrayList(Arrays.asList(terms)), this.exponent());
		else throw new IllegalArgumentException("This method is only applicable for Term and Expression.");
	}
	
	/**
	 * Finds element out within tree and replaces it with in, then returns the psudo-copy 
	 * of tree. If out occurs multiple times within tree, only the first will be replaced 
	 * with in.
	 * @param tree The structure to search for out in.
	 * @param out The AlgebraicParticle to remove.
	 * @param in The AlgebraicParticle to replace out with.
	 * @return A psudo-copy of tree with in swapped for out.
	 */
	public AlgebraicCollection replace(AlgebraicParticle out, AlgebraicParticle in){
		for(int i = 0; i < this.length(); i++){
			AlgebraicParticle current = this.get(i);
			//if the current element is the one we're supposed to replace
			if(current == out){
				return this.cloneWithNewElement(i, in);
			}
			//if the current one is NOT one we're supposed to replace, but it has children
			else if(current instanceof AlgebraicCollection){
				AlgebraicCollection replacement = ((AlgebraicCollection)current).replace(out, in);
				if(replacement != null) return this.cloneWithNewElement(i, replacement);
			}
		}
		return null;
	}
	
}
