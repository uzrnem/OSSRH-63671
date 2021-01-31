# validator
Add Dependency to your maven project
https://search.maven.org/artifact/com.github.uzrnem/verify/1.0.2/jar
```
<dependency>
  <groupId>com.github.uzrnem</groupId>
  <artifactId>verify</artifactId>
  <version>0.0.2</version>
</dependency>
```

Before starting please go through Test cases provided.

You can create your Validator<T> with POJO
```
class Model {

  private String firstName;
  private String lastName;
  private Integer age;
  private Integer Salary;

  //Assume all getter setter methods here

  public static Validator<Model> validator = Validator.stream(Model.class)
    .add(Model::getFirstName, Validator.REQUIRED | Validator.UPPERCASE, "First name is required and to be uppercase")
    .add(Model::getLastName, Validator.LOWERCASE, "Last name required to be lowercase")
    .min(Model::getFirstName, 5, "First name is invalid")
    .max(Model::getLastName, 10, "Last name is invalid")
    .max(Model::getAge, 55, "Employee age limit is reached")
    .min(Model::getSalary, 10000, "Salary can't be less than 10000")
    .in(Model::getLastName, Arrays.asList("Larry", "Moe", "curly"), "Last name is not expected")
    .notIn(Model::getFirstName, Arrays.asList("Thonas", "Loki"), "You are villan")
    .check(Model::getLastName, l -> l.startsWith("c"), "Last name is not C.lassy");
}
```
#Lets talk about functions by Validator
1. add(): you can add here checks(ALPHABETIC, ALPHANUMERIC, NUMERIC, UPPERCASE, LOWERCASE, REQUIRED, EMAIL) with `or` operator
2. min(): you can check min for String and Integer values
3. max(): you can check max for String and Integer values
4. in(): you need to provide list and check given value is provided in list.
4. notIn(): you need to provide list and check given value is provided not in list.
5. check(): you can provide your own Predicate function and say validate using this.
```
Model m = new Model();
m.setFirstName("Greta");
m.setLastName("Thunderburg");
m.setAge(60);
m.setSalary(9000);

Model.validator.test(m) //Returns True or False

Model.validator.getErrors() //Returns Errors List
```

Use Check class for other validations
```
String empty = "";
String lowerCase = "lowercase";
String upperCase = "UPPERCASE";
String mixedCase = "Mixed";

assertTrue(Check.isNull(null));
assertTrue(Check.isEmpty(empty));
assertTrue(Check.isUppercase(upperCase));
assertTrue(Check.isLowercase(lowerCase));
assertTrue(Check.isLengthLessThan(mixedCase, 6));
assertTrue(Check.isLengthGreaterThan(mixedCase, 4));
assertTrue(Check.isLessThan(5, 6));
assertTrue(Check.isGreaterThan(5, 4));

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
```
