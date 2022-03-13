import {Component, ElementRef, Input, SimpleChanges, ViewChild} from '@angular/core';
import {AbstractControl, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";

export interface ScreenshotDialogData {
  name: string,
  file: string,
  fileSource: any,
  fileSize: number
}

export interface ScreenshotDialogSpec {
  mode: 'UPDATE' | 'NEW'
  data: ScreenshotDialogData
  confirm: (data: ScreenshotDialogData) => void
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
  spec: ScreenshotDialogSpec

  @ViewChild('fileElement') fileElement: ElementRef;

  ngOnChanges(changes: SimpleChanges): void {
    if (this.spec != null) {
      if (this.spec.mode == 'NEW') {
        this.newFormGroup.setValue(this.spec.data)
      } else {
        this.editFormGroup.setValue(this.spec.data)
      }
    }
  }

  // new
  newFormGroup: FormGroup = new FormGroup({
    name: new FormControl(null, Validators.required),
    file: new FormControl('', [Validators.required]),
    fileSource: new FormControl('', [Validators.required]),
    fileSize: new FormControl(0, [this.validFileSize()])
  })

  get newNameControl(): FormControl {
    return <FormControl>this.newFormGroup.get('name')
  }

  get fileSizeControl(): FormControl {
    return <FormControl> this.newFormGroup.get('fileSize')
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
      this.newFormGroup.patchValue({
        fileSource: file
      });
      this.fileSizeControl.setValue(file.size)
      if (!this.newNameControl.value) {
        this.newNameControl.setValue(file.name)
      }
    }
  }

  // edit

  editFormGroup: FormGroup = new FormGroup({
    name: new FormControl(null, Validators.required),
  })



  cancel() {
    this.spec.cancel.apply(this.spec.cancel, [])
  }

  save() {
    this.spec.confirm.apply(this.spec.confirm, this.spec.mode=='NEW' ? [this.newFormGroup.value] : [this.editFormGroup.value])
  }

  invalidFile(): boolean {
    return !this.newFormGroup.get('fileSize').valid
  }
}
