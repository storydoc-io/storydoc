import {Component, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {ArtifactDto} from "../../../api/models/artifact-dto";
import {StoryDocId} from "../../../api/models/story-doc-id";
import {ModalService} from "../../../common/modal-service";
import {ArtifactDialogSpec, ArtifactDialogData} from "../../create-artifact-dialog/create-artifact-dialog.component";
import {BlockId} from "../../../api/models/block-id";
import {UiRestControllerService} from "../../../api/services/ui-rest-controller.service";
import {ArtifactDataService, ArtifactDescriptor} from "./artifact-data.service";
import {TimeLineControllerService} from "../../../api/services/time-line-controller.service";
import {DocumentDataService} from "../../document-data.service";
import {PopupMenuComponent} from "../../../common/popup-menu/popup-menu.component";
import {BlockDto} from "../../../api/models/block-dto";
import {BlockDialogSpec} from "../../create-block-dialog/create-block-dialog.component";
import {ConfirmationDialogSpec} from "../../../common/confirmation-dialog/confirmation-dialog.component";

@Component({
  selector: 'app-artifact-block',
  templateUrl: './artifact-block.component.html',
  styleUrls: ['./artifact-block.component.scss']
})
export class ArtifactBlockComponent {

  constructor(
    private modalservice: ModalService,
    private documentDataService: DocumentDataService,
    private artifactDataService: ArtifactDataService,
  ) {
  }

  @Input()
  documentId: StoryDocId

  @Input()
  blockId: BlockId

  @Input()
  artifacts: Array<ArtifactDto>

  @Output()
  onBlockChanged = new EventEmitter()

  refresh() {
    this.onBlockChanged.emit()
  }

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

  // artifact dialog

  artifactDialogSpec: ArtifactDialogSpec

  dialogId(): string {
    return 'add-artifact-dialog-'+ this.blockId.id
  };


  openArtifactDialog(artifactDialogSpec: ArtifactDialogSpec) {
    this.artifactDialogSpec = artifactDialogSpec
    this.modalservice.open(this.dialogId())
  }

  closeArtifactDialog() {
    this.modalservice.close(this.dialogId())
  }

  // confirmation dialog
  confirmationDialogId(): string {
    return 'confirmation-dialog-' + this.blockId.id
  }
  confirmationDialogSpec: ConfirmationDialogSpec

  openConfirmationDialog(confirmationDialogSpec: ConfirmationDialogSpec) {
    this.confirmationDialogSpec = confirmationDialogSpec
    this.modalservice.open(this.confirmationDialogId())
  }

  closeConfirmationDialog() {
    this.modalservice.close(this.confirmationDialogId())
  }



  addArtifact() {
    this.openArtifactDialog(
    this.artifactDialogSpec = {
      mode: 'NEW',
      data: {
        name: null,
        artifactType: null
      },
      confirm: (data) => { this.closeArtifactDialog(), this.confirmAddArtifact(data) },
      cancel: () => this.closeArtifactDialog()

    })
  }

  confirmAddArtifact(formData: ArtifactDialogData) {
    this.documentDataService.addArtifact({
      artifactType: formData.artifactType,
      blockId: this.blockId.id,
      name: formData.name
    })
  }

  @ViewChild(PopupMenuComponent) menu:PopupMenuComponent

  openMenu(event: MouseEvent, artifact: ArtifactDto) {
    this.menu.items = [
      {
        label: 'Rename',
        onClick: () => this.renameArtifact(artifact)
      },
      { label: 'Delete',
        onClick: () => this.deleteArtifact(artifact)
      }
    ]
    this.menu.open(event)
    return false
  }

  private renameArtifact(artifact: ArtifactDto) {
    this.openArtifactDialog({
      mode: 'UPDATE',
      data: {
        name: artifact.name,
        artifactType: artifact.artifactType
      },
      confirm: (data)=> { this.closeArtifactDialog(); this.confirmRenameArtifact(artifact, data)},
      cancel: () => this.closeArtifactDialog()
    })
  }

  private confirmRenameArtifact(artifact: ArtifactDto, data: ArtifactDialogData) {
    this.documentDataService.renameArtifact({
      storyDocId: this.documentId,
      blockId: this.blockId,
      artifactId: artifact.artifactId,
      name: data.name
    })
  }

  private deleteArtifact(artifact: ArtifactDto) {
    this.openConfirmationDialog({
      title: 'Confirm Delete',
      message: `Delete ${artifact.name} ?`,
      confirm: ()=> { this.closeConfirmationDialog(), this.confirmDeleteArtifact(artifact) },
      cancel: () => this.closeConfirmationDialog()
    })
  }


  private confirmDeleteArtifact(artifact: ArtifactDto) {

  }
}
