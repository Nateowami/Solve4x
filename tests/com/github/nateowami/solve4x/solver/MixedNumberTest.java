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
		MixedNumber n1 = new MixedNumber("1(3)/(4)");
		System.out.println("well here it tis:" + n1.getAsString());
		assertEquals("1(3)/(4)", n1.getAsString());
		MixedNumber n2 = new MixedNumber("45(67)/(-18)");
		assertEquals("45(67)/(-18)", n2.getAsString());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.MixedNumber#isMixedNumber(java.lang.String)}.
	 */
	@Test
	public void testIsMixedNumber() {
		assertTrue(MixedNumber.parseable("1/4"));
		assertTrue(MixedNumber.parseable("5/-6"));
		assertTrue(MixedNumber.parseable("5(19)/(-6)"));
		assertFalse(MixedNumber.parseable("(5)(19)/(1)"));
		assertFalse(MixedNumber.parseable("6 56/8"));
		assertFalse(MixedNumber.parseable("6 (56)/(8)"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.MixedNumber#getFraction()}.
	 * @ 
	 */
	@Test
	public void testGetFraction()  {
		assertEquals("2/3", new MixedNumber("2/3").getFraction().getAsString());
		assertEquals("2/3", new MixedNumber("1(2)/(3)").getFraction().getAsString());
		assertEquals("-2/3", new MixedNumber("-2/(3)").getFraction().getAsString());
		assertTrue(new MixedNumber("35").getFraction() == null);
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.MixedNumber#getNumeral()}.
	 * @ 
	 */
	@Test
	public void testGetNumeral()  {
		assertEquals(4, new MixedNumber("4(2)/3").getNumeral());
		assertEquals(1, new MixedNumber("1(2)/(3)").getNumeral());
		assertTrue(new MixedNumber("-85(-2)/(-73)").getNumeral() == -85);
		assertTrue(new MixedNumber("35").getNumeral() ==  35);
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.MixedNumber#getAsString()}.
	 * @ 
	 */
	@Test
	public void testGetAsString()  {
		assertEquals("-256(7)/(-56)", new MixedNumber("-256(7)/-56").getAsString());
		assertEquals("(-87)/(-16)", new MixedNumber("-87/-16").getAsString());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.MixedNumber#hasNumeral()}.
	 * @ 
	 */
	@Test
	public void testHasNumeral()  {
		assertTrue(new MixedNumber("-256(7)/-56").hasNumeral());
		assertTrue(new MixedNumber("12").hasNumeral());
		assertTrue(new MixedNumber("-85(-2)/(-73)").hasNumeral());
		assertFalse(new MixedNumber("-11/2").hasNumeral());
	}	

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.MixedNumber#hasFraction()}.
	 * @ 
	 */
	@Test
	public void testHasFraction()  {
		assertTrue(new MixedNumber("-256(7)/-56").hasFraction());
		assertFalse(new MixedNumber("12").hasFraction());
		assertTrue(new MixedNumber("-85(-2)/(-73)").hasFraction());
		assertTrue(new MixedNumber("-11/2").hasFraction());
	}	
	
}
