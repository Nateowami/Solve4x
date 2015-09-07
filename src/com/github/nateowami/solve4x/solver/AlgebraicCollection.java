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
		ArrayList<AlgebraicParticle> terms = new ArrayList<AlgebraicParticle>(this.length());
		for(int i = 0; i < this.length(); i++){
			if(i == index) terms.add(element);
			else terms.add(this.get(i)); 
		}
		if(this instanceof Expression) return new Expression(this.sign(), terms, this.exponent());
		else if(this instanceof Term) return new Term(this.sign(), terms, this.exponent());
		else throw new IllegalArgumentException("This method is only applicable for Term and Expression.");
	}
	
	/**
	 * Clones this AlgebraicCollection and removes the element at index i.
	 * @param i The index of the element to remove.
	 * @return This cloned with element at index i removed.
	 */
	public abstract AlgebraicCollection cloneAndRemove(int i);
	
	/**
	 * Converts this AlgebraicCollection to an ArrayList&lt;AlgebraicParticle&gt;. Naturally the 
	 * sign and exponent cannot be included in such a list. Because AlgebraicCollections should be  
	 * immutable, the list should be cloned so the instance cannot be modified in any way.
	 * @return The AlgebraicCollection converted to an ArrayList of AlgebraicParticles.
	 */
	public abstract ArrayList<AlgebraicParticle> toList();
		
}
