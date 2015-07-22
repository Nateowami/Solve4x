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
	 * @return A psudo-copy of tree with in swapped for out, or null, if there is nothing to be 
	 * replaced.
	 */
	public AlgebraicParticle replace(AlgebraicParticle out, AlgebraicParticle in){
		if(out == this)return in;
		for(int i = 0; i < this.length(); i++){
			AlgebraicParticle current = this.get(i);
			//if the current element is the one we're supposed to replace
			if(current == out){
				return this.cloneWithNewElement(i, in);
			}
			//if the current one is NOT one we're supposed to replace, but it has children
			else if(current instanceof AlgebraicCollection){
				AlgebraicParticle replacement = ((AlgebraicCollection)current).replace(out, in);
				if(replacement != null) return this.cloneWithNewElement(i, replacement);
			}
		}
		return null;
	}
	
	/**
	 * Flattens the structure of AlgebraicParticles and returns it as a single array. For example, 
	 * if the AlgebraicCollection was 2x(3+4y), flattening it would yield the following result:<br>
	 * [2, x, (3+4y), 3, 4y, 4, y]
	 * @return A flattened version of this AlgebraicCollection.
	 */
	public AlgebraicParticle[] flatten(){
		//the array we're building of flattened stuff
		ArrayList<AlgebraicParticle> list = new ArrayList<AlgebraicParticle>();
		//add this
		list.add(this);
		for(int i = 0; i < this.length(); i++){
			//if this element is a collection, add it and its children
			if(this.get(i) instanceof AlgebraicCollection){
				list.addAll(Arrays.asList(
								((AlgebraicCollection)this.get(i)).flatten()
						));
			}
			//it's not a collection; just add it
			else list.add(this.get(i));
		}
		return list.toArray(new AlgebraicParticle[list.size()]);
	}
	
	/**
	 * Flattens the collection as specified by {@link com.github.nateowami.solve4x.solver.AlgebraicCollection#flatten()
	 *  flatten()}, then limits it to objects of type c.
	 * @param c A class to limit objects to.
	 * @return This collection flattened, with all objects that aren't instances of the specified class c removed.
	 */
	protected ArrayList<? extends AlgebraicParticle> flattenAndLimitByClass(Class<? extends AlgebraicParticle> c){
		ArrayList<AlgebraicParticle> out = new ArrayList<AlgebraicParticle>();
		for(AlgebraicParticle a : flatten()){
			if(c.isInstance(a)){
				out.add(a);
			}
		}
		return out;
	}
	
	/**
	 * @return All terms in this AlgebraicCollection after it has been flattened.
	 * @see com.github.nateowami.solve4x.solver.AlgebraicCollection#flatten()
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Term> terms(){
		return (ArrayList<Term>) flattenAndLimitByClass(Term.class);
	}
	
	/**
	 * @return All expressions in this AlgebraicCollection after it has been flattened.
	 * @see com.github.nateowami.solve4x.solver.AlgebraicCollection#flatten()
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Expression> expressions(){
		return (ArrayList<Expression>) flattenAndLimitByClass(Expression.class);
	}
	
	/**
	 * Clones this AlgebraicCollection and removes the element at index i.
	 * @param i The index of the element to remove.
	 * @return This cloned with element at index i removed.
	 */
	public abstract AlgebraicCollection cloneAndRemove(int i);
	
}
