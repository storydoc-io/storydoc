package io.storydoc.server;

import io.storydoc.server.config.StoryDocServerProperties;
import io.storydoc.server.infra.StitchFactory;
import io.storydoc.server.workspace.app.WorkspaceQueryService;
import io.storydoc.stitch.CodeExecutionTracer;
import io.storydoc.stitch.ScenarioTracer;
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

    ScenarioTracer scenarioTracer = StitchFactory.getScenarioTracer();
    public CodeExecutionTracer executionTracer = StitchFactory.getCodeExecutionTracer();

    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(Description description) {
            scenarioTracer.beginTestCase(description.getClassName(), description.getMethodName());
            log.debug("**");
            log.debug("* Test: " + description.getClassName() + "." + description.getMethodName());
            log.debug("**");
        }
        protected void finished(Description description) {
            scenarioTracer.endTestCase(description.getClassName(), description.getMethodName());
        }
    };

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Autowired
    private StoryDocServerProperties serverProperties;

    @Autowired
    protected WorkspaceQueryService workspaceQueryService;

    @Before
    public void setUp() throws IOException {
        String workspaceFolder = temporaryFolder.newFolder("workspace").getAbsolutePath();
        //log.info("workspaceFolder: " + workspaceFolder);
        serverProperties.setWorkspaceFolder(workspaceFolder);
    }

    protected void given(String text) {
        scenarioTracer.bdd("given", text);
    }

    protected void when(String text) {
        scenarioTracer.bdd("when", text);
    }

    protected void then(String text) {
        scenarioTracer.bdd("then", text);
    }

}
