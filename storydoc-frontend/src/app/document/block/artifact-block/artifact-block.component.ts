import {Component, Input, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {UiBlockDto} from "../../../api/models/ui-block-dto";
import {ArtifactDto} from "../../../api/models/artifact-dto";
import {StoryDocId} from "../../../api/models/story-doc-id";

@Component({
  selector: 'app-artifact-block',
  templateUrl: './artifact-block.component.html',
  styleUrls: ['./artifact-block.component.scss']
})
export class ArtifactBlockComponent {

  constructor() {
  }

  @Input()
  documentId: StoryDocId

  @Input()
  artifacts: Array<ArtifactDto>

  icon(artifactType: string): string {
    if (artifactType == 'UI') {
      return '/assets/artifact-ui-design.png'
    } else if (artifactType == 'UI-SCENARIO') {
      return '/assets/artifact-ui-scenario.png'
    } else if (artifactType == 'DB-CONNECTION-SETTINGS') {
      return '/assets/db-connection-settings.png'
    } else if (artifactType == 'DB-SNAPSHOT') {
      return '/assets/db-snapshot.png'
    } else if (artifactType == 'TEST-SPEC') {
      return '/assets/test-script.png'
    } else if (artifactType == 'CODE-TRACE') {
      return '/assets/test-script.png'
    }
    return ''
  }

  editorUrl(artifact: ArtifactDto): String[] {
    if (artifact.artifactType == 'UI') {
      return ['/ui-mockup/', 'document', this.documentId.id, 'artifact', artifact.artifactId.id]
    } else if (artifact.artifactType == 'UI-SCENARIO') {
      return ['/ui-scenario', 'document', this.documentId.id, 'artifact', artifact.artifactId.id]
    } else if (artifact.artifactType == 'DB-CONNECTION-SETTINGS') {
      return ['/db-connection-settings', 'document', this.documentId.id, 'artifact', artifact.artifactId.id]
    } else if (artifact.artifactType == 'CODE-TRACE') {
      return ['/code-trace', 'document', this.documentId.id, 'artifact', artifact.artifactId.id]
    }

    return ['/error-unknown-block-type']
  }

}
