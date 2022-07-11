package io.storydoc.server;

import io.storydoc.server.config.StoryDocServerProperties;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@Slf4j
 abstract public class CanonTestBase {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Autowired
    private StoryDocServerProperties serverProperties;

    @Before
    public void setUp() throws IOException {
        String workspaceFolder = temporaryFolder.newFolder("workspace").getAbsolutePath();
        //log.info("workspaceFolder: " + workspaceFolder);
        serverProperties.setWorkspaceFolder(workspaceFolder);
    }

}
