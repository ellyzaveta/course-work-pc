package com.kpi.client.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.kpi")
@PropertySource("classpath:application.properties")
public class SpringConfiguration {
}
