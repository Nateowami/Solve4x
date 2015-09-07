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

import static com.github.nateowami.solve4x.solver.Util.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents an algebraic hierarchy. Unlike the Algebra class, each node of a Tree (which nodes 
 * are just trees themselves) points back to its parent. Thus, from a single node it is possible to 
 * determine the entire structure. Each node of the tree stands for an instance of Algebra. Note 
 * that a Tree uses trees as nodes, so the terms are interchangeable.
 * @author Nateowami
 */
public class Tree {
	
	private final Algebra alg;
    private Tree parent;
    private final ArrayList<Tree> children;
	
    /**
     * Constructs a new tree from given algebra. Each node of the tree contains a piece of algebra, 
     * and each node points back to its parent and has a list of children nodes.
     * @param algebra The algebra from which to construct the tree.
     */
	public Tree(Algebra algebra) {
		this.alg = algebra;
		this.parent = null;
		ArrayList<AlgebraicParticle> algebraicChildren = children(algebra);
		if(algebraicChildren == null) this.children = null;
		else {
			this.children = new ArrayList<Tree>(algebraicChildren.size());
			for(AlgebraicParticle a : algebraicChildren) {
				this.children.add(constructTreeWithParent(a, this));
			}
		}
	}
	
	/**
	 * Constructs a new algebraic hierarchy in which the current node is swapped for the specified 
	 * algebra. While this method leave the tree untouched, the simplest way to think of this is 
	 * that the tree swaps this node for a new node constructed from the specified algebra and 
	 * updates all affected parent and child nodes (constructing new algebra for each node) and 
	 * returns the super parent's algebra. It then resets the tree to its previous state. 
	 * @param alg The algebra to replace this node with.
	 */
	public Algebra considerReplacement(Algebra algebra) {
		if(this.parent == null) return algebra;
		else return considerReplacement(this.parent, this.parent.indexOf(this), algebra);
	}
	
	/**
	 * @return The number of children nodes this tree has.
	 */
	public int length() {
		return this.children.size();
	}
	
	/**
	 * Returns the tree's child node indicated by the specified index.
	 * @param index The index of the node to get.
	 * @return The node at the specified index.
	 */
	public Tree get(int index) {
		//if it's a child it must be an AlgebraicParticle
		return this.children.get(index);
	}
	
	/** 
	 * @return the algebra this node represents.
	 */
	public Algebra algebra() {
		return this.alg;
	}
	
	/**
	 * Returns a list of all nodes of this tree that represent instances of the specified class. 
	 * @param c The class to limit nodes to.
	 * @return A list of nodes that represent algebra of the specified class.
	 */
	public List<Tree> where(Class<? extends Algebra> c){
		List<Tree> list = new ArrayList<Tree>();
		if(c.isInstance(this.alg)) list.add(this);
		if(this.children != null) for(Tree tree : this.children) {
			list.addAll(tree.where(c));
		}
		return list;
	}
	
	/**
	 * Calculates what the topmost algebra in this tree would be if the specified tree's child node 
	 * specified by index were replaced by a node constructed form the specified algebra. See 
	 * {@link com.github.nateowami.solve4x.solver.Tree#considerReplacement(Algebra)} for a fuller 
	 * explanation of its purpose and what it does. This method is the internal implementation, 
	 * while the referenced method is the public-facing method.
	 * @param tree The tree whose node needs to be "virtually" replaced.
	 * @param index The index of the node to be "virtually" replaced.
	 * @param algebra The algebra to "swap in" at the given index.
	 * @return An piece of algebra that represents what the topmost node of the tree would be like 
	 * if the specified tree's child given by the specified index were replaced by a node that 
	 * represented the given algebra, and all parents were updated to reflect the change.
	 */
	private static Algebra considerReplacement(Tree tree, int index, Algebra algebra) {
		//the tree's top node will call this and tree will end up null, which means time to return 
		//just what we were given
		if(tree == null) return algebra;
		
		//construct a list of all the tree's children
		ArrayList<AlgebraicParticle> list = new ArrayList<AlgebraicParticle>(tree.children.size());
		for(int i = 0; i < tree.children.size(); i++) {
			//if it's the algebra we're swapping, put it in, otherwise copy from the list
			if(index == i) list.add((AlgebraicParticle)algebra);
			else list.add((AlgebraicParticle)tree.children.get(i).alg);
		}
		
		Algebra updated = constructAlgebra(list, tree.alg);
		return considerReplacement(tree.parent, tree.parent == null ? -1 : tree.parent.indexOf(tree), updated);
	}
	
