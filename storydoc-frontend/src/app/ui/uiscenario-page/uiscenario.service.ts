import {Injectable, OnDestroy} from '@angular/core';
import {BehaviorSubject, Observable, Subscription} from "rxjs";
import {distinctUntilChanged, map} from "rxjs/operators";
import {
  BlockCoordinate,
  BlockId, ScreenshotCollectionCoordinate, ScreenShotCollectionDto, ScreenshotCollectionSummaryDto,
  ScreenshotCoordinate,
  StoryDocId, TimeLineDto,
  TimeLineId,
  TimeLineItemId,
  TimeLineModelCoordinate,
  TimeLineModelDto,
  TimeLineModelId,
  TimeLineModelSummaryDto,
  UiScenarioCoordinate,
  UiScenarioDto
} from "@storydoc/models";
import {log, logChangesToObservable} from "@storydoc/common";
import {TimeLineControllerService, UiRestControllerService} from "@storydoc/services";

interface ScenarioPanelState {
  uiScenarioCoord?: UiScenarioCoordinate,
  uiScenarioDto?: UiScenarioDto,
  timeLineModel?: TimeLineModelDto
  timeLineId?: TimeLineId,
  timeLine? : TimeLineDto
}

interface ScreenshotSelectionPanelState {
  screenshotCollections?: ScreenshotCollectionSummaryDto[]
  screenshotCollectionCoord?: ScreenshotCollectionCoordinate
  screenshotCollection?: ScreenShotCollectionDto
}

interface ConfigPanelState {
  timeLineModels?: TimeLineModelSummaryDto[]
}

@Injectable({
  providedIn: 'root'
})
export class UIScenarioService implements OnDestroy {

  constructor(
    private uiRestControllerService: UiRestControllerService,
    private timeLineControllerService: TimeLineControllerService
  ) {
    this.init()
  }

  // UIScenario, Timeline selection

  private scenarioStore = new BehaviorSubject<ScenarioPanelState>({})
  state$ = this.scenarioStore.asObservable()

  uiScenario$ = this.state$.pipe(
    map(state => state.uiScenarioDto),
    distinctUntilChanged(),
  )

  timeLineModel$ = this.state$.pipe(
    map(state => state.timeLineModel),
    distinctUntilChanged(),
  )

  timeLineId$ = this.state$.pipe(
    map(state => state.timeLineId),
    distinctUntilChanged(),
  )

  timeLine$ = this.state$.pipe(
    map(state => state.timeLine),
    distinctUntilChanged(),
  )

  // ScreenshotSelection

  screenshotStore = new BehaviorSubject<ScreenshotSelectionPanelState>({})

  screenshotCollections$ = this.screenshotStore.pipe(
    map(state => state.screenshotCollections),
    distinctUntilChanged(),
  )

  screenshotCollectionCoord$ = this.screenshotStore.pipe(
    map(state => state.screenshotCollectionCoord),
    distinctUntilChanged(),
  )

  screenshotCollection$ = this.screenshotStore.pipe(
    map(state => state.screenshotCollection),
    distinctUntilChanged(),
  )

  // Config panel

  private configStore = new BehaviorSubject<ConfigPanelState>({})
  timeLineModels$ = this.configStore.pipe(
    map(store => store.timeLineModels),
    distinctUntilChanged()
  )

  private get uiScenario(): UiScenarioDto {
    return this.scenarioStore.getValue()?.uiScenarioDto
  }

  private get uiScenarioCoord(): UiScenarioCoordinate {
    return this.scenarioStore.getValue()?.uiScenarioCoord
  }

  private get timeLineModel(): TimeLineModelDto {
    return this.scenarioStore.getValue()?.timeLineModel
  }

  private subscriptions: Subscription[] = []

  init(): void {
    log('init()')
    this.subscriptions.push(logChangesToObservable('ScenarioStore$ >>', this.scenarioStore))
    this.subscriptions.push(logChangesToObservable('ConfigStore$ >> ', this.configStore))
    this.subscriptions.push(logChangesToObservable('TimeLineId$ >>', this.timeLineId$))
    this.subscriptions.push(logChangesToObservable('timeLine$ >> ', this.timeLine$))
    this.subscriptions.push(this.loadTimeLineModelFromUIScenario())
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }

