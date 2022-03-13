import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {LinkService, ModalService, AdminDataService, PopupMenuComponent, ConfirmationDialogSpec} from "@storydoc/common";
import {ScreenshotCoordinate, ScreenShotDto, TimeLineDto, TimeLineItemDto} from "@storydoc/models";
import {
  ScreenshotDialogData,
  ScreenshotDialogSpec
} from "./create-screenshot-dialog/create-screenshot-dialog.component";
import {ScreenshotCollectionService} from "./screenshot-collection.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-screenshot-collection-page',
  templateUrl: './screenshot-collection-page.component.html',
  styleUrls: ['./screenshot-collection-page.component.scss']
})
export class ScreenshotCollectionPageComponent implements OnInit, OnDestroy {

  constructor(
    private screenshotCollectionService: ScreenshotCollectionService,
    private route: ActivatedRoute,
    public link: LinkService,
  ) {
  }

  screenshotCollection$ = this.screenshotCollectionService.screenshotCollection$

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
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
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
