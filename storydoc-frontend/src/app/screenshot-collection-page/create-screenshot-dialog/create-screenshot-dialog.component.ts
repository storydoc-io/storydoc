import {Component, ElementRef, EventEmitter, Input, OnInit, Output, SimpleChanges, ViewChild} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

export interface CreateScreenshotDialogData {
  name: string,
  file: string,
  fileSource: any
}

export interface CreateScreenshotDialogInput {
  mode: 'UPDATE' | 'NEW'
  data: CreateScreenshotDialogData
}

@Component({
  selector: 'app-create-screenshot-dialog',
  templateUrl: './create-screenshot-dialog.component.html',
  styleUrls: ['./create-screenshot-dialog.component.scss']
})
export class CreateScreenshotDialogComponent  {

  @Input()
  input: CreateScreenshotDialogInput

  @ViewChild('fileElement') fileElement: ElementRef;

  ngOnChanges(changes: SimpleChanges): void {
    if (this.input != null) {
      this.formGroup.setValue(this.input.data)
    }
  }

  formGroup: FormGroup = new FormGroup({
    name: new FormControl(null , Validators.required),
    file: new FormControl('', [Validators.required]),
    fileSource: new FormControl('', [Validators.required])
  })

  onFileChange(event) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0]
      this.formGroup.patchValue({
        fileSource: file
      });
    }
  }

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
