import { Component, OnInit } from '@angular/core';
import {UiRestControllerService} from "../api/services/ui-rest-controller.service";
import {MockUidto} from "../api/models/mock-uidto";
import {Observable} from "rxjs";
import {ActivatedRoute} from '@angular/router';
import {AddScreenshotDialogData,AddScreenshotDialogInput} from "./add-screenshot-dialog/add-screenshot-dialog.component";
import {ModalService} from "../common/modal-service";
import {ScreenShotCollectionDto} from "../api/models/screen-shot-collection-dto";

@Component({
  selector: 'app-uimockup-page',
  templateUrl: './uimockup-page.component.html',
  styleUrls: ['./uimockup-page.component.scss']
})
export class UIMockupPageComponent implements OnInit {

  constructor(
    private modalService: ModalService,
    private route: ActivatedRoute,
    private uiRestControllerService: UiRestControllerService,
    ) {
  }

  mockUI$: Observable<MockUidto>

  screenshotCollection: ScreenShotCollectionDto

  documentId: string
  blockId: string
  id: string

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.documentId  = params.get('documentId')
      this.blockId = params.get('blockId')
      this.id = params.get('artifactId')
      if (this.id) {
        this.mockUI$ = this.uiRestControllerService.getMockUiUsingGet({
          storyDocId: this.documentId,
          blockId: this.blockId,
          id: this.id
        })
        this.mockUI$.subscribe({ next: mockUI => {
            console.log('params: ', {
              storyDocId: this.documentId,
              blockId: this.blockId,
              id: mockUI.associatedCollections[0].id
            })
            this.uiRestControllerService.getScreenShotCollectionUsingGet({
              storyDocId: this.documentId,
              blockId: this.blockId,
              id: mockUI.associatedCollections[0].id
            }).subscribe({
              next: result => this.screenshotCollection = result
            })
        }})
      }

    });
  }

  // add screenshot dialog
  createScreenshotDialogInput: AddScreenshotDialogInput

  getScreenshotDialogId() {
    return "add-screenshot-dialog"
  }

  openAddScreenshotDialog() {
    this.createScreenshotDialogInput = {
      mode: 'NEW',
      data: {
        screenshot: null,
      }
    }
    this.modalService.open(this.getScreenshotDialogId())
  }


  confirmAddScreenshotDialog(data: AddScreenshotDialogData) {
    console.log('data: ', data)
    this.uiRestControllerService.addScreenshotToUiSceanrioUsingPost({
      storyDocId: this.documentId,
      blockId: this.blockId,
      mockUiId: this.id,
      screenshotId: data.screenshot
    }).subscribe({
      next: value => console.log('added')
    })
    this.modalService.close(this.getScreenshotDialogId())
  }

  cancelAddScreenshotDialog() {
    this.modalService.close(this.getScreenshotDialogId())
  }

}
