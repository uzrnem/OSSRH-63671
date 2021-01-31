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

/**
 *
 * @author Bhagyesh Sunil Patel
 * @version 1.8.0
 */
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Validator<T> {
	public static final int ALPHABETIC = 0x00000001;
	public static final int ALPHANUMERIC = 0x00000002;
	public static final int NUMERIC = 0x00000004;
	public static final int UPPERCASE = 0x00000010;
	public static final int LOWERCASE = 0x00000020;
	public static final int REQUIRED = 0x00000040;
	public static final int EMAIL = 0x00000100;
	//public static final int DATE = 0x00000200;
	//public static final int NULLABLE = 0x00000400;

	private static final int TYPE_NORMAL = 1;
	private static final int TYPE_MIN = 2;
	private static final int TYPE_MAX = 3;
	private static final int TYPE_CONTAINS = 4;
	private static final int TYPE_NOT_CONTAINS = 5;
	private static final int TYPE_CUSTOM = 6;
	private List<Validator.Rules> rulesList = null;
	private List<String> errors = null;
	private java.util.function.Consumer<Object> sop = Validator::println; //System.out::println; //

	private Validator<T> initList() {
		this.rulesList = new ArrayList<Validator.Rules>();
		this.errors = new ArrayList<>();
		return this;
	}

	private List<Validator.Rules> getValidateRules() {
		return this.rulesList;
	}

	public List<String> getErrors() {
		return this.errors;
	}

	public static <T> Validator<T> stream(Class<T> classRef) {
		return new Validator<T>().initList();
	}

	@SuppressWarnings("unused")
	private static void println(Object str) {
	}

	public <C> Validator<T> add(Function<T, C> getVal, Integer flags, String error) {
		this.getValidateRules().add(new Rules().withValFunc(getVal).withType(Validator.TYPE_NORMAL).withFlags(flags).withErrorMsg(error));
		return this;
	}

	public <C> Validator<T> min(Function<T, C> getVal, int limit, String error) {
		this.getValidateRules().add(new Rules().withValFunc(getVal).withType(Validator.TYPE_MIN).withLimit(limit).withErrorMsg(error));
		return this;
	}

	public <C> Validator<T> max(Function<T, C> getVal, int limit, String error) {
		this.getValidateRules().add(new Rules().withValFunc(getVal).withType(Validator.TYPE_MAX).withLimit(limit).withErrorMsg(error));
		return this;
	}

	public <C> Validator<T> in(Function<T, C> getVal, List<C> asList, String error) {
		this.getValidateRules().add(new Rules().withValFunc(getVal).withType(Validator.TYPE_CONTAINS).withListIn(asList).withErrorMsg(error));
		return this;
	}

	public <C> Validator<T> notIn(Function<T, C> getVal, List<C> asList, String error) {
		this.getValidateRules().add(new Rules().withValFunc(getVal).withType(Validator.TYPE_NOT_CONTAINS).withListIn(asList).withErrorMsg(error));
		return this;
	}

	public <C> Validator<T> check(Function<T, C> getVal, Predicate<C> isTrue, String error) {
		this.getValidateRules().add(new Rules().withValFunc(getVal).withType(Validator.TYPE_CUSTOM).withCheckFunc(isTrue).withErrorMsg(error));
		return this;
	}

	protected Boolean checkFlag(Integer rules, Integer flag) {
		return (rules & flag) == flag;
	}

	public boolean test(T m) {
		this.getErrors().clear();

		this.getValidateRules().forEach(rules -> {
			Object value = rules.getValFunc().apply(m);
			sop.accept(value);
			switch (rules.getType()) {
			case Validator.TYPE_NORMAL: {
				if (checkFlag(rules.getFlags(), Validator.REQUIRED)) {
					if (value instanceof String) {
						if (Check.isEmpty((String) value)) {
							this.getErrors().add(rules.getErrorMsg());
						}
					} else {
						if (Check.isNull(value)) {
							this.getErrors().add(rules.getErrorMsg());
						}
					}
					break;
				}
				if (checkFlag(rules.getFlags(), Validator.UPPERCASE) && !Check.isUppercase((String) value)) {
					this.getErrors().add(rules.getErrorMsg());
				}
				if (checkFlag(rules.getFlags(), Validator.LOWERCASE) && !Check.isLowercase((String) value)) {
					this.getErrors().add(rules.getErrorMsg());
				}
				if (checkFlag(rules.getFlags(), Validator.ALPHABETIC) && !Check.isAlphabetic((String) value)) {
					this.getErrors().add(rules.getErrorMsg());
				}
				if (checkFlag(rules.getFlags(), Validator.ALPHANUMERIC) && !Check.isAlphaNumeric((String) value)) {
					this.getErrors().add(rules.getErrorMsg());
				}
				if (checkFlag(rules.getFlags(), Validator.NUMERIC) && !Check.isNumeric((String) value)) {
					this.getErrors().add(rules.getErrorMsg());
				}
				if (checkFlag(rules.getFlags(), Validator.EMAIL) && !Check.isEmail((String) value)) {
					this.getErrors().add(rules.getErrorMsg());
				}
				break;
			}
			case Validator.TYPE_MIN: {
				if (value instanceof String
					&& Check.isLengthLessThan((String) value, rules.getLimit()) ) {
					this.getErrors().add(rules.getErrorMsg());
				}
				if (value instanceof Integer
					&& Check.isLessThan((Integer) value, rules.getLimit()) ) {
					this.getErrors().add(rules.getErrorMsg());
				}
				break;
			}
			case Validator.TYPE_MAX: {
				if (value instanceof String
					&& Check.isLengthGreaterThan((String) value, rules.getLimit()) ) {
					this.getErrors().add(rules.getErrorMsg());
				}
				if (value instanceof Integer
					&& Check.isGreaterThan((Integer) value, rules.getLimit()) ) {
					this.getErrors().add(rules.getErrorMsg());
				}
				break;
			}
			case Validator.TYPE_CONTAINS: {
				if (!rules.getListIn().contains(value)) {
					this.getErrors().add(rules.getErrorMsg());
				}
				break;
			}
			case Validator.TYPE_NOT_CONTAINS: {
				if (rules.getListIn().contains(value)) {
					this.getErrors().add(rules.getErrorMsg());
				}
				break;
			}
			case Validator.TYPE_CUSTOM: {
				if (!rules.getCheckFunc().test(value)) {
					this.getErrors().add(rules.getErrorMsg());
				}
				break;
			}
			default:
				break;
			}
		});
		return errors.size() == 0;
	}

	static class Rules {
		private Function valFunc; //get value from object
		private Integer flags; //If type is normal then we have flags, for uppercase lowercase alpha numeric email
		private Integer type; //TYPE_NORMAL MIN MAX CONTAINS CUSTOM 
		private Integer limit; //provide lower or higher limit for Integer and String
		private List listIn; //if given value must be provided in list
		private String errorMsg; //simple error message
		private Predicate checkFunc; //get custom function to validate

		public Function getValFunc() {
			return valFunc;
		}

		public Rules withValFunc(Function valFunc) {
			this.valFunc = valFunc;
			return this;
		}

		public Integer getFlags() {
			return flags;
		}

		public Rules withFlags(Integer flags) {
			this.flags = flags;
			return this;
		}

		public Integer getType() {
			return type;
		}

		public Rules withType(Integer typeNormal) {
			this.type = typeNormal;
			return this;
		}

		public List getListIn() {
			return listIn;
		}

		public Rules withListIn(List listIn) {
			this.listIn = listIn;
			return this;
		}

		public Integer getLimit() {
			return limit;
		}

		public Rules withLimit(Integer limit) {
			this.limit = limit;
			return this;
		}

		public String getErrorMsg() {
			return errorMsg;
		}

		public Rules withErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
			return this;
		}

		public Predicate getCheckFunc() {
			return checkFunc;
		}

		public Rules withCheckFunc(Predicate checkFunc) {
			this.checkFunc = checkFunc;
			return this;
		}
	}
}
