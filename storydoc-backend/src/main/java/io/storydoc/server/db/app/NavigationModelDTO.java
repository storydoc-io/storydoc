package io.storydoc.server.db.app;

import lombok.Data;

import java.util.List;

@Data
public class NavigationModelDTO {
    private List<NavigationItemDTO> items;
}
