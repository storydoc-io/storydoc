package io.storydoc.server.code.infra.stitch;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

@Slf4j
public class StitchFileScannerNew {

    private InputStream inputStream;
    private String fromLine;
    private String toLine;

    Stack<List<StitchLine>> stack = new Stack();
    List<StitchLine> root  = new ArrayList<>();

    boolean firstLineFound = false;
    boolean lastLineFound = false;

    public StitchFileScannerNew(InputStream inputStream, String fromLine, String toLine) {
        this.inputStream = inputStream;
        this.fromLine = fromLine;
        this.toLine = toLine;
    }

    @SneakyThrows
    public List<StitchLine> run() {

        stack.push(root);

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while (!lastLineFound && (line = br.readLine()) != null) {
                if (line.length()>0) {
                    processLine(line);
                }
            }
        }

        return root;
    }

    ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    private void processLine(String line) {
        if (!firstLineFound) {
            if (line.equals(fromLine)) {
                firstLineFound = true;
            }
        }

        if (line.equals(toLine)) {
            lastLineFound = true;
        }


        StitchLine stitchLine = toStitchLine(line);

        if (isBeginOfSpan(stitchLine)) {
            stack.peek().add(stitchLine);
            System.out.println("  ".repeat(stack.size()) + "begin span: " + stitchLine);
            stack.push(stitchLine.getChildren());
        }
        else if (isEndOfSpan(stitchLine)) {
             stack.pop();
             if (stitchLine.getAttributes().get("cid")!= null) {
                 StitchLine lastLine = stack.peek().get(stack.peek().size()-1);
                 if (!lastLine.getAttributes().get("cid").equals(stitchLine.getAttributes().get("cid"))){
                     System.out.println("  ".repeat(stack.size()) + "=====> not equal!!!! " + stitchLine.getAttributes().get("cid") + " " + lastLine.getAttributes().get("cid"));
                     stack.pop();
                 }

             }

            stack.peek().add(stitchLine);
            System.out.println("  ".repeat(stack.size()) + "end span: " + stitchLine);
        } else {
            stack.peek().add(stitchLine);
            System.out.println("  ".repeat(stack.size()) + "simple add: " + stitchLine);
        }

    }

    private boolean isBeginOfSpan(StitchLine stitchLine) {
        return stitchLine.getEventName().equals("TestEntered") || stitchLine.getEventName().equals("MethodCalled");
    }

    private boolean isEndOfSpan(StitchLine stitchLine) {
        return stitchLine.getEventName().endsWith("Return") || stitchLine.getEventName().endsWith("Finished");
    }

    private StitchLine toStitchLine(String line) throws com.fasterxml.jackson.core.JsonProcessingException {
        int indexFirstPipe = line.indexOf('|');
        int indexSecondPipe = line.indexOf('|', indexFirstPipe+1);

        String modelName = line.substring(0, indexFirstPipe);
        String eventName = line.substring(indexFirstPipe+1, indexSecondPipe);
        String attributesJson = line.substring(indexSecondPipe+1);

        StitchLine stitchLine = StitchLine.builder()
                .modelName(modelName)
                .eventName(eventName)
                .attributes(getAttributeMap(attributesJson))
                .children(new ArrayList<>())
                .build();
        return stitchLine;
    }

    private Map<String, String> getAttributeMap(String attributesJson) throws com.fasterxml.jackson.core.JsonProcessingException {
        if (attributesJson.equals("")) {
            attributesJson = "{}";
        }

        Map<String, String> attributes  = objectMapper.readValue(attributesJson, Map.class);
        return attributes;
    }

}

