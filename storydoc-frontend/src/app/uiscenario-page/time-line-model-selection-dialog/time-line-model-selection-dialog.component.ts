import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

export interface TimeLineModelSelectionDialogData {
}

export interface TimeLineModelSelectionDialogInput {
  mode: 'UPDATE' | 'NEW'
  data: TimeLineModelSelectionDialogData
}

@Component({
  selector: 'app-time-line-model-selection-dialog',
  templateUrl: './time-line-model-selection-dialog.component.html',
  styleUrls: ['./time-line-model-selection-dialog.component.scss']
})
export class TimeLineModelSelectionDialogComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  @Input()
  input: TimeLineModelSelectionDialogInput

  @Input()

  formGroup: FormGroup = new FormGroup({
  })

  @Output()
  private onConfirm = new EventEmitter()

  @Output()
  private onCancel = new EventEmitter()

  cancel() {
    this.onCancel.emit()
  }

  save() {
    this.onConfirm.emit(this.formGroup.value)
  }



}
