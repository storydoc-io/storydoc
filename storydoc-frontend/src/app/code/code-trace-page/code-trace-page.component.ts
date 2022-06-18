import {Component, HostListener, OnDestroy, OnInit} from '@angular/core';
import {LinkService, ModalService} from "@storydoc/common";
import {ActivatedRoute} from "@angular/router";
import {CodeExecutionCoordinate} from "@storydoc/models";
import {CodeTraceConfigDialogData, CodeTraceConfigDialogSpec} from "./code-trace-configuration-dialog/code-trace-configuration-dialog.component";
import {Subscription} from "rxjs";
import {CodeService} from "../code.service";

@Component({
  selector: 'app-code-trace-page',
  templateUrl: './code-trace-page.component.html',
  styleUrls: ['./code-trace-page.component.scss'],
  providers: [CodeService]
})
export class CodeTracePageComponent implements OnInit, OnDestroy{

  constructor(
    public link: LinkService,
    private route: ActivatedRoute,
    private modalService: ModalService,
    private codeService: CodeService) {
  }

  codeTrace$ = this.codeService.codeTrace$

  codeTraceCoord$ = this.codeService.codeTraceCoord$
  codeTraceCoord : CodeExecutionCoordinate

  subscriptions: Subscription[] = []

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      let documentId = params.get('documentId')
      let blockId = params.get('blockId')
      let id = params.get('artifactId')
      if (id) {
        this.codeService.loadTrace( <CodeExecutionCoordinate>{
          blockCoordinate: {
            storyDocId: {id: documentId},
            blockId: {id: blockId}
          },
          codeExecutionId: { id  }
        })
      }
    });
    this.subscriptions.push(this.codeTraceCoord$.subscribe((codeTraceCoord) => this.codeTraceCoord = codeTraceCoord))
  }

  ngOnDestroy() {
    this.subscriptions.forEach( (subscription) => subscription.unsubscribe())
  }

  // configuration dialog

  configurationDialogId(): string {
    return 'configuration-dialog-id'
  };


  codeTraceConfigDialogSpec : CodeTraceConfigDialogSpec

  configure() {
    this.codeTraceConfigDialogSpec = {
      data: null,
      coord: this.codeTraceCoord,
      confirm: (data: CodeTraceConfigDialogData) => this.confirmConfig(data),
      cancel: () => this.cancelConfig()
    }
    this.modalService.open(this.configurationDialogId())
  }

  private confirmConfig(data: CodeTraceConfigDialogData) {
    this.codeService.setStitchDetails({
      stitchFile: data.stitchFile,
      testClass: data.testClass,
      testMethod: data.testMethod
    })
    this.modalService.close(this.configurationDialogId())
  }

  private cancelConfig() {
    this.modalService.close(this.configurationDialogId())
  }

  @HostListener('scroll', ['$event']) // for scroll events of the current element
  //@HostListener('window:scroll', ['$event']) // for window scroll events
  onScrollBluePrint($event: Event) {
    console.log("****** scroll ****")
    this.codeService.scroll()
  }
}