  loadUIScenario(params: { storyDocId: StoryDocId, blockId: BlockId, uiScenarioId: string }) {
    log("loadUIScenario(params)", params)
    this.screenshotStore.next({})
    this.configStore.next({})
    this.scenarioStore.next({
      uiScenarioCoord: {
        blockCoordinate: {
          storyDocId: params.storyDocId,
          blockId: params.blockId
        },
        uiScenarioId: {id: params.uiScenarioId}
      }
    })
    this.reloadScenario()
  }

  public selectTimeLineModel(timeLineModelCoordinate: TimeLineModelCoordinate) {
    log("selectTimeLineModel(coord)", timeLineModelCoordinate)
    this.uiRestControllerService.setUiScenarioTimeLineModelUsingPost({
      storyDocId: this.uiScenarioCoord.blockCoordinate.storyDocId.id,
      blockId: this.uiScenarioCoord.blockCoordinate.blockId.id,
      uiScenarioId: this.uiScenarioCoord.uiScenarioId.id,
      timeLineModelStoryDocId: timeLineModelCoordinate.blockCoordinate.storyDocId.id,
      timeLineModelBlockId: timeLineModelCoordinate.blockCoordinate.blockId.id,
      timeLineModelId: timeLineModelCoordinate.timeLineModelId.id,
    }).subscribe({
      next: value => {
        this.reloadScenario()
      }
    })
  }

  public setScenarioTimeLine(timeLineId: TimeLineId) {
    log('setScenarioTimeLine(timeLineId)', timeLineId)
    this.scenarioStore.next({
      ... this.scenarioStore.value,
      timeLineId,
      timeLine: this.timeLineById(this.timeLineModel, timeLineId)
    })
  }

  private timeLineById(timeLineModel: TimeLineModelDto, timeLineId: TimeLineId): TimeLineDto {
    return this.timelinesAsArray(timeLineModel.timeLines).find(timeLine => timeLine.timeLineId.id === timeLineId.id)
  }

  private timelinesAsArray(timeLines: { [p: string]: TimeLineDto }): TimeLineDto[] {
    return Object.keys(timeLines).map(key => timeLines[key])
  }

  public selectScreenshotCollection(coord: ScreenshotCollectionCoordinate) {
    log('selectScreenshotCollection(coord)', coord)
    this.uiRestControllerService.getScreenShotCollectionUsingGet({
      storyDocId: coord.blockCoordinate.storyDocId.id,
      blockId: coord.blockCoordinate.blockId.id,
      id: coord.screenShotCollectionId.id
    }).subscribe(collectionDto => {
      this.screenshotStore.next({
        ... this.screenshotStore.getValue(),
        screenshotCollectionCoord: coord,
        screenshotCollection: collectionDto
      })
    })
  }

  public addScreenshotToScenario(screenshotCoordinate: ScreenshotCoordinate, timeLineItemId: TimeLineItemId) {
    log('addScreenshotToScenario(screenshotCoordinate, timeLineItemId)', screenshotCoordinate, timeLineItemId)
    this.uiRestControllerService.addScreenshotToUiScenarioUsingPost({
      storyDocId: this.uiScenarioCoord.blockCoordinate.storyDocId.id,
      blockId: this.uiScenarioCoord.blockCoordinate.blockId.id,
      uiScenarioId: this.uiScenarioCoord.uiScenarioId.id,
      screenshotStoryDocId: screenshotCoordinate.collectionCoordinate.blockCoordinate.storyDocId.id,
      screenshotBlockId: screenshotCoordinate.collectionCoordinate.blockCoordinate.blockId.id,
      screenshotCollectionId: screenshotCoordinate.collectionCoordinate.screenShotCollectionId.id,
      screenshotId: screenshotCoordinate.screenShotId.id,
      timeLineItemId: timeLineItemId.id
    }).subscribe({
      next: (value => this.reloadScenario())
    })
  }

