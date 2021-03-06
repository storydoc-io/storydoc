import {DOCUMENT} from '@angular/common';
import {Component, Inject, OnDestroy, OnInit} from '@angular/core';
import {Observable, Subscription} from "rxjs";
import {ActivatedRoute} from '@angular/router';
import {closeFullscreen, ConfirmationDialogSpec, LinkService, ModalService, openFullscreen} from "@storydoc/common";
import {BlockCoordinate, ScreenShotCollectionDto, TimeLineId, UiScenarioDto} from "@storydoc/models";
import {UIScenarioService} from "./uiscenario.service";
import {ScenarioConfigDialogData, ScenarioConfigDialogSpec} from "./scenario-config-dialog/scenario-config-dialog.component";


@Component({
  selector: 'app-uiscenario-page',
  templateUrl: './uiscenario-page.component.html',
  styleUrls: ['./uiscenario-page.component.scss']
})
export class UIScenarioPageComponent implements OnInit, OnDestroy {

  constructor(
    @Inject(DOCUMENT) private document: any,
    private modalService: ModalService,
    private route: ActivatedRoute,
    public link: LinkService,
    private uiScenarioService: UIScenarioService
  ) {
  }

  uiScenario$: Observable<UiScenarioDto> = this.uiScenarioService.uiScenario$

  presentationMode$: Observable<boolean> = this.uiScenarioService.presentationMode$
  presentationMode: boolean

  private subscriptions: Subscription[] = []

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      let documentId = params.get('documentId')
      let blockId = params.get('blockId')
      let id = params.get('artifactId')
      if (id) {
        this.uiScenarioService.loadUIScenario({
          storyDocId: {id: documentId},
          blockId: {id: blockId},
          uiScenarioId: id
        })
      }
    });
    this.subscriptions.push(this.presentationMode$.subscribe((presentationMode)=> {
      this.presentationMode = presentationMode
      if (presentationMode) {
        openFullscreen(document)
      } else {
        closeFullscreen(document)
      }
    }))
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }

  togglePresentationMode() {
    this.uiScenarioService.togglePresentationMode()
  }

  // configuration dialog

  configurationDialogId(): string {
    return 'configuration-dialog-id'
  };


  scenarioConfigDialogSpec : ScenarioConfigDialogSpec

  configure() {
    this.scenarioConfigDialogSpec = {
      data: null,
      confirm: (data: ScenarioConfigDialogData) => this.confirmConfig(data),
      cancel: () => this.cancelConfig()
    }
    this.modalService.open(this.configurationDialogId())
  }

  private confirmConfig(data: ScenarioConfigDialogData) {
    this.uiScenarioService.selectTimeLineModel(data.timeLineModel)
    this.modalService.close(this.configurationDialogId())
  }

  private cancelConfig() {
    this.modalService.close(this.configurationDialogId())
  }

}
