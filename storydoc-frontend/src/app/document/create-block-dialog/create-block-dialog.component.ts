import {Component, Input, OnChanges, SimpleChanges} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

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

  @Input()
  spec: BlockDialogSpec

  ngOnChanges(changes: SimpleChanges): void {
    if (this.spec != null) {
      this.formGroup.setValue(this.spec.data)
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
