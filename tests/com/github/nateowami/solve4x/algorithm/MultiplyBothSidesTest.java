/*
    Solve4x - An algebra solver that shows its work
    Copyright (C) 2015  Nathaniel Paulus

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
public class MultiplyBothSidesTest {
	
	MultiplyBothSides m = new MultiplyBothSides();
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.MultiplyBothSides#execute(com.github.nateowami.solve4x.solver.Algebra)}.
	 */
	@Test
	public void testExecute() {
		assertEquals(e("4=(2)/(3)*x"), m.execute(e("(4)/(x)=(2)/(3)")).getChange());
		assertEquals(e("2xx+4=(5)/(7)*3y"), m.execute(e("(2xx+4)/(3y)=(5)/(7)")).getChange());
		assertEquals(e("2x(4+3)*xt=2"), m.execute(e("2x(4+3)=(2)/(xt)")).getChange());
		assertEquals(e("4=(2+3)*x"), m.execute(e("(4)/(x)=2+3")).getChange());
		assertEquals(e("4=(2+3)*x³"), m.execute(e("(4)/(x³)=2+3")).getChange());
		assertEquals(e("4=-(2+3)*x"), m.execute(e("((4)/(-x))=2+3")).getChange());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.MultiplyBothSides#smarts(com.github.nateowami.solve4x.solver.Algebra)}.
	 */
	@Test
	public void testSmarts() {
		assertEquals(7, m.smarts(e("(4)/(x)=(2)/(3)")));
		assertEquals(7, m.smarts(e("(2xx+4)/(3y)=(5)/(7)")));
		assertEquals(7, m.smarts(e("2x(4+3)=(2)/(xt)")));
		assertEquals(7, m.smarts(e("(4)/(x)=2+3")));

		assertEquals(0, m.smarts(e("((4)/(x))²=2+3")));
	}
	
	Equation e(String s) {
		return new Equation(s);
	}
	
}
