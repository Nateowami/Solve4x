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
	 * Test method for {@link com.github.nateowami.solve4x.solver.Util#AllAreNumerals(java.lang.String)}.
	 */
	@Test
	public void testAllAreNumerals() {
		assertTrue(Util.areAllNumerals("127"));
		assertTrue(Util.areAllNumerals("763"));
		assertTrue(Util.areAllNumerals("07"));
		assertTrue(Util.areAllNumerals("0"));
		assertFalse(Util.areAllNumerals("-0"));
		assertFalse(Util.areAllNumerals("0.58"));
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
	 * Test method for {@link com.github.nateowami.solve4x.solver.Util#areAllLetters(java.lang.String)}.
	 */
	@Test
	public void testAreAllLetters() {
		assertTrue(Util.areAllLetters("aj"));
		assertTrue(Util.areAllLetters("Z"));
		assertTrue(Util.areAllLetters("qwRx"));
		assertFalse(Util.areAllLetters("t1"));
		assertFalse(Util.areAllLetters("h6"));
		assertFalse(Util.areAllLetters("l."));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Util#.splitByNonNestedChars(java.lang.String s, char... c)}.
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
	 * Test method for {@link com.github.nateowami.solve4x.solver.Util#isSuperscript(java.lang.String)}.
	 */
	@Test
	public void testIsSuperscriptString() {
		assertTrue(Util.isSuperscript("⁰⁴⁹"));
		assertFalse(Util.isSuperscript(""));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Util#superscriptToInt(java.lang.String)}.
	 */
	@Test
	public void testSuperscriptToInt() {
		assertEquals(56, Util.superscriptToInt("⁵⁶"));
		assertEquals(109, Util.superscriptToInt("¹⁰⁹"));
		assertEquals(6372, Util.superscriptToInt("⁶³⁷²"));
		assertEquals(4, Util.superscriptToInt("⁴"));
		assertEquals(9, Util.superscriptToInt("⁹"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Util#intToSuperscript(java.lang.String)}.
	 */
	@Test
	public void testToSuperscript() {
		assertEquals("⁶", Util.toSuperscript("6"));
		assertEquals("⁰", Util.toSuperscript("0"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Util#isSubscript(java.lang.String)}.
	 */
	@Test
	public void testIsSubscript() {
		assertTrue(Util.isSubscript("₀"));
		assertTrue(Util.isSubscript("₄"));
		assertTrue(Util.isSubscript("₉"));
		assertTrue(Util.isSubscript("₄₉"));
		assertFalse(Util.isSuperscript(""));
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
	 * Test method for {@link com.github.nateowami.solve4x.solver.Util#toSubscript(java.lang.String)}.
	 */
	@Test
	public void testToSubscript() {
		assertEquals("₄₄", Util.toSubscript("44"));
		assertEquals("₅₆", Util.toSubscript("56"));
		assertEquals("₁₉₀", Util.toSubscript("190"));
		assertEquals("₈₄", Util.toSubscript("84"));
	}
	
}
