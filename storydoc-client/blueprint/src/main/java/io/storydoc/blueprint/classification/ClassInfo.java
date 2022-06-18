package io.storydoc.blueprint.classification;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClassInfo {
    String fullClassName;
    String packageName;
}
