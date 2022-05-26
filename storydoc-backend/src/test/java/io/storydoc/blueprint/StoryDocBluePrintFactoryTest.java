package io.storydoc.blueprint;


import io.storydoc.server.code.app.CodeQueryService;
import io.storydoc.server.infra.StoryDocBluePrintFactory;
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
