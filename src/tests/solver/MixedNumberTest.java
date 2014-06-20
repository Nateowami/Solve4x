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

import com.github.nateowami.solve4x.solver.MixedNumber;

/**
 * @author Nateowami
 */
public class MixedNumberTest {

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.MixedNumber#MixedNumber(java.lang.String)}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testMixedNumber() throws MalformedInputException {
		MixedNumber n1 = new MixedNumber("1(3)/(4)");
		System.out.println("well here it tis:" + n1.getAsString());
		assertTrue(n1.getAsString().equals("1(3)/(4)"));
		MixedNumber n2 = new MixedNumber("45(67)/(-18)");
		assertTrue(n2.getAsString().equals("45(67)/(-18)"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.MixedNumber#isMixedNumber(java.lang.String)}.
	 */
	@Test
	public void testIsMixedNumber() {
		assertTrue(MixedNumber.isMixedNumber("1/4"));
		assertTrue(MixedNumber.isMixedNumber("5/-6"));
		assertTrue(MixedNumber.isMixedNumber("5(19)/(-6)"));
		assertFalse(MixedNumber.isMixedNumber("(5)(19)/(1)"));
		assertFalse(MixedNumber.isMixedNumber("6 56/8"));
		assertFalse(MixedNumber.isMixedNumber("6 (56)/(8)"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.MixedNumber#getFraction()}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testGetFraction() throws MalformedInputException {
		assertTrue(new MixedNumber("2/3").getFraction().getAsString().equals("2/3"));
		assertTrue(new MixedNumber("1(2)/(3)").getFraction().getAsString().equals("2/3"));
		assertTrue(new MixedNumber("-2/(3)").getFraction().getAsString().equals("-2/3"));
		assertTrue(new MixedNumber("35").getFraction() == null);
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.MixedNumber#getNumeral()}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testGetNumeral() throws MalformedInputException {
		assertTrue(new MixedNumber("4(2)/3").getNumeral() == 4);
		assertTrue(new MixedNumber("1(2)/(3)").getNumeral() == 1);
		assertTrue(new MixedNumber("-85(-2)/(-73)").getNumeral() == -85);
		assertTrue(new MixedNumber("35").getNumeral() ==  35);
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.MixedNumber#getAsString()}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testGetAsString() throws MalformedInputException {
		assertTrue(new MixedNumber("-256(7)/-56").getAsString().equals("-256(7)/(-56)"));
		assertTrue(new MixedNumber("-87/-16").getAsString().equals("(-87)/(-16)"));
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.MixedNumber#hasNumeral()}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testHasNumeral() throws MalformedInputException {
		assertTrue(new MixedNumber("-256(7)/-56").hasNumeral());
		assertTrue(new MixedNumber("12").hasNumeral());
		assertTrue(new MixedNumber("-85(-2)/(-73)").hasNumeral());
		assertFalse(new MixedNumber("-11/2").hasNumeral());
	}	

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.MixedNumber#hasFraction()}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testHasFraction() throws MalformedInputException {
		assertTrue(new MixedNumber("-256(7)/-56").hasFraction());
		assertFalse(new MixedNumber("12").hasFraction());
		assertTrue(new MixedNumber("-85(-2)/(-73)").hasFraction());
		assertTrue(new MixedNumber("-11/2").hasFraction());
	}	
	
}
