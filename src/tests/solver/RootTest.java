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
package tests.solver;

import static org.junit.Assert.*;

import java.nio.charset.MalformedInputException;

import org.junit.Test;

import com.github.nateowami.solve4x.solver.Root;

/**
 * @author Nateowami
 */
public class RootTest {

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Root#Root(java.lang.String)}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testRoot() throws MalformedInputException {
		Root root1 = new Root("₄√(4x+6)");
		assertTrue(root1.getNthRoot()==4);
		assertTrue(root1.getExpr().getAsString().equals("4x+6"));
		Root root2 = new Root("₂₀₉√(74xy2)");
		assertTrue(root2.getNthRoot()==209);
		assertTrue(root2.getExpr().getAsString().equals("74xy2"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Root#isRoot(java.lang.String)}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testIsRoot() throws MalformedInputException {
		assertTrue(Root.isRoot("₄√(4x+6)"));
		assertTrue(Root.isRoot("₂₀₉√(74xy2)"));
		assertTrue(Root.isRoot("√4xy2"));
		assertTrue(Root.isRoot("√(xyz+6x4)"));
		
		assertFalse(Root.isRoot("√xyz+6x4"));
		assertFalse(Root.isRoot("2√15"));
		assertFalse(Root.isRoot(""));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Root#getRoot()}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testGetNthRoot() throws MalformedInputException {
		Root root1 = new Root("₉√(45+76(4))");
		assertTrue(root1.getNthRoot()==9);
		Root root2 = new Root("₀₉₂√(45)");
		assertTrue(root2.getNthRoot()==92);
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Root#getExpr()}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testGetExpr() throws MalformedInputException {
		Root root1 = new Root("₉√(45+x)");
		assertTrue(root1.getNthRoot()==9);
		Root root2 = new Root("₀₉₂√(x+73)");
		assertTrue(root2.getNthRoot()==92);
	}

}
