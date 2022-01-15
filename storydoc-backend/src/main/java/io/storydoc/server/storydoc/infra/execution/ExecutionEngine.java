package io.storydoc.server.storydoc.infra.execution;

import io.storydoc.server.storydoc.domain.StoryDocException;
import io.storydoc.server.storydoc.infra.store.model.StoryDoc;
import io.storydoc.server.storydoc.infra.execution.event.ExecutionEvent;
import io.storydoc.server.storydoc.infra.execution.event.ExecutionListener;
import io.storydoc.server.storydoc.infra.execution.event.LifeCycleEvent;
import io.storydoc.server.storydoc.infra.execution.event.LifeCycleType;
import io.storydoc.server.storydoc.infra.execution.scheduling.WorkMgr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExecutionEngine implements ExecutionListener {

    private final WorkMgr workMgr;

    public ExecutionEngine(WorkMgr workMgr) {
        this.workMgr = workMgr;
    }

    public void run(StoryDoc storyDoc, ExecutionListener... executionListeners) throws StoryDocException {

        List<ExecutionListener> listeners = new ArrayList<>();
        listeners.addAll(Arrays.asList(executionListeners));
        listeners.add(this);

        ExecutionContext ctx = ExecutionContext.builder()
                .storyDoc(storyDoc)
                .blocksToRun(storyDoc.getBlocks())
                .currentBlock(storyDoc.getBlocks().get(0))
                .listeners(listeners)
                .build();

        workMgr.add(ctx);
    }


    @Override
    public void handle(ExecutionEvent event) {
        if (!(event instanceof LifeCycleEvent)) return;

        LifeCycleEvent lifeCycleEvent = (LifeCycleEvent) event;
        if (!(LifeCycleType.BLOCK_FINISHED.equals(lifeCycleEvent.getType()))) return;

        ExecutionContext nextCtx = lifeCycleEvent.getCtx().withNextBlock();

        if (nextCtx != null) {
            workMgr.add(nextCtx);
        }
    }
}
