package io.storydoc.server.db.app;

import io.storydoc.server.db.app.dataset.DBDataSetDTO;
import io.storydoc.server.db.app.dataset.TableDataSetDTO;

import java.util.ArrayList;
import java.util.List;

public class MockData {

    static DBDataSetDTO getMockDBDataSetDTO() {
        List<TableDataSetDTO> tableDataSets = new ArrayList<>();

        tableDataSets.add(TableDataSetDTO.builder()
                .query("select * from A")
                .build());

        return DBDataSetDTO.builder()
                .tableDataSets(tableDataSets)
                .build();
    }

    static NavigationModelDTO getMockNavigationModelDTO() {
        NavigationModelDTO modelDTO = new NavigationModelDTO();
        List<NavigationItemDTO> items = new ArrayList<>();
        items.add(NavigationItemDTO.builder()
                .query("select * from A")
                .rootItem(true)
                .table("Table A")
                .build());
        items.add(NavigationItemDTO.builder()
                .query("select * from B")
                .rootItem(true)
                .table("Table B")
                .build());


        modelDTO.setItems(items);

        return modelDTO;
    }

}
