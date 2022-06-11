package io.storydoc.server.infra;


import io.storydoc.blueprint.BluePrint;
import io.storydoc.blueprint.Classifier;
import io.storydoc.server.code.app.CodeQueryService;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class StoryDocBluePrintFactoryTest {

    @Test
    public void create_blueprint() {
        StoryDocBluePrintFactory storyDocBluePrintFactory = new StoryDocBluePrintFactory();
        BluePrint bluePrint = storyDocBluePrintFactory.createBluePrint();

        Classifier classifier = new Classifier(bluePrint);

        List<String> classificationNames = classifier.classify(CodeQueryService.class);
        Assert.assertNotNull(classificationNames);
        System.out.println(classificationNames);
    }



}
