import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {TimeLineModelDto} from "../../api/models/time-line-model-dto";
import {ArtifactBlockCoordinate} from "../../api/models/artifact-block-coordinate";
import {TimeLineControllerService} from "../../api/services/time-line-controller.service";
import {Observable} from "rxjs";
import {TimeLineModelSummaryDto} from "../../api/models/time-line-model-summary-dto";
import {ModalService} from "../../common/modal-service";
import {
  TimeLineModelSelectionDialogData,
  TimeLineModelSelectionDialogInput
} from "../time-line-model-selection-dialog/time-line-model-selection-dialog.component";
import { faCog } from '@fortawesome/free-solid-svg-icons';
import {TimeLineModelCoordinate} from "../../api/models/time-line-model-coordinate";
import {TimeLineDto} from "../../api/models/time-line-dto";
import {TimeLineId} from "../../api/models/time-line-id";

export interface TimeLineSelection {
  timeLineModelCoordinate : TimeLineModelCoordinate
  timeLineId : TimeLineId
}

@Component({
  selector: 'app-time-line-selection-panel',
  templateUrl: './time-line-selection-panel.component.html',
  styleUrls: ['./time-line-selection-panel.component.scss']
})
export class TimeLineSelectionPanelComponent implements OnInit {

  constructor(
    private modalService: ModalService,
    private timeLineControllerService: TimeLineControllerService
  ) { }

  cogIcon = faCog;

  @Input()
  blockCoordinate : ArtifactBlockCoordinate

  timeLineModel$: Observable<TimeLineModelDto>

  timeLineModelSummaries$: Observable<Array<TimeLineModelSummaryDto>>

  @Output()
  onTimeLineSelection = new EventEmitter<TimeLineSelection>()

  ngOnInit(): void {
    this.timeLineModelSummaries$ = this.timeLineControllerService.getTimeLineModelSummariesUsingGet({
      storyDocId: this.blockCoordinate.storyDocId.id,
      blockId: this.blockCoordinate.blockId.id
    })
    this.formGroup.setValue({
      timeLineModel: null,
      timeLine: null
    })
  }

  formGroup: FormGroup = new FormGroup({
    timeLine: new FormControl(),
    timeLineModel: new FormControl()
  })

  onTimeLineModelChange() {
    let coord = this.formGroup.get('timeLineModel').value;
    this.timeLineModel$ = this.timeLineControllerService.getTimeLineModelUsingGet({
      storyDocId: coord.blockCoordinate.storyDocId.id,
      blockId: coord.blockCoordinate.blockId.id,
      timeLineModelId: coord.timeLineModelId.id
    })
    this.timeLineModel$.subscribe({
      next: value => console.log(value)
    })
  }

  onTimeLineChange() {
    let timeLineModelCoordinate = this.formGroup.get('timeLineModel').value;
    let timeLineId = this.formGroup.get('timeLine').value;
    this.onTimeLineSelection.emit({
      timeLineModelCoordinate: timeLineModelCoordinate,
      timeLineId: timeLineId
    })
  }

  // select timeline model dialog
  dialogInput: TimeLineModelSelectionDialogInput

  getDialogId() {
    return "select-timeline-model-dialog"
  }

  openDialog() {
    this.dialogInput = {
      mode: 'NEW',
      data: {
      }
    }
    this.modalService.open(this.getDialogId())
  }


  confirmDialog(data: TimeLineModelSelectionDialogData) {
    console.log('data: ', data)
    this.modalService.close(this.getDialogId())
  }

  cancelDialog() {
    this.modalService.close(this.getDialogId())
  }


  asArray(timeLines: { [p: string]: TimeLineDto }):TimeLineDto[]  {
    return Object.keys(timeLines).map(key => timeLines[key])
  }

}
