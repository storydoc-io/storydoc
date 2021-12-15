package io.storydoc.server.document.infra.store;

import io.storydoc.server.document.infra.store.model.StoryDoc;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;

import static io.storydoc.core.Fixtures.jacksonConfig;

public class StoryDocJsonReaderTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void main_succes_scenario_with_artifacts() throws IOException {
        // given
        StoryDocFixture storyDocFixture = new StoryDocFixture();
        StoryDoc storyDoc = storyDocFixture.a_main_successs_scenario();

        String fileName = "main-success-with-artifacts.json";
        writeStoryDoc(storyDoc, fileName);

        // when
        StoryDoc storyDoc1 = readStoryDoc(fileName);

    }

    private void writeStoryDoc(StoryDoc storyDoc, String fileName) throws IOException {
        File file = tempFolder.newFile(fileName);
        OutputStream outputStream = new FileOutputStream(file);


        StoryDocJSONWriter writer = new StoryDocJSONWriter(jacksonConfig());
        writer.write(storyDoc, outputStream);

        outputStream.close();

    }

    private StoryDoc readStoryDoc(String fileName) throws IOException {
        StoryDocJsonReader reader = new StoryDocJsonReader(jacksonConfig());
        return reader.read(new FileInputStream(tempFolder.newFile(fileName)));

    }


}