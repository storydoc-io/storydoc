import { Injectable } from '@angular/core';
import {DocumentRestControllerService} from "../api/services/document-rest-controller.service";
import {Observable} from "rxjs";
import {StoryDocDto} from "../api/models/story-doc-dto";
import {FolderRestControllerService} from "../api/services/folder-rest-controller.service";

@Injectable({
  providedIn: 'root'
})
export class DocumentDataService {

  constructor(private documentRestControllerService: DocumentRestControllerService, private folderRestControllerService: FolderRestControllerService) {  }

   getDocument(id: string): Observable<StoryDocDto> {
    return this.documentRestControllerService.getDocumentUsingGet({id})
  }

}
