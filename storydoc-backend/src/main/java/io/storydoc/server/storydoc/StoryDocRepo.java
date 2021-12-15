package io.storydoc.server.storydoc;

import io.storydoc.server.document.infra.store.model.CmdBlock;
import io.storydoc.server.document.infra.store.model.TextBlock;
import io.storydoc.server.document.infra.store.model.Block;
import io.storydoc.server.document.infra.store.model.StoryDoc;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StoryDocRepo {

    private Map<String, StoryDoc> byId = new HashMap<>();

    public StoryDocRepo() {
        init();
    }

    private void init() {

        String id = "test";

        List<Block> blocks = new ArrayList<>();
        {
            TextBlock textBlock = new TextBlock();
            textBlock.setId("block 1 id");
            textBlock.setName("block 1");
            textBlock.setText("TextBlock content");
            blocks.add(textBlock);
        }
        {
            CmdBlock cmdBlock = new CmdBlock();
            cmdBlock.setId("block 2 id");
            cmdBlock.setName("block 2");
            cmdBlock.setCmd("CmdBlock content");
            blocks.add(cmdBlock);
        }

        StoryDoc storyDoc = new StoryDoc(id, "name " + id, blocks);

        byId.put(id, storyDoc);


    }

    public StoryDoc findById(String id) {
        return byId.get(id);
    }


}
