import {Component, EventEmitter, Input, Output, ViewChild} from '@angular/core';
import {ConfirmationDialogSpec, ModalService, PopupMenuComponent} from "@storydoc/common";
import {ArtifactDto, BlockId, StoryDocId} from "@storydoc/models";
import {DocumentDataService} from "../../document-data.service";
import {ArtifactDialogData, ArtifactDialogSpec} from "../../create-artifact-dialog/create-artifact-dialog.component";
import {ArtifactDataService} from "./artifact-data.service";

@Component({
  selector: 'app-artifact-block',
  templateUrl: './artifact-block.component.html',
  styleUrls: ['./artifact-block.component.scss']
})
export class ArtifactBlockComponent {

  constructor(
    private modalService: ModalService,
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
    return descriptor ? descriptor.icon : ''
  }

  // artifact dialog

  artifactDialogSpec: ArtifactDialogSpec

  dialogId(): string {
    return 'add-artifact-dialog-' + this.blockId.id
  };


  openArtifactDialog(artifactDialogSpec: ArtifactDialogSpec) {
    this.artifactDialogSpec = artifactDialogSpec
    this.modalService.open(this.dialogId())
  }

  closeArtifactDialog() {
    this.modalService.close(this.dialogId())
  }

  // confirmation dialog
  confirmationDialogId(): string {
    return 'confirmation-dialog-' + this.blockId.id
  }

  confirmationDialogSpec: ConfirmationDialogSpec

  openConfirmationDialog(confirmationDialogSpec: ConfirmationDialogSpec) {
    this.confirmationDialogSpec = confirmationDialogSpec
    this.modalService.open(this.confirmationDialogId())
  }

  closeConfirmationDialog() {
    this.modalService.close(this.confirmationDialogId())
  }


  addArtifact() {
    this.openArtifactDialog(
      this.artifactDialogSpec = {
        mode: 'NEW',
        data: {
          name: null,
          artifactType: null
        },
        confirm: (data) => {
          this.closeArtifactDialog(), this.confirmAddArtifact(data)
        },
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

  @ViewChild(PopupMenuComponent) menu: PopupMenuComponent

  openMenu(event: MouseEvent, artifact: ArtifactDto) {
    this.menu.items = [
      {
        label: 'Rename',
        onClick: () => this.renameArtifact(artifact)
      },
      {
        label: 'Delete',
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
      confirm: (data) => {
        this.closeArtifactDialog();
        this.confirmRenameArtifact(artifact, data)
      },
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
      confirm: () => {
        this.closeConfirmationDialog(), this.confirmDeleteArtifact(artifact)
      },
      cancel: () => this.closeConfirmationDialog()
    })
  }


  private confirmDeleteArtifact(artifact: ArtifactDto) {
    this.documentDataService.deleteArtifact({
      storyDocId: this.documentId,
      blockId: this.blockId,
      artifactId: artifact.artifactId,
    })
  }
}
