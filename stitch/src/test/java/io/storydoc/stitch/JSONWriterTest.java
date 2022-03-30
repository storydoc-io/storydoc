package io.storydoc.stitch;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class JSONWriterTest  {

    @Test
    public void writeObject() {
        JSONWriter writer = new JSONWriter();
        writer.obj();

        writer.att("firstName");
        writer.string("John");

        writer.att("lastName");
        writer.string("Smith");

        writer.att("age");
        writer.number(27);

        writer.end();

        System.out.println(writer.getValue());
    }

    @Test
    public void writeArray() {
        JSONWriter writer = new JSONWriter();
        writer.array();

        writer.string("John");

        writer.string("Smith");

        writer.end();

        System.out.println(writer.getValue());
    }


}