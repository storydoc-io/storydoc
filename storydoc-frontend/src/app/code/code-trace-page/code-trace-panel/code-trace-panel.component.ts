import {Component, OnDestroy, OnInit} from '@angular/core';
import {CodeTraceDto, StitchItemDto} from "@storydoc/models";
import {
  CodeExecutionEnterEvent,
  CodeExecutionReturnEvent,
  CodeService,
  isCodeExecutionEnterEvent,
  isCodeExecutionReturnEvent,
  isCodeTestCaseBDDEvent,
  StitchEvent,
  TestCaseBDDEvent
} from "../../code.service";
import {Subscription} from "rxjs";

interface IndentedEvent {
  indent: number,
  event: StitchEvent
}

interface TreeNode {
  name: string,
  children: TreeNode[]
}


@Component({
  selector: 'app-code-trace-panel',
  templateUrl: './code-trace-panel.component.html',
  styleUrls: ['./code-trace-panel.component.scss']
})
export class CodeTracePanelComponent implements OnInit, OnDestroy {

  constructor(private codeService: CodeService) {
  }

  codeTrace$ = this.codeService.codeTrace$
  indentedItems: IndentedEvent[]

  selectedItem$ = this.codeService.selectedItem$
  selectedItem

  nodes: TreeNode[]

  private subscriptions: Subscription[] = []

  ngOnInit(): void {
    this.subscriptions.push(this.selectedItem$.subscribe(item => this.selectedItem = item))
    this.subscriptions.push(this.codeTrace$.subscribe(codeTrace => {
      if (codeTrace) this.nodes = this.toTreeNodes(codeTrace.items)
    }))
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }

  toStitchEvent(item: StitchItemDto): StitchEvent {
    switch (item.modelName) {
      case 'CodeExecution': {
        switch (item.eventName) {
          case 'MethodCalled': {
            return <CodeExecutionEnterEvent> {
              modelName: item.modelName,
              eventName: item.eventName,
              className : item.attributes['typeName'],
              methodName : item.attributes['functionName']
            }
          }
          case 'MethodReturn': {
            return <CodeExecutionReturnEvent> {
              modelName: item.modelName,
              eventName: item.eventName,
              className : item.attributes['typeName'],
              methodName : item.attributes['functionName']
            }
          }
          default: {
            console.log('unmapped CodeExecution event, eventName not supported  ', item)
            return
          }
        }
      }
      case 'TestScenario': {
        switch (item.eventName) {
          case 'given': {
            return <TestCaseBDDEvent> {
              modelName: item.modelName,
              eventName: item.eventName,
              noun: item.attributes['noun'],
              text: item.attributes['text']
            }
          }
          default: {
            console.log('unmapped TestScenario event, eventName not supported ', item)
            return
          }
        }
      }
      default: {
        console.log('unmapped event. model not supported ', item)
        return
      }
    }

  }

  getClassName(item: CodeExecutionEnterEvent) {
    return item.className?.split('.').slice(-1)[0]
  }

  selectItem(item: StitchItemDto) {
    this.codeService.selectTraceItem(item)
  }

  isSelected(item: StitchItemDto): boolean {
    return this.selectedItem === item
  }

  getLabel(event: StitchEvent): string {
    if (!event) return "empty event"
    if (isCodeExecutionEnterEvent(event)) {
      return this.getClassName(event) + ' :: ' + event.methodName
    }
    if (isCodeExecutionReturnEvent(event)) {
      return this.getClassName(event) + ' :: ' + event.methodName
    }
    if (isCodeTestCaseBDDEvent(event)) {
      return event.noun + ' :: ' + event.text
    }
    return ''

  }

  toTreeNodes(items: StitchItemDto[]): TreeNode[] {
    if (!items) return
    return items.map(item => {
      let event = this.toStitchEvent(item)
      return {
        event,
        name: this.getLabel(event),
        children: this.toTreeNodes(item.children)
      }
    })
  }

  isBDDEvent(event: StitchEvent) {
    return isCodeTestCaseBDDEvent(event)
  }

  isCodeEvent(event: StitchEvent) {
    return isCodeExecutionEnterEvent(event) || isCodeExecutionReturnEvent(event)
  }


  go($event: MouseEvent) {

  }
}
