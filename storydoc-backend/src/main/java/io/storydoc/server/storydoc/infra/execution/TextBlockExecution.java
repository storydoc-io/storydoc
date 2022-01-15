package io.storydoc.server.storydoc.infra.execution;

import io.storydoc.server.storydoc.infra.store.model.TextBlock;

public class TextBlockExecution extends AbstractBlockExecutor {

    private final TextBlock textBlock;

    public TextBlockExecution(TextBlock textBlock, ExecutionContext ctx) {
        super(ctx);
        this.textBlock = textBlock;
    }

    @Override
    public void run() {
        log("running textblock: " + textBlock.getText() );
    }
}
