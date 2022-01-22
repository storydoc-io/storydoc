import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

export interface CreateBlockDialogData {
  name?: string
}

export interface CreateBlockDialogInput {
  mode: 'UPDATE' | 'NEW'
  data: CreateBlockDialogData
}

@Component({
  selector: 'app-create-block-dialog',
  templateUrl: './create-block-dialog.component.html',
  styleUrls: ['./create-block-dialog.component.scss']
})
export class CreateBlockDialogComponent implements OnChanges {

  @Input()
  input: CreateBlockDialogInput

  ngOnChanges(changes: SimpleChanges): void {
    if (this.input != null) {
      this.formGroup.setValue(this.input.data)
    }
  }

  formGroup: FormGroup = new FormGroup({
    name: new FormControl(null, Validators.required)
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
