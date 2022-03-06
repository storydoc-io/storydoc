import {ChangeDetectorRef, Component, ElementRef, Input, SimpleChanges, ViewChild} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ArtifactDataService, ArtifactDescriptor} from "../block/artifact-block/artifact-data.service";
import {setFocusOn} from "@storydoc/common";

export interface ArtifactDialogData {
  name?: string
  file?: string
  artifactType: string
}

export interface ArtifactDialogSpec {
  mode: 'UPDATE' | 'NEW'
  data: ArtifactDialogData
  confirm: (ArtifactDialogData) => void
  cancel: () => void
}

@Component({
  selector: 'app-create-artifact-dialog',
  templateUrl: './create-artifact-dialog.component.html',
  styleUrls: ['./create-artifact-dialog.component.scss']
})
export class CreateArtifactDialogComponent {

  constructor(private changeDetector: ChangeDetectorRef, private artifactDataService: ArtifactDataService) {
  }

  @Input()
  spec: ArtifactDialogSpec

  @ViewChild('type') typeField: ElementRef
  @ViewChild('name') nameField: ElementRef

  ngOnChanges(changes: SimpleChanges): void {
    if (this.spec != null) {
      this.formGroup.setValue(this.spec.data)
      this.changeDetector.detectChanges()
      if (this.updateMode()) {
        this.artifactType.disable()
        setFocusOn(this.nameField)
      } else {
        this.artifactType.enable()
        setFocusOn(this.typeField)
      }
    }
  }

  updateMode(): boolean {
    return this.spec.mode=='UPDATE'
  }


  formGroup: FormGroup = new FormGroup({
    name: new FormControl(null, Validators.required),
    artifactType: new FormControl(null, Validators.required),
  })

  get artifactType(): FormControl {
    return <FormControl> this.formGroup.get('artifactType')
  }

  descriptors(): ArtifactDescriptor[] {
    return this.artifactDataService.list()
  }

  cancel() {
    this.spec.cancel.apply(this, [])
  }

  confirm() {
    this.spec.confirm.apply(this, [this.formGroup.value])
  }

}
