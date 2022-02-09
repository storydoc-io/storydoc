import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {TimeLineControllerService} from "../../api/services/time-line-controller.service";
import {TimeLineModelCoordinate} from "../../api/models/time-line-model-coordinate";
import {TimeLineDto} from "../../api/models/time-line-dto";
import {TimeLineId} from "../../api/models/time-line-id";
import {UIScenarioService} from "../uiscenario.service";
import {TimeLineModelSummaryDto} from "../../api/models/time-line-model-summary-dto";

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
  timeLineModel$ = this.uiScenarioService.timeLineModel$

  formGroup: FormGroup = new FormGroup({
    timeLine: new FormControl(),
    timeLineModel: new FormControl()
  })

  compareTimeLineModel(s1: TimeLineModelSummaryDto, s2: TimeLineModelSummaryDto): boolean {
//    console.log('s1 ', s1)
//    console.log('s2 ', s2)
    let result = s1?.timeLineModelCoordinate?.timeLineModelId.id === s2?.timeLineModelCoordinate?.timeLineModelId.id
//    console.log('result: ', result)
    return result
  }

  ngOnInit(): void {
    this.formGroup.setValue({
      timeLineModel: null,
      timeLine: null
    })
    this.timeLineModelSelection$.subscribe({
      next: dto => {
        console.log('setting timeLineModel to ', dto)
        this.formGroup.get('timeLineModel').setValue(dto?.selectedTimeLineModelSummary.timeLineModelCoordinate)
      }
    })
  }

  onTimeLineModelChange() {
    console.log('onTimeLineModelChange')
    let coord: TimeLineModelCoordinate = this.formGroup.get('timeLineModel').value;
    console.log('onTimeLineModelChange value', coord)
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
