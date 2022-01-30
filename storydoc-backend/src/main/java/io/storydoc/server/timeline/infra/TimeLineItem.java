package io.storydoc.server.timeline.infra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeLineItem {
    private String id;
    private String description;

}
