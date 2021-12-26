package io.storydoc.server;

import io.storydoc.server.config.StoryDocServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages={"io.storydoc.server.document", "io.storydoc.server.folder"})
public class TestConfig {

    @Bean
    StoryDocServerProperties serverProperties() {
        return new StoryDocServerProperties();
    }


}
