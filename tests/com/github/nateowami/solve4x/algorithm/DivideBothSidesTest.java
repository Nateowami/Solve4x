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

import org.junit.Test;

import com.github.nateowami.solve4x.solver.*;

/**
 * @author Nateowami
 */
public class DivideBothSidesTest {
	
	DivideBothSides d = new DivideBothSides();
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.DivideBothSides#execute(com.github.nateowami.solve4x.solver.Algebra)}.
	 */
	@Test
	public void testExecute() {
		assertEquals(e("x=(4)/(2)"), d.execute(e("2x=4")).getChange());
		assertEquals(e("x=(4y)/(2.6)"), d.execute(e("2.6x=4y")).getChange());
		assertEquals(e("x(x+4)=(4.3)/(4)"), d.execute(e("x(x+4)4=4.3")).getChange());
		assertEquals(e("x(x+4)=((4.3)/(2))/(4)"), d.execute(e("x(x+4)4=(4.3)/(2)")).getChange());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.DivideBothSides#smarts(com.github.nateowami.solve4x.solver.Algebra)}.
	 */
	@Test
	public void testSmarts() {
		assertEquals(7, d.smarts(e("2=4x")));
		assertEquals(7, d.smarts(e("2(3x-16)=(42)/(13)")));
		assertEquals(7, d.smarts(e("(4y-16)3.42*10⁵=4.67")));
		assertEquals(7, d.smarts(e("2=4x")));
		assertEquals(7, d.smarts(e("x(x+4)4=4.3")));
		
		assertEquals(0, d.smarts(e("x+6=5")));
		assertEquals(0, d.smarts(e("5y=19x")));
		assertEquals(0, d.smarts(e("4.2+10=y")));
	}
	
	private Equation e(String s){
		return new Equation(s);
	}
	
}
