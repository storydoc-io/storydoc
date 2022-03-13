import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {Observable} from "rxjs";
import {ScreenshotCollectionCoordinate, ScreenShotCollectionDto, ScreenshotCollectionSummaryDto, ScreenshotCoordinate, ScreenShotId} from "@storydoc/models";
import {UiRestControllerService} from "@storydoc/services";
import {UIScenarioService} from "../uiscenario.service";

@Component({
  selector: 'app-screenshot-panel',
  templateUrl: './screenshot-panel.component.html',
  styleUrls: ['./screenshot-panel.component.scss']
})
export class ScreenshotPanelComponent implements OnInit {

  constructor(
    private uiScenarioService: UIScenarioService,
    private uiRestControllerService: UiRestControllerService
  ) {
  }

  screenshotCollectionCoord$ = this.uiScenarioService.screenshotCollectionCoord$
  collectionCoordinate

  screenshotCollection$ = this.uiScenarioService.screenshotCollection$

  collections$ = this.uiScenarioService.screenshotCollections$

  ngOnInit(): void {
    this.screenshotCollectionCoord$.subscribe(coord => {
      this.collectionControl.setValue(coord, {onlySelf: true})
      this.collectionCoordinate = coord })
  }

  formGroup = new FormGroup({
    collection: new FormControl(null)
  });

  get collectionControl() : FormControl {
    return <FormControl> this.formGroup.get('collection')
  }

  onCollectionChange() {
    if(this.collectionControl.value) {
      this.uiScenarioService.selectScreenshotCollection(this.collectionControl.value)
    }
  }

  screenshotCoordinate(screenshotId: ScreenShotId): ScreenshotCoordinate {
    return <ScreenshotCoordinate>{
      collectionCoordinate: this.collectionCoordinate,
      screenShotId: screenshotId
    }
  }
}
