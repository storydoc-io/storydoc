package io.storydoc.server.document.infra.execution;

import io.storydoc.server.document.infra.store.model.CmdBlock;

import java.util.concurrent.TimeUnit;

public class CmdBlockExecution extends AbstractBlockExecutor {

    private final CmdBlock cmdBlock;

    public CmdBlockExecution(CmdBlock cmdBlock, ExecutionContext ctx) {
        super(ctx);
        this.cmdBlock = cmdBlock;
    }

    @Override
    public void run() {
        for (int i=0; i<5; i++) {
            sleep();
            log("cmd logline : " + i);
        }
    }

    private void sleep() {
        try {
            TimeUnit.MILLISECONDS.sleep(5);
        } catch (InterruptedException ignored) {
            ;
        }

    }

}
