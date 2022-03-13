package io.storydoc.server.code.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class SourceCodeDTO {

    private List<String> lines;

}
