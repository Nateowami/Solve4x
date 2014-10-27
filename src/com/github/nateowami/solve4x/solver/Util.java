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
package com.github.nateowami.solve4x.solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.github.nateowami.solve4x.Solve4x;

/**
 * Provides static utility methods for use with String equations. Many local methods in other
 * classes may be moved here if they need to have central use (especially methods in Validator.java)
 * @author Nateowami
 */
public class Util {
	
	//Unicode superscripts aren't all contiguous, so it's best to just list them
	//we'll do regular ints and subscripts too for the sake of completeness
	public static char[] superscriptInts = "⁰¹²³⁴⁵⁶⁷⁸⁹".toCharArray();
	public static char[] charInts = "0123456789".toCharArray();
	public static char[] subscriptInts = "₀₁₂₃₄₅₆₇₈₉".toCharArray();
	
	/**
	 * Evaluates a char to see if it is a numeral
	 * @param c The char to evaluate
	 * @return If the char is a numeral
	 */
	public static boolean isNumeral(char c){
		switch (c){
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				return true;
			default: return false;
		}
	}

	/**
	 * Tells if a string can be parsed as an integer. Examples:
	 * 123
	 * -234
	 * Returns false if integer is an empty string
	 * @param n The string to check 
	 * @return If the string can be parsed as an integer
	 */
	public static boolean isInteger(String n) {
		if(n.length() < 1) return false;
		else if(n.length() == 1 && n.charAt(0) == '0') return true;
		else if(n.charAt(0) == '-') n = n.substring(1);
		if(n.charAt(0) != '0'){
			for(char c : n.toCharArray()){
				if(!isNumeral(c)) return false;
			}
			return true;
		}
		else return false;
	}
	
	/**
	 * Checks to see if the entire expression is surrounded by parentheses,
	 * in which case they will be removed for easier parsing
	 */
	public static String removePar(String expr){
		String arg = expr;
		//debugging
		Solve4x.debug("removePar()" + expr);
		System.out.println(Thread.currentThread().getStackTrace()[2]);
		
		//check to see if it starts and ends with parentheses
		if((expr.length() >= 1) && expr.charAt(0) == '(' && expr.charAt(expr.length()-1) == ')'){

			int parDepth = 1; //how deep we are into parentheses nesting
			boolean parDepthReached0 = false;//if parDepth ever reaches 0 we're outside all parentheses
			//check to see if the entire expr is enclosed with parentheses
			for (int i = 1; i < expr.length() - 1; i++){
				if(expr.charAt(i) == '('){
					parDepth++;
				}
				else if(expr.charAt(i) == ')'){
					parDepth--;
				}
				if (parDepth == 0){
					parDepthReached0 = true;
				}
			}

			if(!parDepthReached0){
				//debugging
				Solve4x.debug("removePar(" + arg + ") -> " + expr.substring(1, expr.length()-1));
				return expr.substring(1, expr.length()-1);
			}
			else{
				//debugging
				Solve4x.debug("removePar(" + arg + ") -> " + expr);
				return expr;
			}
		}
		else{
			//debugging
			Solve4x.debug("removePar(" + arg + ") -> " + expr);
			return expr;
		}
	}
	
