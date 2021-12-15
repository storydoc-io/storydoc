package io.storydoc.server.ui.app;

import io.storydoc.server.ui.domain.MockUIId;
import io.storydoc.server.ui.infra.json.MockUI;
import io.storydoc.server.ui.infra.json.UIStore;
import org.springframework.stereotype.Service;

@Service
public class UIQueryServiceImpl implements UIQueryService {

    private final UIStore uiStore;

    public UIQueryServiceImpl(UIStore uiStore) {
        this.uiStore = uiStore;
    }

    @Override
    public MockUIDTO getMockUI(MockUIId id) {
        return toDTO(uiStore.loadMockUI(id));
    }

    private MockUIDTO toDTO(MockUI mockUI) {
        return MockUIDTO.builder()
                .id(MockUIId.fromString(mockUI.getId()))
                .imgURL(mockUI.getImageUrl())
                .build();
    }
}
