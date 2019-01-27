package runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/features",
        glue = "stepdef",
        plugin = {"pretty", "json:build/cucumber-reports/json-report/cucumber.json"})
public class RunCucumberTest {
    @AfterClass
    public static void tearDown() {
        File reportOutputDir = new File("build/cucumber-reports");
        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add("build/cucumber-reports/json-report/cucumber.json");

    }
}

