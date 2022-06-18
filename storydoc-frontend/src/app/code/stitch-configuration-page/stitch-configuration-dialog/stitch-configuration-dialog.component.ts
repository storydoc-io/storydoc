import {ChangeDetectorRef, Component, ElementRef, Input, OnInit, SimpleChanges, ViewChild} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {CodeConfigurationSpec} from "../../code-configuration-page/code-configuration-dialog/code-configuration-dialog.component";

export interface StitchConfigurationData {
  path: string,
  addMore: boolean
}

export interface StitchConfigurationSpec {
  mode: 'UPDATE' | 'NEW'
  data: StitchConfigurationData
  confirm: (StitchConfigurationData) => void
  cancel: () => void
}

@Component({
  selector: 'app-stitch-configuration-dialog',
  templateUrl: './stitch-configuration-dialog.component.html',
  styleUrls: ['./stitch-configuration-dialog.component.scss']
})
export class StitchConfigurationDialogComponent {

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
  })

  cancel() {
    this.spec.cancel()
  }

  save() {
    this.spec.confirm(this.formGroup.value)
  }

}
