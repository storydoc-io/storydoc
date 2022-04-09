package io.storydoc.server.storydoc.infra.store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Association {
    private String name;

    private String documentIdFrom;
    private String blockIdFrom;
    private String artifactIdFrom;

    private String documentIdTo;
    private String blockIdTo;
    private String artifactIdTo;

}
