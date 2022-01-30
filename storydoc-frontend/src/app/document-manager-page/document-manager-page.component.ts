import {Component, OnInit, ViewChild} from '@angular/core';
import {ModalService} from "../common/modal-service";
import {
  CreateDocumentDialogData,
  CreateDocumentDialogInput
} from './create-document-dialog/create-document-dialog.component'
import {StoryDocRestControllerService} from "../api/services/story-doc-rest-controller.service";
import {StoryDocSummaryDto} from "../api/models/story-doc-summary-dto";
import {Observable} from "rxjs";
import {PopupMenuComponent} from "../common/popup-menu/popup-menu.component";
import {share} from "rxjs/operators";

@Component({
  selector: 'app-story-manager-page',
  templateUrl: './document-manager-page.component.html',
  styleUrls: ['./document-manager-page.component.scss']
})
export class DocumentManagerPageComponent implements OnInit {

  constructor(private modalservice: ModalService, private storyDocRestControllerService: StoryDocRestControllerService) { }


  // list documents

  summaries$: Observable<StoryDocSummaryDto[]>

  ngOnInit(): void {
    this.refresh();
  }

  private refresh() {
    this.summaries$ = this.storyDocRestControllerService.getDocumentsUsingGet().pipe(share())
  }

// create storydoc dialog

  createDocumentDialogInput: CreateDocumentDialogInput

  openAddDocumentDialog() {
    this.createDocumentDialogInput = {
      mode: 'NEW',
      data: {
        name: null
      }
    }
    this.modalservice.open("add-document-dialog")
  }

  confirmAddDocumentDialog(data: CreateDocumentDialogData) {
    console.log('data:', data)
    this.storyDocRestControllerService.createDocumentUsingPost({ name : data.name }).subscribe({
      next: value => this.refresh()
    })

    this.modalservice.close("add-document-dialog")
  }

  cancelAddDocumentDialog() {
    this.modalservice.close("add-document-dialog")
  }

  @ViewChild(PopupMenuComponent) menu:PopupMenuComponent

  openMenu(e) {
    this.menu.open(e)
    return false
  }

  itemSelected(item:number) {
    console.log("Item", item)
  }

}
