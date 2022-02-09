import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ModalService} from "../common/modal-service";
import {
  CreateScreenshotDialogData,
  CreateScreenshotDialogInput
} from "./create-screenshot-dialog/create-screenshot-dialog.component";
import {ScreenShotDto} from "../api/models/screen-shot-dto";
import {LinkService} from "../common/link.service";
import {ScreenshotCoordinate} from "../api/models/screenshot-coordinate";
import {ScreenshotCollectionService} from "./screenshot-collection.service";

@Component({
  selector: 'app-screenshot-collection-page',
  templateUrl: './screenshot-collection-page.component.html',
  styleUrls: ['./screenshot-collection-page.component.scss']
})
export class ScreenshotCollectionPageComponent implements OnInit {

  constructor(
    private screenshotCollectionService: ScreenshotCollectionService,
    private route: ActivatedRoute,
    public link: LinkService,
    private modalService: ModalService,
  ) {
  }

  screenshotCollection$ = this.screenshotCollectionService.screenshotCollection$

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
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
    });
  }

// create screenshot dialog
  createScreenshotDialogInput: CreateScreenshotDialogInput

  screenshotTracker(index, item: ScreenShotDto) {
    return item.id.id;
  }

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
    this.screenshotCollectionService.AddScreenshot({
      fileSource: data.fileSource,
      name: data.name
    })
    this.modalService.close(this.getScreenshotDialogId())
  }

  cancelAddScreenshotDialog() {
    this.modalService.close(this.getScreenshotDialogId())
  }

  getScreenshotUrl(screenshot: ScreenShotDto) {
    let coord = <ScreenshotCoordinate>{
      collectionCoordinate: this.screenshotCollectionService.collectionCoord,
      screenShotId: {id: screenshot.id.id}
    };
    return this.link.getScreenshotUrl(coord)
  }

}
