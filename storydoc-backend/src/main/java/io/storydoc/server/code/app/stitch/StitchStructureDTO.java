package io.storydoc.server.code.app.stitch;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StitchStructureDTO {
    String type;
    String label;
    List<StitchStructureDTO> children;
}
