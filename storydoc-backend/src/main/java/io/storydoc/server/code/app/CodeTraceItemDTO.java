package io.storydoc.server.code.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CodeTraceItemDTO {

    private String type;



    private String cid;

    private String threadName;

    private String className;

    private String methodName;

    private String params;

    private String resultValue;

}
