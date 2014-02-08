/*
    Solve4x - An audio-visual algebra solver
    Copyright (C) 2014 Nathaniel Paulus

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

import com.github.nateowami.solve4x.ui.GUI;
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
		//launch the GUI
		new GUI();
	}
	
	/**
	 * Prints a debug message in the form of [Solve4x] MESSAGE - LINE: LINENUMBER
	 * @param msg The debug message to print
	 */
	public static void debug(Object msg) {
		if (DEBUG){
			String className = Thread.currentThread().getStackTrace()[2].getClassName();
			//remove the package stuff at the beginning
			className = className.substring(className.lastIndexOf('.') + 1, className.length());
			String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
			System.out.println("["+ className + "." + methodName + "]" +msg+" - LINE: "+Thread.currentThread().getStackTrace()[2].getLineNumber());
		}
	}
}
