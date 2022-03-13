import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Observable} from "rxjs";
import {share} from "rxjs/operators";
import {ConfirmationDialogSpec, LinkService, ModalService, PopupMenuComponent} from "@storydoc/common";
import {ArtifactDto, TimeLineDto, TimeLineId, TimeLineItemDto, TimeLineModelDto} from "@storydoc/models";
import {TimeLineControllerService} from "@storydoc/services";
import {ItemDialogData, ItemDialogSpec} from "./create-item-dialog/create-item-dialog.component";

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
      this.documentId = params.get('documentId')
      this.blockId = params.get('blockId')
      this.id = params.get('artifactId')
      if (this.id) {
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

  // item dialog
  itemDialogSpec: ItemDialogSpec

  getDialogId() {
    return "item-dialog"
  }

  openItemDialog(dialogInput: ItemDialogSpec) {
    this.itemDialogSpec = dialogInput
    this.modalService.open(this.getDialogId())
  }

  closeItemDialog() {
    this.modalService.close(this.getDialogId())
  }

  // add item
  addItem() {
    this.openItemDialog({
      mode: 'NEW',
      data: {
        description: null,
      },
      confirm: (data) => { this.confirmAddItem(data); this.closeItemDialog() },
      cancel: () => this.closeItemDialog()
    })
  }

  confirmAddItem(data: ItemDialogData) {
    this.timeLineControllerService.createTimeLineItemUsingPost({
      storyDocId: this.documentId,
      blockId: this.blockId,
      timeLineModelId: this.id,
      // timeLineId: this.timeLineId.id,
      name: data.description
    }).subscribe({
      next: value => this.reload()
    })
  }

  // menu
  @ViewChild(PopupMenuComponent) menu: PopupMenuComponent

  openMenu(event: MouseEvent, timeLine: TimeLineDto, item: TimeLineItemDto) {
    this.menu.items = [
      {
        label: 'Rename',
        onClick: () => this.renameItem(timeLine, item)
      },
      {
        label: 'Delete',
        onClick: () => this.deleteItem(timeLine, item)
      }
    ]
    this.menu.open(event)
    return false
  }

  private renameItem(timeLine: TimeLineDto, item: TimeLineItemDto) {
    this.openItemDialog({
      mode: 'UPDATE',
      data: {
        description: item.description,
      },
      confirm: (data) => { this.confirmRenameItem(timeLine, item, data); this.closeItemDialog() },
      cancel: () => this.closeItemDialog()
    })
  }

  private confirmRenameItem(timeLine: TimeLineDto, item: TimeLineItemDto, data: ItemDialogData) {
    this.timeLineControllerService.renameTimeLineItemUsingPut({
      storyDocId: this.documentId,
      blockId: this.blockId,
      timeLineModelId: this.id,
      timeLineId: timeLine.timeLineId.id,
      timeLineItemId: item.itemId.id,
      name: data.description
    }).subscribe({
      next: value => this.reload()
    })
  }

  deleteItem(timeLine: TimeLineDto, item: TimeLineItemDto) {
    this.openConfirmationDialog({
      title: "Confim Delete",
      message: `Delete timeline item '${item.description}' ?`,
      confirm: () => { this.confirmDeleteItem(timeLine, item); this.closeConfirmationDialog() },
      cancel: () => this.closeConfirmationDialog()
    })
  }

  private confirmDeleteItem(timeLine: TimeLineDto, item: TimeLineItemDto) {
    this.timeLineControllerService.deleteTimeLineItemUsingDelete$Response({
      storyDocId: this.documentId,
      blockId: this.blockId,
      timeLineModelId: this.id,
      timeLineId: timeLine.timeLineId.id,
      timeLineItemId: item.itemId.id,
    }).subscribe({
      next: value => this.reload()
    })
  }

  // confirmation dialog

  confirmationDialogSpec: ConfirmationDialogSpec

  confirmationDialogId(): string {
    return 'confirmation-dialog-' + this.blockId
  }

  openConfirmationDialog(confirmationDialogSpec: ConfirmationDialogSpec) {
    this.confirmationDialogSpec = confirmationDialogSpec
    this.modalService.open(this.confirmationDialogId())
  }

  closeConfirmationDialog() {
    this.modalService.close(this.confirmationDialogId())
  }

}
