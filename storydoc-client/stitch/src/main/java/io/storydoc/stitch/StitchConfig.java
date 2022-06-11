package io.storydoc.stitch;

import lombok.Builder;
import lombok.Getter;

import java.nio.file.Path;
import java.nio.file.Paths;

@Builder
@Getter
public class StitchConfig {

    Path rootPath;

    public static final StitchConfig DEFAULT = StitchConfig.builder()
        .rootPath(Paths.get("target", "stitch"))
        .build();

}
