gpg
gpg --full-generate-key
gpg --list-keys
gpg --export --armor KEYSTOBEUPLOADED
gpg -ab verify-1.0.1.jar
gpg -ab verify-1.0.1.pom
gpg -ab Validator.java

mvn -Dtest=com.github.uzrnem.verify.ValidatorTest test
mvn versions:set -DnewVesions 1.0.0
mvn clean deploy
~/Documents/sts-workspace/j-validator/target/nexus-staging/deferred/com/github/uzrnem/verify/1.0.3$
gpg -ab verify-1.0.1.jar
gpg -ab verify-1.0.1.pom

mvn clean deploy -P release
