import { Injectable } from '@angular/core';
import {ArtifactDto, BlockCoordinate, BlockDto} from "@storydoc/models";
import {StoryDocRestControllerService} from "@storydoc/services";
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ArtifactSelectionService {

  artifacts$ = new BehaviorSubject<ArtifactDto[]>(null)

  constructor(private storyDocRestControllerService: StoryDocRestControllerService) { }

  select(artifactType: string, blockCoord: BlockCoordinate) {
    this.storyDocRestControllerService.getDocumentUsingGet({ id: blockCoord.storyDocId.id}).subscribe((storyDoc)=>{
        let block: BlockDto = storyDoc.blocks.find((block)=> block.blockId.id===blockCoord.blockId.id)
        let artifacts = block.artifacts.filter((artifact)=> artifact.artifactType===artifactType)
        this.artifacts$.next(artifacts)
    })
  }
}
