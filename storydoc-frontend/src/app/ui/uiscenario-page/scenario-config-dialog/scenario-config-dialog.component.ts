import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {UIScenarioService} from "../uiscenario.service";
import {TimeLineModelCoordinate, TimeLineModelSummaryDto} from "@storydoc/models";

export interface ScenarioConfigDialogData  {
  timeLineModel: TimeLineModelCoordinate
}

export interface ScenarioConfigDialogSpec  {
  data: ScenarioConfigDialogData
  confirm: (data: ScenarioConfigDialogData)=> void
  cancel: () => void
}

@Component({
  selector: 'app-scenario-config-dialog',
  templateUrl: './scenario-config-dialog.component.html',
  styleUrls: ['./scenario-config-dialog.component.scss']
})
export class ScenarioConfigDialogComponent implements OnInit {

  constructor(private service: UIScenarioService) { }

  timeLineModels$ = this.service.timeLineModels$

  @Input()
  spec: ScenarioConfigDialogSpec


  ngOnInit(): void {
    this.service.loadAssociatedTimeLineModels()
  }

  formGroup: FormGroup = new FormGroup({
      timeLineModel : new FormControl(null, [Validators.required])
  })

  compareTimeLineModel(s1: TimeLineModelSummaryDto, s2: TimeLineModelSummaryDto): boolean {
    return s1?.timeLineModelCoordinate?.timeLineModelId.id === s2?.timeLineModelCoordinate?.timeLineModelId.id
  }

  cancel() {
    this.spec.cancel()
  }

  confirm() {
    this.spec.confirm(this.formGroup.value)
  }
}
