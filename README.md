# validator
Add Dependency to your maven project
https://search.maven.org/artifact/com.github.uzrnem/verify/1.0.2/jar
```
<dependency>
  <groupId>com.github.uzrnem</groupId>
  <artifactId>verify</artifactId>
  <version>1.0.0</version>
</dependency>
```

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
    .in(Model::getLastName, Arrays.asList("Larry", "Moe", "curly"), "Last name is not expected");
}


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
