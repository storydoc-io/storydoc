package io.storydoc.server.storydoc.app.dto;

import io.storydoc.server.storydoc.domain.ArtifactCoordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssociationDto {
    private String name;
    ArtifactCoordinate from;
    ArtifactCoordinate to;
}
