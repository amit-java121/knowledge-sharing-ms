package com.knowledgesharing.ms.configuration.observability;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HealthMetricConfig {

    @Bean
    public Gauge healthGauge(MeterRegistry meterRegistry, HealthEndpoint healthEndpoint) {
        return Gauge.builder("health", healthEndpoint, this::healthToInteger)
            .strongReference(true) // so that gauge is not garbage collected
            .register(meterRegistry);
    }

    private int healthToInteger(HealthEndpoint healthEndpoint) {
        if (healthEndpoint.health().getStatus() == Status.UP) {
            return 1;
        }
        return 0;
    }

}
