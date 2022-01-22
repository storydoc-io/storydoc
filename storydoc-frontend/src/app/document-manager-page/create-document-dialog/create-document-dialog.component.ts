import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

export interface CreateDocumentDialogData {
  name?: string
}

export interface CreateDocumentDialogInput {
  mode: 'UPDATE' | 'NEW'
  data: CreateDocumentDialogData
}


@Component({
  selector: 'app-create-document-dialog',
  templateUrl: './create-document-dialog.component.html',
  styleUrls: ['./create-document-dialog.component.scss']
})
export class CreateDocumentDialogComponent implements OnChanges {

  @Input()
  input: CreateDocumentDialogInput

  ngOnChanges(changes: SimpleChanges): void {
    if (this.input != null) {
      this.formGroup.setValue(this.input.data)
    }
  }

  formGroup: FormGroup = new FormGroup({
    name: new FormControl(null , Validators.required)
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
