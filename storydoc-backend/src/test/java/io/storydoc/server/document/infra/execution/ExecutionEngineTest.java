package io.storydoc.server.document.infra.execution;

import io.storydoc.core.Fixtures;
import io.storydoc.server.document.domain.DocumentException;
import io.storydoc.server.document.infra.store.model.StoryDoc;
import io.storydoc.server.document.infra.execution.event.EventLogger;
import io.storydoc.server.document.infra.execution.event.FilteringEventCollector;
import io.storydoc.server.document.infra.execution.event.LogEvent;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static io.storydoc.core.Fixtures.aStoryDoc;
import static org.junit.Assert.assertEquals;

public class ExecutionEngineTest {

    private ExecutionEngine executionEngine;

    @Before
    public void setUp() throws Exception {
        executionEngine = Fixtures.anEngine();
    }

    @Test
    public void testExecution() throws DocumentException, InterruptedException {

        StoryDoc storyDoc = aStoryDoc();

        FilteringEventCollector collector = new FilteringEventCollector((evt)->evt instanceof LogEvent);
        EventLogger logger = new EventLogger();

        executionEngine.run(storyDoc, logger, collector);

        TimeUnit.MILLISECONDS.sleep(200);
        assertEquals(6, collector.getEvents().size());
    }

}