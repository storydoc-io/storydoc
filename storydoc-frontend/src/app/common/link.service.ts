import {Injectable} from '@angular/core';
import {StoryDocSummaryDto} from "../api/models/story-doc-summary-dto";
import {ScreenshotCoordinate} from "../api/models/screenshot-coordinate";

@Injectable({
  providedIn: 'root'
})
export class LinkService {

  constructor() {
  }

  public toStoryDoc(storyDocSummary: StoryDocSummaryDto): string[] {
    return ['/document', storyDocSummary.storyDocId.id]
  }

  getScreenshotUrl(coordinate: ScreenshotCoordinate) {
    return 'http://localhost:4200/api/ui/screenshot'
      + '/' + coordinate.collectionCoordinate.blockCoordinate.storyDocId.id
      + '/' + coordinate.collectionCoordinate.blockCoordinate.blockId.id
      + '/' + coordinate.collectionCoordinate.screenShotCollectionId.id
      + '/' + coordinate.screenShotId.id
  }
}
