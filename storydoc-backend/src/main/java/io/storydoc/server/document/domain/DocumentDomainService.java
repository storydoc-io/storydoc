package io.storydoc.server.document.domain;

import org.springframework.stereotype.Service;

@Service
public class DocumentDomainService {

    private final DocumentStore documentStore;

    public DocumentDomainService(DocumentStore documentStore) {
        this.documentStore = documentStore;
    }


    public Document getDocument(StoryDocId storyDocId) {

        return Document.builder()
                .documentStore(documentStore)
                .id(storyDocId)
                .build();

    }

    public StoryDocId createDocument(StoryDocId storyDocId) {
        documentStore.createDocument(storyDocId);
        return storyDocId;
    }
}
