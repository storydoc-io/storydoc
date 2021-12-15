package io.storydoc.server.document.app;

import io.storydoc.server.config.Settings;
import io.storydoc.server.document.domain.DocumentDomainService;
import io.storydoc.server.document.domain.DocumentStore;
import io.storydoc.server.document.infra.store.DocumentStoreImpl;

import java.nio.file.Path;

public class DocumentServiceTestFixture {

    private Path workFolder;

    private DocumentService documentService;

    private DocumentQueryService documentQueryService;

    private DocumentDomainService documentDomainService;

    private DocumentStore documentStore;

    public DocumentServiceTestFixture(Path workFolder) {

        Settings settings  =  new Settings();
        settings.setWorkFolder(workFolder);

        this.workFolder = workFolder;

        documentStore = new DocumentStoreImpl(settings);

        documentDomainService = new DocumentDomainService(documentStore);

        documentService = new DocumentServiceImpl(documentDomainService);

        documentQueryService = new DocumentQueryServiceImpl(documentStore);
    }

    public DocumentService getDocumentService() {
        return documentService;
    }

    public DocumentQueryService getDocumentQueryService() {
        return documentQueryService;
    }

    public DocumentStore getDocumentStore() {
        return documentStore;
    }
}
