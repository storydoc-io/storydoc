package io.storydoc.server.document.app.dto;

import io.storydoc.server.document.domain.BlockId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlockDTO {

    private BlockId blockId;

    private int[] numbering;

    private BlockId parentBlockId;

    public String blockType;

    private String title;

    private String text;

    private List<ArtifactDTO> artifacts;

}
