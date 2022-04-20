import {Injectable, OnDestroy} from '@angular/core';
import {BehaviorSubject, Subscription} from "rxjs";
import {distinctUntilChanged, map} from "rxjs/operators";
import {log, logChangesToObservable} from "@storydoc/common";
import {
  CodeTraceDto,
  SourceCodeConfigCoordinate,
  SourceCodeDto,
  SourceCodeConfigDto,
  CodeExecutionCoordinate,
  ArtifactDto,
  BlockCoordinate, ArtifactId
} from "@storydoc/models";
import {CodeRestControllerService} from "@storydoc/services";

export interface StitchEvent {
  modelName: string
  eventName: string
}

export interface CodeExecutionEnterEvent extends StitchEvent{
  className: string,
  methodName: string
}

export function isCodeExecutionEnterEvent(event: StitchEvent): event is CodeExecutionEnterEvent {
  return event.modelName === 'CodeExecution'  && event.eventName === 'MethodCalled'
}

export interface CodeExecutionReturnEvent extends StitchEvent{
  className: string,
  methodName: string
}

export function isCodeExecutionReturnEvent(event: StitchEvent): event is CodeExecutionReturnEvent {
  return event.modelName === 'CodeExecution'  && event.eventName === 'MethodReturn'
}

export interface TestCaseBDDEvent extends StitchEvent {
  noun: string
  text: string
}

export function isCodeTestCaseBDDEvent(event: StitchEvent): event is TestCaseBDDEvent {
  return event.modelName === 'TestScenario' && event.eventName === 'given'
}



interface TraceStoreState {
  coord? : CodeExecutionCoordinate,
  codeTrace?: CodeTraceDto
  selectedItem?: StitchEvent,
}

interface SourceCodeStoreState {
  className?: string,
  sourceCode? : SourceCodeDto
}

interface SourceCodeConfigStoreState {
  coord: SourceCodeConfigCoordinate,
  sourceCodeConfig? : SourceCodeConfigDto
}

interface ConfigPanelState {
  configs: ArtifactDto[]
}

@Injectable({
  providedIn: 'root'
})
export class CodeService implements  OnDestroy{

  constructor(private codeRestControllerService: CodeRestControllerService) {
    this.init()
  }

  private traceStore = new BehaviorSubject<TraceStoreState>({} )

  codeTraceCoord$ = this.traceStore.pipe(
    map(state => state.coord),
    distinctUntilChanged(),
  )

  codeTrace$ = this.traceStore.pipe(
    map(state => state.codeTrace),
    distinctUntilChanged(),
  )

  selectedItem$ = this.traceStore.pipe(
    map(state => state.selectedItem),
    distinctUntilChanged(),
  )

  private sourceCodeStore = new BehaviorSubject<SourceCodeStoreState>({} )

  sourceCode$ =  this.sourceCodeStore.pipe(
    map(state => state.sourceCode),
    distinctUntilChanged(),
  )

  private configStore = new BehaviorSubject<SourceCodeConfigStoreState>({
    coord: null,
    sourceCodeConfig: null
  })

  config$ =  this.configStore.pipe(
    map(state => state.sourceCodeConfig),
    distinctUntilChanged(),
  )

  private subscriptions: Subscription[] = []

  init(): void {
    log('init()')
    this.subscriptions.push(logChangesToObservable('traceStore::codeTrace$ >>', this.codeTrace$))
    this.subscriptions.push(logChangesToObservable('traceStore::selectedItem$ >> ', this.selectedItem$))
    this.subscriptions.push(logChangesToObservable('sourceCodeStore::sourceCode$ >> ', this.sourceCode$))
    this.subscriptions.push(logChangesToObservable('configStore >> ', this.configStore))
    this.subscriptions.push(this.selectedItem$.subscribe(event => {
      if (!event)  return
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
    log('loadTrace()')
    this.codeRestControllerService.getCodeExecutionUsingGet({
      storyDocId: coord.blockCoordinate.storyDocId.id,
      blockId: coord.blockCoordinate.blockId.id,
      codeExecutionId: coord.codeExecutionId.id
    })
      .subscribe((codeTrace: CodeTraceDto) => {
        this.traceStore.next({
          coord: coord,
          codeTrace
        })
        this.loadConfig(codeTrace.config)
        this.selectTraceItem(codeTrace.items[0])
      })
  }

  selectTraceItem(item) {
    log('selectTraceItem(item)', item)
    this.traceStore.next({
      ... this.traceStore.getValue(),
      selectedItem: item
    })
  }

  loadSourceCode(className: string) {
    log('loadSourceCode(className)', className)
    if (!className || !this.traceStore.getValue().codeTrace) return
    let configCoord = this.traceStore.getValue().codeTrace.config
    this.codeRestControllerService.sourceUsingGet({
      storyDocId:configCoord.blockCoordinate.storyDocId.id,
      blockId: configCoord.blockCoordinate.blockId.id,
      sourceCodeConfigId : configCoord.sourceCodeConfigId.id,
      className
    })
      .subscribe(sourceCode => this.sourceCodeStore.next({ className, sourceCode }))
  }



  loadConfig(coord: SourceCodeConfigCoordinate ) {
    log('loadConfig(coord)', coord)
    this.codeRestControllerService.getSourceConfigUsingGet({
      storyDocId: coord.blockCoordinate.storyDocId.id,
      blockId: coord.blockCoordinate.blockId.id,
      sourceCodeConfigId: coord.sourceCodeConfigId.id
    }).subscribe(config => this.configStore.next({
      coord,
      sourceCodeConfig: config }))

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
    let traceCoord = this.traceStore.getValue().coord
    this.codeRestControllerService.setConfigForCodeExecutionUsingPost({
      execStoryDocId: traceCoord.blockCoordinate.storyDocId.id,
      execBlockId: traceCoord.blockCoordinate.blockId.id,
      codeExecutionId: traceCoord.codeExecutionId.id,
      configStoryDocId: blockCoordinate.storyDocId.id,
      configBlockId: blockCoordinate.blockId.id,
      sourceCodeConfigId: artifactId.id,
    }).subscribe( ()=> this.loadTrace(traceCoord))
  }
}
