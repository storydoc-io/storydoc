import { Component, OnInit } from '@angular/core';
import {DocumentDataService} from "./document-data.service";
import {Observable} from "rxjs";
import {StoryDocDto} from "../api/models/story-doc-dto";
import {BlockDto} from "../api/models/block-dto";
import {ActivatedRoute} from "@angular/router";
import {
  CreateDocumentDialogData,
  CreateDocumentDialogInput
} from "../document-manager-page/create-document-dialog/create-document-dialog.component";
import {CreateBlockDialogData, CreateBlockDialogInput} from "./create-block-dialog/create-block-dialog.component";
import {ModalService} from "../common/modal-service";
import {StoryDocRestControllerService} from "../api/services/story-doc-rest-controller.service";

@Component({
  selector: 'app-document',
  templateUrl: './document.component.html',
  styleUrls: ['./document.component.css']
})
export class DocumentComponent implements OnInit {

  constructor(
    private storyDocRestControllerService: StoryDocRestControllerService,
    private documentDataService: DocumentDataService,
    private route: ActivatedRoute,
    private modalservice: ModalService, ) {
  }

  id : string;
  doc$ : Observable<StoryDocDto>

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.id = params.get('id')
      if (this.id) {
        this.refresh();
      }
    });
  }

  private refresh() {
    this.doc$ = this.documentDataService.getDocument(this.id)
  }

  numbering(block: BlockDto): string {
    if (!block) return ''
    let val =  ''
    // @ts-ignore
    for(let nr of block.numbering) {
      val += (val.length==0 ? '' :'.') + nr
    }
    return val
  }


  // create block dialog

  createBlockDialogInput: CreateBlockDialogInput

  openAddBlockDialog() {
    this.createBlockDialogInput = {
      mode: 'NEW',
      data: {
        name: null
      }
    }
    this.modalservice.open("add-block-dialog")
  }

  confirmAddBlockDialog(data: CreateBlockDialogData) {
    this.storyDocRestControllerService.addBlockUsingPost({ id: this.id , name: data.name}).subscribe({
      next: value => this.refresh()
    })
    this.modalservice.close("add-block-dialog")
  }

  cancelAddBlockDialog() {
    this.modalservice.close("add-block-dialog")
  }

}
