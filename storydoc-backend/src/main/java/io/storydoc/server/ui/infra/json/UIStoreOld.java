package io.storydoc.server.ui.infra.json;

import io.storydoc.server.storydoc.domain.StoryDocException;
import io.storydoc.server.ui.domain.MockUIId;
import io.storydoc.server.workspace.app.WorkspaceSettings;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;

@Component
public class UIStoreOld {

    private Path workFolder;

    private MockUIJsonReader jsonReader;

    private MockUIJSONWriter jsonWriter;

    public UIStoreOld(WorkspaceSettings settings) {
/*
        this.workFolder = settings.getWorkspaceFolder();
        JacksonConfig jacksonConfig = new JacksonConfig();
        jacksonConfig.registerModule(new MockUIJacksonModule());
        jsonReader = new MockUIJsonReader(jacksonConfig);
        jsonWriter = new MockUIJSONWriter(jacksonConfig);

 */
    }

    public MockUI loadMockUI(MockUIId id) throws StoryDocException {
/*
        try(InputStream inputStream = inputStream(id)) {
            return jsonReader.read(inputStream);
        } catch (IOException e) {
            throw new StoryDocException("could not load mock ui " + id, e);
        }

 */
        return null;
    }

    private InputStream inputStream(MockUIId id) throws FileNotFoundException {
        return new FileInputStream(getFile(id));
    }

    private File getFile(MockUIId id) {
        Path path = workFolder.resolve("files").resolve(id.getId()+".json");
        return path.toFile();
    }


}
