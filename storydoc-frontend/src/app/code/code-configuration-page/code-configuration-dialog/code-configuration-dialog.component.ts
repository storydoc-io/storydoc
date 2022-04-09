import {ChangeDetectorRef, Component, ElementRef, Input, OnChanges, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {setFocusOn} from "@storydoc/common";
import {FormControl, FormGroup, Validators} from "@angular/forms";

export interface CodeConfigurationData {
  path: string,
  addMore: boolean
}

export interface CodeConfigurationSpec {
  mode: 'UPDATE' | 'NEW'
  data: CodeConfigurationData
  confirm: (CodeConfigurationData) => void
  cancel: () => void
}

@Component({
  selector: 'app-code-configuration-dialog',
  templateUrl: './code-configuration-dialog.component.html',
  styleUrls: ['./code-configuration-dialog.component.scss']
})
export class CodeConfigurationDialogComponent implements OnChanges {

  constructor(private changeDetector: ChangeDetectorRef) {
  }

  @Input()
  spec: CodeConfigurationSpec

  @ViewChild('path') pathField: ElementRef

  ngOnChanges(changes: SimpleChanges): void {
    if (this.spec != null) {
      //this.formGroup.setValue(this.spec.data)
      this.changeDetector.detectChanges()
     // setFocusOn(this.pathField)
    }
  }

  formGroup: FormGroup = new FormGroup({
    path: new FormControl(null, Validators.required),
    addMore: new FormControl(null)
  })


  cancel() {
    this.spec.cancel.apply(this, [])
  }

  save() {
    this.spec.confirm.apply(this, [this.formGroup.value])
  }

}
