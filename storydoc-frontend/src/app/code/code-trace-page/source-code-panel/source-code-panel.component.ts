import { Component, OnInit } from '@angular/core';
import {CodeRestControllerService} from "@storydoc/services";
import {SourceCodeDto} from "@storydoc/models";
import {CodeService} from "../../code.service";
import {Subscription} from "rxjs";


@Component({
  selector: 'app-source-code-panel',
  templateUrl: './source-code-panel.component.html',
  styleUrls: ['./source-code-panel.component.scss']
})
export class SourceCodePanelComponent implements OnInit {

  constructor(
    private codeService: CodeService,
    private codeRestControllerService: CodeRestControllerService) { }

  sourceCode$ = this.codeService.sourceCode$
  sourceCode: SourceCodeDto

  private subscriptions: Subscription[] = []

  ngOnInit() : void {
    this.subscriptions.push(this.sourceCode$.subscribe(sourceCode => this.sourceCode = sourceCode))
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }

  code() {
    if (this.sourceCode) {
      return this.sourceCode.lines.join('\n')
    }
    return ''
  }

}
