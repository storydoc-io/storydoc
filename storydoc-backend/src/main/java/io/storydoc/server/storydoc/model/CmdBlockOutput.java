package io.storydoc.server.storydoc.model;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Builder
@Value
@ToString
public class CmdBlockOutput {

    String[] lines;

}
