package io.storydoc.server;

import io.storydoc.server.config.StoryDocServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"io.storydoc.server.storydoc", "io.storydoc.server.workspace", "io.storydoc.server.ui", "io.storydoc.server.infra", "io.storydoc.server.timeline" })
public class TestConfig {

    @Bean
    StoryDocServerProperties serverProperties() {
        return new StoryDocServerProperties();
    }


}
