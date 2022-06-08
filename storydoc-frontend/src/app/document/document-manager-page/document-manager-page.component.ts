import {Component, ViewChild} from '@angular/core';
import {ConfirmationDialogSpec, ModalService, PopupMenuComponent} from "@storydoc/common";
import {StoryDocSummaryDto} from "@storydoc/models";
import {DocumentDialogData, DocumentDialogSpec} from './create-document-dialog/create-document-dialog.component'
import {DocumentManagerService} from "./document-manager.service";

@Component({
  selector: 'app-story-manager-page',
  templateUrl: './document-manager-page.component.html',
  styleUrls: ['./document-manager-page.component.scss']
})
export class DocumentManagerPageComponent {

  constructor(private modalService: ModalService, private documentManagerService: DocumentManagerService) {
  }

  // document list

  summaries$ = this.documentManagerService.summaries$

  // document dialog

  documentDialogSpec: DocumentDialogSpec

  documentDialogId(): string {
    return 'document-dialog'
  }

  private openDocumentDialog(spec: DocumentDialogSpec) {
    this.documentDialogSpec = spec
    this.modalService.open(this.documentDialogId())
  }

  private closeDocumentDialog() {
    this.modalService.close(this.documentDialogId())
  }

  // confirmation dialog

  confirmationDialogSpec: ConfirmationDialogSpec

  confirmationDialogId(): string {
    return 'confirmation-dialog'
  }

  private openConfirmationDialog(spec: ConfirmationDialogSpec) {
    this.confirmationDialogSpec = spec
    this.modalService.open(this.confirmationDialogId())
  }

  private closeConfirmationDialog() {
    this.modalService.close(this.confirmationDialogId())
  }

  // popup menu

  @ViewChild(PopupMenuComponent) menu: PopupMenuComponent

  openMenu(event: MouseEvent, storyDocSummaryDto: StoryDocSummaryDto) {
    this.menu.items = [
      {
        label: 'Rename',
        onClick: () => this.renameDocument(storyDocSummaryDto)
      },
      {
        label: 'Delete',
        onClick: () => this.deleteDocument(storyDocSummaryDto)
      }
    ]
    this.menu.open(event)
    return false
  }

  storyDoc: StoryDocSummaryDto

  // add document

  addDocument() {
    this.openDocumentDialog({
      mode: 'NEW',
      data: {
        name: null
      },
      confirm: (data) => {
        this.closeDocumentDialog();
        this.confirmAddDocument(data)
      },
      cancel: () => {
        this.closeDocumentDialog()
      }
    });
  }

  confirmAddDocument(data: DocumentDialogData) {
    this.documentManagerService.addDocument(data.name)
  }

  // rename document

  renameDocument(storyDoc: StoryDocSummaryDto) {
    this.openDocumentDialog({
      mode: 'UPDATE',
      data: {
        name: storyDoc.name
      },
      confirm: (data) => {
        this.closeDocumentDialog();
        this.confirmRenameDocument(data, storyDoc)
      },
      cancel: () => {
        this.closeDocumentDialog()
      }
    })
  }

  confirmRenameDocument(data: DocumentDialogData, storyDoc: StoryDocSummaryDto) {
    this.documentManagerService.renameDocument({
      storyDocId: storyDoc.storyDocId,
      name: data.name
    })
  }

  // delete document

  deleteDocument(storyDoc: StoryDocSummaryDto) {
    this.openConfirmationDialog({
      title: 'Confirmation',
      message: `Delete  '${storyDoc.name}' ?`,
      confirm: () => {
        this.closeConfirmationDialog();
        this.confirmDeleteDocument(storyDoc)
      },
      cancel: () => this.closeConfirmationDialog()
    })
  }

  confirmDeleteDocument(storyDoc: StoryDocSummaryDto) {
    this.documentManagerService.deleteDocument(storyDoc.storyDocId)
  }


}
