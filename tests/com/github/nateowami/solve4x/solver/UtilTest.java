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

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Nateowami
 */
public class UtilTest {

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Util#isNumeral(char)}.
	 */
	@Test
	public void testIsNumeral() {
		assertTrue(Util.isNumeral('0'));
		assertTrue(Util.isNumeral('5'));
		assertTrue(Util.isNumeral('9'));
		assertFalse(Util.isNumeral('a'));
		assertFalse(Util.isNumeral('^'));
		assertFalse(Util.isNumeral('o'));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Util#isInteger(java.lang.String)}.
	 */
	@Test
	public void testIsInteger() {
		assertTrue(Util.isInteger("127"));
		assertTrue(Util.isInteger("-763"));
		assertTrue(Util.isInteger("0"));
		assertFalse(Util.isInteger("-0"));
		assertFalse(Util.isInteger("07"));
		assertFalse(Util.isInteger("+8"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Util#removePar(java.lang.String)}.
	 */
	@Test
	public void testRemovePar() {
		assertEquals(Util.removePar("4"), "4");
		assertEquals(Util.removePar("xy+6z)"), "xy+6z)");
		assertEquals(Util.removePar("(asdfa + z 4"), "(asdfa + z 4");
		assertEquals(Util.removePar("(4)"), "4");
		assertEquals(Util.removePar("(xy+6/3)"), "xy+6/3");
		assertEquals(Util.removePar("((asdfa)asdkjflaskdfj)"), "(asdfa)asdkjflaskdfj");
		assertEquals(Util.removePar("(z6+4)"), "z6+4");
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Util#areLetters(java.lang.String)}.
	 */
	@Test
	public void testAreLetters() {
		assertTrue(Util.areLetters("aj"));
		assertTrue(Util.areLetters("Z"));
		assertTrue(Util.areLetters("qwRx"));
		assertFalse(Util.areLetters("t1"));
		assertFalse(Util.areLetters("h6"));
		assertFalse(Util.areLetters("l."));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Util#isFullySimplified(java.lang.String)}.
	 */
	@Test
	public void testIsFullySimplified() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Util#GCF(int, int)}.
	 */
	@Test
	public void testGCF() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Util#commonFactors(int, int)}.
	 */
	@Test
	public void testCommonFactors() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Util#factors(int)}.
	 */
	@Test
	public void testFactors() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Util#.splitByNonNestedChars(String s, char... c)}.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testSplitByNonNestedChars() {
		assertEquals(new String[]{"2x", "+6"}, Util.splitByNonNestedChars("2x+6", '+', '-'));
		assertEquals(new String[]{"4y(6+2)"}, Util.splitByNonNestedChars("4y(6+2)", '+', '-'));
		assertEquals(new String[]{"x", "-7(4+4)(x+2)", "+2"}, Util.splitByNonNestedChars("x-7(4+4)(x+2)+2", '+', '-'));
		assertEquals(new String[]{"(x+4)", "/(8)"}, Util.splitByNonNestedChars("(x+4)/(8)", '/'));
		assertEquals(new String[]{"2x", "+6(34+9xy2(45x+6))"}, Util.splitByNonNestedChars("2x+6(34+9xy2(45x+6))", '+', '-'));
		assertEquals(new String[]{"j"}, Util.splitByNonNestedChars("j", '+', '-'));
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Util#isSuperscript(char)}.
	 */
	@Test
	public void testIsSuperscriptChar() {
		assertTrue(Util.isSuperscript('⁹'));
		assertTrue(Util.isSuperscript('⁰'));
		assertTrue(Util.isSuperscript('⁴'));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Util#isSuperscript(java.lang.String)}.
	 */
	@Test
	public void testIsSuperscriptString() {
		assertTrue(Util.isSuperscript("⁰⁴⁹"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Util#superscriptToInt(java.lang.String)}.
	 */
	@Test
	public void testSuperscriptToInt() {
		assertEquals(56, Util.superscriptToInt("⁵⁶"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Util#intToSuperscript(int)}.
	 */
	@Test
	public void testIntToSuperscript() {
		assertEquals("⁶", Util.intToSuperscript(6));
		assertEquals("⁰", Util.intToSuperscript(0));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Util#isSubscript(char)}.
	 */
	@Test
	public void testIsSubscript() {
		assertTrue(Util.isSubscript('₀'));
		assertTrue(Util.isSubscript('₄'));
		assertTrue(Util.isSubscript('₉'));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Util#subscriptToInt(java.lang.String)}.
	 */
	@Test
	public void testSubscriptToInt() {
		assertEquals(34, Util.subscriptToInt("₃₄"));
		assertEquals(12, Util.subscriptToInt("₁₂"));
		assertEquals(472, Util.subscriptToInt("₄₇₂"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Util#intToSubscript(int)}.
	 */
	@Test
	public void testIntToSubscript() {
		assertEquals("₄₄", Util.intToSubscript(44));
		assertEquals("₅₆", Util.intToSubscript(56));
		assertEquals("₁₉₀", Util.intToSubscript(190));
		assertEquals("₈₄", Util.intToSubscript(84));
	}
	
}
