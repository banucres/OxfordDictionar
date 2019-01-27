package stepdef;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.CaseFormat;
import com.google.common.io.Files;
import com.google.common.primitives.Ints;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import models.JsonUtility;
import models.PathParameterConfig;
import services.EndpointService;
import services.RestWebService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static io.restassured.RestAssured.basePath;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.Assert.fail;

public class GeneralStepdef {

    private Scenario scenario;
    private Properties properties;
    private RestWebService restWebService;
    private Response response;
    private EndpointService endpointService;

    public GeneralStepdef(EndpointService endpointService, RestWebService restWebService) {
        this.endpointService = endpointService;
        this.restWebService = restWebService;
    }

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
        endpointService.setWebserviceName(Files.getNameWithoutExtension(scenario.getUri()));
        properties = new Properties();
        try {
            properties.load(new FileInputStream("src/test/resources/requests.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Given("^the base path for API$")
    public void theBasePathForTheGetAPI() throws IOException {
        System.out.println("hi how are you?");
        String env = "Test";
        basePath = endpointService.retrieveEndpoint(env, endpointService.getWebserviceName());
        System.out.println(basePath);
    }


    @And("^send the (.+) header$")
    public void sendTheValidHeader(String headerStatus) {
        Map<String, String> headers = new HashMap<>(0);
        String WebSerrviceName = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, endpointService.getWebserviceName());
        switch (headerStatus) {
            case "Valid":
                headers.put("app_key", properties.getProperty(WebSerrviceName + "." + "valid.apikey"));
                headers.put("app_id", properties.getProperty(WebSerrviceName + "." + "valid.appid"));
                headers.put("Content-Type", properties.getProperty(WebSerrviceName + "." + "Content-Type"));
                break;
            case "NoApikey":
                headers.put("app_id", properties.getProperty(WebSerrviceName + "." + "valid.appid"));
                headers.put("Content-Type", properties.getProperty(WebSerrviceName + "." + "Content-Type"));
                break;
            case "invalidapikey":
                headers.put("app_key", properties.getProperty(WebSerrviceName + "." + "invalid.apikey"));
                headers.put("app_id", properties.getProperty(WebSerrviceName + "." + "valid.appid"));
                headers.put("Content-Type", properties.getProperty(WebSerrviceName + "." + "Content-Type"));
                break;
            case "invalidappid":
                headers.put("app_key", properties.getProperty(WebSerrviceName + "." + "valid.apikey"));
                headers.put("app_id", properties.getProperty(WebSerrviceName + "." + "invalid.appid"));
                headers.put("Content-Type", properties.getProperty(WebSerrviceName + "." + "Content-Type"));
                break;
        }
        restWebService.constructHeaders(headers);

    }

    @When("^I send the (.+) request$")
    public void iSendTheGetRequest(String httpMethod) {
        restWebService.sendRequest(httpMethod);
        response = restWebService.getResponse();
        scenario.write("Response Status" + response.statusCode() + "\nResponse Content\n" + response.prettyPrint());
    }

    public static boolean isJsonValid(String jsonStr) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonStr);
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    @And("I should receive (.+) response")
    public void iShouldReceiveValidResponse(String responseStatus) {
        int responseCode = Ints.tryParse(responseStatus.substring(0, 3));
        // Validate if the status code is expected
        restWebService.setValidatableResponse(response.then().statusCode(responseCode));
        System.out.println("Response is: " + response.getBody().prettyPrint());

        // Validate if the response is matched against json schema
        if (responseCode == 200) {
            System.out.println("API has 200 status code -Success");
            /*String schemaPath = "schema/" + endpointService.getWebserviceName() + "-valid-schema.json";
            if (JsonUtility.isJsonValid(response.asString())) {
                restWebService.setValidatableResponse(response.then().assertThat().body(matchesJsonSchemaInClasspath(schemaPath)));
            } else {
                fail("Wrong response:\n" + response.asString());
            }*/
        } else if (responseCode == 403) {
            System.out.println("API has 403 status code ");
            /*String schemaPath = "schema/" + endpointService.getWebserviceName() + "-errors-schema.json";
            if (JsonUtility.isJsonValid(response.asString())) {
                restWebService.setValidatableResponse(response.then().assertThat().body(matchesJsonSchemaInClasspath(schemaPath)));
            } else {
                fail("Wrong response:\n" + response.asString());
            }*/
        } else if (responseCode == 403) {
            if (endpointService.getWebserviceName().contains("createCustomerAuthorities")) {
                String schemaPath = "schema/createCustomerAuthorities-errors-schema.json";
                if (JsonUtility.isJsonValid(response.asString())) {
                    restWebService.setValidatableResponse(response.then().assertThat().body(matchesJsonSchemaInClasspath(schemaPath)));
                } else {
                    fail("Wrong response:\n" + response.asString());
                }
            } else {
                fail("No errors-schema to compare with for: " + endpointService.getWebserviceName());
            }
        } else if (responseCode == 500) {
            String schemaPath = "schema/" + endpointService.getWebserviceName() + "-errors-schema.json";
            if (JsonUtility.isJsonValid(response.asString())) {
                restWebService.setValidatableResponse(response.then().assertThat().body(matchesJsonSchemaInClasspath(schemaPath)));
            } else {
                fail("Wrong response:\n" + response.asString());
            }
        }


    }


    @And("path parameters are the following")
    public void pathParametersAreTheFollowing(List<PathParameterConfig> pathParameterConfigList) {
        restWebService.constructPathParameters(pathParameterConfigList);
    }
}


