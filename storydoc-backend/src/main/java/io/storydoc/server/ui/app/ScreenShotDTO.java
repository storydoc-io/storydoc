package io.storydoc.server.ui.app;

import io.storydoc.server.ui.domain.ScreenShotId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenShotDTO {

    private ScreenShotId id;

    private String imgURL;

    private String name;
}
