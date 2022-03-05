package io.storydoc.server.db.app;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NavigationItemDTO {

    private boolean rootItem;
    private String table;
    private String query;

}
