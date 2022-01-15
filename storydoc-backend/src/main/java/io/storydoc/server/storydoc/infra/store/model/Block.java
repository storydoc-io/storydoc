package io.storydoc.server.storydoc.infra.store.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(ArtifactBlock.class),
        @JsonSubTypes.Type(Section.class),
        @JsonSubTypes.Type(CmdBlock.class),
        @JsonSubTypes.Type(TextBlock.class)
})
@Data
@NoArgsConstructor
@AllArgsConstructor
abstract public class Block {

    String id;

    String blockType;

    String name;

    List<String> tags;

}
