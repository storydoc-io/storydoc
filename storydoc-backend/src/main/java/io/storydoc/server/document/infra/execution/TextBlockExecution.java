package io.storydoc.server.document.infra.execution;

import io.storydoc.server.document.infra.store.model.TextBlock;

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
