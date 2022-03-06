import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ScreenShotCollectionDto} from "@storydoc/models";

export interface AddScreenshotDialogData {
  screenshot: string,
}

export interface AddScreenshotDialogInput {
  mode: 'UPDATE' | 'NEW'
  data: AddScreenshotDialogData
}

@Component({
  selector: 'app-add-screenshot-dialog',
  templateUrl: './add-screenshot-dialog.component.html',
  styleUrls: ['./add-screenshot-dialog.component.scss']
})
export class AddScreenshotDialogComponent {

  @Input()
  input: AddScreenshotDialogInput

  @Input()
  screenshotCollectionDTO: ScreenShotCollectionDto

  formGroup: FormGroup = new FormGroup({
    screenshot: new FormControl(null, Validators.required),
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
