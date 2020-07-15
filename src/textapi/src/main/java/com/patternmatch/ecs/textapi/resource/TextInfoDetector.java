package com.patternmatch.ecs.textapi.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.patternmatch.ecs.textapi.model.TextInfo;
import com.patternmatch.ecs.textapi.model.TextInput;
import com.patternmatch.ecs.textapi.service.AwsComprehendClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/textapi", produces = APPLICATION_JSON_VALUE)
public class TextInfoDetector {

    @Autowired
    private AwsComprehendClient client;

    @PostMapping
    public TextInfo info(@RequestBody TextInput input) {
        log.info("Received input: [{}]", input.getText());

        final String language = client.detectLanguage(input.getText());

        return new TextInfo(language, client.detectSentiment(input.getText(), language));
    }
}
