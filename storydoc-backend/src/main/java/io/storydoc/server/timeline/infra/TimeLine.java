package io.storydoc.server.timeline.infra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeLine {
    private String id;
    private List<TimeLineItem> items;
}
