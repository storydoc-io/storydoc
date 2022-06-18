package io.storydoc.server.infra;


import io.storydoc.blueprint.BluePrint;
import io.storydoc.blueprint.Classifier;
import io.storydoc.blueprint.classification.ClassInfo;
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

        ClassInfo classInfo = ClassInfo.builder()
                .fullClassName(CodeQueryService.class.getName())
                .packageName(CodeQueryService.class.getPackageName())
                .build();

        List<String> classificationNames = classifier.classify(classInfo);
        System.out.println(classificationNames);

        Assert.assertNotNull(classificationNames);
        Assert.assertEquals("Storydoc Backend", classificationNames.get(0));
        Assert.assertEquals("Code Domain", classificationNames.get(1));
        Assert.assertEquals("Service", classificationNames.get(2));
    }



}
