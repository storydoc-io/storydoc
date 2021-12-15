package io.storydoc.server.ui.infra.json;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;

import static io.storydoc.core.Fixtures.jacksonConfig;

public class MockUIJSONWriterTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void simple_ui() throws IOException {

        MockUI mockUI = new MockUI();
        mockUI.setImageUrl("home.png");
        writeMockUI(mockUI, "home.json");
    }

    private void writeMockUI(MockUI mockUI, String fileName) throws IOException {
        File file = tempFolder.newFile(fileName);
        OutputStream outputStream = new FileOutputStream(file);


        MockUIJSONWriter writer = new MockUIJSONWriter(jacksonConfig());
        writer.write(mockUI, outputStream);

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