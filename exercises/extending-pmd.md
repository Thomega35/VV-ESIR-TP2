# Extending PMD

Use XPath to define a new rule for PMD to prevent complex code. The rule should detect the use of three or more nested `if` statements in Java programs so it can detect patterns like the following:

```Java
if (...) {
    ...
    if (...) {
        ...
        if (...) {
            ....
        }
    }

}
```
Notice that the nested `if`s may not be direct children of the outer `if`s. They may be written, for example, inside a `for` loop or any other statement.
Write below the XML definition of your rule.

You can find more information on extending PMD in the following link: https://pmd.github.io/latest/pmd_userdocs_extending_writing_rules_intro.html, as well as help for using `pmd-designer` [here](https://github.com/selabs-ur1/VV-TP2/blob/master/exercises/designer-help.md).

Use your rule with different projects and describe you findings below. See the [instructions](../sujet.md) for suggestions on the projects to use.

## Answer

To detect the nested `if`s we first created a new custom rule and put this rule in a ruleset

```Xml
<?xml version="1.0"?>

<ruleset name="customruleset">

    <description>
        Description
    </description>

    <rule name="MyRule"
        language="java"
        message="If detector"
        class="net.sourceforge.pmd.lang.rule.XPathRule" >

        <description>
            Description
        </description>

        <priority>4</priority>

        <properties>

            <property name="version" value="2.0" />
            
            <property name="xpath">
            <value>
                <![CDATA[
                //IfStatement//IfStatement//IfStatement
                ]]>
            </value>
            </property>

        </properties>

    </rule>

</ruleset>
```

In this rule we had to define its name, language, message and class (net.sourceforge.pmd.lang.rule.XPathRule). Then in a "property" called "xpath". Using the graphic tool we found that the XPath for a triple nested `if` was "//IfStatement//IfStatement//IfStatement".
Finnaly we executed it and found several placies where the pattern was found :

```
/home/kilian/Documents/ESIR/ESIR2/VV/TP2/VV-ESIR-TP2/code/Exercise4/commons-collections/src/main/java/org/apache/commons/collections4/MapUtils.java:229:   MyRule: If detector
```

If we go to that place we find :

```
    public static <K> Boolean getBoolean(final Map<? super K, ?> map, final K key) {
        if (map != null) {
            final Object answer = map.get(key);
            if (answer != null) {
                if (answer instanceof Boolean) {
                    return (Boolean) answer;
                }
                if (answer instanceof String) {
                    return Boolean.valueOf((String) answer);
                }
                if (answer instanceof Number) {
                    final Number n = (Number) answer;
                    return n.intValue() != 0 ? Boolean.TRUE : Boolean.FALSE;
                }
            }
        }
        return null;
    }
```

So it works.