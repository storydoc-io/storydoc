package io.storydoc.server.code.infra.stitch;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@Slf4j
public class StitchFileScannerTest  {

    @Test
    @SneakyThrows
    public void load_sample_file() {
        Path path = Paths.get("src","test","resources", "io", "storydoc", "server", "code", "infra", "sample-stitch-file.txt");
        FileInputStream fileInputStream = new FileInputStream(path.toFile());
        StitchFileScannerNew scanner = new StitchFileScannerNew(fileInputStream,
                "TestScenario|TestEntered|{ \"testCaseName\": \"io.storydoc.server.ui.app.UIServiceTest createUIScenario(io.storydoc.server.ui.app.UIServiceTest)\"",
                "TestScenario|TestFinished|{ \"testCaseName\": \"io.storydoc.server.ui.app.UIServiceTest createUIScenario(io.storydoc.server.ui.app.UIServiceTest)\""
        );
        List<StitchLine> lines = scanner.run();
        assertNotNull(lines);

    }

}