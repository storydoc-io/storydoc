package io.storydoc.server.storydoc.domain;

import org.springframework.stereotype.Service;

@Service
public class StoryDocDomainService {

    private final StoryDocStorage storage;

    public StoryDocDomainService(StoryDocStorage storage) {
        this.storage = storage;
    }


    public StoryDoc getDocument(StoryDocId storyDocId) {

        return StoryDoc.builder()
                .storage(storage)
                .id(storyDocId)
                .build();

    }

    public StoryDocId createDocument(StoryDocId storyDocId, String name) {
        storage.createDocument(storyDocId, name);
        return storyDocId;
    }
}
