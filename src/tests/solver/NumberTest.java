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

import java.nio.charset.MalformedInputException;

import com.github.nateowami.solve4x.solver.Number;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Nateowami
 */
public class NumberTest {

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Number#Number(java.lang.String)}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testNumber() throws MalformedInputException {
		Number n1 = new Number("123.65");
		assertTrue(n1.sign() == '+');
		assertTrue(n1.getIntegerPart().equals("123"));
		assertTrue(n1.getDecimalPart().equals("65"));
		Number n2 = new Number("-7.28");
		assertTrue(n2.sign() == '-');
		assertTrue(n2.getIntegerPart().equals("7"));
		assertTrue(n2.getDecimalPart().equals("28"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Number#add(com.github.nateowami.solve4x.solver.Number, com.github.nateowami.solve4x.solver.Number)}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testAdd() throws MalformedInputException {
		assertTrue(Number.add(new Number("1"), new Number("2")).getAsString().equals("3"));
		assertTrue(Number.add(new Number("1.637"), new Number("2.1")).getAsString().equals("3.737"));
		assertTrue(Number.add(new Number("-16"), new Number("13")).getAsString().equals("-3"));
		assertTrue(Number.add(new Number("3.1415926535"), new Number("-16.001")).getAsString().equals("-12.8594073465"));
		assertTrue(Number.add(new Number("0.024"), new Number("23.06")).getAsString().equals("23.084"));
		assertTrue(Number.add(new Number("23"), new Number("2")).getAsString().equals("25"));
		assertTrue(Number.add(new Number("-1.01"), new Number("2")).getAsString().equals("0.99"));
		assertTrue(Number.add(new Number("6.6"), new Number("13.02")).getAsString().equals("19.62"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Number#getAsString()}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testGetAsString() throws MalformedInputException {
		assertTrue(new Number("-234.026").getAsString().equals("-234.026"));
		assertTrue(new Number("234.6").getAsString().equals("234.6"));
		assertTrue(new Number("-8.87").getAsString().equals("-8.87"));
		assertTrue(new Number("1.0").getAsString().equals("1.0"));
		assertTrue(new Number("1").getAsString().equals("1"));
		assertTrue(new Number("6.03").getAsString().equals("6.03"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Number#sign()}.
	 * @throws MalformedInputException
	 */
	@Test
	public void testSign() throws MalformedInputException {
		Number n1 = new Number("10.34");
		assertTrue(n1.sign() == '+');
		Number n2 = new Number("-16.56");
		assertTrue(n2.sign() == '-');
		Number n3 = new Number("16");
		assertTrue(n3.sign() == '+');
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Number#isNumber(java.lang.String)}.
	 */
	@Test
	public void testIsNumber() {
		assertTrue(Number.isNumber("1"));
		assertTrue(Number.isNumber("0"));
		assertTrue(Number.isNumber("-0.1"));
		assertTrue(Number.isNumber("-1.63"));
		assertTrue(Number.isNumber("3.141592"));
		assertFalse(Number.isNumber(""));
		assertFalse(Number.isNumber("1.9.639"));
		assertFalse(Number.isNumber(".1111"));
		assertFalse(Number.isNumber("12."));
		assertFalse(Number.isNumber("j8.7"));
		assertFalse(Number.isNumber("8.8s"));
	}
	
}
