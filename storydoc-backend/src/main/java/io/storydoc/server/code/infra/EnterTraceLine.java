package io.storydoc.server.code.infra;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class EnterTraceLine extends TraceLine {

    private String cid;

    private String threadName;

    private String className;

    private String methodName;

    private String params;

}
