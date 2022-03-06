import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {LinkService, ModalService} from "@storydoc/common";
import {ScreenshotCoordinate, ScreenShotDto} from "@storydoc/models";
import {
  CreateScreenshotDialogData,
  CreateScreenshotDialogInput
} from "./create-screenshot-dialog/create-screenshot-dialog.component";
import {ScreenshotCollectionService} from "./screenshot-collection.service";
import {AdminDataService} from "../../document/admin-data.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-screenshot-collection-page',
  templateUrl: './screenshot-collection-page.component.html',
  styleUrls: ['./screenshot-collection-page.component.scss']
})
export class ScreenshotCollectionPageComponent implements OnInit, OnDestroy {

  constructor(
    private screenshotCollectionService: ScreenshotCollectionService,
    private admin: AdminDataService,
    private route: ActivatedRoute,
    public link: LinkService,
    private modalService: ModalService,
  ) {
  }

  screenshotCollection$ = this.screenshotCollectionService.screenshotCollection$
  settings$ = this.admin.settings$
  maxFileSize : number

  private subscriptions: Subscription[] = []

  ngOnInit(): void {
    this.subscriptions.push(this.route.paramMap.subscribe((params) => {
      let documentId = params.get('documentId')
      let blockId = params.get('blockId')
      let id = params.get('artifactId')
      if (id) {
        this.screenshotCollectionService.initId({
          blockCoordinate: {
            blockId: { id: blockId },
            storyDocId: { id: documentId }
          },
          screenShotCollectionId: {id: id }
        });
      }
    }));
    this.subscriptions.push(this.settings$.subscribe((settings)=> this.maxFileSize = settings?.maxFileSize))
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }

// create screenshot dialog
  screenshotDialogSpec: CreateScreenshotDialogInput

  screenshotTracker(index, item: ScreenShotDto) {
    return item.id.id;
  }

  getScreenshotDialogId() {
    return "create-screenshot-dialog"
  }

  openAddScreenshotDialog() {
    this.screenshotDialogSpec = {
      mode: 'NEW',
      data: {
        name: null,
        file: null,
        fileSource: null,
        fileSize: 0
      },
      confirm: (data) => this.confirmAddScreenshotDialog(data),
      cancel: () => this.cancelAddScreenshotDialog(),
      maxFileSize : this.maxFileSize
    }
    this.modalService.open(this.getScreenshotDialogId())
  }


  confirmAddScreenshotDialog(data: CreateScreenshotDialogData) {
    this.screenshotCollectionService.AddScreenshot({
      fileSource: data.fileSource,
      name: data.name
    }, () => {
      console.log('callback')
    })
    this.modalService.close(this.getScreenshotDialogId())
  }

  cancelAddScreenshotDialog() {
    this.modalService.close(this.getScreenshotDialogId())
  }

  getScreenshotUrl(screenshot: ScreenShotDto) {
    let coord = this.screenshotCoord(screenshot);
    return this.link.getScreenshotUrl(coord)
  }

  screenshotCoord(screenshot: ScreenShotDto) {
    let coord = <ScreenshotCoordinate>{
      collectionCoordinate: this.screenshotCollectionService.collectionCoord,
      screenShotId: {id: screenshot.id.id}
    };
    return coord;
  }
}
