package io.storydoc.server.code.infra.stitch;

import io.storydoc.server.code.app.stitch.StitchStructureDTO;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

public class StitchStructureReader {

    public StitchStructureDTO readStructure(Path root) {
        File dir = root.toFile();
        StitchStructureDTO stitchStructureDTO = StitchStructureDTO.builder()
            .type("DIR")
            .label(dir.getName())
            .build();
        recursiveAdd(stitchStructureDTO, dir);
        return stitchStructureDTO;
    }

    private void recursiveAdd(StitchStructureDTO parentDto, File parentDir) {
        File[] children = parentDir.listFiles();
        if (children==null || children.length == 0) return;
        parentDto.setChildren(new ArrayList<>());
        for (File child : children) {
            StitchStructureDTO childDto = StitchStructureDTO.builder()
                .label(child.getName())
                .type(child.isDirectory() ? "DIR" : "FILE")
                .build();
            parentDto.getChildren().add(childDto);
            if (child.isDirectory()) {
                recursiveAdd(childDto, child);
            }
        }
    }

}


