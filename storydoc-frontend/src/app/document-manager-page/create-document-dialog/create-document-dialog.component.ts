import {ChangeDetectorRef, Component, ElementRef, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {setFocusOn} from "@storydoc/common";

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
export class CreateDocumentDialogComponent implements OnChanges{

  constructor(private changeDetector: ChangeDetectorRef) {}

  @Input()
  spec: DocumentDialogSpec

  @ViewChild('name') nameField: ElementRef

  ngOnChanges(changes: SimpleChanges): void {
    if (this.spec != null) {
      this.formGroup.setValue(this.spec.data)
      this.changeDetector.detectChanges()
      setFocusOn(this.nameField)
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
    this.spec.cancel.apply(this.spec.cancel, [])
  }

  confirm() {
    this.spec.confirm.apply(this.spec.confirm, [this.formGroup.value])
  }
}
