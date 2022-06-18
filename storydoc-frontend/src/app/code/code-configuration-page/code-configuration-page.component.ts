import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {CodeService} from "../code.service";
import {SourceCodeConfigCoordinate} from '@storydoc/models'
import {LinkService, ModalService, PopupMenuComponent} from "@storydoc/common";
import {CodeConfigurationData, CodeConfigurationSpec} from "./code-configuration-dialog/code-configuration-dialog.component";
import {SourceCodeConfigDto} from "../../api/models/source-code-config-dto";
import {CodeConfigurationService} from "./code-configuration.service";
import {StitchConfigurationService} from "../stitch-configuration-page/stitch-configuration.service";

@Component({
  selector: 'app-code-configuration-page',
  templateUrl: './code-configuration-page.component.html',
  styleUrls: ['./code-configuration-page.component.scss'],
  providers: [CodeConfigurationService]
})
export class CodeConfigurationPageComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    public link: LinkService,
    private modalService: ModalService,
    private codeConfigurationService: CodeConfigurationService
  ) { }

  config$ = this.codeConfigurationService.config$

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      let documentId = params.get('documentId')
      let blockId = params.get('blockId')
      let id = params.get('artifactId')
      if (id) {
        this.codeConfigurationService.loadConfig(<SourceCodeConfigCoordinate>{
            blockCoordinate: {
              storyDocId: { id: documentId },
              blockId: { id: blockId }
            },
            sourceCodeConfigId: { id: id}
        });
      }
    });
  }

  // config dialog
  codeConfigurationSpec: CodeConfigurationSpec

  getDialogId() {
    return "code-configuration-dialog"
  }

  openConfigDialog(dialogInput: CodeConfigurationSpec) {
    this.codeConfigurationSpec = dialogInput
    this.modalService.open(this.getDialogId())
  }

  closeConfigDialog() {
    this.modalService.close(this.getDialogId())
  }

  addPath(addMore: boolean = false) {
    this.openConfigDialog({
      mode: 'NEW',
      data: {
        path: null,
        addMore
      },
      confirm: (data) => {
        this.confirmAddPath(data)
        this.closeConfigDialog()
        if (data.addMore) this.addPath(true)
      },
      cancel: () => this.closeConfigDialog()
    })
  }

  confirmAddPath(data: CodeConfigurationData) {
    this.codeConfigurationService.addPathToConfig(data.path)
  }

  // popup menu
  @ViewChild(PopupMenuComponent) menu: PopupMenuComponent

  openMenu(event: MouseEvent, config: SourceCodeConfigDto , dir: string) {
    this.menu.items = [
      {
        label: 'Rename',
        onClick: () => {} // todo
      },
      {
        label: 'Delete',
        onClick: () => {} // todo
      }
    ]
    this.menu.open(event)
    return false

  }

  // artifact selction dialog

  artifactSelectionDialogId(): string {
    return 'artifact-selection-dialog-id'
  };

  openArtifactSelectionDialog() {
    this.modalService.open(this.artifactSelectionDialogId())
  }

  closeArtifactSelectionDialog() {
    this.modalService.close(this.artifactSelectionDialogId())
  }



}
