package io.storydoc.server.ui.app;

import io.storydoc.server.ui.domain.MockUIId;

public interface UIQueryService {
    MockUIDTO getMockUI(MockUIId id);
}
