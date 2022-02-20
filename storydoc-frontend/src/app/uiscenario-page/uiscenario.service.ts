import {environment} from "../../environments/environment";
import {Injectable, OnDestroy} from '@angular/core';
import {BehaviorSubject, Subscription} from "rxjs";
import {distinctUntilChanged, map, tap} from "rxjs/operators";
import {
  BlockCoordinate,
  BlockId,
  ScreenshotCoordinate,
  StoryDocId,
  TimeLineId,
  TimeLineItemId,
  TimeLineModelCoordinate,
  TimeLineModelDto,
  TimeLineModelId,
  TimeLineModelSummaryDto,
  UiScenarioDto
} from "@storydoc/models";
import {TimeLineControllerService, UiRestControllerService} from "@storydoc/services";
import {TimeLineSelection} from "./time-line-selection-panel/time-line-selection-panel.component";

interface TimelineModelSelectionState {
  models?: TimeLineModelSummaryDto[]
  selectedCoord?: TimeLineModelCoordinate,
}

export interface TimeLineSelectionState {
  timeLineModel?: TimeLineModelDto,
  timeLineId?: TimeLineId
}

interface UIScenarioState {
  storyDocId?: StoryDocId,
  blockId?: BlockId,
  uiScenarioId?: string,
  uiScenarioDto?: UiScenarioDto,
  modelSelection?: TimelineModelSelectionState
  timeLineSelection?: TimeLineSelectionState,
}

function log(msg?: any, param1?: any, param2?: any) {
  if (!environment.production) {
    console.log('UIScenarioService::'+msg, param1, param2)
  }
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

  private store = new BehaviorSubject<UIScenarioState>({ modelSelection: null, timeLineSelection: null})
  state$ = this.store.asObservable()

  public get storyDocId() {
    return this.store.getValue()?.storyDocId;
  }

  public get blockId() {
    return this.store.getValue()?.blockId;
  }

  public get uiScenarioId() {
    return this.store.getValue()?.uiScenarioId;
  }

  uiScenario$ = this.state$.pipe(
    map(state => state.uiScenarioDto),
    distinctUntilChanged(),
  )

  timeLineModelSelection$ = this.state$.pipe(
    map(state => state.modelSelection),
    distinctUntilChanged(),
  )

  timeLineSelection$ = this.state$.pipe(
    map(state => state.timeLineSelection),
    distinctUntilChanged(),
  )

  timeLineModel$ = this.state$.pipe(
    map(state => state.timeLineSelection?.timeLineModel),
    distinctUntilChanged(),
  )

  private subscriptions: Subscription[] = []

  init(): void {
    log('init()')
    this.subscriptions.push(this.logStateChanges())
    this.subscriptions.push(this.refreshTimeLineModelWhenScenarioUpdates())
    this.subscriptions.push(this.refreshTimeLineModelSummariesWhenScenarioUpdates())
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }

  loadUIScenario(params: { storyDocId: StoryDocId, blockId: BlockId, uiScenarioId: string }) {
    log("loadUIScenario(params)", params)
    this.store.next(params)
    this.reloadScenario()
  }

  public selectTimeLineModel(coord: TimeLineModelCoordinate) {
    log("selectTimeLineModel(coord)", coord)
    if (coord) {
      this.store.next({
        ... this.store.getValue(),
        modelSelection: {
          ... this.store.getValue().modelSelection,
          selectedCoord: coord
        }
      })
      this.reloadTimeLineModel(coord, null)
    }
  }

  public setScenarioTimeLine(selection: TimeLineSelection) {
    log('setScenarioTimeLine(selection)', selection)
    this.uiRestControllerService.setUiScenarioTimeLineUsingPost({
      storyDocId: this.storyDocId.id,
      blockId: this.blockId.id,
      uiScenarioId: this.uiScenarioId,
      timeLineModelStoryDocId: selection.timeLineModelCoordinate.blockCoordinate.storyDocId.id,
      timeLineModelBlockId: selection.timeLineModelCoordinate.blockCoordinate.blockId.id,
      timeLineModelId: selection.timeLineModelCoordinate.timeLineModelId.id,
      timeLineId: selection.timeLineId.id
    }).subscribe({
      next: value => {
        this.reloadScenario()
      }
    })
  }

  public addScreenshotToScenario(screenshotCoordinate: ScreenshotCoordinate, timeLineItemId: TimeLineItemId) {
    log('addScreenshotToScenario(screenshotCoordinate, timeLineItemId)', screenshotCoordinate, timeLineItemId)
    this.uiRestControllerService.addScreenshotToUiScenarioUsingPost({
      storyDocId: this.storyDocId.id,
      blockId: this.blockId.id,
      uiScenarioId: this.uiScenarioId,
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
      storyDocId: this.storyDocId.id,
      blockId: this.blockId.id,
      id: this.uiScenarioId
    }).subscribe({
      next: dto => {
        let next = {
          ...this.store.getValue(),
          uiScenarioDto: dto,
        }
        this.store.next(next)}
    })
  }

  private refreshTimeLineModelWhenScenarioUpdates(): Subscription {
    return this.uiScenario$.subscribe({
      next: uiScenario => {
        log('uiScenario$-->refreshTimeLineModelWhenScenarioUpdates()')
        if (uiScenario && uiScenario.timeLineModelCoordinate) {
          this.reloadTimeLineModel(uiScenario.timeLineModelCoordinate, uiScenario.timeLineId)
        }
      }
    })
  }

  private refreshTimeLineModelSummariesWhenScenarioUpdates(): Subscription {
    return this.uiScenario$.subscribe({
      next: uiScenario => {
        log('uiScenario$-->refreshTimeLineModelSummariesWhenScenarioUpdates()')
        if (uiScenario) {
          this.reloadTimelineModelSummaries(uiScenario)
        }
      }
    })
  }

  private reloadTimeLineModel(coord: TimeLineModelCoordinate, timeLineId: TimeLineId) {
    log('reloadTimeLineModel(coord)', coord)
    this.timeLineControllerService.getTimeLineModelUsingGet({
      storyDocId: coord.blockCoordinate.storyDocId.id,
      blockId: coord.blockCoordinate.blockId.id,
      timeLineModelId: coord.timeLineModelId.id
    }).subscribe({
      next: (dto) => {
        let nextValue = <UIScenarioState>{
          ...this.store.getValue(),
          modelSelection: {
            ... this.store.getValue().modelSelection,
            selectedCoord2: dto.timeLineModelCoordinate,
            selectedCoord: dto.timeLineModelCoordinate
          },
          timeLineSelection: {
            ... this.store.getValue().timeLineSelection,
            timeLineModel:  dto,
            timeLineId
          },
        }
        this.store.next(nextValue)
    }})
  }

  private reloadTimelineModelSummaries(uiScenario: UiScenarioDto) {
    log('reloadTimelineModelSummaries(uiScenario)', uiScenario)
    this.timeLineControllerService.getTimeLineModelSummariesUsingGet({
      storyDocId: this.storyDocId.id,
      blockId: this.blockId.id
    }).subscribe({
      next: (summaries => {
        let nextValue = {
          ...this.store.getValue(),
          modelSelection: {
            ... this.store.getValue().modelSelection,
            models: summaries,
            // selectedCoord: summaries.find(summary => this.equalTimeLineModelCoord(summary.timeLineModelCoordinate, uiScenario.timeLineModelCoordinate))?.timeLineModelCoordinate,
          }
        }
        this.store.next(nextValue)})
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

  private logStateChanges() {
    return this.state$.subscribe((state)=> { log('   state$ >> ', state) })
  }
}
