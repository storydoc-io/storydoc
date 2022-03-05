import {Component, ElementRef, Input, SimpleChanges, ViewChild} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";

export interface CreateScreenshotDialogData {
  name: string,
  file: string,
  fileSource: any,
  fileSize: number
}

export interface CreateScreenshotDialogInput {
  mode: 'UPDATE' | 'NEW'
  data: CreateScreenshotDialogData
  confirm: (data: CreateScreenshotDialogData) => void
  cancel: () => void
  maxFileSize: number
}

@Component({
  selector: 'app-create-screenshot-dialog',
  templateUrl: './create-screenshot-dialog.component.html',
  styleUrls: ['./create-screenshot-dialog.component.scss']
})
export class CreateScreenshotDialogComponent {

  @Input()
  spec: CreateScreenshotDialogInput

  @ViewChild('fileElement') fileElement: ElementRef;

  ngOnChanges(changes: SimpleChanges): void {
    if (this.spec != null) {
      this.formGroup.setValue(this.spec.data)
    }
  }

  formGroup: FormGroup = new FormGroup({
    name: new FormControl(null, Validators.required),
    file: new FormControl('', [Validators.required]),
    fileSource: new FormControl('', [Validators.required]),
    fileSize: new FormControl(0, [this.validFileSize()])
  })

  get nameControl(): FormControl {
    return <FormControl>this.formGroup.get('name')
  }

  get fileSizeControl(): FormControl {
    return <FormControl> this.formGroup.get('fileSize')
  }

  validFileSize(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors| null => {
      if (!this.spec) return null
      const invalid = control.value > this.spec.maxFileSize
      return invalid ? {validFileSize: {value: 'File size is too big, max filesize is ' + this.spec.maxFileSize}} : null;
    }
  }


  onFileChange(event) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0]
      this.formGroup.patchValue({
        fileSource: file
      });
      this.fileSizeControl.setValue(file.size)
      if (!this.nameControl.value) {
        this.nameControl.setValue(file.name)
      }
    console.log('formControl: ', this.formGroup.value)
    }
  }

  cancel() {
    this.spec.cancel.apply(this.spec.cancel, [])
  }

  save() {
    this.spec.confirm.apply(this.spec.confirm, [this.formGroup.value])
  }

  invalidFile(): boolean {
    return !this.formGroup.get('fileSize').valid
  }
}
