import {Component, OnDestroy, OnInit} from '@angular/core';
import {CodeTraceItemDto} from "@storydoc/models";
import {CodeService} from "../../code.service";
import {Subscription} from "rxjs";
import {log, logChangesToObservable} from "@storydoc/common";

@Component({
  selector: 'app-code-trace-panel',
  templateUrl: './code-trace-panel.component.html',
  styleUrls: ['./code-trace-panel.component.scss']
})
export class CodeTracePanelComponent implements OnInit, OnDestroy {

  constructor(private codeService: CodeService) { }

  codeTrace$ = this.codeService.codeTrace$

  selectedItem$ = this.codeService.selectedItem$
  selectedItem

  private subscriptions: Subscription[] = []

  ngOnInit() : void {
    this.subscriptions.push(this.selectedItem$.subscribe(item => this.selectedItem = item))
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }

  getClassName(item: CodeTraceItemDto) {
    return item.className?.split('.').slice(-1)[0]
  }

  selectItem(item: CodeTraceItemDto) {
    this.codeService.selectTraceItem(item)
  }

  isSelected(item: CodeTraceItemDto): boolean {
      return this.selectedItem === item
  }
}
