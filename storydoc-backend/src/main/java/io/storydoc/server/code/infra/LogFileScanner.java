package io.storydoc.server.code.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LogFileScanner {

    public List<TraceLine> scan(InputStream inputStream) {

        try {
            List<TraceLine> lines = new ArrayList<>();

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();
            String logLineStr;
            while ((logLineStr = br.readLine()) != null) {
                if (logLineStr.length()>0) {
                    lines.add(toLogLine(logLineStr));
                }
            }
            return lines;
        } catch (IOException ioe) {
            throw new RuntimeException("error while scanning log file", ioe);
        } finally {
            try {
                inputStream.close();
            } catch (IOException ioe) {
                throw new RuntimeException("error while closing log file", ioe);
            }
        }
    }

    ObjectMapper objectMapper = new ObjectMapper();

    private TraceLine toLogLine(String logLineAsStr) throws JsonProcessingException {
        Map<String, String> logLineAsMap  = objectMapper.readValue(logLineAsStr, Map.class);

        if (logLineAsMap.get("kind").equals("enter")) {
            return EnterTraceLine.builder()
                .cid(logLineAsMap.get("cid"))
                .threadName(logLineAsMap.get("thread_name"))
                .className(logLineAsMap.get("signature.declaringTypeName"))
                .methodName(logLineAsMap.get("signature.name"))
                .params(logLineAsMap.get("args"))
                .build();
        } else {
            return ExitTraceLine.builder()
                .cid(logLineAsMap.get("cid"))
                .returnValue(logLineAsMap.get("returnValue"))
                .build();
        }
    }

}
