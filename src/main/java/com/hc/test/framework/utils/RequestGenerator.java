package com.hc.test.framework.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
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
    private ObjectMapper objectMapper;
    private String INDEX_PATTERN = "(.+)\\[([0-9]+)\\]";
    private Pattern pattern;
    private ResponseEntity<String> result;
    private HttpHeaders headers;
    private HttpEntity<String> entity;

    public RequestGenerator(String url ) {
        this.url = url;
        restTemplate = new RestTemplate();
        objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        pattern = Pattern.compile(INDEX_PATTERN);
        headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        entity = new HttpEntity<String>("parameters", headers);
    }



    public ResponseEntity<String> getResponseObject() {
        try {
           // output = restTemplate.getForObject(url, String.class);
            result = restTemplate.exchange(url, HttpMethod.GET,entity, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public String parseJson(String jsonSource, String key) throws IOException {
        JsonNode valueNode = get(jsonSource, key);
        if (valueNode != null) {
            return valueNode.textValue();
        }else{
            return "No Data Found";
        }

    }

    private JsonNode get(String jsonSource, String key) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(jsonSource);
        String[] keyTokens = key.split("\\.");
        String keyToken = keyTokens[0];
        Matcher m = pattern.matcher(keyToken);
        JsonNode value = null;
        if (m.matches()) {
            // its not a plain key , its a indexed key.
            keyToken = m.group(1);
            int index = Integer.parseInt(m.group(2));
            ArrayNode arrayNode = (ArrayNode) jsonNode.get(keyToken);
            value = arrayNode.get(index);
        } else {
            value = jsonNode.get(keyToken);
        }

        if (keyTokens.length > 1)
            return get(value.toString(), key.replaceFirst(keyToken + ".", ""));
        return value;
    }



}
