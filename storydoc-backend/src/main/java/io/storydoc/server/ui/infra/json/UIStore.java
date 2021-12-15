package io.storydoc.server.ui.infra.json;

import io.storydoc.server.config.Settings;
import io.storydoc.server.document.domain.DocumentException;
import io.storydoc.server.document.infra.store.JacksonConfig;
import io.storydoc.server.ui.domain.MockUIId;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Path;

@Component
public class UIStore {

    private Path workFolder;

    private MockUIJsonReader jsonReader;

    private MockUIJSONWriter jsonWriter;

    public UIStore(Settings settings) {
        this.workFolder = settings.getWorkFolder();
        JacksonConfig jacksonConfig = new JacksonConfig();
        jacksonConfig.registerModule(new MockUIJacksonModule());
        jsonReader = new MockUIJsonReader(jacksonConfig);
        jsonWriter = new MockUIJSONWriter(jacksonConfig);
    }

    public MockUI loadMockUI(MockUIId id) throws DocumentException {
        try(InputStream inputStream = inputStream(id)) {
            return jsonReader.read(inputStream);
        } catch (IOException e) {
            throw new DocumentException("could not load mock ui " + id, e);
        }
    }

    private InputStream inputStream(MockUIId id) throws FileNotFoundException {
        return new FileInputStream(getFile(id));
    }

    private File getFile(MockUIId id) {
        Path path = workFolder.resolve("files").resolve(id.getId()+".json");
        return path.toFile();
    }


}
