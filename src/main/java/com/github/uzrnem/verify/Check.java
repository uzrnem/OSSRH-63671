/*
 * MIT License
 *
 * Copyright (c) 2021 Bhagyesh Sunil Patel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package com.github.uzrnem.verify;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author Bhagyesh Sunil Patel
 * @version 1.8.0
 */
public class Check {

	public static Boolean isNull(Object value) {
		return value == null;
	}

	public static Boolean isEmpty(String value) {
		return value == null || value == "";
	}

	public static Boolean isUppercase(String value) {
		return Check.isEmpty((String) value) || ((String) value).toUpperCase() == ((String) value);
	}

	public static Boolean isLowercase(String value) {
		return Check.isEmpty((String) value) || ((String) value).toLowerCase() == ((String) value);
	}

	public static Boolean isLengthLessThan(String value, Integer limit) {
		return Check.isEmpty((String) value) || value.length() < limit;
	}

	public static Boolean isLessThan(Integer value, Integer limit) {
		return Check.isNull(value) || value < limit;
	}

	public static Boolean isLengthGreaterThan(String value, Integer limit) {
		return Check.isEmpty((String) value) || value.length() > limit;
	}

	public static Boolean isGreaterThan(Integer value, Integer limit) {
		return Check.isNull(value) || value > limit;
	}

	/*
	 * Below regex are taken from geeks for geeks
	 * https://www.geeksforgeeks.org/
	 */
	public static Boolean isEmail(String email) {
    	if (Check.isEmpty((String) email)) {
    		return true;
    	}
    	String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+"[a-zA-Z0-9_+&*-]+)*@" +
    			"(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    	Pattern pat = Pattern.compile(emailRegex);
    	return pat.matcher(email).matches();
    }

	public static Boolean isAlphabetic(String value) {
    	if (Check.isEmpty((String) value)) {
    		return true;
    	}
        String regex = "^[a-zA-Z]*$";
    	Pattern pat = Pattern.compile(regex);
    	return pat.matcher(value).matches();
    }

	public static Boolean isAlphaNumeric(String value) {
    	if (Check.isEmpty((String) value)) {
    		return true;
    	}
        String regex = "^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9]+$";
    	Pattern pat = Pattern.compile(regex);
    	return pat.matcher(value).matches();
    }

	public static Boolean isNumeric(String value) {
    	if (Check.isEmpty((String) value)) {
    		return true;
    	}
        String regex = "[+-]?[0-9]+(\\\\.[0-9]+)?([Ee][+-]?[0-9]+)?";
    	Pattern pat = Pattern.compile(regex);
    	return pat.matcher(value).matches();
    }
/*
	public static final int ALPHA = 0x00000001;
	public static final int ALPHANUMERIC = 0x00000002;
	public static final int NUMERIC = 0x00000004;
	public static final int EMAIL = 0x00000100;
 */
}
