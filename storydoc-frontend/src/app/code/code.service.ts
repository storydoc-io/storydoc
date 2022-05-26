import {Injectable, OnDestroy} from '@angular/core';
import {BehaviorSubject, Subscription} from "rxjs";
import {distinctUntilChanged, map} from "rxjs/operators";
import {log, logChangesToObservable} from "@storydoc/common";
import {
  ArtifactDto,
  ArtifactId,
  BlockCoordinate,
  CodeExecutionCoordinate,
  CodeTraceDto,
  SourceCodeConfigCoordinate,
  SourceCodeConfigDto,
  SourceCodeDto,
  StitchItemDto
} from "@storydoc/models";
import {CodeRestControllerService} from "@storydoc/services";
import {BluePrint} from "../api/models/blue-print";

export interface StitchEvent {
  modelName: string
  eventName: string
}

export interface CodeExecutionEnterEvent extends StitchEvent {
  className: string,
  methodName: string
}

export function isCodeExecutionEnterEvent(event: StitchEvent): event is CodeExecutionEnterEvent {
  return event.modelName === 'CodeExecution' && event.eventName === 'MethodCalled'
}

export interface CodeExecutionReturnEvent extends StitchEvent {
  className: string,
  methodName: string
}

export function isCodeExecutionReturnEvent(event: StitchEvent): event is CodeExecutionReturnEvent {
  return event.modelName === 'CodeExecution' && event.eventName === 'MethodReturn'
}

export interface TestCaseBDDEvent extends StitchEvent {
  noun: string
  text: string
}

export function isCodeTestCaseBDDEvent(event: StitchEvent): event is TestCaseBDDEvent {
  return event.modelName === 'TestScenario' && event.eventName === 'given'
}


interface TraceStoreState {
  coord?: CodeExecutionCoordinate,
  codeTrace?: CodeTraceDto
  treeNodes?: TreeNode[]
  selectedEvent?: StitchEvent,
}

interface SourceCodeStoreState {
  className?: string,
  sourceCode?: SourceCodeDto
}

interface SourceCodeConfigStoreState {
  coord: SourceCodeConfigCoordinate,
  sourceCodeConfig?: SourceCodeConfigDto
}

interface ConfigPanelState {
  configs: ArtifactDto[]
}

export interface TreeNode {
  name: string,
  children: TreeNode[]
}


@Injectable({
  providedIn: 'root'
})
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

  private sourceCodeStore = new BehaviorSubject<SourceCodeStoreState>({})

  sourceCode$ = this.sourceCodeStore.pipe(
    map(state => state.sourceCode),
    distinctUntilChanged(),
  )

  private configStore = new BehaviorSubject<SourceCodeConfigStoreState>({
    coord: null,
    sourceCodeConfig: null
  })

  config$ = this.configStore.pipe(
    map(state => state.sourceCodeConfig),
    distinctUntilChanged(),
  )


  private subscriptions: Subscription[] = []

  init(): void {
    log('init()')
    this.subscriptions.push(logChangesToObservable('traceStore::codeTrace$ >>', this.codeTrace$))
    this.subscriptions.push(logChangesToObservable('traceStore::selectedItem$ >> ', this.selectedEvent$))
    this.subscriptions.push(logChangesToObservable('traceStore::treeNodes$ >> ', this.treeNodes$))
    this.subscriptions.push(logChangesToObservable('sourceCodeStore::sourceCode$ >> ', this.sourceCode$))
    this.subscriptions.push(logChangesToObservable('configStore >> ', this.configStore))
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
          treeNodes: codeTrace ? this.toTreeNodes(codeTrace.items) : null
        })
        this.loadConfig(codeTrace.config)
        this.selectNode(codeTrace.items[0])
      })
  }

  selectNode(node) {
    log('selectNode(node)', node)
    this.traceStore.next({
      ...this.traceStore.getValue(),
      selectedEvent: node.data.event
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


  loadConfig(coord: SourceCodeConfigCoordinate) {
    log('loadConfig(coord)', coord)
    this.codeRestControllerService.getSourceConfigUsingGet({
      storyDocId: coord.blockCoordinate.storyDocId.id,
      blockId: coord.blockCoordinate.blockId.id,
      sourceCodeConfigId: coord.sourceCodeConfigId.id
    }).subscribe(config => this.configStore.next({
      coord,
      sourceCodeConfig: config
    }))

  }

  addPathToConfig(path: string) {
    log('addPathToConfig(path)', path)
    let coord = this.configStore.getValue().coord
    this.codeRestControllerService.setSourcePathUsingPost({
      storyDocId: coord.blockCoordinate.storyDocId.id,
      blockId: coord.blockCoordinate.blockId.id,
      sourceCodeConfigId: coord.sourceCodeConfigId.id,
      path
    }).subscribe(() => {
      this.loadConfig(coord)
    })

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


  // stitch dto to tree node conversion

  private toTreeNodes(items: StitchItemDto[]): TreeNode[] {
    if (!items) return
    return items.map(item => {
      let event = this.toStitchEvent(item)
      return {
        event,
        name: this.getLabel(event),
        children: this.toTreeNodes(item.children)
      }
    })
  }

  private toStitchEvent(item: StitchItemDto): StitchEvent {
    switch (item.modelName) {
      case 'CodeExecution': {
        switch (item.eventName) {
          case 'MethodCalled': {
            return <CodeExecutionEnterEvent>{
              modelName: item.modelName,
              eventName: item.eventName,
              className: item.attributes['typeName'],
              methodName: item.attributes['functionName']
            }
          }
          case 'MethodReturn': {
            return <CodeExecutionReturnEvent>{
              modelName: item.modelName,
              eventName: item.eventName,
              className: item.attributes['typeName'],
              methodName: item.attributes['functionName']
            }
          }
          default: {
            console.log('unmapped CodeExecution event, eventName not supported  ', item)
            return
          }
        }
      }
      case 'TestScenario': {
        switch (item.eventName) {
          case 'given': {
            return <TestCaseBDDEvent>{
              modelName: item.modelName,
              eventName: item.eventName,
              noun: item.attributes['noun'],
              text: item.attributes['text']
            }
          }
          default: {
            console.log('unmapped TestScenario event, eventName not supported ', item)
            return
          }
        }
      }
      default: {
        console.log('unmapped event. model not supported ', item)
        return
      }
    }

  }

  private getLabel(event: StitchEvent): string {
    if (!event) return "empty event"
    if (isCodeExecutionEnterEvent(event)) {
      return this.getClassName(event) + ' :: ' + event.methodName
    }
    if (isCodeExecutionReturnEvent(event)) {
      return this.getClassName(event) + ' :: ' + event.methodName
    }
    if (isCodeTestCaseBDDEvent(event)) {
      return event.noun + ' :: ' + event.text
    }
    return ''

  }

  private getClassName(item: CodeExecutionEnterEvent) {
    return item.className?.split('.').slice(-1)[0]
  }

  //  blueprint panel section


}
