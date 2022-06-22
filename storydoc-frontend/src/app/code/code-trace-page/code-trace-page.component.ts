import {Component, HostListener, OnDestroy, OnInit} from '@angular/core';
import {LinkService, ModalService} from "@storydoc/common";
import {ActivatedRoute} from "@angular/router";
import {CodeExecutionCoordinate, StitchStructureDto} from "@storydoc/models";
import {CodeTraceConfigDialogData, CodeTraceConfigDialogSpec} from "./code-trace-configuration-dialog/code-trace-configuration-dialog.component";
import {Subscription} from "rxjs";
import {CodeService} from "../code.service";
import {StitchConfigurationService} from "../stitch-configuration-page/stitch-configuration.service";
import {TreeNode} from "@circlon/angular-tree-component";
import {StitchEvent} from "../code.functions";

@Component({
  selector: 'app-code-trace-page',
  templateUrl: './code-trace-page.component.html',
  styleUrls: ['./code-trace-page.component.scss'],
  providers: [CodeService, StitchConfigurationService]
})
export class CodeTracePageComponent implements OnInit, OnDestroy{

  constructor(
    public link: LinkService,
    private route: ActivatedRoute,
    private modalService: ModalService,
    private codeService: CodeService) {
  }

  codeTrace$ = this.codeService.codeTrace$

  stitchStructure$ = this.codeService.stitchStructure$
  stitchStructure: StitchStructureDto
  stitchStructureNodes: StitchStructureTreeNode[]

  codeTraceCoord$ = this.codeService.codeTraceCoord$
  codeTraceCoord : CodeExecutionCoordinate

  subscriptions: Subscription[] = []

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      let documentId = params.get('documentId')
      let blockId = params.get('blockId')
      let id = params.get('artifactId')
      if (id) {
        this.codeService.loadTrace( <CodeExecutionCoordinate>{
          blockCoordinate: {
            storyDocId: {id: documentId},
            blockId: {id: blockId}
          },
          codeExecutionId: { id  }
        })
      }
    });
    this.subscriptions.push(this.codeTraceCoord$.subscribe((codeTraceCoord) => this.codeTraceCoord = codeTraceCoord))
    this.subscriptions.push(this.stitchStructure$.subscribe((stitchStructureDto)=> {
      this.stitchStructure=stitchStructureDto
      this.stitchStructureNodes = new StitchStructure2TreeNodeConverter().run(stitchStructureDto)
    }))
  }

  ngOnDestroy() {
    this.subscriptions.forEach( (subscription) => subscription.unsubscribe())
  }

  // configuration dialog

  configurationDialogId(): string {
    return 'configuration-dialog-id'
  };


  codeTraceConfigDialogSpec : CodeTraceConfigDialogSpec

  configure() {
    this.codeTraceConfigDialogSpec = {
      data: null,
      treeNodes: this.stitchStructureNodes,
      coord: this.codeTraceCoord,
      confirm: (data: CodeTraceConfigDialogData) => this.confirmConfig(data),
      cancel: () => this.cancelConfig()
    }
    this.modalService.open(this.configurationDialogId())
  }

  private confirmConfig(data: CodeTraceConfigDialogData) {
    this.modalService.close(this.configurationDialogId())
  }

  private cancelConfig() {
    this.modalService.close(this.configurationDialogId())
  }

}


export interface StitchStructureTreeNode {
  name: string,
  children: StitchStructureTreeNode[],
  dto?: StitchStructureDto,
  data? : any
  parent?: StitchStructureTreeNode

}

class StitchStructure2TreeNodeConverter {

  public run(stitchStructureDto: StitchStructureDto): StitchStructureTreeNode[] {
    if (!stitchStructureDto) return
    let rootNode = this.toNode(stitchStructureDto)

    console.log('rootNode', rootNode)
    return [rootNode]
  }

  private toNode(stitchStructureDto: StitchStructureDto): StitchStructureTreeNode {
    let node = {
      dto: stitchStructureDto,
      name: stitchStructureDto.label,
      children: null
    }
    node.children = stitchStructureDto.children ? stitchStructureDto.children.map(child => {return this.toNode(child)}) : null
    return node
  }

}

