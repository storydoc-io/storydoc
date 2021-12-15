package io.storydoc.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
public class SettingsConfig {

    @Bean
    Settings getSettings() {
        Settings settings = new Settings();
        settings.setWorkFolder(Path.of("/home/stiepeb/_/project/github/storydoc-io/workspace"));
        return settings;
    }

}
