package io.storydoc.server.document.infra.store;

import com.fasterxml.jackson.databind.module.SimpleModule;
import io.storydoc.server.document.infra.store.model.*;

public class StoryDocJacksonModule extends SimpleModule {

    public StoryDocJacksonModule() {
        super();
        registerSubtypes(Block.class, TextBlock.class, CmdBlock.class, Section.class, ArtifactBlock.class);
    }

}
