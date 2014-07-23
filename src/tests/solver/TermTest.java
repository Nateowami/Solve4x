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
	public void testTerm() throws MalformedInputException {
		Term term1 = new Term("23x2y32(x+6)5(2x+6)3");
		System.out.println("Term created.");
		assertTrue(term1.getCoe().getAsString().equals("23"));
		assertTrue(term1.getVarAt(0) == 'x' && term1.getVarPower(0) == 2);
		assertTrue(term1.getVarAt(1) == 'y' && term1.getVarPower(1) == 32);
		assertTrue(term1.getExprAt(0).getAsString().equals("x+6") && term1.getExprPower(0) == 5);
		assertTrue(term1.getExprAt(1).getAsString().equals("2x+6") && term1.getExprPower(1) == 3);
		Term term2 = new Term("6((34)/(6x))");
		assertTrue(term2.getExprAt(0).getAsString().equals("((34)/(6x))"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#hasCoe()}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testHasCoe() throws MalformedInputException {
		Term term1 = new Term("x(45)");
		assertFalse(term1.hasCoe());
		Term term2 = new Term("22(2)");
		assertTrue(term2.hasCoe());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#getCoe()}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testGetCoe() throws MalformedInputException {
		Term term1 = new Term("x(45)");
		assertTrue(term1.getCoe() == null);
		Term term2 = new Term("22(2)");
		assertTrue(term2.hasCoe());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#numOfVars()}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testNumOfVars() throws MalformedInputException {
		Term term1 = new Term("x(45)");
		assertTrue(term1.numOfVars() == 1);
		Term term2 = new Term("22(2)");
		assertTrue(term2.numOfVars() == 0);
		Term term3 = new Term("x2yz7(45+6x)");
		assertTrue(term3.numOfVars() == 3);
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#getVarAt(int)}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testGetVarAt() throws MalformedInputException {
		Term term1 = new Term("x(45)");
		assertTrue(term1.getVarAt(0) == 'x');
		Term term2 = new Term("22x4y(2)");
		assertTrue(term2.getVarAt(1) == 'y');
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#getVarPower(int)}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testGetVarPower() throws MalformedInputException {
		Term term1 = new Term("x2(45)");
		assertTrue(term1.getVarPower(0) == 2);
		Term term2 = new Term("22x4y4(2)");
		assertTrue(term2.getVarPower(1) == 4);
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#numOfExprs()}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testNumOfExprs() throws MalformedInputException {
		Term term1 = new Term("x(45)");
		assertTrue(term1.numOfExprs() == 1);
		Term term2 = new Term("22x4y(2)(45+56x2y(45+6))");
		assertTrue(term2.numOfExprs() == 2);
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#getExprAt(int)}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testGetExprAt() throws MalformedInputException {
		Term term1 = new Term("x(45)");
		assertTrue(term1.getExprAt(0).getAsString().equals("45"));
		Term term2 = new Term("22x4y(2)(45+56x2y(45+6))");
		assertTrue(term2.getExprAt(0).getAsString().equals("2"));
		assertTrue(term2.getExprAt(1).getAsString().equals("45+56x2y(45+6)"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#getExprPower(int)}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testGetExprPower() throws MalformedInputException {
		Term term1 = new Term("x(45)3");
		assertTrue(term1.getExprPower(0) == 3);
		Term term2 = new Term("22x4y(2)12(45+56x2y(45+6))6");
		assertTrue(term2.getExprPower(0) == 12);
		assertTrue(term2.getExprPower(1) == 6);
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#getBody()}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testGetBody() throws MalformedInputException {
		Term term1 = new Term("x(45)3");
		System.out.println("body:" + term1.getBody());
		assertTrue(term1.getBody().equals("x(45)3"));
		Term term2 = new Term("22x4y(2)12(45+56x2y(45+6))6");
		assertTrue(term2.getBody().equals("x4y(2)12(45+56x2y(45+6))6"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#getAsString()}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testGetAsString() throws MalformedInputException {
		Term term1 = new Term("x(45)3");
		assertTrue(term1.getAsString().equals("x(45)3"));
		Term term2 = new Term("22x4y(2)12(45+56x2y(45+6))6");
		assertTrue(term2.getAsString().equals("22x4y(2)12(45+56x2y(45+6))6"));
		Term term3 = new Term("1xy34y(45+6)4");
		System.out.println(">>>" + term3.getAsString());
		assertTrue(term3.getAsString().equals("xy34y(45+6)4"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#setCoe(com.github.nateowami.solve4x.solver.Number)}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testSetCoe() throws MalformedInputException {
		Term term1 = new Term("x(45)3");
		term1.setCoe(new Number("34"));
		assertTrue(term1.getCoe().getAsString().equals("34"));
		Term term2 = new Term("34y3");
		term2.setCoe(new Number("2"));
		assertTrue(term2.getCoe().getAsString().equals("2"));
	}

}
