import { Component, OnInit } from '@angular/core';
import {UiRestControllerService} from "../api/services/ui-rest-controller.service";
import {UiScenarioDto} from "../api/models/ui-scenario-dto";
import {Observable} from "rxjs";
import {ActivatedRoute} from '@angular/router';
import {AddScreenshotDialogData,AddScreenshotDialogInput} from "./add-screenshot-dialog/add-screenshot-dialog.component";
import {ModalService} from "../common/modal-service";
import {ScreenShotCollectionDto} from "../api/models/screen-shot-collection-dto";
import {LinkService} from "../common/link.service";
import {share} from "rxjs/operators";
import {ArtifactBlockCoordinate} from "../api/models/artifact-block-coordinate";
import {TimeLineSelection} from "./time-line-selection-panel/time-line-selection-panel.component";
import {TimeLineModelCoordinate} from "../api/models/time-line-model-coordinate";
import {TimeLineId} from "../api/models/time-line-id";
import {TimeLineModelDto} from "../api/models/time-line-model-dto";
import {TimeLineControllerService} from "../api/services/time-line-controller.service";
import {CdkDragDrop} from "@angular/cdk/drag-drop";
import {ScreenshotCoordinate, TimeLineItemId} from "../api/models";


@Component({
  selector: 'app-uiscenario-page',
  templateUrl: './uiscenario-page.component.html',
  styleUrls: ['./uiscenario-page.component.scss']
})
export class UIScenarioPageComponent implements OnInit {

  constructor(
    private modalService: ModalService,
    private route: ActivatedRoute,
    public link: LinkService,
    private uiRestControllerService: UiRestControllerService,
    private timeLineControllerService: TimeLineControllerService
    ) {
  }

  uiScenario$: Observable<UiScenarioDto>
  timeLineModelDto: TimeLineModelDto

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
        this.uiScenario$ = this.uiRestControllerService.getMockUiUsingGet({
          storyDocId: this.documentId,
          blockId: this.blockId,
          id: this.id
        }).pipe(share())
        this.uiScenario$.subscribe({ next: uiScenario => {
          this.refreshTimeLineModel(uiScenario.timeLineModelCoordinate, uiScenario.timeLineId)
        }})
      }
    });
  }

  refreshTimeLineModel(timeLineModelCoordinate: TimeLineModelCoordinate, timeLineId: TimeLineId) {
    this.timeLineControllerService.getTimeLineModelUsingGet({
      storyDocId: timeLineModelCoordinate.blockCoordinate.storyDocId.id,
      blockId: timeLineModelCoordinate.blockCoordinate.blockId.id,
      timeLineModelId: timeLineModelCoordinate.timeLineModelId.id
    }).subscribe({
      next: dto => this.timeLineModelDto = dto
    })
  }


  // timeline selection

  selectTimeLine(selection: TimeLineSelection) {
    this.uiRestControllerService.setUiScenarioTimeLineUsingPost({
      storyDocId: this.documentId,
      blockId: this.blockId,
      uiScenarioId: this.id,
      timeLineModelStoryDocId: selection.timeLineModelCoordinate.blockCoordinate.storyDocId.id,
      timeLineModelBlockId:    selection.timeLineModelCoordinate.blockCoordinate.blockId.id,
      timeLineModelId:         selection.timeLineModelCoordinate.timeLineModelId.id,
      timeLineId:              selection.timeLineId.id
    }).subscribe({
      next: value => {}
    })
  }


  blockCoordinate() {
    return <ArtifactBlockCoordinate>{
      blockId: { id : this.blockId} ,
      storyDocId: { id: this.documentId}
    }
  }

  allowDrop(ev: DragEvent) {
    ev.preventDefault();
  }

  doDrop(ev: any, timeLineItemId: TimeLineItemId) {
    ev.preventDefault();
    var data = ev.dataTransfer.getData("text");
    let screenshotCoordinate: ScreenshotCoordinate = <ScreenshotCoordinate> JSON.parse(data)
    this.uiRestControllerService.addScreenshotToUiScenarioUsingPost({
      storyDocId: this.documentId,
      blockId: this.blockId,
      uiScenarioId: this.id,
      screenshotStoryDocId: screenshotCoordinate.collectionCoordinate.blockCoordinate.storyDocId.id,
      screenshotBlockId: screenshotCoordinate.collectionCoordinate.blockCoordinate.blockId.id,
      screenshotCollectionId: screenshotCoordinate.collectionCoordinate.screenShotCollectionId.id,
      screenshotId: screenshotCoordinate.screenShotId.id,
      timeLineItemId: timeLineItemId.id
    }).subscribe({
      next: (value => console.log("screenshot added to ui scenario"))
    })
  }

  screenshotCoord(uiScenario: UiScenarioDto, itemId: TimeLineItemId): ScreenshotCoordinate {
    return uiScenario.screenshots
      .find( screenshot => screenshot.timeLineItemId.id==itemId.id)
      ?.screenshotCoordinate;
  }
}