  private reloadScenario() {
    log('reloadScenario()')
    this.uiRestControllerService.getUiScenarioUsingGet({
      storyDocId: this.uiScenarioCoord.blockCoordinate.storyDocId.id,
      blockId: this.uiScenarioCoord.blockCoordinate.blockId.id,
      id: this.uiScenarioCoord.uiScenarioId.id
    }).subscribe({
      next: uiScenarioDto => {
        this.scenarioStore.next({
          ...this.scenarioStore.getValue(),
          uiScenarioDto: uiScenarioDto,
        })
        this.screenshotStore.next({
          ... this.screenshotStore.getValue(),
          screenshotCollections: uiScenarioDto.associatedCollections
        })
      }
    })
  }

  private loadTimeLineModelFromUIScenario(): Subscription {
    return this.uiScenario$.subscribe({
      next: uiScenario => {
        log('uiScenario$-->refreshTimeLineModelWhenScenarioUpdates()')
        if (!this.equalTimeLineModelCoord(this.timeLineModel?.timeLineModelCoordinate, uiScenario?.timeLineModelCoordinate)) {
          this.loadTimeLineModel(uiScenario.timeLineModelCoordinate)
        }
      }
    })
  }

  private loadTimeLineModel(coord: TimeLineModelCoordinate) {
    log('reloadTimeLineModel(coord)', coord)
    this.timeLineControllerService.getTimeLineModelUsingGet({
      storyDocId: coord.blockCoordinate.storyDocId.id,
      blockId: coord.blockCoordinate.blockId.id,
      timeLineModelId: coord.timeLineModelId.id
    }).subscribe({
      next: (dto) => {
        let nextValue = <ScenarioPanelState>{
          ...this.scenarioStore.getValue(),
          timeLineModel: dto,
        }
        this.scenarioStore.next(nextValue)
        let defaultId = this.defaultTimeline(dto)?.timeLineId
        if (!nextValue.timeLineId && (defaultId)) {
          this.setScenarioTimeLine(defaultId)
        }
      }
    })
  }

  private equalTimeLineCoord(tlmCoord1: TimeLineModelCoordinate, tlId1: TimeLineId, tlmCoord2: TimeLineModelCoordinate, tlId2: TimeLineId) {
    return this.equalTimeLineModelCoord(tlmCoord1, tlmCoord2) && this.equalTimeLineId(tlId1, tlId2)
  }

  private equalTimeLineModelCoord(coord1: TimeLineModelCoordinate, coord2: TimeLineModelCoordinate): boolean {
    return this.equalTimeLineModelId(coord1?.timeLineModelId, coord2?.timeLineModelId) && this.equalBlockCoordinate(coord1?.blockCoordinate, coord2?.blockCoordinate)
  }

  private equalTimeLineId(id1: TimeLineId, id2: TimeLineId): boolean {
    return id1?.id === id2?.id;
  }

  private equalTimeLineModelId(id1: TimeLineModelId, id2: TimeLineModelId): boolean {
    return id1?.id === id2?.id;
  }

  private equalBlockCoordinate(coord1: BlockCoordinate, coord2: BlockCoordinate): boolean {
    return coord1?.storyDocId.id === coord2?.storyDocId.id && coord1?.blockId.id === coord2?.blockId.id;
  }


  public loadAssociatedTimeLineModels() {
    log('loadAssociatedTimeLineModels()')
    let uiScenario = this.uiScenario
    this.timeLineControllerService.getTimeLineModelSummariesUsingGet({
      storyDocId: this.uiScenarioCoord.blockCoordinate.storyDocId.id,
      blockId: this.uiScenarioCoord.blockCoordinate.blockId.id
    }).subscribe({
      next: summaries => this.configStore.next({timeLineModels: summaries})
    })
  }

  private defaultTimeline(dto: TimeLineModelDto) {
    return this.asArray(dto.timeLines).find((timeLine)=> timeLine.name==='default');
  }

  private asArray(timeLines: { [p: string]: TimeLineDto }): TimeLineDto[] {
    return Object.keys(timeLines).map(key => timeLines[key])
  }

}
