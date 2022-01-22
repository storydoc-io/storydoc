import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {StoryDocDto} from "../api/models/story-doc-dto";
import {StoryDocRestControllerService} from "../api/services/story-doc-rest-controller.service";

@Injectable({
  providedIn: 'root'
})
export class DocumentDataService {

  constructor(private storyDocRestControllerService: StoryDocRestControllerService) {  }

   getDocument(id: string): Observable<StoryDocDto> {
    return this.storyDocRestControllerService.getDocumentUsingGet({id})
  }

}
