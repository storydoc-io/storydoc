import { Component, OnInit } from '@angular/core';
import {DocumentDataService} from "./document-data.service";
import {Observable} from "rxjs";
import {StoryDocDto} from "../api/models/story-doc-dto";
import {BlockDto} from "../api/models/block-dto";

@Component({
  selector: 'app-document',
  templateUrl: './document.component.html',
  styleUrls: ['./document.component.css']
})
export class DocumentComponent implements OnInit {

  constructor(private documentDataService: DocumentDataService) {
    this.doc$ = this.documentDataService.getDocument('6b5bb87c-6d76-4bc4-81a1-b7b8e57619e5')
  }

  doc$ : Observable<StoryDocDto>

  ngOnInit(): void {

  }

  numbering(block: BlockDto): string {
    if (!block) return ''
    let val =  ''
    // @ts-ignore
    for(let nr of block.numbering) {
      val += (val.length==0 ? '' :'.') + nr
    }
    return val
  }


}
