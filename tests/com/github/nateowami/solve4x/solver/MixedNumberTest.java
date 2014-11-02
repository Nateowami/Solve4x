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
public class MixedNumberTest {

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.MixedNumber#MixedNumber(java.lang.String)}.
	 * @ 
	 */
	@Test
	public void testMixedNumber()  {
		assertEquals("1(3)/(4)", new MixedNumber("1(3)/(4)").getAsString());
		assertEquals("45(67)/(-18)", new MixedNumber("45(67)/(-18)").getAsString());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.MixedNumber#isMixedNumber(java.lang.String)}.
	 */
	@Test
	public void testParseable() {
		assertTrue(MixedNumber.parseable("1(3)/(4)"));
		assertTrue(MixedNumber.parseable("1(-5)/(6)"));
		assertTrue(MixedNumber.parseable("5(1)/(6)"));
		assertFalse(MixedNumber.parseable("5(19)/(1)"));
		assertFalse(MixedNumber.parseable("656/8"));
		assertFalse(MixedNumber.parseable("-6(56)/(8)"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.MixedNumber#getFraction()}.
	 * @ 
	 */
	@Test
	public void testGetFraction()  {
		assertEquals("(2)/(3)", new MixedNumber("57(2)/(3)").getFraction().getAsString());
		assertEquals("(2)/(3)", new MixedNumber("1(2)/(3)").getFraction().getAsString());
		assertEquals("(-2)/(3)", new MixedNumber("6(-2)/(3)").getFraction().getAsString());
		assertEquals("(5)/(8)", new MixedNumber("3(5)/(8)").getFraction().getAsString());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.MixedNumber#getNumeral()}.
	 * @ 
	 */
	@Test
	public void testGetNumeral()  {
		assertEquals(4, new MixedNumber("4(2)/(3)").getNumeral());
		assertEquals(1, new MixedNumber("1(2)/(3)").getNumeral());
		assertEquals(85, new MixedNumber("85(-2)/(73)").getNumeral());
		assertEquals(35, new MixedNumber("35(1)/(2)").getNumeral());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.MixedNumber#getAsString()}.
	 * @ 
	 */
	@Test
	public void testGetAsString()  {
		assertEquals("256(-7)/(56)", new MixedNumber("256(-7)/(56)").getAsString());
		assertEquals("2(-87)/(-16)", new MixedNumber("2(-87)/(-16)").getAsString());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.MixedNumber#isSimplified()}.
	 * @ 
	 */
	@Test
	public void testIsSimplified()  {
		assertTrue(new MixedNumber("2(3)/(4)").isSimplified());
		assertTrue(new MixedNumber("1(1)/(2)").isSimplified());
		assertTrue(new MixedNumber("8(3)/(16)").isSimplified());
		
		assertFalse(new MixedNumber("8(4)/(16)").isSimplified());
		assertFalse(new MixedNumber("3(-1)/(8)").isSimplified());
		assertFalse(new MixedNumber("2(4)/(12)").isSimplified());
		assertFalse(new MixedNumber("2(1)/(-12)").isSimplified());
	}
	
}
