import {Component, OnInit} from '@angular/core';
import {UiScenarioDto} from "../api/models/ui-scenario-dto";
import {Observable} from "rxjs";
import {ActivatedRoute} from '@angular/router';
import {ModalService} from "../common/modal-service";
import {ScreenShotCollectionDto} from "../api/models/screen-shot-collection-dto";
import {LinkService} from "../common/link.service";
import {ArtifactBlockCoordinate} from "../api/models/artifact-block-coordinate";
import {TimeLineSelection} from "./time-line-selection-panel/time-line-selection-panel.component";
import {TimeLineModelDto} from "../api/models/time-line-model-dto";
import {ScreenshotCoordinate, TimeLineItemId} from "../api/models";
import {UIScenarioService} from "./uiscenario.service";


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
    private uiScenarioService: UIScenarioService

    ) {
  }

  uiScenario$: Observable<UiScenarioDto> = this.uiScenarioService.uiScenario$

  timeLineModel$: Observable<TimeLineModelDto> = this.uiScenarioService.timeLineModel$

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
        this.uiScenarioService.loadUIScenario({
          storyDocId: { id: this.documentId},
          blockId: { id: this.blockId},
          uiScenarioId: this.id
        })
      }
    });
  }



  // timeline selection

  selectTimeLine(selection: TimeLineSelection) {
    this.uiScenarioService.setScenarioTimeLine(selection)
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
    this.uiScenarioService.addScreenshotToScenario(screenshotCoordinate, timeLineItemId)
  }

  screenshotCoord(uiScenario: UiScenarioDto, itemId: TimeLineItemId): ScreenshotCoordinate {
    return uiScenario.screenshots
      .find( screenshot => screenshot.timeLineItemId.id==itemId.id)
      ?.screenshotCoordinate;
  }
}
