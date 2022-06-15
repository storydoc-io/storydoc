import {Injectable, OnDestroy} from '@angular/core';
import {BehaviorSubject, Subscription} from "rxjs";
import {distinctUntilChanged, map} from "rxjs/operators";
import {log, logChangesToObservable} from "@storydoc/common";
import {ArtifactId, BlockCoordinate, CodeExecutionCoordinate, CodeTraceDto, SourceCodeDto, StitchItemDto} from "@storydoc/models";
import {CodeRestControllerService} from "@storydoc/services";
import {getLabel, isCodeExecutionEnterEvent, StitchEvent, toStitchEvent, TreeNode} from "./code.functions";


interface TraceStoreState {
  coord?: CodeExecutionCoordinate,
  codeTrace?: CodeTraceDto
  treeNodes?: TreeNode[]
  selectedEvent?: StitchEvent,
  selectedNode?: TreeNode
}

interface SourceCodeStoreState {
  className?: string,
  sourceCode?: SourceCodeDto
}


let instanceCount = 0

@Injectable()
export class CodeService implements OnDestroy {

  constructor(private codeRestControllerService: CodeRestControllerService) {
    this.init()
  }

  private traceStore = new BehaviorSubject<TraceStoreState>({})

  codeTraceCoord$ = this.traceStore.pipe(
    map(state => state.coord),
    distinctUntilChanged(),
  )

  codeTrace$ = this.traceStore.pipe(
    map(state => state.codeTrace),
    distinctUntilChanged(),
  )

  treeNodes$ = this.traceStore.pipe(
    map(state => state.treeNodes),
    distinctUntilChanged(),
  )

  selectedEvent$ = this.traceStore.pipe(
    map(state => state.selectedEvent),
    distinctUntilChanged(),
  )

  selectedNode$ = this.traceStore.pipe(
    map(state => state.selectedNode),
    distinctUntilChanged(),
  )

  private sourceCodeStore = new BehaviorSubject<SourceCodeStoreState>({})

  sourceCode$ = this.sourceCodeStore.pipe(
    map(state => state.sourceCode),
    distinctUntilChanged(),
  )


  private subscriptions: Subscription[] = []

  init(): void {
    log('init()')
    this.subscriptions.push(logChangesToObservable('traceStore::codeTrace$ >>', this.codeTrace$))
    this.subscriptions.push(logChangesToObservable('traceStore::selectedEvent$ >> ', this.selectedEvent$))
    this.subscriptions.push(logChangesToObservable('traceStore::selectedNode$ >> ', this.selectedNode$))
    this.subscriptions.push(logChangesToObservable('traceStore::treeNodes$ >> ', this.treeNodes$))
    this.subscriptions.push(logChangesToObservable('sourceCodeStore::sourceCode$ >> ', this.sourceCode$))
    this.subscriptions.push(this.selectedEvent$.subscribe(event => {
      if (!event) return
      if (isCodeExecutionEnterEvent(event)) {
        log('selectedItem$ -> loadSourceCode()')
        this.loadSourceCode(event.className)
      }
    }))
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }

  loadTrace(coord: CodeExecutionCoordinate) {
    log('loadTrace(coord)', coord)
    this.codeRestControllerService.getCodeExecutionUsingGet({
      storyDocId: coord.blockCoordinate.storyDocId.id,
      blockId: coord.blockCoordinate.blockId.id,
      codeExecutionId: coord.codeExecutionId.id
    })
      .subscribe((codeTrace: CodeTraceDto) => {
        this.traceStore.next({
          coord: coord,
          codeTrace,
          treeNodes: codeTrace ? new StitchDto2TreeNodeConverter().run(codeTrace.items) : null
        })
        this.selectNode(codeTrace.items[0])
      })
  }

  selectNode(node) {
    log('selectNode(node)', node)
    this.traceStore.next({
      ...this.traceStore.getValue(),
      selectedNode: node,
      selectedEvent: node.data?.event,
    })
  }


  loadSourceCode(className: string) {
    log('loadSourceCode(className)', className)
    if (!className || !this.traceStore.getValue().codeTrace) return
    let configCoord = this.traceStore.getValue().codeTrace.config
    this.codeRestControllerService.sourceUsingGet({
      storyDocId: configCoord.blockCoordinate.storyDocId.id,
      blockId: configCoord.blockCoordinate.blockId.id,
      sourceCodeConfigId: configCoord.sourceCodeConfigId.id,
      className
    })
      .subscribe(sourceCode => this.sourceCodeStore.next({className, sourceCode}))
  }


  setCodeExecutionConfig(blockCoordinate: BlockCoordinate, artifactId: ArtifactId) {
    log('setCodeExecutionConfig(blockCoordinate, artifactId)', blockCoordinate, artifactId)
    let traceCoord = this.traceStore.getValue().coord
    this.codeRestControllerService.setConfigForCodeExecutionUsingPost({
      execStoryDocId: traceCoord.blockCoordinate.storyDocId.id,
      execBlockId: traceCoord.blockCoordinate.blockId.id,
      codeExecutionId: traceCoord.codeExecutionId.id,
      configStoryDocId: blockCoordinate.storyDocId.id,
      configBlockId: blockCoordinate.blockId.id,
      sourceCodeConfigId: artifactId.id,
    }).subscribe(() => this.loadTrace(traceCoord))
  }

  setStitchDetails(param: { stitchFile: string; testMethod: string; testClass: string }) {
    log('setStitchDetails(param)', param)
    let traceCoord = this.traceStore.getValue().coord
    this.codeRestControllerService.setStitchDetailsForCodeExecutionUsingPost({
      execStoryDocId: traceCoord.blockCoordinate.storyDocId.id,
      execBlockId: traceCoord.blockCoordinate.blockId.id,
      codeExecutionId: traceCoord.codeExecutionId.id,
      stitchFile: param.stitchFile,
      testClass: param.testClass,
      testMethod: param.testMethod
    }).subscribe(() => this.loadTrace(traceCoord))
  }



  //  blueprint panel section


}

class StitchDto2TreeNodeConverter {

  public run(items: StitchItemDto[], parent?: TreeNode): TreeNode[] {
    if (!items) return
    return items.map(item => {
      let event = toStitchEvent(item)
      let node =  {
        event,
        name: getLabel(event),
        children: null
      }
      node.children = this.run(item.children, node)
      return node
    })
  }



}
