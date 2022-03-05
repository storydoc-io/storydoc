package io.storydoc.server.storydoc.app;

import io.storydoc.server.config.StoryDocServerProperties;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final StoryDocServerProperties serverProperties;

    public AdminController(StoryDocServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @GetMapping(value="/config", produces= MediaType.APPLICATION_JSON_VALUE)
    public StoryDocServerProperties getConfig() {
        return serverProperties;
    }

}
