package io.storydoc.server.code.app;

import org.junit.Test;

import static org.junit.Assert.*;

public class CodeServiceTest {

    @Test
    public void load_code() {

        CodeService codeService = new CodeService();

        codeService.getExecution();

    }


}