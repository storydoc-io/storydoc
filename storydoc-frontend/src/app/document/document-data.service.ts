import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {StoryDocDto} from "../api/models/story-doc-dto";
import {StoryDocRestControllerService} from "../api/services/story-doc-rest-controller.service";
import {map} from "rxjs/operators";
import {BlockId} from "../api/models/block-id";
import {BlockDto} from "../api/models/block-dto";
import {UiRestControllerService} from "../api/services/ui-rest-controller.service";
import {TimeLineControllerService} from "../api/services/time-line-controller.service";
import {ArtifactId} from "../api/models/artifact-id";
import {StoryDocId} from "../api/models/story-doc-id";

interface DocumentState {
  id?: string
  storyDoc?: StoryDocDto
  selectedBlock?: BlockDto
}

@Injectable({
  providedIn: 'root'
})
export class DocumentDataService {

  private store = new BehaviorSubject<DocumentState>({})

  storyDoc$ = this.store.asObservable().pipe(
    map(state => state.storyDoc)
  )

  selectedBlock$ = this.store.asObservable().pipe(
    map(state => state.selectedBlock)
  )

  private getId() {
    return this.store.getValue()?.id;
  }

  constructor(
    private storyDocRestControllerService: StoryDocRestControllerService,
    private uiRestControllerService: UiRestControllerService,
    private timeLineControllerService: TimeLineControllerService
  ) {
  }

  private refresh() {
    this.storyDocRestControllerService.getDocumentUsingGet({id: this.getId()}).subscribe({
      next: storyDoc => this.store.next({...this.store.getValue(), storyDoc})
    })
  }

  init(id: string) {
    this.store.next({id, storyDoc: null})
    this.refresh()
  }

  addBlock(name: string) {
    this.storyDocRestControllerService.addBlockUsingPost({id: this.getId(), name}).subscribe({
      next: value => this.refresh()
    })
  }

  selectBlock(block: BlockDto) {
    this.store.next({...this.store.getValue(), selectedBlock: block})
  }

  renameBlock(blockId: BlockId, name: string) {
    this.storyDocRestControllerService.renameBlockUsingPut({
      storyDocId: this.getId(),
      blockId: blockId.id,
      name
    }).subscribe({
      next: value => this.refresh()
    })
  }

  deleteBlock(blockId: BlockId) {
    this.storyDocRestControllerService.removeBlockUsingDelete({
      storyDocId: this.getId(),
      blockId: blockId.id
    }).subscribe({
      next: value => this.refresh()
    })
  }

  addArtifact(param: { blockId: string; name: string; artifactType: string }) {
    switch (param.artifactType) {
      case 'io.storydoc.server.timeline.domain.TimeLineModel': {
        this.timeLineControllerService.createTimeLineModelUsingPost({
          storyDocId: this.getId(),
          blockId: param.blockId,
          name: param.name
        }).subscribe({
          next: value => this.refresh()
        })
        break
      }
      case 'io.storydoc.server.ui.domain.UIScenario': {
        this.uiRestControllerService.createUiScenarioUsingPost({
          storyDocId: this.getId(),
          blockId: param.blockId,
          name: param.name
        }).subscribe({
          next: value => this.refresh()
        })
        break
      }
      case 'io.storydoc.server.ui.domain.ScreenShotCollection': {
        this.uiRestControllerService.createScreenShotCollectionUsingPost({
          storyDocId: this.getId(),
          blockId: param.blockId,
          name: param.name
        }).subscribe({
          next: value => this.refresh()
        })
        break
      }
   }
  }

  renameArtifact(params: { blockId: BlockId; name: string; artifactId: ArtifactId; storyDocId: StoryDocId }) {
     this.storyDocRestControllerService.renameArtifactUsingPut({
       storyDocId: params.storyDocId.id,
       blockId: params.blockId.id,
       artifactId: params.artifactId.id,
       name: params.name
     }).subscribe( {
       next: value => this.refresh()
     })
  }
}
