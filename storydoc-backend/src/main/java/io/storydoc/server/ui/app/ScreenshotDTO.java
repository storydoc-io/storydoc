package io.storydoc.server.ui.app;

import io.storydoc.server.ui.domain.MockUIId;
import io.storydoc.server.ui.domain.ScreenshotId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenshotDTO {

    private ScreenshotId id;

    private String imgURL;


}
