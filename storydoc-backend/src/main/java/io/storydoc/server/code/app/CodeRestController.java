package io.storydoc.server.code.app;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/code")
public class CodeRestController {

    private final CodeService codeService;

    public CodeRestController(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public CodeTraceDTO trace() {
        return codeService.getExecution();
    }


}
