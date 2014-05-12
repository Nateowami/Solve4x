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
import com.github.nateowami.solve4x.solver.Term;
import com.github.nateowami.solve4x.solver.Number;


/**
 * @author Nateowami
 */
public class TermTest {

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#Term(java.lang.String)}.
	 */
	@Test
	public void testTerm() {
		try {
			System.out.println("Starting check...");
			Term term = new Term("23x2y32(x+6)5(2x+6)3");
			System.out.println("Term created.");
			assertTrue(term.getCoe().getAsString().equals("23"));
			assertTrue(term.getVarAt(0) == 'x' && term.getVarPower(0) == 2);
			assertTrue(term.getVarAt(1) == 'y' && term.getVarPower(1) == 32);
			assertTrue(term.getExprAt(0).getAsString().equals("x+6") && term.getExprPower(0) == 5);
			assertTrue(term.getExprAt(1).getAsString().equals("2x+6") && term.getExprPower(1) == 3);
		} catch (MalformedInputException e) {
			e.printStackTrace();
			fail("MalformedInputException");
		}
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#hasCoe()}.
	 */
	@Test
	public void testHasCoe() {
		try {
			Term term1 = new Term("x(45)");
			assertFalse(term1.hasCoe());
			Term term2 = new Term("22(2)");
			assertTrue(term2.hasCoe());
		} catch (MalformedInputException e) {
			e.printStackTrace();
			fail("MalformedInputException");
		}
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#getCoe()}.
	 */
	@Test
	public void testGetCoe() {
		try {
			Term term1 = new Term("x(45)");
			assertTrue(term1.getCoe() == null);
			Term term2 = new Term("22(2)");
			assertTrue(term2.hasCoe());
		} catch (MalformedInputException e) {
			e.printStackTrace();
			fail("MalformedInputException");
		}
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#numOfVars()}.
	 */
	@Test
	public void testNumOfVars() {
		try {
			Term term1 = new Term("x(45)");
			assertTrue(term1.numOfVars() == 1);
			Term term2 = new Term("22(2)");
			assertTrue(term2.numOfVars() == 0);
			Term term3 = new Term("x2yz7(45+6x)");
			assertTrue(term3.numOfVars() == 3);
		} catch (MalformedInputException e) {
			e.printStackTrace();
			fail("MalformedInputException");
		}
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#getVarAt(int)}.
	 */
	@Test
	public void testGetVarAt() {
		try {
			Term term1 = new Term("x(45)");
			assertTrue(term1.getVarAt(0) == 'x');
			Term term2 = new Term("22x4y(2)");
			assertTrue(term2.getVarAt(1) == 'y');
		} catch (MalformedInputException e) {
			e.printStackTrace();
			fail("MalformedInputException");
		}
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#getVarPower(int)}.
	 */
	@Test
	public void testGetVarPower() {
		try {
			Term term1 = new Term("x2(45)");
			assertTrue(term1.getVarPower(0) == 2);
			Term term2 = new Term("22x4y4(2)");
			assertTrue(term2.getVarPower(1) == 4); 
		} catch (MalformedInputException e) {
			e.printStackTrace();
			fail("MalformedInputException");
		}
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#numOfExprs()}.
	 */
	@Test
	public void testNumOfExprs() {
		try {
			Term term1 = new Term("x(45)");
			assertTrue(term1.numOfExprs() == 1);
			Term term2 = new Term("22x4y(2)(45+56x2y(45+6))");
			assertTrue(term2.numOfExprs() == 2);
		} catch (MalformedInputException e) {
			e.printStackTrace();
			fail("MalformedInputException");
		}
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#getExprAt(int)}.
	 */
	@Test
	public void testGetExprAt() {
		try {
			Term term1 = new Term("x(45)");
			assertTrue(term1.getExprAt(0).getAsString().equals("45"));
			Term term2 = new Term("22x4y(2)(45+56x2y(45+6))");
			assertTrue(term2.getExprAt(0).getAsString().equals("2"));
			assertTrue(term2.getExprAt(1).getAsString().equals("45+56x2y(45+6)"));
		} catch (MalformedInputException e) {
			e.printStackTrace();
			fail("MalformedInputException");
		}
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#getExprPower(int)}.
	 */
	@Test
	public void testGetExprPower() {
		try {
			Term term1 = new Term("x(45)3");
			assertTrue(term1.getExprPower(0) == 3);
			Term term2 = new Term("22x4y(2)12(45+56x2y(45+6))6");
			assertTrue(term2.getExprPower(0) == 12);
			assertTrue(term2.getExprPower(1) == 6);
		} catch (MalformedInputException e) {
			e.printStackTrace();
			fail("MalformedInputException");
		}
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#getBody()}.
	 */
	@Test
	public void testGetBody() {
		try {
			Term term1 = new Term("x(45)3");
			System.out.println("body:" + term1.getBody());
			assertTrue(term1.getBody().equals("x(45)3"));
			Term term2 = new Term("22x4y(2)12(45+56x2y(45+6))6");
			assertTrue(term2.getBody().equals("x4y(2)12(45+56x2y(45+6))6"));
		} catch (MalformedInputException e) {
			e.printStackTrace();
			fail("MalformedInputException");
		}
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#getAsString()}.
	 */
	@Test
	public void testGetAsString() {
		try {
			Term term1 = new Term("x(45)3");
			assertTrue(term1.getAsString().equals("x(45)3"));
			Term term2 = new Term("22x4y(2)12(45+56x2y(45+6))6");
			assertTrue(term2.getAsString().equals("22x4y(2)12(45+56x2y(45+6))6"));
			Term term3 = new Term("1xy34y(45+6)4");
			System.out.println(">>>" + term3.getAsString());
			assertTrue(term3.getAsString().equals("xy34y(45+6)4"));
		} catch (MalformedInputException e) {
			e.printStackTrace();
			fail("MalformedInputException");
		}
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#toString()}.
	 */
	@Test
	public void testToString() {
		//not testing
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#setCoe(com.github.nateowami.solve4x.solver.Number)}.
	 */
	@Test
	public void testSetCoe() {
		try {
			Term term1 = new Term("x(45)3");
			term1.setCoe(new Number("34"));
			assertTrue(term1.getCoe().getAsString().equals("34"));
			Term term2 = new Term("34y3");
			term2.setCoe(new Number("2"));
			assertTrue(term2.getCoe().getAsString().equals("2"));
		} catch (MalformedInputException e) {
			e.printStackTrace();
			fail("MalformedInputException");
		}
	}

}
