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

@Slf4j
public class StitchFileScanner {

    private InputStream inputStream;
    private String fromLine;
    private String toLine;

    List<StitchLine> lines = new ArrayList<>();
    boolean firstLineFound = false;
    boolean lastLineFound = false;

    public StitchFileScanner(InputStream inputStream, String fromLine, String toLine) {
        this.inputStream = inputStream;
        this.fromLine = fromLine;
        this.toLine = toLine;
    }

    @SneakyThrows
    public List<StitchLine> run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while (!lastLineFound && (line = br.readLine()) != null) {
                if (line.length()>0) {
                    processLine(line);
                }
            }
            return lines;
        }
    }

    ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    private void processLine(String line) {
        if (!firstLineFound) {
            if (line.equals(fromLine)) {
                firstLineFound = true;
            }
            return;
        }

        if (line.equals(toLine)) {
            lastLineFound = true;
            return;
        }

        int indexFirstPipe = line.indexOf('|');
        int indexSecondPipe = line.indexOf('|', indexFirstPipe+1);

        String modelName = line.substring(0, indexFirstPipe);
        String eventName = line.substring(indexFirstPipe+1, indexSecondPipe);
        String attributesJson = line.substring(indexSecondPipe+1);

        if (attributesJson.equals("")) {
            attributesJson = "{}";
        }

        Map<String, String> attributes  = objectMapper.readValue(attributesJson, Map.class);
        lines.add(StitchLine.builder()
                .modelName(modelName)
                .eventName(eventName)
                .attributes(attributes)
                .build());

    }

}

