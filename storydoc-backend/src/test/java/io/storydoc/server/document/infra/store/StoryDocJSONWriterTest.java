package io.storydoc.server.document.infra.store;

import io.storydoc.server.document.infra.store.model.StoryDoc;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.file.Path;

import static io.storydoc.server.document.app.Fixtures.jacksonConfig;

public class StoryDocJSONWriterTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private Path workFolder;

    @Before
    public void setUp() throws IOException {
        workFolder = tempFolder.newFolder("work").toPath();
    }

    @Test
    public void main_succes_scenario_with_artifacts() throws IOException {

        StoryDocFixture storyDocFixture = new StoryDocFixture();
        StoryDoc storyDoc = storyDocFixture.a_main_successs_scenario();

        String fileName = "main-success-with-artifacts.json";
        writeStoryDoc(storyDoc, fileName);

    }


    private void writeStoryDoc(StoryDoc storyDoc, String fileName) throws IOException {
        File file = workFolder.resolve(fileName).toFile();
        OutputStream outputStream = new FileOutputStream(file);


        StoryDocJSONWriter writer = new StoryDocJSONWriter(jacksonConfig());
        writer.write(storyDoc, outputStream);

        outputStream.close();

        printFile(file);
    }

    private void printFile(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

}