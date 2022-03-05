import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Observable, Subscription} from "rxjs";
import {ScreenShotCollectionDto, ScreenshotCoordinate, ScreenShotDto, ScreenShotId} from "@storydoc/models";

@Component({
  selector: 'app-screenshot-collection-panel',
  templateUrl: './screenshot-collection-panel.component.html',
  styleUrls: ['./screenshot-collection-panel.component.scss']
})
export class ScreenshotCollectionPanelComponent implements OnInit, OnDestroy{

  constructor() { }

  @Input()
  screenshotCollection$: Observable<ScreenShotCollectionDto>
  screenshotCollection: ScreenShotCollectionDto

  private subscriptions: Subscription[] = []

  ngOnInit(): void {
      this.subscriptions.push(this.screenshotCollection$.subscribe(screenshotCollectionDTO => this.screenshotCollection=screenshotCollectionDTO))
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }

  screenshotTracker(index, item: ScreenShotDto) {
    return item.id.id;
  }

  dragStart(ev: DragEvent, screenshotId: ScreenShotId) {
    let screenshotCoordinate = this.screenshotCoordinate(screenshotId)
    ev.dataTransfer.setData("text", JSON.stringify(screenshotCoordinate));
  }

  screenshotCoordinate(screenShotId: ScreenShotId) {
    return <ScreenshotCoordinate>{
      collectionCoordinate: this.screenshotCollection.coordinate,
      screenShotId
    };
  }


}
