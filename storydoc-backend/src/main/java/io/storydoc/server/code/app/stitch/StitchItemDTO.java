package io.storydoc.server.code.app.stitch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class StitchItemDTO {

    private String modelName;

    private String eventName;

    private Map<String, String> attributes;

    private List<StitchItemDTO> children;
}
