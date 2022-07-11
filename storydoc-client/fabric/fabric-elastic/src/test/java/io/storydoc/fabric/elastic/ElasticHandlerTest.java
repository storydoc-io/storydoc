package io.storydoc.fabric.elastic;

import io.storydoc.fabric.core.BundleDescriptor;
import io.storydoc.fabric.core.Fabric;
import lombok.SneakyThrows;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class ElasticHandlerTest {

    @Test
    @SneakyThrows
    public void createBundle()  {
        Fabric fabric = new Fabric();
        fabric.addHandler(new ElasticHandler());

        ElasticComponentDescriptor elasticDEV = ElasticComponentDescriptor.builder()
                // add url
                .build();

        BundleDescriptor bundleDesc = BundleDescriptor.builder()
                .component(elasticDEV)
                .build();

        fabric.createBundle(bundleDesc);

    }


}
