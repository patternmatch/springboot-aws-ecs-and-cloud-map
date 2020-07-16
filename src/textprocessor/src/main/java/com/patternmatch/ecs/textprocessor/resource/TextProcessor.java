package com.patternmatch.ecs.textprocessor.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.patternmatch.ecs.textprocessor.model.TextInfo;
import com.patternmatch.ecs.textprocessor.model.TextInput;
import com.patternmatch.ecs.textprocessor.service.TextApiClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/textprocessor", produces = APPLICATION_JSON_VALUE)
public class TextProcessor {

    @Autowired
    private TextApiClient textApiClient;

    @PostMapping
    @SneakyThrows
    public TextInfo info(@RequestBody TextInput input) {
        log.info("Received input: [{}]", input.getText());

        return textApiClient.detect(input);
    }
}
