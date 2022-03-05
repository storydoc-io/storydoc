package io.storydoc.server.db.app;

import io.storydoc.server.db.app.dataset.DBDataSetDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sqldb")
public class SQLDBController {

    private final SQLDBService service;

    public SQLDBController(SQLDBService service) {
        this.service = service;
    }

    @GetMapping(value="/navigationmodel", produces = MediaType.APPLICATION_JSON_VALUE)
    public NavigationModelDTO getNavigationModel() {
        return service.getNavigationModelDTO();
    }

    @GetMapping(value="/dataset", produces = MediaType.APPLICATION_JSON_VALUE)
    public DBDataSetDTO getDBDataSet() {
        return service.getDBDataSet();
    }


}
