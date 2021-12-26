package io.storydoc.server;

import io.storydoc.server.config.StoryDocServerProperties;
import io.storydoc.server.document.domain.DocumentStore;
import io.storydoc.server.document.domain.StoryDocId;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@Slf4j
abstract public class TestBase {

    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(Description description) {
            log.info("**");
            log.info("* Test: " + description.getClassName() + "." + description.getMethodName());
            log.info("**");
        }
    };

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Autowired
    private StoryDocServerProperties serverProperties;

    @Autowired
    private DocumentStore documentStore;

    @Before
    public void setUp() throws IOException {
        String workspaceFolder = temporaryFolder.newFolder("workspace").getAbsolutePath();
        log.info("workspaceFolder: " + workspaceFolder);
        serverProperties.setWorkspaceFolder(workspaceFolder);
    }

    protected void dump(String msg, StoryDocId storyDocId) {
        log.info(msg + ":");
        log.info(documentStore.getContentAsString(storyDocId));
    }


}
