import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {LinkService, ModalService} from "@storydoc/common";
import {StitchConfigCoordinate} from "@storydoc/models";
import {StitchConfigurationData, StitchConfigurationSpec} from "../stitch-configuration-page/stitch-configuration-dialog/stitch-configuration-dialog.component";
import {StitchConfigurationService} from "./stitch-configuration.service";

@Component({
  selector: 'app-stitch-configuration-page',
  templateUrl: './stitch-configuration-page.component.html',
  styleUrls: ['./stitch-configuration-page.component.scss'],
  providers: [StitchConfigurationService]
})
export class StitchConfigurationPageComponent implements OnInit {

  constructor(
    private route: ActivatedRoute,
    public link: LinkService,
    private modalService: ModalService,
    private stitchConfigurationService: StitchConfigurationService
  ) { }

  config$ = this.stitchConfigurationService.config$

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      let documentId = params.get('documentId')
      let blockId = params.get('blockId')
      let id = params.get('artifactId')
      if (id) {
        this.stitchConfigurationService.loadConfig(<StitchConfigCoordinate>{
          blockCoordinate: {
            storyDocId: { id: documentId },
            blockId: { id: blockId }
          },
          stitchConfigId: { id: id}
        });
      }
    });
  }

  // config dialog
  codeConfigurationSpec: StitchConfigurationSpec

  getDialogId() {
    return "stitch-configuration-dialog"
  }

  openConfigDialog(dialogInput: StitchConfigurationSpec) {
    this.codeConfigurationSpec = dialogInput
    this.modalService.open(this.getDialogId())
  }

  closeConfigDialog() {
    this.modalService.close(this.getDialogId())
  }

  editPath(addMore: boolean = false) {
    this.openConfigDialog({
      mode: 'NEW',
      data: {
        path: null,
        addMore
      },
      confirm: (data) => {
        this.confirmAddPath(data)
        this.closeConfigDialog()
        if (data.addMore) this.editPath(true)
      },
      cancel: () => this.closeConfigDialog()
    })
  }

  confirmAddPath(data: StitchConfigurationData) {
    this.stitchConfigurationService.setPath(data.path)
  }


}
