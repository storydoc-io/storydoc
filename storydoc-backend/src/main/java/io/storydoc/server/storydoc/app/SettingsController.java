package io.storydoc.server.storydoc.app;

import io.storydoc.server.storydoc.app.dto.SettingsDTO;
import io.storydoc.server.storydoc.app.dto.SettingsEntryDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
public class SettingsController {

    private final StoryDocService storyDocService;

    private final StoryDocQueryService storyDocQueryService;

    public SettingsController(StoryDocService storyDocService, StoryDocQueryService storyDocQueryService) {
        this.storyDocService = storyDocService;
        this.storyDocQueryService = storyDocQueryService;
    }

    @PostMapping(value="/setting", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void setGlobalSetting(@RequestBody  SettingsEntryDTO setting) {
        storyDocService.setGlobalSetting(setting);
    }

    @GetMapping(value="/settings", produces= MediaType.APPLICATION_JSON_VALUE)
    public SettingsDTO getGlobalSettings() {
        return storyDocQueryService.getGlobalSettings();
    }

}
