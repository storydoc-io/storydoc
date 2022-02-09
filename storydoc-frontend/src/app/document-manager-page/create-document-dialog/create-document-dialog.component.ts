import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

export interface DocumentDialogData {
  name?: string
}

export interface DocumentDialogSpec {
  mode: 'UPDATE' | 'NEW'
  data: DocumentDialogData
  cancel: () => void
  confirm: (data: DocumentDialogData) => void
}


@Component({
  selector: 'app-create-document-dialog',
  templateUrl: './create-document-dialog.component.html',
  styleUrls: ['./create-document-dialog.component.scss']
})
export class CreateDocumentDialogComponent implements OnChanges {

  @Input()
  input: DocumentDialogSpec

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
    this.input.cancel.apply(this.input.cancel, [])
  }

  confirm() {
    this.input.confirm.apply(this.input.confirm, [this.formGroup.value])
  }
}
