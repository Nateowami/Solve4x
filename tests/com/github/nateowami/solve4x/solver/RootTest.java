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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author Nateowami
 */
public class RootTest {

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Root#Root(java.lang.String)}.
	 * @ 
	 */
	@Test
	public void testRoot()  {
		Root root1 = new Root("₄√(4x+6)");
		assertEquals(4, root1.getNthRoot());
		assertEquals("4x+6", root1.getExpr().render());
		Root root2 = new Root("₂₀₉√(74xy2)");
		assertEquals(209, root2.getNthRoot());
		assertEquals("74xy2", root2.getExpr().render());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Root#isRoot(java.lang.String)}.
	 * @ 
	 */
	@Test
	public void testparseable()  {
		assertTrue(Root.parseable("√(4)"));
		assertTrue(Root.parseable("√(xyz+6x4)"));
		assertTrue(Root.parseable("₄√(4x+6)"));
		assertTrue(Root.parseable("₄√(4y+x⁶)"));
		assertTrue(Root.parseable("₂₀₉√(74xy2)"));
		
		assertFalse(Root.parseable("√4xy2"));
		assertFalse(Root.parseable("√xyz+6x4"));
		assertFalse(Root.parseable("2√15"));
		assertFalse(Root.parseable(""));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Root#getRoot()}.
	 * @ 
	 */
	@Test
	public void testGetNthRoot()  {
		Root root1 = new Root("₉√(45+76(4))");
		assertEquals(9, root1.getNthRoot());
		Root root2 = new Root("₀₉₂√(45)");
		assertEquals(92, root2.getNthRoot());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Root#getExpr()}.
	 * @ 
	 */
	@Test
	public void testGetExpr()  {
		Root root1 = new Root("₉√(45+x)");
		assertEquals(9, root1.getNthRoot());
		Root root2 = new Root("₀₉₂√(x+73)");
		assertEquals(92, root2.getNthRoot());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Root#isConstant()}.
	 * @ 
	 */
	@Test
	public void testIsConstant()  {
		assertFalse(new Root("₄√(4x+6)").isConstant());
		assertFalse(new Root("₂₀₉√(74xy2)").isConstant());
		assertTrue(new Root("₀₉₂√(45)").isConstant());
		assertTrue(new Root("√2").isConstant());
	}
	
}
