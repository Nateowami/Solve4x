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

import static org.junit.Assert.*;
import static com.github.nateowami.solve4x.solver.SolverTests.*; 

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author Nateowami
 */
public class TreeTest {

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Tree#Tree(com.github.nateowami.solve4x.solver.Algebra)}.
	 */
	@Test
	public void testTree() {
		new Tree(AlgebraicParticle.getInstance("2x(4+3)"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Tree#considerReplacement(com.github.nateowami.solve4x.solver.Algebra)}.
	 */
	@Test
	public void testConsiderReplacement() {
		assertEquals(a("2x(5+3)").render(), new Tree(a("2x(4+3)")).get(2).get(0).considerReplacement(a("5")).render());
		assertEquals(a("2x(5+3)*2(3)/(5)").render(), new Tree(a("2x(5+3)*2(4)/(5)")).get(3).get(1).get(0).considerReplacement(a("3")).render());
		assertEquals(a("√(13)+18*9.5").render(), new Tree(a("√(4+9)+18*9.5")).get(0).get(0).considerReplacement(a("13")).render());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Tree#where(Class).
	 */
	@Test
	public void testWhere() {
		List<Tree> list = new ArrayList<Tree>();
		Tree t = new Tree(a("2x(4+5)"));
		list.add(t.get(0));
		list.add(t.get(2).get(0));
		list.add(t.get(2).get(1));
		assertEquals(list, t.where(Number.class));
		
		Tree t2 = new Tree(a("4"));
		assertEquals(a("5"), t2.considerReplacement(a("5")));
	}

}
