package io.storydoc.server.code.infra;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ExitTraceLine extends TraceLine {

    private String cid;

    private String returnValue;

}
