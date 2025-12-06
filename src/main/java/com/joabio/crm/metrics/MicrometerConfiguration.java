package com.joabio.crm.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MicrometerConfiguration {
    private final MeterRegistry meterRegistry;

    @Autowired
    public MicrometerConfiguration(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Bean
    public Counter clientCreatedCounter() { 
        return Counter.builder("crm.clients.created")
                .description("Número de clientes criados")
                .register(meterRegistry);
    }
}