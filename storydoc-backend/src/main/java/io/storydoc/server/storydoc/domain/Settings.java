package io.storydoc.server.storydoc.domain;

import io.storydoc.server.storydoc.app.dto.SettingsEntryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@AllArgsConstructor
@Builder
public class Settings {

    StoryDocStorage storage;

    public void addAll(List<SettingsEntryDTO> entries) {
        storage.setGlobalSettings(entries);
    }

    public void add(SettingsEntryDTO setting) { storage.setGlobalSetting(setting); }

}
