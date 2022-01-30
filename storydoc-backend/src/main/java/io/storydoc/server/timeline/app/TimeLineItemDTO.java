package io.storydoc.server.timeline.app;

import io.storydoc.server.timeline.domain.TimeLineItemId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeLineItemDTO {

    private TimeLineItemId itemId;
    private String description;

}
