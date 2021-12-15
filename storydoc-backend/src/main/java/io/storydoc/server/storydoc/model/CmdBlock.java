package io.storydoc.server.storydoc.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CmdBlock {

    String id;
    String cmd;
    CmdBlockOutput output;

}
