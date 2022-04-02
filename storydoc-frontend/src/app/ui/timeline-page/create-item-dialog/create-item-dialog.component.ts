import {ChangeDetectorRef, Component, ElementRef, EventEmitter, Input, OnChanges, Output, SimpleChanges, ViewChild} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {setFocusOn} from "@storydoc/common";

export interface ItemDialogData {
  description: string,
  addMore: boolean
}

export interface ItemDialogSpec {
  mode: 'UPDATE' | 'NEW'
  data: ItemDialogData
  confirm: (ItemDialogData) => void
  cancel: () => void
}

@Component({
  selector: 'app-create-item-dialog',
  templateUrl: './create-item-dialog.component.html',
  styleUrls: ['./create-item-dialog.component.scss']
})
export class CreateItemDialogComponent implements OnChanges {

  constructor(private changeDetector: ChangeDetectorRef) {}

  @Input()
  spec: ItemDialogSpec

  @ViewChild('description') descriptionField: ElementRef

  ngOnChanges(changes: SimpleChanges): void {
    if (this.spec != null) {
      this.formGroup.setValue(this.spec.data)
      this.changeDetector.detectChanges()
      setFocusOn(this.descriptionField)
    }
  }

  formGroup: FormGroup = new FormGroup({
    description: new FormControl(null, Validators.required),
    addMore: new FormControl(null)
  })


  cancel() {
    this.spec.cancel.apply(this, [])
  }

  save() {
    this.spec.confirm.apply(this, [this.formGroup.value])
  }

}
