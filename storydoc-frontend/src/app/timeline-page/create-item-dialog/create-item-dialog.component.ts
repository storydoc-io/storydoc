import {ChangeDetectorRef, Component, ElementRef, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {setFocusOn} from "@storydoc/common";

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

  constructor(private changeDetector: ChangeDetectorRef) {}

  @Input()
  input: CreateItemDialogInput

  @ViewChild('description') descriptionField: ElementRef

  ngOnChanges(changes: SimpleChanges): void {
    if (this.input != null) {
      this.formGroup.setValue(this.input.data)
      this.changeDetector.detectChanges()
      setFocusOn(this.descriptionField)
    }
  }

  formGroup: FormGroup = new FormGroup({
    description: new FormControl(null, Validators.required),
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
