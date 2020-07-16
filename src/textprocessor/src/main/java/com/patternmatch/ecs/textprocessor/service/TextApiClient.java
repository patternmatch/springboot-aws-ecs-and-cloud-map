package com.patternmatch.ecs.textprocessor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patternmatch.ecs.textprocessor.model.TextInfo;
import com.patternmatch.ecs.textprocessor.model.TextInput;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TextApiClient {

    private static final String API_PATH = "/textapi";

    @Value("${textapi.endpoint}")
    private String textApiEndpoint;

    private RestTemplate client = new RestTemplate();
    private ObjectMapper objectMapper = new ObjectMapper();

    public TextInfo detect(TextInput input) throws JsonProcessingException {
        final String textInfo = client.postForObject(textApiEndpoint + API_PATH, new HttpEntity<>(input), String.class);

        JsonNode root = objectMapper.readTree(textInfo);
        return new TextInfo(root.path("language").asText(), root.path("sentiment").asText());
    }
}
