package com.study.test.actuator;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
public class HealthActuator extends AbstractHealthIndicator {


    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        builder.up();
        builder.withDetail("freeMemory", Runtime.getRuntime().freeMemory());
        builder.withDetail("maxMemory", Runtime.getRuntime().maxMemory());
        builder.withDetail("totalMemory", Runtime.getRuntime().totalMemory());
        builder.withDetail("availableProcessors", Runtime.getRuntime().availableProcessors());
        builder.build();
    }
}
