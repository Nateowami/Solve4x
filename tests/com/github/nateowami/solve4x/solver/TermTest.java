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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * @author Nateowami
 */
public class TermTest {

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#render()}.
	 */
	@Test
	public void testrender() {
		assertEquals("2x", new Term("2x").render());
		assertEquals("47y", new Term("47y").render());
		assertEquals("2(3+zy)", new Term("2(3+zy)").render());
		assertEquals("2((3)/(xy+2))", new Term("2((3)/(xy+2))").render());
		assertEquals("4x(2-4)", new Term("4x(2-4)").render());
		assertEquals("(2x)²", AlgebraicParticle.getInstance("(2x)²").render());
		assertEquals("2x(4+6)²", AlgebraicParticle.getInstance("2x(4+6)²").render());
		assertEquals("2(xy)²", AlgebraicParticle.getInstance("2(xy)²").render());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#Term(java.lang.String)}.
	 * @ 
	 */
	@Test
	public void testTerm()  {
		assertEquals("2x", new Term("2x").render());
		assertEquals("2x4xy", new Term("2x4xy").render());
		assertEquals("2(1)", new Term("2(1)").render());
		assertEquals("6(34)", new Term("6(34)").render());
		assertEquals("xy(6+2)", new Term("xy(6+2)").render());
		assertEquals("4y(-y+6)", new Term("4y(-y+6)").render());
		assertEquals("4(32)", new Term("4(32)").render());
		assertEquals("x(4+y)⁴", new Term("x(4+y)⁴").render());
		assertEquals("x(4y)⁴3", new Term("x(4*y)⁴*3").render());
		assertEquals("2x4yx", new Term("2x*4yx").render());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#parsable(java.lang.String)}.
	 * @ 
	 */
	@Test
	public void testparsable()  {
		assertTrue(Term.parsable("(2x)y"));
		assertTrue(Term.parsable("1x"));
		assertTrue(Term.parsable("6(34+9xy2(45x+6))"));
		assertTrue(Term.parsable("x(4+y)⁴"));
		assertTrue(Term.parsable("5*6"));
		assertTrue(Term.parsable("(x+6)⁴*6.4"));
		assertTrue(Term.parsable("5(x+6)⁴*6.4"));
		assertTrue(Term.parsable("3.0*10⁷x"));
		assertTrue(Term.parsable("-4y"));
		
		assertFalse(Term.parsable("6(34+9xy2(45x+6)"));
		assertFalse(Term.parsable("xy+2"));
		assertFalse(Term.parsable("4"));
		assertFalse(Term.parsable("2+6"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#length()}.
	 */
	@Test
	public void testLength() {
		assertEquals(2, new Term("x6").length());
		assertEquals(4, new Term("4xy6(4)/(5)").length());
		assertEquals(3, new Term("2y(3+6)").length());
		assertEquals(2, new Term("2((x+6)/(-4y))").length());
		assertEquals(3, new Term("js(4y+3)").length());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#get(int)}.
	 */
	@Test
	public void testGetPartAt() {
		assertEquals("x", new Term("2x").get(1).render());
		assertEquals("x+6", new Term("4y(x+6)").get(2).render());
		assertEquals("y", new Term("4xy").get(2).render());
		assertEquals("4", new Term("xyz4").get(3).render());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#like(com.github.nateowami.solve4x.solver.Term)}.
	 */
	@Test
	public void testLike() {
		assertTrue(new Term("2x").like(new Term("1.7x")));
		assertTrue(new Term("4y(x+3)").like(new Term("y(x+3)")));
		assertTrue(new Term("4a(b+c)").like(new Term("-12(2)/(3)a(b+c)")));
		assertFalse(new Term("4(x+6)").like(new Term("4x")));
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#cloneAndRemove()}.
	 */
	@Test
	public void testCloneAndRemove() {
		assertEquals(new Term("2*4*xz"), new Term("2*4*xyz").cloneAndRemove(3));
	}
	
}
