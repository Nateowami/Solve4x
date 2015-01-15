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
package com.github.nateowami.solve4x.algorithm;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

import com.github.nateowami.solve4x.solver.*;

/**
 * @author Nateowami
 */
public class CombineLikeTermsTest {
	
	CombineLikeTerms c = new CombineLikeTerms();
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.CombineLikeTerms#execute(com.github.nateowami.solve4x.solver.Equation)}.
	 */
	@Test
	public void testExecute() {
		Step s = c.execute(new Equation(new Expression[]{(Expression)a("2x+4y²-6-2.3y²-4x")}));
		assertEquals(5, s.getDifficulty());
		assertEquals(new Equation(new Expression[]{(Expression)a("-2x+1.7y²-6")}), s.getEquation());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.CombineLikeTerms#smarts(com.github.nateowami.solve4x.solver.Equation)}.
	 */
	@Test
	public void testSmarts() {
		assertEquals(7, c.smarts(new Equation("2+2")));
		assertEquals(7, c.smarts(new Equation("(1)/(3)+(1)/(3)")));
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.CombineLikeTerms#combineLikeTerms(boolean, java.util.ArrayList, int)}.
	 */
	@Test
	public void testCombineLikeTerms() {
		ArrayList<ArrayList<AlgebraicParticle>> terms = new ArrayList<ArrayList<AlgebraicParticle>>();
		ArrayList<AlgebraicParticle> a1 = new ArrayList<AlgebraicParticle>();
		a1.add(a("2x"));
		a1.add(a("7x"));
		ArrayList<AlgebraicParticle> a2 = new ArrayList<AlgebraicParticle>();
		a2.add(a("((1)/(5))y²"));
		a2.add(a("3y²"));
		ArrayList<AlgebraicParticle> a3 = new ArrayList<AlgebraicParticle>();
		a3.add(a("6"));
		terms.add(a1);
		terms.add(a2);
		terms.add(a3);
		assertEquals(a("9x+(3(1)/(5))y²+6"), c.combineLikeTerms(true, terms, 1));
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.CombineLikeTerms#combineTerms(com.github.nateowami.solve4x.solver.AlgebraicParticle, com.github.nateowami.solve4x.solver.AlgebraicParticle)}.
	 */
	@Test
	public void testCombineTerms() {
		assertEquals(a("2x"), c.combineTerms(a("1.5x"), a("0.5x")));
		assertEquals(a("0"), c.combineTerms(a("xy(4+7)"), a("-xy(4+7)")));
		assertEquals(a("(1(2)/(2))x²"), c.combineTerms(a("((1)/(2))x²"), a("(1(1)/(2))x²")));
		assertEquals(a("4x"), c.combineTerms(a("7x"), a("-3x")));
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.CombineLikeTerms#addConstants(com.github.nateowami.solve4x.solver.AlgebraicParticle, com.github.nateowami.solve4x.solver.AlgebraicParticle)}.
	 */
	@Test
	public void testAddConstants() {
		//create an array of strings that we can later "interpret". this is like a 3-tuple, delineated by spaces
		//t = true, f = false
		String[] s = new String[]{
				//numbers
				"2 2 4",
				"2.68 -3 -0.32",
				//fractions
				"(1)/(3) (2)/(3) 1",
				"(1)/(4) (3)/(4) 1",
				"(1)/(4) -(3)/(4) -(2)/(4)",
				//mixednumbers
				"2(1)/(4) 3(5)/(4) 5(6)/(4)",
				"-5(1)/(4) -3(3)/(4) -8(4)/(4)",
				"-2(1)/(4) 3(5)/(4) 1(4)/(4)",
				"2(1)/(4) -3(5)/(4) -1(4)/(4)",
				//number-fraction
				"5 (5)/(19) 5(5)/(19)", 
				"6 (4)/(3) 6(4)/(3)",
				//number-mixednumber
				"-5 6(4)/(15) 1(4)/(15)",
				//fraction-number
				"(1)/(2) 16 16(1)/(2)",
				"-(1)/(2) -16 -16(1)/(2)",
				//fraction-mixednumber
				"(1)/(4) 2(17)/(4) 2(18)/(4)",
				//mixednumber-number
				"2(5)/(18) 4(6)/(18) 6(11)/(18)",
				"-2(5)/(18) 4(6)/(18) 2(1)/(18)",
				//mixednumber-fraction
				"-3(2)/(9) -(5)/(9) -3(7)/(9)",
				"-2(5)/(9) -3(2)/(9) -5(7)/(9)",
				"-2(5)/(9) 3(5)/(9) 1",
		};
		for(String tuple : s){
			String[] p = tuple.split("\\s");
			AlgebraicParticle a = AlgebraicParticle.getInstance(p[0]), 
					b = AlgebraicParticle.getInstance(p[1]),
					correctResult = AlgebraicParticle.getInstance(p[2]),
					actualResult = c.addConstants(a, b);
			assertEquals(a.getAsString() + " and " + b.getAsString() + " should combine to be " + p[2] + " (parsed as " + correctResult.getAsString() + "), but the result is " + actualResult.getAsString(), correctResult, actualResult);
			//1.5 (5)/(19) 1.5(5)/(19)
			//assertEquals(a("1.5(5)/(19)"), c.addConstants(a("1.5"), a("(5)/(19)")));
		}
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.CombineLikeTerms#listCombineableTerms(com.github.nateowami.solve4x.solver.Expression)}.
	 */
	@Test
	public void testListCombineableTerms() {
		ArrayList<AlgebraicParticle> a1 = new ArrayList<AlgebraicParticle>(Arrays.asList(AlgebraicParticle.getInstance("2x²")));
		ArrayList<AlgebraicParticle> a2 = new ArrayList<AlgebraicParticle>(Arrays.asList(AlgebraicParticle.getInstance("+4x"), AlgebraicParticle.getInstance("-2.3x")));
		ArrayList<AlgebraicParticle> a3 = new ArrayList<AlgebraicParticle>(Arrays.asList(AlgebraicParticle.getInstance("+12")));
		ArrayList<ArrayList<AlgebraicParticle>> one = new ArrayList(Arrays.asList(a1, a2, a3)), two = c.listCombineableTerms((Expression) a("2x²+4x-2.3x+12"));
		
		//assertEquals(new ArrayList(Arrays.asList(a1, a2, a3)).toString().length(), e1.combinableTerms().toString().length());
		assertEquals(one, two);
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.CombineLikeTerms#areCombinableTerms(com.github.nateowami.solve4x.solver.AlgebraicParticle, com.github.nateowami.solve4x.solver.AlgebraicParticle)}.
	 */
	@SuppressWarnings("static-access")
	@Test
	public void testAreCombinableTerms() {
		assertTrue(c.areCombinableTerms(a("xyz"), a("xyz")));
		assertTrue(c.areCombinableTerms(a("2y"), a("76.82y")));
		assertTrue(c.areCombinableTerms(a("4yz"), a("yz")));
		assertTrue(c.areCombinableTerms(a("(17)/(5)y(4+2)"), a("(1)/(5)y(4+2)")));
		assertTrue(c.areCombinableTerms(a("-48r"), a("-r")));
		assertTrue(c.areCombinableTerms(a("2.5(xy+6)"), a("((2)/(3))(xy+6)")));
		
		//check variables
		assertFalse(c.areCombinableTerms(a("a"), a("a²")));
		assertFalse(c.areCombinableTerms(a("x³"), a("x")));
		assertFalse(c.areCombinableTerms(a("x"), a("a")));
		
		//check constants such as numbers, mixed numbers, and constant fractions
		assertTrue(c.areCombinableTerms(a("4"), a("17.3")));
		assertTrue(c.areCombinableTerms(a("(4)/(7)"), a("(5)/(7)")));
		assertFalse(c.areCombinableTerms(a("4(2)/(3)"), a("1(7)/(16)")));
		assertTrue(c.areCombinableTerms(a("2(4)/(5)"), a("17.3")));
		assertFalse(c.areCombinableTerms(a("(4)/(x)"), a("12")));
		assertFalse(c.areCombinableTerms(a("23.2"), a("(x+4)/(16)")));
		
		assertTrue(c.areCombinableTerms(a("4x"), a("17.3x")));
		assertTrue(c.areCombinableTerms(a("2y(3x-6)((4)/(5))"), a("9.2y(3x-6)((4)/(5))")));
		assertFalse(c.areCombinableTerms(a("2y(3-6)((4)/(5))"), a("9.2y(3x-6)((4)/(5))")));
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.CombineLikeTerms#areCombinable(com.github.nateowami.solve4x.solver.AlgebraicParticle, com.github.nateowami.solve4x.solver.AlgebraicParticle)}.
	 */
	@Test
	public void testAreCombinable() {
		//create an array of strings that we can later "interpret". this is like a 3-tuple, delineated by spaces
		//t = true, f = false
		String[] s = new String[]{
				//numbers
				"2 2 t",
				"2.68 -3 t",
				//fractions
				"(1)/(3) (2)/(3) t",
				"(1)/(8) (1)/(16) f",
				"(1)/(4) (3)/(4) t",
				"(1)/(4) -(3)/(4) t",
				//mixednumbers
				"2(1)/(4) 3(5)/(4) t",
				"-5(1)/(4) -3(3)/(4) t",
				"-2(1)/(4) 3(5)/(4) t",
				"2(1)/(4) -3(5)/(4) t",
				"1(1)/(-2) 1(1)/(-2) f",
				"2(5)/(9) -3(2)/(9) f",
				//number-fraction
				"1.5 (5)/(19) t",
				"2.6 (4)/(3) t",
				"-1 (1)/(4) f",
				//number-mixednumber
				"-5 6(4)/(15) t",
				"-5 3(4)/(15) f",
				//fraction-number
				"(1)/(2) -16 f",
				"(1)/(2) 16 t",
				"-(1)/(2) -16 t",
				//fraction-mixednumber
				"(1)/(4) 2(17)/(4) t",
				"(1)/(-4) 2(1)/(-4) f",
				//mixednumber-number
				"-1(3)/(4) 5 f",
				"2(5)/(18) 4(6)/(18) t",
				"-2(5)/(18) 4(6)/(18) t",
				//mixednumber-fraction
				"-3(2)/(9) (5)/(9) f",
				"-3(2)/(9) -(5)/(9) t",
				"-2(5)/(9) -3(2)/(9) t",
				"-2(5)/(9) 3(5)/(9) t",
				};
		for(String tuple : s){
			String[] p = tuple.split("\\s");
			AlgebraicParticle a = AlgebraicParticle.getInstance(p[0]), 
					b = AlgebraicParticle.getInstance(p[1]);
			boolean areCombineable = p[2].charAt(0) == 't';
			assertEquals("The following two should " + (areCombineable ? "" : "not ") + "be combineable: " + a.getAsString() + " and " + b.getAsString(), 
					areCombineable, c.areCombinable(a, b));
		}
	}
	
	/**
	 * Constructs a new AlgebraicParticle from s.
	 */
	private AlgebraicParticle a(String s){
		return AlgebraicParticle.getInstance(s);
	}
	
}
