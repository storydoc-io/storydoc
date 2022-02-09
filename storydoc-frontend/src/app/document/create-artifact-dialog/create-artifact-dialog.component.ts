import {Component, EventEmitter, Input, OnInit, Output, SimpleChanges} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ArtifactDataService, ArtifactDescriptor} from "../block/artifact-block/artifact-data.service";

export interface ArtifactDialogData {
  name?: string
  file?: string
  artifactType: string
}

export interface ArtifactDialogSpec {
  mode: 'UPDATE' | 'NEW'
  data: ArtifactDialogData
  confirm: (ArtifactDialogData) => void
  cancel: ()=> void
}

@Component({
  selector: 'app-create-artifact-dialog',
  templateUrl: './create-artifact-dialog.component.html',
  styleUrls: ['./create-artifact-dialog.component.scss']
})
export class CreateArtifactDialogComponent {

  constructor(private artifactDataService: ArtifactDataService) {}

  @Input()
  spec: ArtifactDialogSpec

  ngOnChanges(changes: SimpleChanges): void {
    if (this.spec != null) {
      this.formGroup.setValue(this.spec.data)
    }
  }

  formGroup: FormGroup = new FormGroup({
    name: new FormControl(null, Validators.required),
    artifactType: new FormControl(null, Validators.required),
  })

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
