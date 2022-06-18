import {Injectable, OnDestroy} from "@angular/core";
import {CodeRestControllerService} from "@storydoc/services";
import {BehaviorSubject, Subscription} from "rxjs";
import {log, logChangesToObservable} from "@storydoc/common";
import {distinctUntilChanged, map} from "rxjs/operators";
import {StitchConfigCoordinate, StitchConfigDto} from "@storydoc/models";

interface StitchConfigStoreState {
  coord: StitchConfigCoordinate,
  stitchConfig?: StitchConfigDto
}

@Injectable()
export class StitchConfigurationService implements OnDestroy {

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

  private configStore = new BehaviorSubject<StitchConfigStoreState>({
    coord: null,
    stitchConfig: null
  })

  config$ = this.configStore.pipe(
    map(state => state.stitchConfig),
    distinctUntilChanged(),
  )

  loadConfig(coord: StitchConfigCoordinate) {
    log('loadConfig(coord)', coord)
    this.codeRestControllerService.getStitchConfigUsingGet({
      storyDocId: coord.blockCoordinate.storyDocId.id,
      blockId: coord.blockCoordinate.blockId.id,
      stitchConfigId: coord.stitchConfigId.id
    }).subscribe(config => this.configStore.next({
      coord,
      stitchConfig: config
    }))

  }

  setPath(path: string) {
    log('setPath(path)', path)
    let coord = this.configStore.getValue().coord
    this.codeRestControllerService.setStitchPathUsingPost({
      storyDocId: coord.blockCoordinate.storyDocId.id,
      blockId: coord.blockCoordinate.blockId.id,
      stitchConfigId: coord.stitchConfigId.id,
      path
    }).subscribe(() => {
      this.loadConfig(coord)
    })

  }



}
