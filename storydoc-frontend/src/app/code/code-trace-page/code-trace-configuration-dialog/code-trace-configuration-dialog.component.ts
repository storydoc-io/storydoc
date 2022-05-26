import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ModalService} from "@storydoc/common";
import {CodeService} from "../../code.service";
import {ArtifactSelectionDialogSpec} from "../../../document/document-page/artifact-selection-dialog/artifact-selection-dialog.component";
import {ArtifactDto, ArtifactId, BlockCoordinate, CodeExecutionCoordinate} from "@storydoc/models";

export interface CodeTraceConfigDialogData  {
  stitchFile: string,
  testClass: string,
  testMethod: string
}

export interface CodeTraceConfigDialogSpec  {
  coord: CodeExecutionCoordinate
  data: CodeTraceConfigDialogData
  confirm: (data: CodeTraceConfigDialogData)=> void
  cancel: () => void
}

@Component({
  selector: 'app-code-trace-configuration-dialog',
  templateUrl: './code-trace-configuration-dialog.component.html',
  styleUrls: ['./code-trace-configuration-dialog.component.scss']
})
export class CodeTraceConfigurationDialogComponent implements OnInit {

  constructor(
    private modalService: ModalService,
    private service: CodeService)
  { }

  @Input()
  spec: CodeTraceConfigDialogSpec


  ngOnInit(): void {
  }

  formGroup: FormGroup = new FormGroup({
    stitchFile: new FormControl(null, Validators.required),
    testClass: new FormControl(null, Validators.required),
    testMethod: new FormControl(null, Validators.required),
  })

  cancel() {
    this.spec.cancel()
  }

  confirm() {
    this.spec.confirm(this.formGroup.value)
  }

  // artifact selection dialog

  artifactSelectionDialogSpec: ArtifactSelectionDialogSpec

  selectCodeConfigArtifact() {
     this.openArtifactSelectionDialog({
       artifactType: 'io.storydoc.server.code.domain.SourceCodeConfig',
       artifactLabel: 'SourceCode Configuration',
       blockCoord: this.spec.coord.blockCoordinate,
       cancel: () => this.closeArtifactSelectionDialog(),
       confirm: (artifactId) => { this.confirmSelection(this.spec.coord.blockCoordinate, artifactId); this.closeArtifactSelectionDialog()},
     });
  }

  artifactSelectionDialogId(): string {
    return 'artifact-selection-dialog-id'
  };

  openArtifactSelectionDialog(spec: ArtifactSelectionDialogSpec) {
    this.artifactSelectionDialogSpec = spec
    this.modalService.open(this.artifactSelectionDialogId())
  }

  closeArtifactSelectionDialog() {
    this.modalService.close(this.artifactSelectionDialogId())
  }


  private confirmSelection(blockCoordinate: BlockCoordinate, artifactId: ArtifactId) {
    this.service.setCodeExecutionConfig(blockCoordinate, artifactId)
  }
}
