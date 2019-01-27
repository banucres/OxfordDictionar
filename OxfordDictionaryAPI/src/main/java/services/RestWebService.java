package services;


import com.google.common.base.CharMatcher;
import models.PathParameterConfig;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;

@Getter
@Setter
@ToString
@Slf4j
public class RestWebService {

    private RequestSpecification request;
    private Response response;
    private ValidatableResponse validatableResponse;

    private Map<String, String> pathMap = new HashMap<>();
    private Map<String, String> pathParaMap = new HashMap<>();


    public RestWebService() {
        // Trust all hosts
        RestAssured.useRelaxedHTTPSValidation();

        // Initiate request
        request = given();
    }

    /**
     * Construct request headers
     *
     * @param headerMap map of the headers
     */


    public void constructHeaders(Map<String, String> headerMap) {
        Map<String, String> headers = new HashMap<>(0);
        for (String name : headerMap.keySet()) {
            headers.put(name, headerMap.get(name));
        }

       // log.info("Construct request's headers " + headers.toString());

        request.headers(headers);
    }

//*
//     * Construct request path parameters
//     *
//     * @param pathParameterConfigList cucumber data table
//

    public void constructPathParameters(List<PathParameterConfig> pathParameterConfigList) {

        for (PathParameterConfig pathParameterConfig : pathParameterConfigList) {
            pathMap.put(pathParameterConfig.getPath(), pathParameterConfig.getPathParameter());
            pathParaMap.put(pathParameterConfig.getPathParameter(), pathParameterConfig.getParameterValue());
        }

        Map<String, String> pathParams = new HashMap<>(0);
        for (String key : pathParaMap.keySet()) {
            pathParams.put(key, pathParaMap.get(key));
        }
       // log.info("Construct request's path parameters " + pathParams.toString());

        request.pathParams(pathParams);
    }

    /*
     *
     * Construct request query parameters
     *
     * @param queryParameterConfigList cucumber data table

     */

    public void constructQueryParameters(Map<String, String> queryParameterConfigList) {
        Map<String, String> queryParams = new HashMap<>(0);
        for (String key : queryParameterConfigList.keySet()) {
            if (!queryParameterConfigList.get(key).isEmpty()) {
                queryParams.put(key, queryParameterConfigList.get(key));
            }
        }
        //log.info("Construct request's query parameters " + queryParams.toString());

        request.queryParams(queryParams);
    }
    /*
     *
     * Construct request body
     *
     * @param object request body

     */

    public void constructRequestBody(Object object) {
        // log.info("Construct request's body " + object.toString());

        request.contentType("application/json");
        request.body(object);
    }

    public void sendRequest(String httpMethod) {
        HttpMethod method = HttpMethod.valueOf(httpMethod);
        StringBuilder resourceURI = new StringBuilder();

        // log.info("Construct request's path parameters with it values");
        for (String key : pathMap.keySet()) {
            resourceURI.append("/").append(key).append("/{").append(pathMap.get(key)).append("}");
        }

      //  log.info("Remove the last slash if existed");
        String parameterURI = CharMatcher.is('/').trimTrailingFrom(resourceURI);

        // log.info("Logging the request as: \n");
        request.log().all();

        switch (method) {
            case GET:
                //          response = request.when().get(basePath + parameterURI);
                response = request.when().get(basePath + parameterURI);
                break;
            case PUT:
                response = request.when().put(basePath + parameterURI);
                break;
            case POST:
                response = request.when().post(basePath + parameterURI);
                break;
            case PATCH:
                response = request.when().patch(basePath + parameterURI);
                break;
            case DELETE:
                response = request.when().delete(basePath + parameterURI);
                break;
            case OPTIONS:
                response = request.when().options(basePath + parameterURI);
                break;
            default:
           //     log.error("Wrong HTTP method. Please make sure the method is only among GET, PUT, POST, PATCH & DELETE");
                break;
        }

        //   log.info("Logging the response as: \n");
        response.then().log().all();

    }

    private enum HttpMethod {
        /**
         * HTTP GET
         */
        GET,

        /**
         * HTTP PUT
         */
        PUT,

        /**
         * HTTP POST
         */
        POST,

        /**
         * HTTP PATCH
         */
        PATCH,

        /**
         * HTTP DELETE
         */
        DELETE,

        /**
         * HTTP OPTIONS
         */
        OPTIONS,
    }

}
