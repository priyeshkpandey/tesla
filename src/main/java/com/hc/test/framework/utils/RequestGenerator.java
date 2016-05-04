package com.hc.test.framework.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.jayway.jsonpath.JsonPath;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RequestGenerator {

    static {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");

    }

    public static Logger LOGGER = LoggerFactory.getLogger(RequestGenerator.class);

    private String url;
    private RestTemplate restTemplate;
    private String output;
    private ObjectMapper objectMapper = new ObjectMapper().configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    private String INDEX_PATTERN = "(.+)\\[([0-9]+)\\]";
    private Pattern pattern = Pattern.compile(INDEX_PATTERN);;
    private ResponseEntity<String> result;
    private HttpHeaders headers_;
    private HttpEntity<String> entity;
    MultivaluedMap headers;
    HttpClient httpClient = HttpClientBuilder.create().build();
    HttpGet httpGet;
    Header header;
    HttpResponse response_;
    WebTarget webTarget;
    Response response;
    Invocation.Builder invocationBuilder;
    Client client = ClientBuilder.newClient();

    public RequestGenerator(String url, MultivaluedMap headers) {

        this.url=url;
        this.headers=headers;
        this.postResponse();



    }
   public RequestGenerator(){}

   public void postResponse(){
       webTarget=client.target(url);
       invocationBuilder =  webTarget.request(MediaType.APPLICATION_JSON);
       response = invocationBuilder.headers(headers).get();
       }


    /*public ResponseEntity<String> getResponseObject() {
        try {
           // output = restTemplate.getForObject(url, String.class);
            result = restTemplate.exchange(url, HttpMethod.GET,entity, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }*/


    public String parseJson(String jsonSource, String key) throws IOException {
        JsonNode valueNode = getJsonNode(jsonSource, key);
        if (valueNode != null) {
            return valueNode.textValue();
        }else{
            return "No Data Found";
        }

    }

    private JsonNode getJsonNode(String jsonSource, String key) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(jsonSource);
        String[] keyTokens = key.split("\\.");
        String keyToken = keyTokens[0];
        Matcher m = pattern.matcher(keyToken);
        JsonNode value = null;
        if (m.find()) {
            // its not a plain key , its a indexed key.
            keyToken = m.group(1);
            int index = Integer.parseInt(m.group(2));
            ArrayNode arrayNode = (ArrayNode) jsonNode.get(keyToken);
            value = arrayNode.get(index);
        } else {
            value = jsonNode.get(keyToken);
        }

        if (keyTokens.length > 1)
            return getJsonNode(value.toString(), key.replaceFirst(keyToken + ".", ""));
        return value;
    }



    public String returnResponseAsString() {
        String returntType = null;

        try {
            returntType = response.readEntity(String.class);
            if (returntType.length() > 0) {
                Object json = objectMapper.readValue(returntType, Object.class);
                returntType = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
                LOGGER.info("Content is valid Json Response");
            }
        } catch (IOException e) {
            LOGGER.error("Content is not valid Json");
            e.printStackTrace();
        }

        return returntType;
    }


    public static void main(String ...args) throws Exception {
        MultivaluedMap akoshaAuthHeader=new HttpRequestHelper().setAndGetAkoshaAuth("8892566529");

      RequestGenerator req=new RequestGenerator("http://172.16.1.145:8080/mobileapp/v4/sections",akoshaAuthHeader);
        String resp=req.returnResponseAsString();
        //System.out.println(resp);
       List<String> grids= JsonPath.read(resp,"$.[*].display_name");
        System.out.println(grids);
    }


}
