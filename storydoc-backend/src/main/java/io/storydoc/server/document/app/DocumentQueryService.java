package io.storydoc.server.document.app;

import io.storydoc.server.document.app.dto.StoryDocDTO;
import io.storydoc.server.document.domain.StoryDocId;

public interface DocumentQueryService {

    StoryDocDTO getDocument(StoryDocId storyDocId);



}
