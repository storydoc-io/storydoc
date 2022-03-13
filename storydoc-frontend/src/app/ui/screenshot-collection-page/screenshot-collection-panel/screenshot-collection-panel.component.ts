import {Component, Input, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Observable, Subscription} from "rxjs";
import {ScreenShotCollectionDto, ScreenshotCoordinate, ScreenShotDto, ScreenShotId} from "@storydoc/models";
import {AdminDataService, ConfirmationDialogSpec, LinkService, ModalService, PopupMenuComponent} from "@storydoc/common";
import {ScreenshotCollectionService} from "../screenshot-collection.service";
import {ActivatedRoute} from "@angular/router";
import {ScreenshotDialogData, ScreenshotDialogSpec} from "../create-screenshot-dialog/create-screenshot-dialog.component";

@Component({
  selector: 'app-screenshot-collection-panel',
  templateUrl: './screenshot-collection-panel.component.html',
  styleUrls: ['./screenshot-collection-panel.component.scss']
})
export class ScreenshotCollectionPanelComponent implements OnInit, OnDestroy{

  constructor(
    private screenshotCollectionService: ScreenshotCollectionService,
    private modalService: ModalService,
    private admin: AdminDataService,
  ) { }

  settings$ = this.admin.settings$
  maxFileSize : number

  @Input()
  screenshotCollection$: Observable<ScreenShotCollectionDto>
  screenshotCollection: ScreenShotCollectionDto

  @Input()
  readonly: boolean = true

  private subscriptions: Subscription[] = []

  ngOnInit(): void {
      this.subscriptions.push(this.screenshotCollection$.subscribe(screenshotCollectionDTO => this.screenshotCollection=screenshotCollectionDTO))
      this.subscriptions.push(this.settings$.subscribe((settings)=> this.maxFileSize = settings?.maxFileSize))
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }

  dragStart(ev: DragEvent, screenshotId: ScreenShotId) {
    let screenshotCoordinate = this.screenshotCoordinate(screenshotId)
    ev.dataTransfer.setData("text", JSON.stringify(screenshotCoordinate));
  }

  screenshotCoordinate(screenShotId: ScreenShotId) {
    return <ScreenshotCoordinate>{
      collectionCoordinate: this.screenshotCollection.coordinate,
      screenShotId
    };
  }

  // create screenshot dialog
  screenshotDialogSpec: ScreenshotDialogSpec

  screenshotTracker(index, item: ScreenShotDto) {
    return item.id.id;
  }

  getScreenshotDialogId() {
    return "screenshot-dialog"
  }

  openScreenshotDialog(spec: ScreenshotDialogSpec) {
    this.screenshotDialogSpec = spec
    this.modalService.open(this.getScreenshotDialogId())
  }

  private closeScreenShotDialog() {
    this.modalService.close(this.getScreenshotDialogId())
  }

  addScreenShot() {
    this.openScreenshotDialog({
        mode: 'NEW',
        data: {
          name: null,
          file: null,
          fileSource: null,
          fileSize: 0
        },
        confirm: (data) => { this.confirmAddScreenshot(data); this.closeScreenShotDialog() },
        cancel: () => this.closeScreenShotDialog(),
        maxFileSize : this.maxFileSize
      }
    )
  }

  confirmAddScreenshot(data: ScreenshotDialogData) {
    this.screenshotCollectionService.AddScreenshot({
      fileSource: data.fileSource,
      name: data.name
    }, () => {
    })
  }

  // confirmation dialog

  confirmationDialogSpec: ConfirmationDialogSpec

  confirmationDialogId(): string {
    return 'confirmation-dialog'
  }

  openConfirmationDialog(confirmationDialogSpec: ConfirmationDialogSpec) {
    this.confirmationDialogSpec = confirmationDialogSpec
    this.modalService.open(this.confirmationDialogId())
  }

  closeConfirmationDialog() {
    this.modalService.close(this.confirmationDialogId())
  }


  // menu
  @ViewChild(PopupMenuComponent) menu: PopupMenuComponent

  openMenu(event: MouseEvent, screenShot: ScreenShotDto) {
    this.menu.items = [
      {
        label: 'Rename',
        onClick: () => this.renameScreenshot(screenShot)
      },
      {
        label: 'Delete',
        onClick: () => this.deleteScreenshot(screenShot)
      }
    ]
    this.menu.open(event)
    return false
  }

  private renameScreenshot(screenShot: ScreenShotDto) {
    this.openScreenshotDialog({
        mode: 'UPDATE',
        data: {
          name: screenShot.name,
          file: null,
          fileSource: null,
          fileSize: 0
        },
        confirm: (data) => { this.confirmRenameScreenShot(screenShot, data); this.closeScreenShotDialog() },
        cancel: () => this.closeScreenShotDialog(),
        maxFileSize : this.maxFileSize
      }
    )
  }

  private confirmRenameScreenShot(screenShot: ScreenShotDto, data: ScreenshotDialogData) {
    this.screenshotCollectionService.renameScreenShot(screenShot.id, data.name)
  }


  private deleteScreenshot(screenShot: ScreenShotDto) {
    this.openConfirmationDialog({
      title: "Confim Delete",
      message: `Delete screenshot '${screenShot.name}' ?`,
      confirm: () => { this.confirmDeleteScreenShot(screenShot); this.closeConfirmationDialog() },
      cancel: () => this.closeConfirmationDialog()
    })
  }

  private confirmDeleteScreenShot(screenShot: ScreenShotDto) {
    this.screenshotCollectionService.deleteScreenshot(screenShot.id)
  }


}
