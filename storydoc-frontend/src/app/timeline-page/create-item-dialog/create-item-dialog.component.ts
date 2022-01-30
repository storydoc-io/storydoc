import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

export interface CreateItemDialogData {
  description: string,
}

export interface CreateItemDialogInput {
  mode: 'UPDATE' | 'NEW'
  data: CreateItemDialogData
}

@Component({
  selector: 'app-create-item-dialog',
  templateUrl: './create-item-dialog.component.html',
  styleUrls: ['./create-item-dialog.component.scss']
})
export class CreateItemDialogComponent implements OnChanges {

  @Input()
  input: CreateItemDialogInput

  ngOnChanges(changes: SimpleChanges): void {
    if (this.input != null) {
      console.log('value: ', this.input.data)
      this.formGroup.setValue(this.input.data)
    }
  }

  formGroup: FormGroup = new FormGroup({
    description: new FormControl(null , Validators.required),
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
