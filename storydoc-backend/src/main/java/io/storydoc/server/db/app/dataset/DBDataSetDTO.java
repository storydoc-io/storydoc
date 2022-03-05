package io.storydoc.server.db.app.dataset;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DBDataSetDTO {

    List<TableDataSetDTO> tableDataSets;


}
