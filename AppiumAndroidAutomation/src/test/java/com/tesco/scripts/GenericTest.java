package com.tesco.scripts;

import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@Cucumber.Options(format = { "pretty", "html:target/cucumber-html-report",
		"json-pretty:target/cucumber-report.json" },features="src/test/resources/com/tesco/scripts/")
// this is an empty class to run with. This needs to remain empty
public class GenericTest {
}
