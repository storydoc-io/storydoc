import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {DocumentDataService} from "./document-data.service";
import {Observable, Subscription} from "rxjs";
import {StoryDocDto} from "../api/models/story-doc-dto";
import {BlockDto} from "../api/models/block-dto";
import {ActivatedRoute} from "@angular/router";
import {
  DocumentDialogData,
  DocumentDialogSpec
} from "../document-manager-page/create-document-dialog/create-document-dialog.component";
import {BlockDialogData, BlockDialogSpec} from "./create-block-dialog/create-block-dialog.component";
import {ModalService} from "../common/modal-service";
import {StoryDocRestControllerService} from "../api/services/story-doc-rest-controller.service";
import {share} from "rxjs/operators";
import {ConfirmationDialogSpec} from "../common/confirmation-dialog/confirmation-dialog.component";
import {PopupMenuComponent} from "../common/popup-menu/popup-menu.component";

@Component({
  selector: 'app-document',
  templateUrl: './document.component.html',
  styleUrls: ['./document.component.css']
})
export class DocumentComponent implements OnInit, OnDestroy {

  constructor(
    private storyDocRestControllerService: StoryDocRestControllerService,
    private documentDataService: DocumentDataService,
    private route: ActivatedRoute,
    private modalService: ModalService, ) {
  }

  id : string;
  doc$ = this.documentDataService.storyDoc$
  selectedBlock$ = this.documentDataService.selectedBlock$
  selectedBlock: BlockDto

  subscriptions: Subscription[] = []

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.id = params.get('id')
      if (this.id) {
        this.documentDataService.init(this.id);
      }
    });
    this.subscriptions.push(this.selectedBlock$.subscribe({ next: value => this.selectedBlock = value}))
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }

  numbering(block: BlockDto): string {
    if (!block) return ''
    let val =  ''
    for(let nr of block.numbering) {
      val += (val.length==0 ? '' :'.') + nr
    }
    return val
  }

  // left panel

  @ViewChild(PopupMenuComponent) menu:PopupMenuComponent

  openMenu(event: MouseEvent, block: BlockDto) {
    this.selectBlock(block)
    this.menu.items = [
      {
        label: 'Rename',
        onClick: () => this.renameBlock()
      },
      { label: 'Delete',
        onClick: () => this.deleteBlock()
      }
    ]
    this.menu.open(event)
    return false
  }

  selectBlock(block: BlockDto) {
    this.documentDataService.selectBlock(block)
  }

  isSelected(block: BlockDto) : boolean {
    return this.selectedBlock?.blockId.id == block.blockId.id
  }


  // block dialog

  blockDialogId = "block-dialog"

  blockDialogSpec: BlockDialogSpec

  openBlockDialog(blockDialogSpec: BlockDialogSpec) {
    this.blockDialogSpec = blockDialogSpec
    this.modalService.open(this.blockDialogId)
  }

  closeBlockDialog() {
    this.modalService.close(this.blockDialogId)
  }

  // confirmation dialog
  confirmationDialogId = 'confirmation-dialog'
  confirmationDialogSpec: ConfirmationDialogSpec

  openConfirmationDialog(confirmationDialogSpec: ConfirmationDialogSpec) {
    this.confirmationDialogSpec = confirmationDialogSpec
    this.modalService.open(this.confirmationDialogId)
  }

  closeConfirmationDialog() {
    this.modalService.close(this.confirmationDialogId)
  }

  // add block

  addBlock() {
    this.openBlockDialog({
      mode: 'NEW',
      data: {
        name: null
      },
      confirm: (data)=> { this.closeBlockDialog(); this.confirmAddBlock(data)},
      cancel: () => this.closeBlockDialog()
    })
  }

  confirmAddBlock(data: BlockDialogData) {
    this.documentDataService.addBlock(data.name)
  }

  // rename block

  renameBlock() {
    this.openBlockDialog({
      mode: 'UPDATE',
      data: {
        name: this.selectedBlock.title
      },
      confirm: (data)=> { this.closeBlockDialog(); this.confirmRenameBlock(data)},
      cancel: () => this.closeBlockDialog()
    })
  }

  confirmRenameBlock(data: BlockDialogData) {
    this.documentDataService.renameBlock(this.selectedBlock.blockId, data.name)
  }

  // delete block

  deleteBlock() {
    this.openConfirmationDialog({
      title: 'Confirm delete',
      message: `Delete paragraph "${this.selectedBlock.title}" ?`,
      cancel: () => this.closeConfirmationDialog(),
      confirm: () => { this.closeConfirmationDialog(); this.confirmDeleteBlock() }
    })
  }

  confirmDeleteBlock() {
    this.documentDataService.deleteBlock(this.selectedBlock.blockId)
  }


}
