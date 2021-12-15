package io.storydoc.server.document.infra.store.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes(value={})
@Data
@NoArgsConstructor
@AllArgsConstructor
abstract public class Block {

    String id;

    String blockType;

    String name;

    List<String> tags;

}
