import {Component, EventEmitter, Input, OnInit, Output, SimpleChanges} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ArtifactDataService, ArtifactDescriptor} from "../block/artifact-block/artifact-data.service";

export interface CreateArtifactDialogData {
  name?: string
  file?: string
  artifactType: string
}

export interface CreateArtifactDialogInput {
  mode: 'UPDATE' | 'NEW'
  data: CreateArtifactDialogData
}

@Component({
  selector: 'app-create-artifact-dialog',
  templateUrl: './create-artifact-dialog.component.html',
  styleUrls: ['./create-artifact-dialog.component.scss']
})
export class CreateArtifactDialogComponent {

  constructor(private artifactDataService: ArtifactDataService) {}

  @Input()
  input: CreateArtifactDialogInput

  ngOnChanges(changes: SimpleChanges): void {
    if (this.input != null) {
      this.formGroup.setValue(this.input.data)
    }
  }

  formGroup: FormGroup = new FormGroup({
    name: new FormControl(null, Validators.required),
    artifactType: new FormControl(null, Validators.required),
  })

  descriptors(): ArtifactDescriptor[] {
    return this.artifactDataService.list()
  }

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
