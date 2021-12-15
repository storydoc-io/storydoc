package io.storydoc.core;

import io.storydoc.server.document.infra.store.model.CmdBlock;
import io.storydoc.server.document.infra.store.model.TextBlock;
import io.storydoc.server.document.infra.store.model.Block;
import io.storydoc.server.document.infra.store.model.StoryDoc;
import io.storydoc.server.document.infra.execution.BlockExecutionMgr;
import io.storydoc.server.document.infra.execution.ExecutionEngine;
import io.storydoc.server.document.infra.execution.scheduling.WorkMgr;
import io.storydoc.server.document.infra.store.JacksonConfig;
import io.storydoc.server.document.infra.store.StoryDocJacksonModule;
import io.storydoc.server.ui.infra.json.MockUIJacksonModule;

import java.util.ArrayList;
import java.util.List;

public class Fixtures {

    public static StoryDoc aStoryDoc() {


        List<Block> blocks = new ArrayList<>();
        {
            TextBlock textBlock = new TextBlock();
            textBlock.setText("TextBlock content");
            blocks.add(textBlock);
        }
        {
            CmdBlock cmdBlock = new CmdBlock();
            cmdBlock.setCmd("CmdBlock content");
            blocks.add(cmdBlock);
        }

        return new StoryDoc("id", "name", blocks);

    }

    public static JacksonConfig jacksonConfig() {

        JacksonConfig jacksonConfig = new JacksonConfig();
        jacksonConfig.registerModule(new StoryDocJacksonModule());
        jacksonConfig.registerModule(new MockUIJacksonModule());

        return jacksonConfig;
    }



    public static ExecutionEngine anEngine() {

        BlockExecutionMgr blockExecutionMgr = new BlockExecutionMgr();
        WorkMgr workMgr = new WorkMgr(blockExecutionMgr);

        return new ExecutionEngine(workMgr);
    }
}