	/**
	 * Constructs a tree and sets its parent to the specified value.
	 * @param algebra The algebra from which to construct the tree/node.
	 * @param parent The parent tree/node of the newly constructed node.
	 * @return A new tree using the specified algebra and parent.
	 */
	private Tree constructTreeWithParent(Algebra algebra, Tree parent) {
		Tree tree = new Tree(algebra);
		tree.parent = parent;
		return tree;
	}
	
	/**
	 * Decomposes a instance of Algebra into an ArrayList. Each direct child of the given algebra 
	 * is added to a list in the order they would appear when rendered.
	 * @param a The algebra to decompose.
	 * @return The given algebra as an ArrayList.
	 */
	private ArrayList<AlgebraicParticle> children(Algebra a) {
		if(a instanceof Equation) return list(((Equation)a).left(), ((Equation)a).right());
		if(a instanceof AlgebraicCollection) return ((AlgebraicCollection)a).toList();
		if(a instanceof Fraction) return list(((Fraction)a).getTop(), ((Fraction)a).getBottom());
		if(a instanceof MixedNumber) return list(((MixedNumber)a).getNumeral(), ((MixedNumber)a).getFraction());
		if(a instanceof Root) return list(((Root)a).getExpr());
		else return null;
	}
	
	/**
	 * Returns the index of the specified node. If it is not direct child of this node -1 will be 
	 * returned.
	 * @param tree The child node the index of which you want to find.
	 * @return The index of the given tree, or -1 if it is not a direct child of this tree.
	 */
	private int indexOf(Tree tree) {
		for(int i = 0; i < this.children.size(); i++) {
			if(this.children.get(i) == tree) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Constructs algebra from a given list of AlgebraicParticles. This is a reverse of 
	 * {@link com.github.nateowami.solve4x.solver.Tree#children(Algebra)}. An instance of Algebra 
	 * that is of the same type as the original Algebra is required in order to construct the right 
	 * class. In general this will be the same Algebra (or a similar one) to the one that was 
	 * "decomposed" into a list.
	 * @param l The list of AlgebraicParticle from which to construct Algebra. 
	 * @param old Any object that is of the type of the algebra we're constructing. 
	 * @return The given list l constructed as algebra.
	 */
	private static Algebra constructAlgebra(ArrayList<AlgebraicParticle> l, Algebra old) {
		Boolean s /*sign*/ = null;
		Integer e /*exponent*/ = null;
		if(old instanceof AlgebraicParticle) {
			AlgebraicParticle a =  (AlgebraicParticle) old;
			s = a.sign();
			e = a.exponent();
		}
		
		if(old instanceof Equation) return new Equation(l.get(0), l.get(1));
		if(old instanceof Expression) return new Expression(s, l, e);
		if(old instanceof Term) return new Term(s, l, e);
		if(old instanceof Fraction) return new Fraction(s, l.get(0), l.get(1), e);
		if(old instanceof MixedNumber) return new MixedNumber(s, (Number) l.get(0), (Fraction) l.get(1), e);
		if(old instanceof Root) return new Root(s, ((Root)old).getNthRoot(), l.get(0), e);
		throw new RuntimeException("None of the above matched. Must have been sent a variable or number or something.");
	}

	
}
