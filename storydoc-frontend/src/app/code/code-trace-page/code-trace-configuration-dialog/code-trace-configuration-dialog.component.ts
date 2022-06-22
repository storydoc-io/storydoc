import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {ModalService} from "@storydoc/common";
import {CodeService} from "../../code.service";
import {ArtifactSelectionDialogSpec} from "../../../document/document-page/artifact-selection-dialog/artifact-selection-dialog.component";
import {ArtifactDto, ArtifactId, BlockCoordinate, CodeExecutionCoordinate, StitchStructureDto} from "@storydoc/models";
import {TreeNode} from "../../code.functions";
import {StitchStructureTreeNode} from "../code-trace-page.component";

export interface CodeTraceConfigDialogData  {
  testClass: string,
  testMethod: string
}

export interface CodeTraceConfigDialogSpec  {
  coord: CodeExecutionCoordinate
  treeNodes: StitchStructureTreeNode[]
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
  })

  cancel() {
    this.spec.cancel()
  }

  confirm() {
    this.spec.confirm(this.formGroup.value)
  }


  treeNodes() {
    return this.spec.treeNodes
  }


  selectNode($event: any) {
      console.log('***** selected: *****', $event)
  }

}

