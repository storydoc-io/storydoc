import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {UiRestControllerService} from "../api/services/ui-rest-controller.service";
import {ActivatedRoute} from "@angular/router";
import {Observable} from "rxjs";
import {ScreenShotCollectionDto} from "../api/models/screen-shot-collection-dto";
import {ModalService} from "../common/modal-service";
import {
  CreateScreenshotDialogData,
  CreateScreenshotDialogInput
} from "./create-screenshot-dialog/create-screenshot-dialog.component";
import {HttpClient} from "@angular/common/http";
import {ScreenShotDto} from "../api/models/screen-shot-dto";
import {ScreenshotCollectionCoordinate} from "../api/models/screenshot-collection-coordinate";
import {LinkService} from "../common/link.service";
import {share} from "rxjs/operators";
import {ScreenshotCoordinate} from "../api/models/screenshot-coordinate";

@Component({
  selector: 'app-screenshot-collection-page',
  templateUrl: './screenshot-collection-page.component.html',
  styleUrls: ['./screenshot-collection-page.component.scss']
})
export class ScreenshotCollectionPageComponent implements OnInit {

  constructor(
    private uiRestControllerService: UiRestControllerService,
    private route: ActivatedRoute,
    public link: LinkService,
    private modalService: ModalService,
    private http: HttpClient) {
  }

  screenshotCollection$: Observable<ScreenShotCollectionDto>

  documentId: string
  blockId: string
  id: string

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.documentId  = params.get('documentId')
      this.blockId = params.get('blockId')
      this.id = params.get('artifactId')
      if (this.id) {
        console.log('params: ', {
          storyDocId: this.documentId,
          blockId: this.blockId,
          id: this.id
        })
        this.reload();
      }
    });
  }

  private reload() {
    this.screenshotCollection$ = this.uiRestControllerService.getScreenShotCollectionUsingGet({
      storyDocId: this.documentId,
      blockId: this.blockId,
      id: this.id
    }).pipe(share())
  }

// create screenshot dialog
  createScreenshotDialogInput: CreateScreenshotDialogInput

  getScreenshotDialogId() {
    return "create-screenshot-dialog"
  }

  openAddScreenshotDialog() {
    this.createScreenshotDialogInput = {
      mode: 'NEW',
      data: {
        name: null,
        file: null,
        fileSource: null
      }
    }
    this.modalService.open(this.getScreenshotDialogId())
  }


  confirmAddScreenshotDialog(data: CreateScreenshotDialogData) {
    const formData = new FormData();
    formData.append('file', data.fileSource);
    formData.set('storyDocId', this.documentId)
    formData.set('blockId', this.blockId)
    formData.set('screenshotCollectionId', this.id)
    formData.set('name', data.name)
    this.http.post('http://localhost:4200/api/ui/screenshot', formData).subscribe({
      next: value => this.reload()
    })
    this.modalService.close(this.getScreenshotDialogId())
  }

  cancelAddScreenshotDialog() {
    this.modalService.close(this.getScreenshotDialogId())
  }

  getScreenshotUrl(screenshot: ScreenShotDto) {
    let coord = <ScreenshotCoordinate>{
      collectionCoordinate: {
        blockCoordinate: {
          storyDocId: { id: this.documentId},
          blockId: { id : this.blockId}
        },
        screenShotCollectionId: { id: this.id }
      },
      screenShotId: { id : screenshot.id.id }
    };
    console.log('coord:', coord)
    return this.link.getScreenshotUrl(coord)
  }

}
