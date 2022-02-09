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

  constructor(private modalservice: ModalService, private documentManagerService: DocumentManagerService) {
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
    this.modalservice.open(this.documentDialogId())
  }

  private closeDocumentDialog() {
    this.modalservice.close(this.documentDialogId())
  }

  // confirmation dialog

  confirmationDialogSpec: ConfirmationDialogSpec

  confirmationDialogId(): string {
    return 'confirmation-dialog'
  }

  private openConfirmationDialog(spec: ConfirmationDialogSpec) {
    this.confirmationDialogSpec = spec
    this.modalservice.open(this.confirmationDialogId())
  }

  private closeConfirmationDialog() {
    this.modalservice.close(this.confirmationDialogId())
  }

  // popup menu

  @ViewChild(PopupMenuComponent) menu: PopupMenuComponent

  storyDoc: StoryDocSummaryDto

  openMenu(e, storyDoc: StoryDocSummaryDto) {
    this.storyDoc = storyDoc
    this.menu.open(e)
    return false
  }

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

  renameDocument() {
    this.openDocumentDialog({
      mode: 'UPDATE',
      data: {
        name: this.storyDoc.name
      },
      confirm: (data) => {
        this.closeDocumentDialog();
        this.confirmRenameDocument(data)
      },
      cancel: () => {
        this.closeDocumentDialog()
      }
    })
  }

  confirmRenameDocument(data: DocumentDialogData) {
    this.documentManagerService.renameDocument({
      storyDocId: this.storyDoc.storyDocId,
      name: data.name
    })
  }

  // delete document

  deleteDocument() {
    this.openConfirmationDialog({
      title: 'Confirmation',
      message: `Delete  '${this.storyDoc.name}' ?`,
      confirm: () => {
        this.closeConfirmationDialog();
        this.confirmDeleteDocument()
      },
      cancel: () => this.closeConfirmationDialog()
    })
  }

  confirmDeleteDocument() {
    this.documentManagerService.deleteDocument(this.storyDoc.storyDocId)
  }


}
