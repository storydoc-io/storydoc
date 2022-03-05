package io.storydoc.server.db.app;

import io.storydoc.server.db.app.dataset.DBDataSetDTO;
import org.springframework.stereotype.Component;

@Component
public class SQLDBService {

    public NavigationModelDTO getNavigationModelDTO() {
        return MockData.getMockNavigationModelDTO();
    }

    public DBDataSetDTO getDBDataSet() {
        return MockData.getMockDBDataSetDTO();
    }

}
