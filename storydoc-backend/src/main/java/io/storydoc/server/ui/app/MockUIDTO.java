package io.storydoc.server.ui.app;

import io.storydoc.server.ui.domain.MockUIId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MockUIDTO {

    private MockUIId id;

    private String name;

    private List<ScreenshotDTO> screenshots;

}
