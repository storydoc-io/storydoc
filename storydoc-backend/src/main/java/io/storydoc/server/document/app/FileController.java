package io.storydoc.server.document.app;

import io.storydoc.server.config.Settings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;

@Controller
@RequestMapping("/files")
@Slf4j
public class FileController {

    private final Settings settings;

    public FileController(Settings settings) {
        this.settings = settings;
    }

    @RequestMapping(value = "/{file_name}", method = RequestMethod.GET)
    public void getFile(@PathVariable("file_name") String fileName, HttpServletResponse response) {
        try {
            Files.copy(settings.getWorkFolder().resolve("files").resolve(fileName), response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            log.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
            throw new RuntimeException("IOError writing file to output stream");
        }
    }

}
