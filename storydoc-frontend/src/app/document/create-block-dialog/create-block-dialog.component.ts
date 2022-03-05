import {ChangeDetectorRef, Component, ElementRef, Input, OnChanges, SimpleChanges, ViewChild} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {setFocusOn} from "@storydoc/common";

export interface BlockDialogData {
  name?: string
}

export interface BlockDialogSpec {
  mode: 'UPDATE' | 'NEW'
  data: BlockDialogData
  confirm: (BlockDialogData) => void
  cancel: () => void
}

@Component({
  selector: 'app-create-block-dialog',
  templateUrl: './create-block-dialog.component.html',
  styleUrls: ['./create-block-dialog.component.scss']
})
export class CreateBlockDialogComponent implements OnChanges {

  constructor(private changeDetector: ChangeDetectorRef) {}

  @Input()
  spec: BlockDialogSpec

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

  cancel() {
    this.spec.cancel.apply(this, [])
  }

  confirm() {
    this.spec.confirm.apply(this, [this.formGroup.value])
  }

}
