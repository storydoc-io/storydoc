package io.storydoc.server.ui.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScreenShotCollectionDTO {

    private List<ScreenShotDTO> screenShots;

}
