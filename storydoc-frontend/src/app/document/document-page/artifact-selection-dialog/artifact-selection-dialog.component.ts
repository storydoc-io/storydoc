import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {ArtifactDto, ArtifactId, BlockCoordinate} from "@storydoc/models";
import {ArtifactSelectionService} from "./artifact-selection.service";

export interface ArtifactSelectionDialogData  {
}

export interface ArtifactSelectionDialogSpec  {
  artifactType: string;
  artifactLabel: string;
  blockCoord: BlockCoordinate,
  confirm: (selection: ArtifactId)=> void
  cancel: () => void
}

@Component({
  selector: 'app-artifact-selection-dialog',
  templateUrl: './artifact-selection-dialog.component.html',
  styleUrls: ['./artifact-selection-dialog.component.scss']
})
export class ArtifactSelectionDialogComponent implements OnInit, OnChanges {

  @Input()
  spec: ArtifactSelectionDialogSpec

  constructor(private service: ArtifactSelectionService) { }

  artifacts$ = this.service.artifacts$

  ngOnInit(): void {

  }

  ngOnChanges(changes: SimpleChanges): void {
    if (!this.spec) return
    this.service.select(this.spec.artifactType, this.spec.blockCoord)
  }



  cancel() {
    this.spec.cancel()
  }

  select(artifact: ArtifactDto) {
    this.spec.confirm(artifact.artifactId)
  }
}
