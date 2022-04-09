package io.storydoc.server.code.infra.stitch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class StitchLine {

    private String modelName;

    private String eventName;

    private Map<String, String> attributes;


}
