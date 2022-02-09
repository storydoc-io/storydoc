import {Component, Input, OnInit} from '@angular/core';
import {ScreenshotCollectionCoordinate} from "../../api/models/screenshot-collection-coordinate";
import {ScreenshotCollectionSummaryDto} from "../../api/models/screenshot-collection-summary-dto";
import {UiRestControllerService} from "../../api/services/ui-rest-controller.service";
import {FormControl, FormGroup} from "@angular/forms";
import {Observable} from "rxjs";
import {ScreenShotCollectionDto} from "../../api/models/screen-shot-collection-dto";
import {ScreenshotCoordinate, ScreenShotId} from "../../api/models";

@Component({
  selector: 'app-screenshot-panel',
  templateUrl: './screenshot-panel.component.html',
  styleUrls: ['./screenshot-panel.component.scss']
})
export class ScreenshotPanelComponent implements OnInit {

  constructor(
    private uiRestControllerService: UiRestControllerService
  ) { }

  ngOnInit(): void {
  }

  collectionCoordinate: ScreenshotCollectionCoordinate
  screenshotCollection$ : Observable<ScreenShotCollectionDto>


  @Input()
  collections: Array<ScreenshotCollectionSummaryDto>

  formGroup = new FormGroup({
    collection: new FormControl(null)
  });

  refresh() {

  }

  onCollectionChange() {
      let collectionSummary = <ScreenshotCollectionSummaryDto> this.formGroup.get('collection').value
      if (collectionSummary) {
        this.collectionCoordinate = collectionSummary.collectionCoordinate
        this.screenshotCollection$ = this.uiRestControllerService.getScreenShotCollectionUsingGet({
          storyDocId: this.collectionCoordinate.blockCoordinate.storyDocId.id,
          blockId: this.collectionCoordinate.blockCoordinate.blockId.id,
          id: this.collectionCoordinate.screenShotCollectionId.id
        })
        this.screenshotCollection$.subscribe({ next: value => console.log(value)})
      } else {
        this.screenshotCollection$ = null;
      }
  }

  drag(ev: DragEvent, screenshotId: ScreenShotId) {
    let screenshotCoordinate = this.screenshotCoordinate(screenshotId)
    ev.dataTransfer.setData("text", JSON.stringify(screenshotCoordinate));  }

  screenshotCoordinate(screenshotId: ScreenShotId): ScreenshotCoordinate {
    let coord = <ScreenshotCoordinate> {
      collectionCoordinate : this.collectionCoordinate,
      screenShotId: screenshotId
    }
    console.log('coord', coord)
    return coord
  }
}
