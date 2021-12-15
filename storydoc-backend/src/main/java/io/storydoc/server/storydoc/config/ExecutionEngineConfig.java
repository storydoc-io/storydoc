package io.storydoc.server.storydoc.config;

import io.storydoc.server.document.infra.execution.BlockExecutionMgr;
import io.storydoc.server.document.infra.execution.ExecutionEngine;
import io.storydoc.server.document.infra.execution.scheduling.WorkMgr;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutionEngineConfig {

    @Bean
    public ExecutionEngine executionEngine() {

        BlockExecutionMgr blockExecutionMgr = new BlockExecutionMgr();

        WorkMgr workMgr = new WorkMgr(blockExecutionMgr);

        return new ExecutionEngine(workMgr);

    }


}
