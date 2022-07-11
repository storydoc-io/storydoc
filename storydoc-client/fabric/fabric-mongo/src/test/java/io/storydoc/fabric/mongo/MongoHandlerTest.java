package io.storydoc.fabric.mongo;

import io.storydoc.fabric.core.BundleDescriptor;
import io.storydoc.fabric.core.Fabric;
import lombok.SneakyThrows;
import org.junit.Ignore;
import org.junit.Test;

public class MongoHandlerTest {

    @Test @Ignore
    @SneakyThrows
    public void createBundle()  {
        Fabric fabric = new Fabric();
        fabric.addHandler(new MongoHandler());

        MongoComponentDescriptor mongoDEV = MongoComponentDescriptor.builder()
                // add url
                .build();

        BundleDescriptor bundleDesc = BundleDescriptor.builder()
                .component(mongoDEV)
                .build();

        fabric.createBundle(bundleDesc);

    }


}
