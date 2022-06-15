import {Component, OnDestroy, OnInit} from '@angular/core';
import {StitchItemDto} from "@storydoc/models";
import {isCodeExecutionEnterEvent, isCodeExecutionReturnEvent, isCodeTestCaseBDDEvent, StitchEvent} from "../../code.functions";
import {Subscription} from "rxjs";
import {CodeService} from "../../code.service";

@Component({
  selector: 'app-code-trace-panel',
  templateUrl: './code-trace-panel.component.html',
  styleUrls: ['./code-trace-panel.component.scss']
})
export class CodeTracePanelComponent implements OnInit, OnDestroy {

  constructor(private codeService: CodeService) {
  }

  treeNodes$ = this.codeService.treeNodes$

  selectedItem$ = this.codeService.selectedEvent$
  selectedItem

  private subscriptions: Subscription[] = []

  ngOnInit(): void {
    this.subscriptions.push(this.selectedItem$.subscribe(item => this.selectedItem = item))
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }


  selectNode(event: any) {
    this.codeService.selectNode(event.node)
  }

  isSelected(item: StitchItemDto): boolean {
    return this.selectedItem === item
  }

  isBDDEvent(event: StitchEvent) {
    return isCodeTestCaseBDDEvent(event)
  }

  isCodeEvent(event: StitchEvent) {
    return isCodeExecutionEnterEvent(event) || isCodeExecutionReturnEvent(event)
  }

  isCodeExecutionEnterEvent(event: StitchEvent) {
    return isCodeExecutionEnterEvent(event)
  }

  isCodeExecutionReturnEvent(event: StitchEvent) {
    return isCodeExecutionReturnEvent(event)
  }

}
