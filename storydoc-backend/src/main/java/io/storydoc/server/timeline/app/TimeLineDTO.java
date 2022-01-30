package io.storydoc.server.timeline.app;


import io.storydoc.server.timeline.domain.TimeLineId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeLineDTO {
    private String name;
    private TimeLineId timeLineId;
    private List<TimeLineItemDTO> items;

}
