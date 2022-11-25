# Using PMD

Pick a Java project from Github (see the [instructions](../sujet.md) for suggestions). Run PMD on its source code using any ruleset. Describe below an issue found by PMD that you think should be solved (true positive) and include below the changes you would add to the source code. Describe below an issue found by PMD that is not worth solving (false positive). Explain why you would not solve this issue.

## Answer

I picked the project [Apache Commons CLI](https://github.com/apache/commons-cli) for this exercise as advised in the [sujet](../sujet.md). I took online on the github of PMD in ressources the [ruleset](https://github.com/pmd/pmd/blob/master/pmd-apex/src/main/resources/rulesets/apex/ruleset.xml). Then I was able to run PMD on the project using the following command:

```bash
pmd-bin-6.51.0/bin/run.sh pmd -d commons-cli/src/ -R ruleset.xml
```
### True positive

I got a lot of issues, the one I found the most interesting and is for me a true positive is the following:

```java
/home/thomas/VV/VV-ESIR-TP2/code/Exercise3/commons-cli/src/main/java/org/apache/commons/cli/DefaultParser.java:606:     SimplifyBooleanReturns: Avoid unnecessary if..then..else statements when returning booleans
if (getLongPrefix(token) != null && !token.startsWith("--")) {
    // -LV
return true;
}
return false;
```
This code could easily be simplified by removing the if statement and returning the result of the condition directly. This would make the code more readable and easier to understand. The change I would make to the code would be the following:

```java
return getLongPrefix(token) != null && !token.startsWith("--");
```

### False positive

In the same project, with the same ruleset, I got the following issue:

```java
/home/thomas/VV/VV-ESIR-TP2/code/Exercise3/commons-cli/src/main/java/org/apache/commons/cli/Util.java:23:       UseUtilityClass:        All methods are static.  Consider using a utility class instead. Alternatively, you could add a private constructor or make the class abstract to silence this warning.
/**
 * Contains useful helper methods for classes within this package.
 */
final class Util {
    static String stripLeadingAndTrailingQuotes(String str) {}
    static String stripLeadingHyphens(final String str) {}
}
```

I think this is a false positive because, the class is a utility class so it is normal that all methods are static. It is also normal that the class is not abstract because it is not meant to be extended. I would not change the code because it is not necessary.