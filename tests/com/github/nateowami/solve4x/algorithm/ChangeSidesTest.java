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
public class ChangeSidesTest {

	ChangeSides c = new ChangeSides();
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.ChangeSides#execute(com.github.nateowami.solve4x.solver.Equation)}.
	 */
	@Test
	public void testExecute() {
		Step s = c.execute(new Equation("x+4=16"));
		assertEquals(new Equation("x=16-4"), s.getChange());
		
		assertEquals(new Equation("x=41-18.2"), c.execute(new Equation("x+18.2=41")).getChange());
		assertEquals(new Equation("x=41+18.2"), c.execute(new Equation("x-18.2=41")).getChange());
		assertEquals(new Equation("-18+41=x"), c.execute(new Equation("-18=x-41")).getChange());
		assertEquals(new Equation("2x²-5x+2x-14+x²=73"), c.execute(new Equation("2x²-5x+2x-14=73-x²")).getChange());
		assertEquals(new Equation("2x²-5x+2x+x²=73+14"), c.execute(new Equation("2x²-5x+2x-14+x²=73")).getChange());
		assertEquals(new Equation("x=14-4"), c.execute(new Equation("x+4=14")).getChange());
		assertEquals(new Equation("0=14-4"), c.execute(new Equation("4=14")).getChange());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.ChangeSides#smarts(com.github.nateowami.solve4x.solver.Equation)}.
	 */
	@Test
	public void testSmarts() {
		assertEquals(5, c.smarts(new Equation("x+4=14")));
		assertEquals(5, c.smarts(new Equation("2x+6=4")));
		assertEquals(0, c.smarts(new Equation("2x=4")));
		assertEquals(7, c.smarts(new Equation("2x+6-86=4")));
		assertEquals(0, c.smarts(new Equation("2x+27y(4+3)=4")));
		assertEquals(5, c.smarts(new Equation("x-6=4")));
		assertEquals(9, c.smarts(new Equation("2x+6-2(3)/(9)+48=4")));
		assertEquals(0, c.smarts(new Equation("2*2=x")));
	}

}
