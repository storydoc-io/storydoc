package io.storydoc.server.code.infra;

import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class LogFileScannerTest {

    @Test
    public void read_while_is_being_added() {

        InputStream fileInputStream = this.getClass().getResourceAsStream("json-log-file.log");
        LogFileScanner logFileScanner = new LogFileScanner();
        List<TraceLine> lines = logFileScanner.scan(fileInputStream);

        Assert.assertNotNull(lines);
        System.out.println(lines);
    }

}
