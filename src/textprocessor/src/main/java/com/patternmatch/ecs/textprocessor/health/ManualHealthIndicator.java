package com.patternmatch.ecs.textprocessor.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class ManualHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        return HealthStatus.healthy() ? Health.up().build() : Health.down().build();
    }
}
