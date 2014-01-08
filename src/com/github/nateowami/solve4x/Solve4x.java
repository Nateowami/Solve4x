/*
    Solve4x - A Java program to solve and explain algebra problems
    Copyright (C) 2013 Nathaniel Paulus

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

package com.github.nateowami.solve4x;

import com.github.nateowami.solve4x.av.ui.GUI;
import com.github.nateowami.solve4x.Solve4x;

/**
 * Created as soon as Solve4x runs. Starts the GUI and holds a static debug method.
 */
public class Solve4x {
	
	private static final boolean DEBUG = true;
	
	/**
	 * Starts the program rolling by starting the GUI
	 */
	Solve4x(){
		//TODO Clean GUI code
		new GUI();
	}
	
	/**
	 * Prints a debug message in the form of [Solve4x] MESSAGE - LINE: LINENUMBER
	 * @param msg The debug message to print
	 */
	public static void debug(Object msg) {
		if (DEBUG)
			System.out.println("[Solve4x] "+msg+" - LINE: "+Thread.currentThread().getStackTrace()[2].getLineNumber());
	}
}
