package io.storydoc.server.storydoc.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SettingsEntryDTO {
    String nameSpace;
    String key;
    String value;
}
