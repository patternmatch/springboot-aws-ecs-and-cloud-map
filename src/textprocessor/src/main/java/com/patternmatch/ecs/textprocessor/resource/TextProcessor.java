package com.patternmatch.ecs.textprocessor.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.patternmatch.ecs.textprocessor.model.TextInfo;
import com.patternmatch.ecs.textprocessor.model.TextInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@Slf4j
@RestController
@RequestMapping(path = "/textprocessor", produces = APPLICATION_JSON_VALUE)
public class TextProcessor {

    @PostMapping
    public TextInfo info(@RequestBody TextInput input) {
        log.info("Received input: [{}]", input.getText());

        return new TextInfo(Locale.getISOLanguages()[(int) (System.currentTimeMillis() % 10)]);
    }
}
