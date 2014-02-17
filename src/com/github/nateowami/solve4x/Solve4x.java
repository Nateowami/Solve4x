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
	
	//remove a class from this list if you want to be able to see console/debug output from it
	private static final String blackList[] = {"Validator"};
	
	/**
	 * Starts the program rolling by starting the GUI
	 */
	Solve4x(){
		//launch the GUI
		new GUI();
	}
	
	/**
	 * Prints a debug message in the form of [Solve4x] MESSAGE - LINE: LINENUMBER
	 * Won't print the message if it's from a class that is on a black list. Black lists
	 * are to make the console output more readable.
	 * @param msg The debug message to print
	 */
	public static void debug(Object msg) {
		if (DEBUG){
			String className = Thread.currentThread().getStackTrace()[2].getClassName();
			//remove the package stuff at the beginning
			className = className.substring(className.lastIndexOf('.') + 1, className.length());
			//if the class is not on the blacklist
			if(!isOnBlackList(className)){
				//get the method name
				String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
				//and print it out
				System.out.println("["+ className + "." + methodName + "]" +msg+" - LINE: "+Thread.currentThread().getStackTrace()[2].getLineNumber());
			}
		}
	}
	
	/**
	 * Tells if a particular class is on the blacklist.
	 * @param str The class to check (in string form)
	 * @return If the class is on the blacklist
	 */
	private static boolean isOnBlackList(String str){
		//loop through the blacklist
		for(int i = 0; i<blackList.length; i++){
			if(blackList[i].equals(str)){
				return true;
			}
		}
		return false;
	}
}
