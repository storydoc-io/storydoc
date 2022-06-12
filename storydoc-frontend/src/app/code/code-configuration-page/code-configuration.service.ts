import {Injectable, OnDestroy} from "@angular/core";
import {CodeRestControllerService} from "@storydoc/services";
import {BehaviorSubject, Subscription} from "rxjs";
import {log, logChangesToObservable} from "@storydoc/common";
import {distinctUntilChanged, map} from "rxjs/operators";
import {SourceCodeConfigCoordinate, SourceCodeConfigDto} from "@storydoc/models";

interface SourceCodeConfigStoreState {
  coord: SourceCodeConfigCoordinate,
  sourceCodeConfig?: SourceCodeConfigDto
}

@Injectable()
export class CodeConfigurationService implements OnDestroy {

  constructor(private codeRestControllerService: CodeRestControllerService) {
    this.init()
  }

  private subscriptions: Subscription[] = []

  init(): void {
    log('init()')
    this.subscriptions.push(logChangesToObservable('configStore >> ', this.configStore))
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }

  private configStore = new BehaviorSubject<SourceCodeConfigStoreState>({
    coord: null,
    sourceCodeConfig: null
  })

  config$ = this.configStore.pipe(
    map(state => state.sourceCodeConfig),
    distinctUntilChanged(),
  )

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



}
