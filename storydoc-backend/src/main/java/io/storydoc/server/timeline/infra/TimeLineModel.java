package io.storydoc.server.timeline.infra;

import io.storydoc.server.storydoc.domain.Artifact;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeLineModel implements Artifact  {
    private String id;
    private Map<String, TimeLine> timeLines;
}
