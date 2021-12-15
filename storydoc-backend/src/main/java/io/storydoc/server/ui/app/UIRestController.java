package io.storydoc.server.ui.app;

import io.storydoc.server.ui.domain.MockUIId;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ui")
public class UIRestController {

    private final UIQueryService uiQueryService;

    public UIRestController(UIQueryService uiQueryService) {
        this.uiQueryService = uiQueryService;
    }

    @GetMapping(value ="/", produces = MediaType.APPLICATION_JSON_VALUE)
    public UIBlockDTO getUI() {
        return UIBlockDTO.builder()
                .blockId(UIBlockId.fromString(UUID.randomUUID().toString()))
                .uiList(List.of(
                        UIDTO.builder()
                                .uiId(UIId.fromString(UUID.randomUUID().toString()))
                                .imgURL("http://localhost:8080/files/scenario-01-01-home.png")
                                .build(),
                        UIDTO.builder()
                                .uiId(UIId.fromString(UUID.randomUUID().toString()))
                                .imgURL("http://localhost:8080/files/scenario-01-02-owner-information.png")
                                .build(),
                        UIDTO.builder()
                                .uiId(UIId.fromString(UUID.randomUUID().toString()))
                                .imgURL("http://localhost:8080/files/scenario-01-03-new-visit.png")
                                .build(),
                        UIDTO.builder()
                                .uiId(UIId.fromString(UUID.randomUUID().toString()))
                                .imgURL("http://localhost:8080/files/scenario-01-04-owner-information.png")
                                .build(),
                        UIDTO.builder()
                                .uiId(UIId.fromString(UUID.randomUUID().toString()))
                                .imgURL("http://localhost:8080/files/scenario-01-05-new-visit.png")
                                .build()
                ))
                .build();
    }

    @GetMapping(value ="/mockui", produces = MediaType.APPLICATION_JSON_VALUE)
    public MockUIDTO getMockUI(String id) {
        return uiQueryService.getMockUI(MockUIId.fromString(id));
    }

}
