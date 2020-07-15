package com.patternmatch.ecs.textapi.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.patternmatch.ecs.textapi.health.HealthStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/textapi", produces = APPLICATION_JSON_VALUE)
public class AppStatus {

    @PostMapping(path = "/status")
    public void info(@RequestParam boolean healthy) {
        if (healthy) {
            HealthStatus.up();
        } else {
            HealthStatus.down();
        }
    }
}
