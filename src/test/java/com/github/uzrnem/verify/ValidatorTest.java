package com.github.uzrnem.verify;

import java.util.Arrays;
import java.util.function.Function;

import junit.framework.TestCase;

public class ValidatorTest extends TestCase {

	public void test() {

		Model m = new Model();
		m.setFirstName("Loki");
		m.setLastName("Thunderburg");
		m.setAge(60);
		m.setSalary(9000);
		assertFalse(Model.validator.test(m));
		
		Model.validator.getErrors().stream().map(Function.identity()).forEach(System.out::println);

		assertTrue(Model.validator.getErrors().size() > 0);

		m.setFirstName("GRETA");
		m.setLastName("curly");
		m.setAge(26);
		m.setSalary(99000);
		assertTrue(Model.validator.test(m));
	}
	static class Model {

		private String firstName;
		private String lastName;
		private Integer age;
		private Integer Salary;

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public Integer getAge() {
			return age;
		}

		public void setAge(Integer age) {
			this.age = age;
		}

		public Integer getSalary() {
			return Salary;
		}

		public void setSalary(Integer salary) {
			Salary = salary;
		}

		public static Validator<Model> validator = Validator.stream(Model.class)
				.add(Model::getFirstName, Validator.REQUIRED, "First name is required")
				.add(Model::getFirstName, Validator.UPPERCASE, "First name required to be uppercase")
				.add(Model::getLastName, Validator.LOWERCASE, "Last name required to be lowercase")
				.min(Model::getFirstName, 5, "First name is invalid")
				.max(Model::getLastName, 10, "Last name is invalid")
				.max(Model::getAge, 55, "Employee age limit is reached")
				.min(Model::getSalary, 10000, "Salary can't be less than 10000")
				.in(Model::getLastName, Arrays.asList("Larry", "Moe", "curly"), "Last name is not expected")
				.notIn(Model::getFirstName, Arrays.asList("Thonas", "Loki"), "You are villan")
				.check(Model::getLastName, l -> l.startsWith("c"), "Last name is not C.lassy")
				;
	}
}
