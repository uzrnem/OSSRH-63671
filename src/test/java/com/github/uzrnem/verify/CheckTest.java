package com.github.uzrnem.verify;

import java.util.regex.Pattern;

import junit.framework.TestCase;

public class CheckTest extends TestCase {

	public void test() {

		String empty = "";
		String lowerCase = "lowercase";
		String upperCase = "UPPERCASE";
		String mixedCase = "Mixed";

		assertTrue(Check.isNull(null));
		assertFalse(Check.isNull(lowerCase));

		assertTrue(Check.isEmpty(null));
		assertTrue(Check.isEmpty(empty));
		assertFalse(Check.isEmpty(lowerCase));

		assertTrue(Check.isUppercase(null));
		assertTrue(Check.isUppercase(empty));
		assertFalse(Check.isUppercase(mixedCase));
		assertTrue(Check.isUppercase(upperCase));

		assertTrue(Check.isLowercase(null));
		assertTrue(Check.isLowercase(empty));
		assertFalse(Check.isLowercase(mixedCase));
		assertTrue(Check.isLowercase(lowerCase));

		assertFalse(Check.isLengthLessThan(mixedCase, 4));
		assertFalse(Check.isLengthLessThan(mixedCase, 5));
		assertTrue(Check.isLengthLessThan(mixedCase, 6));

		assertTrue(Check.isLengthGreaterThan(mixedCase, 4));
		assertFalse(Check.isLengthGreaterThan(mixedCase, 5));
		assertFalse(Check.isLengthGreaterThan(mixedCase, 6));

		assertFalse(Check.isLessThan(5, 4));
		assertFalse(Check.isLessThan(5, 5));
		assertTrue(Check.isLessThan(5, 6));

		assertTrue(Check.isGreaterThan(5, 4));
		assertFalse(Check.isGreaterThan(5, 5));
		assertFalse(Check.isGreaterThan(5, 6));

		assertTrue(Check.isEmail("uzrnem@gmail.com"));
		assertFalse(Check.isEmail("uzr..nem@gmail.com"));

		assertTrue(Check.isAlphabetic("bhagyeshsunilpatel"));
		assertFalse(Check.isAlphabetic("bhagyesh sunil patel"));

		assertTrue(Check.isAlphaNumeric("bhagyesh94"));
		assertFalse(Check.isAlphaNumeric("bhagyesh.patel"));

		assertTrue(Check.isNumeric("2e10"));
		assertTrue(Check.isNumeric("2010"));
		assertFalse(Check.isNumeric("10e5.4"));
		assertFalse(Check.isNumeric("2,10"));
	}
}
