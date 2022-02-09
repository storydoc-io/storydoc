import {Injectable, OnDestroy} from '@angular/core';
import {UiScenarioDto} from "../api/models/ui-scenario-dto";
import {BehaviorSubject, Observable, Subscription} from "rxjs";
import {distinctUntilChanged, map, tap} from "rxjs/operators";
import {UiRestControllerService} from "../api/services/ui-rest-controller.service";
import {StoryDocId} from "../api/models/story-doc-id";
import {BlockId} from "../api/models/block-id";
import {TimeLineModelCoordinate} from "../api/models/time-line-model-coordinate";
import {TimeLineId} from "../api/models/time-line-id";
import {TimeLineControllerService} from "../api/services/time-line-controller.service";
import {TimeLineModelDto} from "../api/models/time-line-model-dto";
import {TimeLineSelection} from "./time-line-selection-panel/time-line-selection-panel.component";
import {ScreenshotCoordinate} from "../api/models/screenshot-coordinate";
import {TimeLineItemId} from "../api/models/time-line-item-id";
import {TimeLineModelSummaryDto} from "../api/models/time-line-model-summary-dto";
import {TimeLineModelId} from "../api/models/time-line-model-id";
import {ArtifactBlockCoordinate} from "../api/models/artifact-block-coordinate";

interface TimelineModelSelectionState {
  selectedTimeLineModelSummary?: TimeLineModelSummaryDto,
  timeLineModelSummaries?: TimeLineModelSummaryDto[]
}

interface UIScenarioState {
  storyDocId?: StoryDocId,
  blockId?: BlockId,
  uiScenarioId?: string,
  uiScenarioDto?: UiScenarioDto,
  timelineModelSelectionState? : TimelineModelSelectionState
  selectedTimeLineModelDto?: TimeLineModelDto,
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

  private store = new BehaviorSubject<UIScenarioState>({ })
  private state$ = this.store.asObservable()

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
    distinctUntilChanged()
  )

  timeLineModel$ = this.state$.pipe(
    map(state => state.selectedTimeLineModelDto),
    distinctUntilChanged()
  )

  timeLineModelSelection$ = this.state$.pipe(
    map(state => state.timelineModelSelectionState),
    distinctUntilChanged(),
    tap(dto => console.log('timeLineModelSelection$', dto))
  )

  private subscriptions: Subscription[] = []

  init(): void {
    this.subscriptions.push(this.refreshTimeLineModelWhenScenarioUpdates())
    this.subscriptions.push(this.refreshTimeLineModelSummariesWhenScenarioUpdates())
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }

  loadUIScenario(params: {storyDocId: StoryDocId, blockId: BlockId, uiScenarioId: string} ) {
    this.store.next(params)
    this.reloadScenario()
  }

  public selectTimeLineModel(coord: TimeLineModelCoordinate) {
    if (coord) {
      this.timeLineControllerService.getTimeLineModelUsingGet({
        storyDocId: coord.blockCoordinate.storyDocId.id,
        blockId: coord.blockCoordinate.blockId.id,
        timeLineModelId: coord.timeLineModelId.id
      }).subscribe({
        next: (value => this.reloadScenario())
      })
    }
  }

  public setScenarioTimeLine(selection: TimeLineSelection) {
    this.uiRestControllerService.setUiScenarioTimeLineUsingPost({
      storyDocId: this.storyDocId.id,
      blockId: this.blockId.id,
      uiScenarioId: this.uiScenarioId,
      timeLineModelStoryDocId: selection.timeLineModelCoordinate.blockCoordinate.storyDocId.id,
      timeLineModelBlockId:    selection.timeLineModelCoordinate.blockCoordinate.blockId.id,
      timeLineModelId:         selection.timeLineModelCoordinate.timeLineModelId.id,
      timeLineId:              selection.timeLineId.id
    }).subscribe({
      next: value => { this.reloadScenario() }
    })
  }

  public addScreenshotToScenario(screenshotCoordinate: ScreenshotCoordinate, timeLineItemId: TimeLineItemId ) {
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
    this.uiRestControllerService.getMockUiUsingGet({
      storyDocId: this.storyDocId.id,
      blockId: this.blockId.id,
      id: this.uiScenarioId
    }).subscribe({
      next: dto => this.store.next({
        ... this.store.getValue(),
        uiScenarioDto: dto
      })
    })
  }

  private refreshTimeLineModelWhenScenarioUpdates(): Subscription {
    return this.uiScenario$.subscribe({ next: uiScenario => {
        if (uiScenario) {
          this.reloadTimeLineModel(uiScenario.timeLineModelCoordinate, uiScenario.timeLineId)
        }
      }})
  }

  private refreshTimeLineModelSummariesWhenScenarioUpdates() : Subscription{
    return this.uiScenario$.subscribe({ next: uiScenario => {
        if (uiScenario) {
          this.reloadTimelineModelSummaries(uiScenario)
        }
      }})
  }

  private reloadTimeLineModel(timeLineModelCoordinate: TimeLineModelCoordinate, timeLineId: TimeLineId) {
    this.timeLineControllerService.getTimeLineModelUsingGet({
      storyDocId: timeLineModelCoordinate.blockCoordinate.storyDocId.id,
      blockId: timeLineModelCoordinate.blockCoordinate.blockId.id,
      timeLineModelId: timeLineModelCoordinate.timeLineModelId.id
    }).subscribe({
      next: dto => this.store.next({
        ... this.store.getValue(),
        selectedTimeLineModelDto: dto
      })
    })
  }

  private reloadTimelineModelSummaries(uiScenario: UiScenarioDto) {
    this.timeLineControllerService.getTimeLineModelSummariesUsingGet({
      storyDocId: this.storyDocId.id,
      blockId: this.blockId.id
    }).subscribe({
      next: (summaries => this.store.next({
        ... this.store.getValue(),
        timelineModelSelectionState: {
          timeLineModelSummaries: summaries,
          selectedTimeLineModelSummary: summaries.find(summary => this.equalTimeLineModelCoord(summary.timeLineModelCoordinate, uiScenario.timeLineModelCoordinate)),
        }
      }))
    })

  }

  private equalTimeLineCoord(tlmCoord1: TimeLineModelCoordinate, tlId1: TimeLineId, tlmCoord2: TimeLineModelCoordinate, tlId2: TimeLineId) {
    return this.equalTimeLineModelCoord(tlmCoord1, tlmCoord2) && this.equalTimeLineId(tlId1, tlId2)
  }

  private equalTimeLineModelCoord(coord1: TimeLineModelCoordinate, coord2: TimeLineModelCoordinate): boolean {
     return this.equalTimeLineModelId(coord1.timeLineModelId, coord2.timeLineModelId) && this.equalBlockCoordinate(coord1.blockCoordinate, coord2.blockCoordinate)
  }

  private equalTimeLineId(id1: TimeLineId, id2: TimeLineId):boolean {
    return id1?.id === id2?.id;
  }

  private equalTimeLineModelId(id1: TimeLineModelId, id2: TimeLineModelId):boolean {
    return id1?.id === id2?.id;
  }

  private equalBlockCoordinate(coord1: ArtifactBlockCoordinate, coord2: ArtifactBlockCoordinate):boolean {
    return coord1.storyDocId?.id === coord2?.storyDocId.id && coord1?.blockId.id === coord2?.blockId.id;
  }
}
