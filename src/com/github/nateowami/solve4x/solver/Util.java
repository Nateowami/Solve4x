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
	public static String superscriptInts = "⁰¹²³⁴⁵⁶⁷⁸⁹";
	public static String charInts = "0123456789";
	public static String subscriptInts = "₀₁₂₃₄₅₆₇₈₉";
	
	/**
	 * Tells if a string contains only numerals.
	 * Examples:
	 * areAllNumerals("234") returns true.
	 * areAllNumerals("05") returns true.
	 * areAllNumerals("-6") returns false.
	 * Returns false if n is an empty string.
	 * @param n The string to check 
	 * @return True if n is not an empty string and contains only numerals, otherwise false.
	 */
	public static boolean areAllNumerals(String n) {
		if(n.length() < 1) return false;
		for(char c : n.toCharArray()){
			if(c < '0' || c > '9')return false;
		}
		return true;
	}
	
	/**
	 * Checks to see if the entire expression is surrounded by parentheses,
	 * in which case they will be removed for easier parsing
	 */
	public static String removePar(String expr){
		String arg = expr;
		
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
				return expr.substring(1, expr.length()-1);
			}
			else{
				return expr;
			}
		}
		else{
			return expr;
		}
	}
	
	/**
	 * Evaluates a string to see if all chars are alphabetic (a-z or A-Z).
	 * @param s The string to evaluate
	 * @return If the s contains only alphabetic characters.
	 */
	public static boolean areAllLetters(String s){
		for(char c : s.toCharArray()){
			if(!(c <= 'z' && c >= 'a' || c <= 'Z' && c >= 'A')){
				return false;
			}
		}
		return true;
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
	 * Tells if every char in s is a superscript. If the string is empty, false is returned.
	 * @param s The string to check.
	 * @return If every char in s is a valid superscript char.
	 */
	public static boolean isSuperscript(String s){
		if(s.isEmpty())return false;
		for(char c : s.toCharArray()){
			if(superscriptInts.indexOf(c) == -1) return false;
		}
		return true;
	}
	
	/**
	 * Converts a string of superscript chars to an int.
	 * @param s The string to parse.
	 * @return The int value of the superscript chars.
	 */
	public static int superscriptToInt(String s){
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++){
			chars[i] = charInts.charAt(superscriptInts.indexOf(chars[i]));
		}
		return Integer.parseInt(new String(chars));
	}
	
	/**
	 * Converts a given integer (in string form) to Unicode superscript.
	 * @param s The string to convert.
	 * @return The superscript form of s.
	 */
	public static String toSuperscript(String s){
		char[] chars = s.toCharArray();
		for(int i = 0; i < chars.length; i++){
			chars[i] = superscriptInts.charAt(charInts.indexOf(chars[i]));
		}
		return new String(chars);
	}
	
	/**
	 * Tells if every char in s is a subscript. If the string is empty, false is returned.
	 * @param s The string to check.
	 * @return If every char in s is a valid subscript char.
	 */
	public static boolean isSubscript(String s){
		if(s.isEmpty())return false;
		for(char c : s.toCharArray()){
			if(subscriptInts.indexOf(c) == -1) return false;
		}
		return true;
	}
	
	/**
	 * Converts a string of subscript chars to an int.
	 * @param s The string to parse.
	 * @return The int value of the subscript chars.
	 */
	public static int subscriptToInt(String s){
		char[] chars = s.toCharArray();
		for(int i = 0; i < chars.length; i++){
			chars[i] = charInts.charAt(subscriptInts.indexOf(chars[i]));
		}
		return Integer.parseInt(new String(chars));
	}
	
	/**
	 * Converts an integer to a superscript.
	 * @param s The integer (in string form) to convert.
	 * @return s, converted to superscript.
	 */
	public static String toSubscript(String s){
		char[] chars = s.toCharArray();
		for(int i = 0; i < chars.length; i++){
			chars[i] = subscriptInts.charAt(charInts.indexOf(chars[i])); 
		}
		return new String(chars);
	}
	
	/**
	 * Tells if an AlgebraicParticle is constant (i.e., Number, MixedNumber that is constant, 
	 * or Fraction that is constant, and it has an exponent of 1).
	 * @param a The AlgebraicParticle to check.
	 * @return If a is constant.
	 */
	public static boolean constant(AlgebraicParticle a){
		return a.exponent() == 1 && (a instanceof Number || a instanceof Fraction && ((Fraction)a).constant()
				|| a instanceof MixedNumber && ((MixedNumber)a).getFraction().constant());
	}
	
	/**
	 * Calculates prime factors for a given number, from smallest to largest.
	 * @param num The number to calculate prime factors for.
	 * @return All prime factors of num. 
	 */
	public static Long[] primeFactors(long num) {
		ArrayList<Long> primes = new ArrayList<Long>(1);
		for(long i = 2; i*i <= num;) {
			if(num % i == 0) {
				primes.add((Long)i);
				num /= i;
			}
			else i++;
		}
		primes.add(num);
		return primes.toArray(new Long[primes.size()]);
	}
	
}
