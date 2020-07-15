package com.patternmatch.ecs.textapi.health;

import java.util.concurrent.atomic.AtomicBoolean;

public class HealthStatus {

    private static AtomicBoolean isUp = new AtomicBoolean(true);

    public static boolean healthy() {
        return isUp.get();
    }

    public static void up() {
        isUp.compareAndSet(false, true);
    }

    public static void down() {
        isUp.compareAndSet(true, false);
    }
}