	/**
	 * Evaluates a char to see if it is between a-z or A-Z
	 * @param c The char to evaluate
	 * @return If the char is a a-z or A-Z
	 */
	public static boolean areLetters(String s){
		for(char c : s.toCharArray()){
			if(!(c <= 'z' && c >= 'a' || c <= 'Z' && c >= 'A')){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Tells if a number, fraction, or mixed number is fully simplified. 
	 * If it's a complicated fraction and it's unsure it will return false
	 * @param expr The number/fraction/mixed number to evaluate
	 * @return If expr is fully simplified
	 * TODO this probably needs review badly
	 */
	public static boolean isFullySimplified(String expr) {
		//first check that we're dealing with a number/mixed number/fraction
		if(Number.parseable(expr)){
			//it's a number; make sure it's simplified
			
			//the index of the first non-numeral
			int i =0;
			//update i if there's a - sign
			if(expr.charAt(0) == '-'){
				i++;
			}
			//tell if we actually found a non-numeral
			boolean found = false;
			for(; i<expr.length(); i++){
				//if it's not a numeral
				if(!isNumeral(expr.charAt(i))){
					//we found a non-numeral
					found = true;
					break;
				}
			}
			//if we didn't find a non-numeral it's just a number
			if(!found){
				return true;
			}
			//we did find a non-numeral
			else{
				//check if the fraction is simplified
				return isFracSimplified(expr.substring(i, expr.length()));
			}
		}
		//it's not even a number; return false		
		return false;
	}

	/**
	 * Tells if a fraction is fully simplified. If it contains variables it may return false
	 * even when it's fully simplified
	 * @param frac The fraction to check
	 * @return If it's fully simplified (may be hard to tell; see above)
	 */
	private static boolean isFracSimplified(String frac) {
		//TODO debug
		//first find the /
		int i = 0;
		for(; i < frac.length(); i++){
			if(frac.charAt(i) == '/'){
				break;
			}
		}
		//i is now the index of the /
		//if they have common factors
		if(GCF(Integer.parseInt(frac.substring(0, i)), Integer.parseInt(frac.substring(i+1, frac.length()))) > 1){
			return false;
		}
		else return true;
	}	
	
	/**
	 * TODO completely broken if I remember
	 * @param a The first number
	 * @param b The second number
	 * @return The GCF (greatest common factor) of the two numbers
	 */
	public static int GCF(int a, int b){
		
		int commonFactors[] = commonFactors(a,b);
		//the final answer
		int answer = 1;
		//now multiply all the common factors together
		for(int i = 0; i < commonFactors.length; i++){
			answer *= commonFactors[i];
		}
		return answer;
	}
	
	/**
	 * Finds common prime factors of two numbers
	 * @param a The first number
	 * @param b The second number
	 * @return And int[] array of common factors
	 */
	public static int[] commonFactors(int a, int b){
		
		//XXX on debug check this (4 lines). don't know if it really works
		//perform intersection logic on two sets
		//FIXME this won't work because sets don't allow duplicates
		Set<Integer> s1 = new HashSet<Integer>(Arrays.asList(a));
		Set<Integer> s2 = new HashSet<Integer>(Arrays.asList(b));
		s1.retainAll(s2);
		Integer[] intersection = s1.toArray(new Integer[s1.size()]);
		
		//convert Integer[] to int[]
		int commonFactors[] = new int[intersection.length];
		for(int i = 0; i < intersection.length; i++){
			commonFactors[i] = intersection[i].intValue();
		}
		return commonFactors;		
	}
	
	/**
	 * Returns the factors of a given number
	 * @param num The number  for which you want the factors
	 * @return An array of factors for the number
	 */
	public static int[] factors(int num){
		//loop through from high to low
		for(int i = num-1; i > 1; i--){
			if(num % i == 0){
				//factors must be i and num/i (try it)
				//return the factors of the two factors we just found
				return concat(factors(i), factors(num/i));
			}
		}
		//if we get here there are no factors
		int ans[] = {};
		return ans;
	}
	
	/**
	 * Concatenates two arrays
	 * @param a The first array
	 * @param b The second array
	 * @return The two arrays concatenated
	 */
	private static int[] concat(int a[], int b[]){
		int out[] = new int[a.length + b.length];
		int i; //we need to keep track of i outside the loop
		//Iterate through the first one and add the elements to out[]
		for(i = 0; i < a.length; i++){
			out[i] = a [i];
		}
		//and the second array
		//we have to keep track of two vars, the place we are in out and the place in b
		for(int j = 0; j<b.length; j++){
			i++;//keeping track of i still so we know where to add things to out
			out[i] = b[j];
		}
		return out;
	}

	/**
	 * Splits string s by any chars c for every occurrence of chars c that are not nested in parentheses.
	 * The characters at which s is split will be included at the beginning of the respective elements.
	 * Occurrences of chars c at the beginning of s will be ignored.
	 * Examples:
	 * splitByNonNestedChars("xy+6-4", '+', '-') returns ["xy", "+6", "-4"]
	 * splitByNonNestedChars("(4x)/(6y(2+x))", '/') returns ["(4x)", "/(6y(2+x))"]
	 * @param s The string to split.
	 * @param c The chars by which to split s.
	 * @return A string array of s split by occurrences of chars c not nested in parentheses.
	 */
	public static String[] splitByNonNestedChars(String s, char... c){
		//get setup
		int parDepth = 0;
		String chars = new String(c);
		ArrayList<String> parts = new ArrayList<String>();
		//search for matches
		for(int i = 0; i < s.length(); i++){
			if(s.charAt(i) == '(') parDepth++;
			else if(s.charAt(i) == ')') parDepth--;
			//if it's a char we split at, and it's not the first one
			else if(parDepth == 0 && i != 0 && chars.indexOf(s.charAt(i)) != -1){
				parts.add(s.substring(0, i));
				s = s.substring(i);
				i = 0;
			}
		}
		parts.add(s); // add the rest of the string
		return parts.toArray(new String[parts.size()]);
	}
	
	/**
	 * Tells if a specified char c is a superscript. 
	 * @param c The char to check.
	 * @return If c is a superscript.
	 */
	public static boolean isSuperscript(char c){
		return new String(superscriptInts).indexOf(c) != -1;
	}
	
	/**
	 * Tells if every char in s is a superscript.
	 * @param s The string to check.
	 * @return If every char in s is a valid superscript char.
	 */
	public static boolean isSuperscript(String s){
		for(char c : s.toCharArray()){
			if(!isSuperscript(c)) return false;
		}
		return true;
	}
	
	/**
	 * Converts a string of superscript chars to an int.
	 * @param s The string to parse.
	 * @return The int value of the superscript chars.
	 */
	public static int superscriptToInt(String s){
		String answer = "";
		for (char c : s.toCharArray()){
			answer += Character.getNumericValue(c);
		}
		return Integer.parseInt(answer);
	}
	
	/**
	 * Converts a given integer n to Unicode superscript.
	 * @param n The integer to convert.
	 * @return The superscript form of integer n.
	 */
	public static String intToSuperscript(int n){
		char[] s = Integer.toString(n).toCharArray();
		for(int i = 0; i < s.length; i++){
			s[i] = superscriptInts[Integer.parseInt(Character.toString(s[i]))];
		}
		return new String(s);
	}

	/**
	 * Tells if a specified char c is a subscript. 
	 * @param c The char to check.
	 * @return If c is a subscript.
	 */
	public static boolean isSubscript(char c){
		return c >= '\u2080' && c <= '\u2089';
	}
	
	/**
	 * Tells if every char in s is a subscript.
	 * @param s The string to check.
	 * @return If every char in s is a valid subscript char.
	 */
	public static boolean isSubscript(String s){
		for(char c : s.toCharArray()){
			if(!isSubscript(c)) return false;
		}
		return true;
	}
	
	/**
	 * Converts a string of subscript chars to an int.
	 * @param s The string to parse.
	 * @return The int value of the subscript chars.
	 */
	public static int subscriptToInt(String s){
		String answer = "";
		for (char c : s.toCharArray()){
			answer += (char)(c - 8272);
		}
		return Integer.parseInt(answer);
	}
	
	/**
	 * Converts an int to a superscript.
	 * @param n The int to convert.
	 * @return n, converted to a string and then to superscript.
	 */
	public static String intToSubscript(int n){
		String answer = "";
		for(char c : (n + "").toCharArray()){
			answer += (char)(c + 8272);
		}
		return answer;
	}
	
}
