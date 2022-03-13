import {Component, OnChanges, OnDestroy, OnInit, SimpleChanges} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {TimeLineDto, TimeLineId, TimeLineModelCoordinate, TimeLineModelSummaryDto} from "@storydoc/models";
import {TimeLineControllerService} from "@storydoc/services";
import {UIScenarioService} from "../uiscenario.service";
import {Subscription} from "rxjs";

export interface TimeLineSelection {
  timeLineId: TimeLineId
}

@Component({
  selector: 'app-time-line-selection-panel',
  templateUrl: './time-line-selection-panel.component.html',
  styleUrls: ['./time-line-selection-panel.component.scss']
})
export class TimeLineSelectionPanelComponent implements OnInit, OnDestroy{

  constructor(
    private timeLineControllerService: TimeLineControllerService,
    private uiScenarioService: UIScenarioService
  ) {
  }

  timeLineModel$ = this.uiScenarioService.timeLineModel$

  timeLineId$ = this.uiScenarioService.timeLineId$

  private subscriptions: Subscription[] = []

  ngOnInit(): void {
    this.subscriptions.push(this.timeLineId$.subscribe(timelineId => {
      this.timeLineControl.setValue(timelineId, {onlySelf: true})
    }))
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }

  formGroup: FormGroup = new FormGroup({
    timeLine: new FormControl(),
  })

  private get timeLineControl(): FormControl {
    return <FormControl> this.formGroup.get('timeLine')
  }

  onTimeLineChange() {
    let timeLineId = this.timeLineControl.value;
    this.uiScenarioService.setScenarioTimeLine(timeLineId)
  }

  timelinesAsArray(timeLines: { [p: string]: TimeLineDto }): TimeLineDto[] {
    return Object.keys(timeLines).map(key => timeLines[key])
  }

}
