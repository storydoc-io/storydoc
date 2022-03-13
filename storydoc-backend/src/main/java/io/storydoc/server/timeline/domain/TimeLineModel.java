package io.storydoc.server.timeline.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class TimeLineModel {

    static public String ARTIFACT_TYPE = TimeLineModel.class.getName();

    private TimeLineStore timeLineStore;
    private TimeLineModelId timeLineModelId;

    public void addItemToTimeLine(TimeLineCoordinate timeLineCoordinate, TimeLineItemId timeLineItemId, String name) {
        timeLineStore.addItemToTimeLine(timeLineCoordinate, timeLineItemId, name);
    }

    public void removeItem(TimeLineCoordinate timeLineCoordinate, TimeLineItemId timeLineItemId) {
        timeLineStore.removeItem(timeLineCoordinate, timeLineItemId);
    }

    public void renameItem(TimeLineCoordinate timeLineCoordinate, TimeLineItemId timeLineItemId, String name) {
        timeLineStore.renameItem(timeLineCoordinate, timeLineItemId, name);
    }
}
