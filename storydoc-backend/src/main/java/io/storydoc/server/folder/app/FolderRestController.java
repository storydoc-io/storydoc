package io.storydoc.server.folder.app;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/folder")
public class FolderRestController {

    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public String hello() {
        return "world";
    }

}
