import {Component, Input, OnInit} from '@angular/core';
import {BlockDto} from "../../api/models/block-dto";
import {StoryDocId} from "../../api/models/story-doc-id";

@Component({
  selector: 'app-block',
  templateUrl: './block.component.html',
  styleUrls: ['./block.component.scss']
})
export class BlockComponent implements OnInit {

  constructor() { }

  @Input()
  block: BlockDto

  @Input()
  documentId: StoryDocId

  ngOnInit(): void {
  }

  numbering(): string {
    if (!this.block) return ''
    let val =  ''
    for(let nr of this.block.numbering) {
      val += (val.length==0 ? '' :'.') + nr
    }
    return val
  }
}
