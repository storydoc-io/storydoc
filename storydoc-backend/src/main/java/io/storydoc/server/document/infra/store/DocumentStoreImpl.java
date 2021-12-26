package io.storydoc.server.document.infra.store;

import io.storydoc.server.document.domain.*;
import io.storydoc.server.document.infra.store.model.ArtifactBlock;
import io.storydoc.server.document.infra.store.model.Block;
import io.storydoc.server.document.infra.store.model.Section;
import io.storydoc.server.document.infra.store.model.StoryDoc;
import io.storydoc.server.folder.app.FolderService;
import io.storydoc.server.folder.app.FolderSettings;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
public class DocumentStoreImpl implements DocumentStore {

    private FolderSettings folderSettings;

    private StoryDocJsonReader jsonReader;

    private StoryDocJSONWriter jsonWriter;

    private final FolderService folderService;

    public DocumentStoreImpl(FolderSettings folderSettings, FolderService folderService) {
        this.folderSettings = folderSettings;
        this.folderService = folderService;
        JacksonConfig jacksonConfig = new JacksonConfig();
        jacksonConfig.registerModule(new StoryDocJacksonModule());
        jsonReader = new StoryDocJsonReader(jacksonConfig);
        jsonWriter = new StoryDocJSONWriter(jacksonConfig);
    }

    @Override
    public void createDocument(StoryDocId storyDocId) {
        StoryDoc storyDoc = new StoryDoc();
        storyDoc.setId(storyDocId.getId().toString());
        storyDoc.setBlocks(new ArrayList<>());
        saveDocument(storyDocId, storyDoc);
    }


    @Override
    public void addArtifactBlock(StoryDocId storyDocId, BlockId blockId) {
        StoryDoc storyDoc = loadDocument(storyDocId);
        Block block = createArtifactBlock(blockId);
        storyDoc.getBlocks().add(block);
        saveDocument(storyDocId, storyDoc);
    }

    @Override
    public void addArtifactBlock(StoryDocId storyDocId, BlockId blockId, SectionId sectionId) {
        StoryDoc storyDoc = loadDocument(storyDocId);
        Section section = lookupSection(sectionId, storyDoc);
        Block block = createArtifactBlock(blockId);
        section.getBlocks().add(block);
        saveDocument(storyDocId, storyDoc);
    }

    private Block createArtifactBlock(BlockId blockId) {
        Block block = new ArtifactBlock();
        block.setId(blockId.getId().toString());
        block.setTags(new ArrayList<>());
        return block;
    }

    private Section lookupSection(SectionId sectionId, StoryDoc storyDoc) {
        for (Block block: storyDoc.getBlocks()){
            if (block instanceof Section) {
                Section section = (Section) block;
                if (section.getId().equals(sectionId.getId().toString())) {
                    return section;
                } else {
                    // recurse
                }
            }
        }
        return null;
    }

    private Block lookupBlock(BlockId blockId, List<Block> blocks) {
        for (Block block: blocks) {
            if (block instanceof Section) {
                Section section = (Section) block;
                Block subBlock = lookupBlock(blockId, section.getBlocks());
                if (subBlock != null) return subBlock;
            } else {  // (!(block instanceof Section))
                if (block.getId().equals(blockId.getId().toString())) {
                    return block;
                }
            }
        }
        return null;
    }


    @Override
    public void removeBlock(StoryDocId storyDocId, BlockId blockId) {
        StoryDoc storyDoc = loadDocument(storyDocId);

        Block block = storyDoc.getBlocks().stream()
                .filter(b->b.getId().equals(blockId.getId().toString()))
                .findFirst()
                .orElseThrow(() -> new DocumentException ("cannot delete block with id " + blockId + " no such block found"));

        storyDoc.getBlocks().remove(block);

        saveDocument(storyDocId, storyDoc);
    }

    @Override
    public void addSection(StoryDocId storyDocId, SectionId sectionId) {
        StoryDoc storyDoc = loadDocument(storyDocId);

        Section section = new Section();
        section.setId(sectionId.getId().toString());
        section.setBlocks(new ArrayList<>());

        storyDoc.getBlocks().add(section);

        saveDocument(storyDocId, storyDoc);
    }

    @Override
    public void addTag(StoryDocId storyDocId, BlockId blockId, String tag) {
        StoryDoc storyDoc = loadDocument(storyDocId);
        Block block = lookupBlock(blockId, storyDoc.getBlocks());
        block.getTags().add(tag);
        saveDocument(storyDocId, storyDoc);
    }

    @Override
    public StoryDoc loadDocument(StoryDocId storyDocId) throws DocumentException {
        try(InputStream inputStream = inputStream(storyDocId)) {
            return jsonReader.read(inputStream);
        } catch (IOException e) {
            throw new DocumentException("could not load document " + storyDocId, e);
        }
    }

    private void saveDocument(StoryDocId storyDocId, StoryDoc storyDoc) throws DocumentException {
        try (OutputStream outputStream = outputStream(storyDocId)) {
            jsonWriter.write(storyDoc, outputStream);
        } catch (IOException e) {
            throw new DocumentException("could not save document " + storyDocId, e);
        }
    }


    private InputStream inputStream(StoryDocId storyDocId) throws FileNotFoundException {
        return new FileInputStream(getFile(storyDocId));
    }

    private File getFile(StoryDocId storyDocId) {
        Path path = folderSettings.getWorkspaceFolder().resolve(storyDocId.getId().toString());
        return path.toFile();
    }

    private OutputStream outputStream(StoryDocId storyDocId) throws FileNotFoundException {
        return new FileOutputStream(getFile(storyDocId));
    }

    public String getContentAsString(StoryDocId storyDocId) {
        try {
            InputStream inputStream =  inputStream(storyDocId);
            String newLine = System.getProperty("line.separator");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream));
            StringBuilder result = new StringBuilder();
            for (String line; (line = reader.readLine()) != null; ) {
                if (result.length() > 0) {
                    result.append(newLine);
                }
                result.append(line);
            }
            return result.toString();
        } catch (IOException e) {
            throw new DocumentException("could not read document content" + storyDocId, e);
        }
    }

}
