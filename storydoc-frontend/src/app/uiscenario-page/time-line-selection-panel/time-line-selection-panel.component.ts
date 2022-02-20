import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {TimeLineDto, TimeLineId, TimeLineModelCoordinate, TimeLineModelSummaryDto} from "@storydoc/models";
import {TimeLineControllerService} from "@storydoc/services";
import {UIScenarioService} from "../uiscenario.service";

export interface TimeLineSelection {
  timeLineModelCoordinate: TimeLineModelCoordinate
  timeLineId: TimeLineId
}

@Component({
  selector: 'app-time-line-selection-panel',
  templateUrl: './time-line-selection-panel.component.html',
  styleUrls: ['./time-line-selection-panel.component.scss']
})
export class TimeLineSelectionPanelComponent implements OnInit {

  constructor(
    private timeLineControllerService: TimeLineControllerService,
    private uiScenarioService: UIScenarioService
  ) {
  }

  timeLineModelSelection$ = this.uiScenarioService.timeLineModelSelection$

  timeLineSelection$ = this.uiScenarioService.timeLineSelection$

  formGroup: FormGroup = new FormGroup({
    timeLine: new FormControl(),
    timeLineModel: new FormControl()
  })

  compareTimeLineModel(s1: TimeLineModelSummaryDto, s2: TimeLineModelSummaryDto): boolean {
    return s1?.timeLineModelCoordinate?.timeLineModelId.id === s2?.timeLineModelCoordinate?.timeLineModelId.id
  }

  ngOnInit(): void {
    this.formGroup.setValue({
      timeLineModel: null,
      timeLine: null
    })
    this.timeLineModelSelection$.subscribe({
      next: dto => {
        this.formGroup.get('timeLineModel').setValue(dto?.selectedCoord)
      }
    })
  }

  onTimeLineModelChange() {
    let coord: TimeLineModelCoordinate = this.formGroup.get('timeLineModel').value;
    this.uiScenarioService.selectTimeLineModel(coord)
  }

  onTimeLineChange() {
    let timeLineModelCoordinate = this.formGroup.get('timeLineModel').value;
    let timeLineId = this.formGroup.get('timeLine').value;
    this.uiScenarioService.setScenarioTimeLine({timeLineModelCoordinate, timeLineId})
  }


  asArray(timeLines: { [p: string]: TimeLineDto }): TimeLineDto[] {
    return Object.keys(timeLines).map(key => timeLines[key])
  }

}
