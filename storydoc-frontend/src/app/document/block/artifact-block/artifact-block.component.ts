import {Component, Input} from '@angular/core';
import {ArtifactDto} from "../../../api/models/artifact-dto";
import {StoryDocId} from "../../../api/models/story-doc-id";
import {ModalService} from "../../../common/modal-service";
import {CreateArtifactDialogInput, CreateArtifactDialogData} from "../../create-artifact-dialog/create-artifact-dialog.component";
import {BlockId} from "../../../api/models/block-id";
import {UiRestControllerService} from "../../../api/services/ui-rest-controller.service";
import {ArtifactDataService, ArtifactDescriptor} from "./artifact-data.service";

@Component({
  selector: 'app-artifact-block',
  templateUrl: './artifact-block.component.html',
  styleUrls: ['./artifact-block.component.scss']
})
export class ArtifactBlockComponent {

  constructor(
    private modalservice: ModalService,
    private uiRestControllerService : UiRestControllerService,
    private artifactDataService: ArtifactDataService) {
  }

  @Input()
  documentId: StoryDocId

  @Input()
  blockId: BlockId

  @Input()
  artifacts: Array<ArtifactDto>

  // artifact list

  editorUrl(artifact: ArtifactDto): string[] {
    let descriptor = this.artifactDataService.descriptor(artifact.artifactType)
    if (descriptor) {
      return [descriptor.editorUrl, 'd', this.documentId.id, 'b', this.blockId.id, 'a', artifact.artifactId.id]
    }
    return ['/error-unknown-block-type']

  }

  icon(artifact: ArtifactDto) {
    let descriptor = this.artifactDataService.descriptor(artifact.artifactType)
    return descriptor? descriptor.icon : ''
  }

  // create artifact dialog

  createArtifactDialogInput: CreateArtifactDialogInput

  dialogId(): string {
    return 'add-artifact-dialog-'+ this.blockId.id
  };

  openAddArtifactDialog() {
    this.createArtifactDialogInput = {
      mode: 'NEW',
      data: {
        name: null,
        artifactType: null
      }
    }
    this.modalservice.open(this.dialogId())
  }

  confirmAddArtifactDialog(formData: CreateArtifactDialogData) {
    switch (formData.artifactType) {
      case 'io.storydoc.server.ui.domain.MockUI': {
        console.log("ui scenario")
        this.uiRestControllerService.createMockUiUsingPost({
          storyDocId: this.documentId.id,
          blockId: this.blockId.id,
          name: formData.name
        }).subscribe({
          next: value => console.log(value)
        })
        break
      }
      case 'io.storydoc.server.ui.domain.ScreenShotCollection': {
        console.log("screenshots")
        console.log('formData: ', formData)
        this.uiRestControllerService.createScreenShotCollectionUsingPost({
            storyDocId: this.documentId.id,
            blockId: this.blockId.id,
            name: formData.name
        }).subscribe({
          next: value => console.log(value)
        })
        break
      }

    }
    this.modalservice.close(this.dialogId())
  }

  cancelAddArtifactDialog() {
    this.modalservice.close(this.dialogId())
  }

}
