# Code of your exercise

```xml
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