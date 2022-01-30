import { Component, OnInit } from '@angular/core';
import {BehaviorSubject, Observable} from "rxjs";
import {ScreenShotCollectionDto} from "../api/models/screen-shot-collection-dto";
import {ActivatedRoute} from "@angular/router";
import {TimeLineModelDto} from "../api/models/time-line-model-dto";
import {TimeLineControllerService} from "../api/services/time-line-controller.service";
import {LinkService} from "../common/link.service";
import {
  CreateScreenshotDialogData,
  CreateScreenshotDialogInput
} from "../screenshot-collection-page/create-screenshot-dialog/create-screenshot-dialog.component";
import {ModalService} from "../common/modal-service";
import {CreateItemDialogData, CreateItemDialogInput} from "./create-item-dialog/create-item-dialog.component";
import {share, tap} from "rxjs/operators";
import {TimeLineId} from "../api/models/time-line-id";

@Component({
  selector: 'app-timeline-page',
  templateUrl: './timeline-page.component.html',
  styleUrls: ['./timeline-page.component.scss']
})
export class TimelinePageComponent implements OnInit {

  constructor(
    private timeLineControllerService: TimeLineControllerService,
    public link: LinkService,
    private modalService: ModalService,
    private route: ActivatedRoute
  ) {
  }

  timeLineModel$: Observable<TimeLineModelDto>

  documentId: string
  blockId: string
  id: string
  timeLine: 'default'

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.documentId  = params.get('documentId')
      this.blockId = params.get('blockId')
      this.id = params.get('artifactId')
      if (this.id) {
        console.log('params: ', {
          storyDocId: this.documentId,
          blockId: this.blockId,
          id: this.id
        })
        this.reload();
      }
    });
  }

  timeLineId: TimeLineId;

  private reload() {
    this.timeLineModel$ = this.timeLineControllerService.getTimeLineModelUsingGet({
      storyDocId: this.documentId,
      blockId: this.blockId,
      timeLineModelId: this.id
    }).pipe(share())
    this.timeLineModel$.subscribe({
      next: timeLineModel => this.timeLineId = timeLineModel?.timeLines['default'].timeLineId
    })
  }

// create screenshot dialog
  dialogInput: CreateItemDialogInput

  getDialogId() {
    return "create-item-dialog"
  }

  openAddItemDialog() {
    this.dialogInput = {
      mode: 'NEW',
      data: {
        description: null,
      }
    }
    this.modalService.open(this.getDialogId())
  }


  confirmDialog(data: CreateItemDialogData) {
    this.timeLineControllerService.createTimeLineItemUsingPost({
      storyDocId: this.documentId,
      blockId: this.blockId,
      timeLineModelId: this.id,
      // timeLineId: this.timeLineId.id,
      name: data.description
    }).subscribe({
      next: value => this.reload()
    })
    this.modalService.close(this.getDialogId())
  }

  cancelDialog() {
    this.modalService.close(this.getDialogId())
  }

}
