package io.storydoc.server.infra;

import io.storydoc.blueprint.BluePrint;
import io.storydoc.blueprint.element.Role;
import io.storydoc.blueprint.layout.FlexLayout;

import java.util.List;

import static io.storydoc.blueprint.classification.AnyClassNameClassification.anyClassName;
import static io.storydoc.blueprint.classification.ByClassNameClassification.hasClassNameEndingWith;
import static io.storydoc.blueprint.classification.ByPackageNameClassification.packageNameStartsWith;

public class StoryDocBluePrintFactory {


    public BluePrint createBluePrint() {

        BluePrint config = BluePrint.builder()
                .name("Config")
                .classifier(packageNameStartsWith("io.storydoc.server.config"))
                .layout(new FlexLayout())
                .elements(List.of(
                        Role.builder()
                            .name("config")
                            .classifier(anyClassName())
                            .build())
                )
                .build();

        BluePrint infra = BluePrint.builder()
                .name("Infra")
                .classifier(packageNameStartsWith("io.storydoc.server.infra"))
                .layout(new FlexLayout())
                .elements(List.of(
                        Role.builder()
                                .name("config")
                                .classifier(anyClassName())
                                .build())
                )
                .build();

        BluePrint codeDomain = dddBlueprint()
                .name("Code Domain")
                .classifier(packageNameStartsWith("io.storydoc.server.code"))
                .build();
        BluePrint uiDomain = dddBlueprint()
                .name("UI Domain")
                .classifier(packageNameStartsWith("io.storydoc.server.ui"))
                .build();

        BluePrint storyDocDomain = dddBlueprint()
                .name("StoryDoc Domain")
                .classifier(packageNameStartsWith("io.storydoc.server.storydoc"))
                .build();

        BluePrint workspaceDomain = dddBlueprint()
                .name("Workspace Domain")
                .classifier(packageNameStartsWith("io.storydoc.server.workspace"))
                .build();

        return BluePrint.builder()
                .name("Storydoc Backend")
                .layout(new FlexLayout())
                .classifier(packageNameStartsWith("io.storydoc.server"))
                .elements(List.of(config, infra, codeDomain, uiDomain, storyDocDomain, workspaceDomain))
                .build();

    }


    private BluePrint.BluePrintBuilder dddBlueprint() {
        Role storageRole = Role.builder()
                .name("Repository")
                .classifier(hasClassNameEndingWith("Storage", "StorageImpl", "Repository", "RepositoryImpl"))
                .build();

        Role serviceRole = Role.builder()
                .name("Service")
                .classifier(hasClassNameEndingWith("Service", "ServiecImpl"))
                .build();

        Role domainRepositoryRole = Role.builder()
                .name("DomainRepository")
                .classifier(hasClassNameEndingWith("DomainRepository"))
                .build();


        return BluePrint.builder()
                .layout(new FlexLayout())
                .elements(List.of(serviceRole, domainRepositoryRole, storageRole));

    }


}
