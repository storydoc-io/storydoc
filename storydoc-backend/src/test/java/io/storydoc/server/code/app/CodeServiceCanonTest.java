package io.storydoc.server.code.app;

import io.storydoc.canon.Canon;
import io.storydoc.canon.Interpreter;
import io.storydoc.server.TestBase;
import io.storydoc.server.infra.canon.StoryDocCanonBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;


@ContextConfiguration(
        loader = AnnotationConfigContextLoader.class,
        classes = {CanonTestContext.class}
)
public class CodeServiceCanonTest extends TestBase {

    @Autowired
    Interpreter interpreter;

    @Test
    public void stitch_config__create() {

        Canon canon = StoryDocCanonBuilder.canonBuilder()
            .createStoryDoc("storyDoc")
            .createBlock("storyDoc", "block1")
            .build();

        interpreter.run(canon);


    }


}
