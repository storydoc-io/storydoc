import {DOCUMENT} from '@angular/common';
import {Component, Inject, OnInit} from '@angular/core';
import {Observable} from "rxjs";
import {ActivatedRoute} from '@angular/router';
import {LinkService, ModalService} from "@storydoc/common";
import {openFullscreen, closeFullscreen} from "@storydoc/common"
import {BlockCoordinate, ScreenShotCollectionDto, UiScenarioDto} from "@storydoc/models";
import {TimeLineSelection} from "./time-line-selection-panel/time-line-selection-panel.component";
import {TimeLineSelectionState, UIScenarioService} from "./uiscenario.service";


@Component({
  selector: 'app-uiscenario-page',
  templateUrl: './uiscenario-page.component.html',
  styleUrls: ['./uiscenario-page.component.scss']
})
export class UIScenarioPageComponent implements OnInit {

  constructor(
    @Inject(DOCUMENT) private document: any,
    private modalService: ModalService,
    private route: ActivatedRoute,
    public link: LinkService,
    private uiScenarioService: UIScenarioService
  ) {
  }

  presentationMode: boolean = false

  uiScenario$: Observable<UiScenarioDto> = this.uiScenarioService.uiScenario$

  timeLineSelection$: Observable<TimeLineSelectionState> = this.uiScenarioService.timeLineSelection$

  screenshotCollection: ScreenShotCollectionDto

  documentId: string
  blockId: string
  id: string

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.documentId = params.get('documentId')
      this.blockId = params.get('blockId')
      this.id = params.get('artifactId')
      if (this.id) {
        this.uiScenarioService.loadUIScenario({
          storyDocId: {id: this.documentId},
          blockId: {id: this.blockId},
          uiScenarioId: this.id
        })
      }
    });

  }


  // timeline selection

  selectTimeLine(selection: TimeLineSelection) {
    this.uiScenarioService.setScenarioTimeLine(selection)
  }


  blockCoordinate() {
    return <BlockCoordinate>{
      blockId: {id: this.blockId},
      storyDocId: {id: this.documentId}
    }
  }


  togglePresentationMode() {
    this.presentationMode = !this.presentationMode
    if (this.presentationMode) {
      openFullscreen(document)
    } else {
      closeFullscreen(document)
    }
  }

}
