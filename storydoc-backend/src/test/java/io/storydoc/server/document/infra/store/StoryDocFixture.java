package io.storydoc.server.document.infra.store;

import io.storydoc.server.document.infra.store.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StoryDocFixture {

    public StoryDoc a_main_successs_scenario() {
        StoryDoc storyDoc = new StoryDoc();
        storyDoc.setId(UUID.randomUUID().toString());
        storyDoc.setName("Add a visit for a pet");

        List<Block> docBlocks = new ArrayList<>();
        {
            Section analysisSection = new Section();
            analysisSection.setId(UUID.randomUUID().toString());
            analysisSection.setName("Analysis");
            docBlocks.add(analysisSection);

            List analysisSectionBlocks = new ArrayList();
            analysisSection.setBlocks(analysisSectionBlocks);

            {
                ArtifactBlock mockUI = new ArtifactBlock();
                mockUI.setId("mock-ui");
                mockUI.setBlockType("ARTIFACT");
                mockUI.setName("Mock UI");
                analysisSectionBlocks.add(mockUI);

                List<Artifact> artifacts = new ArrayList<>();
                mockUI.setArtifacts(artifacts);
                {
                    Artifact artifact = new Artifact();
                    artifact.setArtifactId("mock-ui-01");
                    artifact.setArtifactType("UI");
                    artifact.setName("home screen");
                    artifact.setUrn("home.png");
                    artifacts.add(artifact);
                }
                {
                    Artifact artifact = new Artifact();
                    artifact.setArtifactId("mock-ui-02");
                    artifact.setArtifactType("UI");
                    artifact.setName("owners");
                    artifact.setUrn("owner-information.png");
                    artifacts.add(artifact);
                }
                {
                    Artifact artifact = new Artifact();
                    artifact.setArtifactId("mock-ui-03");
                    artifact.setArtifactType("UI");
                    artifact.setName("create visit");
                    artifact.setUrn("analysis/new visit - design.png");
                    artifacts.add(artifact);
                }
            }
            {
                ArtifactBlock uiScenario = new ArtifactBlock();
                uiScenario.setId(UUID.randomUUID().toString());
                uiScenario.setBlockType("ARTIFACT");
                uiScenario.setName("UI Scenarios");
                analysisSectionBlocks.add(uiScenario);

                List<Artifact> artifacts = new ArrayList<>();
                uiScenario.setArtifacts(artifacts);
                {
                    Artifact artifact = new Artifact();
                    artifact.setArtifactId("ui-scenario-01");
                    artifact.setArtifactType("UI-SCENARIO");
                    artifact.setName("main flow");
                    artifact.setUrn("home.png");
                    artifacts.add(artifact);
                }

            }


        }

        {
            Section implSection = new Section();
            implSection.setId(UUID.randomUUID().toString());
            implSection.setName("Implementation");
            docBlocks.add(implSection);

            List implSectionBlocks = new ArrayList();
            implSection.setBlocks(implSectionBlocks);

            {
                ArtifactBlock integrationTest = new ArtifactBlock();
                integrationTest.setId(UUID.randomUUID().toString());
                integrationTest.setBlockType("ARTIFACT");
                integrationTest.setName("Integration Test");
                implSectionBlocks.add(integrationTest);

                List<Artifact> artifacts = new ArrayList<>();
                integrationTest.setArtifacts(artifacts);
                {
                    Artifact artifact = new Artifact();
                    artifact.setArtifactId("db-connection");
                    artifact.setArtifactType("DB-CONNECTION-SETTINGS");
                    artifact.setName("db connection config");
                    artifacts.add(artifact);
                }
                {
                    Artifact artifact = new Artifact();
                    artifact.setArtifactId("db-snapshot");
                    artifact.setArtifactType("DB-SNAPSHOT");
                    artifact.setName("db state before");
                    artifacts.add(artifact);
                }
                {
                    Artifact artifact = new Artifact();
                    artifact.setArtifactId("db-snapshot-02");
                    artifact.setArtifactType("DB-SNAPSHOT");
                    artifact.setName("db state after");
                    artifacts.add(artifact);
                }
                {
                    Artifact artifact = new Artifact();
                    artifact.setArtifactId("test-spec");
                    artifact.setArtifactType("TEST-SPEC");
                    artifact.setName("test script");
                    artifacts.add(artifact);
                }

            }
            {
                ArtifactBlock integrationTest = new ArtifactBlock();
                integrationTest.setId(UUID.randomUUID().toString());
                integrationTest.setBlockType("ARTIFACT");
                integrationTest.setName("Screen implementation");
                implSectionBlocks.add(integrationTest);

                List<Artifact> artifacts = new ArrayList<>();
                integrationTest.setArtifacts(artifacts);
                {
                    Artifact artifact = new Artifact();
                    artifact.setArtifactId("screen");
                    artifact.setArtifactType("UI");
                    artifact.setUrn("home.png");
                    artifact.setName("visit screen - impl");
                    artifacts.add(artifact);
                }
            }

        }

        storyDoc.setBlocks(docBlocks);
        return storyDoc;
    }

}
