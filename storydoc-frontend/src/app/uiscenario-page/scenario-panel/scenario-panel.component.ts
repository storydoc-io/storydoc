import {Component, OnInit} from '@angular/core';
import {ScreenShotCollectionDto, ScreenshotCoordinate, TimeLineDto, TimeLineItemDto, TimeLineItemId, TimeLineModelDto, UiScenarioDto} from "@storydoc/models";
import {TimeLineSelectionState, UIScenarioService} from "../uiscenario.service";
import {Observable} from "rxjs";

@Component({
  selector: 'app-scenario-panel',
  templateUrl: './scenario-panel.component.html',
  styleUrls: ['./scenario-panel.component.scss']
})
export class ScenarioPanelComponent implements OnInit {

  constructor(
    private uiScenarioService: UIScenarioService
  ) {
  }

  selectedTimeLineItem: TimeLineItemDto = null

  uiScenario$: Observable<UiScenarioDto> = this.uiScenarioService.uiScenario$

  timeLineModel$: Observable<TimeLineModelDto> = this.uiScenarioService.timeLineModel$

  screenshotCollection: ScreenShotCollectionDto


  ngOnInit(): void {
  }

  allowDrop(ev: DragEvent) {
    ev.preventDefault();
  }

  doDrop(ev: any, timeLineItemId: TimeLineItemId) {
    ev.preventDefault();
    var data = ev.dataTransfer.getData("text");
    let screenshotCoordinate: ScreenshotCoordinate = <ScreenshotCoordinate>JSON.parse(data)
    this.uiScenarioService.addScreenshotToScenario(screenshotCoordinate, timeLineItemId)
  }

  assignedScreenshot(uiScenario: UiScenarioDto, item: TimeLineItemDto): ScreenshotCoordinate {
    if (!item) return null
    return uiScenario.screenshots
      .find(screenshot => screenshot.timeLineItemId.id == item.itemId.id)
      ?.screenshotCoordinate;
  }

  selectedTimeLine(timeLineModel: TimeLineModelDto, uiScenario: UiScenarioDto): TimeLineDto {
    if (!timeLineModel || !uiScenario || !uiScenario.timeLineModelCoordinate || !uiScenario.timeLineId) return null
    return this.asArray(timeLineModel.timeLines).find(timeLine => timeLine.timeLineId.id === uiScenario.timeLineId?.id)
  }

  asArray(timeLines: { [p: string]: TimeLineDto }): TimeLineDto[] {
    return Object.keys(timeLines).map(key => timeLines[key])
  }

  select(timeLineItem: TimeLineItemDto) {
    this.selectedTimeLineItem = timeLineItem
  }

  isSelected(timeLineItem: TimeLineItemDto):boolean {
    return this.selectedTimeLineItem?.itemId.id === timeLineItem?.itemId.id
  }

}
