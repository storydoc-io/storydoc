import {Injectable, OnInit} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {StoryDocSummaryDto} from "../api/models/story-doc-summary-dto";
import {StoryDocRestControllerService} from "../api/services/story-doc-rest-controller.service";
import {map} from "rxjs/operators";
import {StoryDocId} from "../api/models/story-doc-id";

interface DocumentManagerState {
  summaries: StoryDocSummaryDto[]
}

@Injectable({
  providedIn: 'root'
})
export class DocumentManagerService implements OnInit {

  constructor(private storyDocRestControllerService: StoryDocRestControllerService) {
    this.refreshDocumentList()
  }

  private store = new BehaviorSubject<DocumentManagerState>({ summaries: []})
  private state$ = this.store.asObservable()

  summaries$ = this.state$.pipe(
    map(state => state.summaries)
  )

  ngOnInit(): void {
  }

  private refreshDocumentList() {
    this.storyDocRestControllerService.getDocumentsUsingGet().subscribe({
      next: summaries => this.store.next({ summaries })})
  }

  public addDocument(name: string) {
    this.storyDocRestControllerService.createDocumentUsingPost({ name }).subscribe({
      next: value => this.refreshDocumentList()
    })
  }

  public renameDocument(data: { storyDocId: StoryDocId, name: string}) {
    this.storyDocRestControllerService.changeDocumentNameUsingPut({
      storyDocId: data.storyDocId.id,
      name: data.name
    }).subscribe({
      next: (value) => this.refreshDocumentList()
    })
  }

  public deleteDocument(storyDocId: StoryDocId) {
    this.storyDocRestControllerService.removeDocumentUsingDelete({
      storyDocId: storyDocId.id,
    }).subscribe({
      next: (value) => this.refreshDocumentList()
    })

  }

}
