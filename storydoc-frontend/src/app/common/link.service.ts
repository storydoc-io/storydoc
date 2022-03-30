import {environment} from "../../environments/environment";
import {Injectable} from '@angular/core';
import {ScreenshotCoordinate, StoryDocSummaryDto} from "@storydoc/models";

@Injectable({
  providedIn: 'root'
})
export class LinkService {

  constructor() {
  }

  public toStoryDoc(storyDocSummary: StoryDocSummaryDto): string[] {
    return ['/fe/document', storyDocSummary.storyDocId.id]
  }

  rootUrl() : string {
    return environment.production ? '' : ('http://localhost:' + environment.port)
  }

  getScreenshotUrl(coordinate: ScreenshotCoordinate) {
    return this.rootUrl() +'/api/ui/screenshot'
      + '/' + coordinate.collectionCoordinate.blockCoordinate.storyDocId.id
      + '/' + coordinate.collectionCoordinate.blockCoordinate.blockId.id
      + '/' + coordinate.collectionCoordinate.screenShotCollectionId.id
      + '/' + coordinate.screenShotId.id
  }
}
