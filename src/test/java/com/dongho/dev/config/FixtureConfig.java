package com.dongho.dev.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Validated
@TestConfiguration
@ConfigurationProperties(prefix = "fixtures")
public class FixtureConfig {

    @NotEmpty
    private String test;

}
