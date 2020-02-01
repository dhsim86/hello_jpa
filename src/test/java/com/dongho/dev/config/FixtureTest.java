package com.dongho.dev.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {FixtureConfig.class})
@ActiveProfiles("test")
public class FixtureTest {

    @Autowired
    private FixtureConfig fixtureConfig;

    @Test
    public void testProperty() {

        given: {

        }

        when: {

        }

        then: {
            assertThat(fixtureConfig.getTest()).isEqualTo("test");
            assertThat(fixtureConfig.getMemberList()).isNotEmpty().hasSize(2);
        }
    }

}
