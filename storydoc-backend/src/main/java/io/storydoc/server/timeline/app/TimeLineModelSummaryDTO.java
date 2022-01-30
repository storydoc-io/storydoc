package io.storydoc.server.timeline.app;

import io.storydoc.server.timeline.domain.TimeLineModelCoordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeLineModelSummaryDTO {
    private String name;
    private TimeLineModelCoordinate timeLineModelCoordinate;
}
