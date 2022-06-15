import {StitchItemDto} from "@storydoc/models";

export interface StitchEvent {
  modelName: string
  eventName: string
}

export interface CodeExecutionEnterEvent extends StitchEvent {
  className: string,
  methodName: string
}

export function isCodeExecutionEnterEvent(event: StitchEvent): event is CodeExecutionEnterEvent {
  return event.modelName === 'CodeExecution' && event.eventName === 'MethodCalled'
}

export interface CodeExecutionReturnEvent extends StitchEvent {
  className: string,
  methodName: string
}

export function isCodeExecutionReturnEvent(event: StitchEvent): event is CodeExecutionReturnEvent {
  return event.modelName === 'CodeExecution' && event.eventName === 'MethodReturn'
}

export interface TestCaseBDDEvent extends StitchEvent {
  noun: string
  text: string
}

export function isCodeTestCaseBDDEvent(event: StitchEvent): event is TestCaseBDDEvent {
  return event.modelName === 'TestScenario' && event.eventName === 'given'
}


export function getClassName(item: CodeExecutionEnterEvent) {
  return getSimpleClassName(item.className)
}

export function getSimpleClassName(fullClassName: string) {
  return fullClassName?.split('.').slice(-1)[0]
}


export function getLabel(event: StitchEvent): string {
  if (!event) return "empty event"
  if (isCodeExecutionEnterEvent(event)) {
    return getClassName(event) + ' :: ' + event.methodName
  }
  if (isCodeExecutionReturnEvent(event)) {
    return getClassName(event) + ' :: ' + event.methodName
  }
  if (isCodeTestCaseBDDEvent(event)) {
    return event.noun + ' :: ' + event.text
  }
  return ''

}

export function toStitchEvent(item: StitchItemDto): StitchEvent {
  switch (item.modelName) {
    case 'CodeExecution': {
      switch (item.eventName) {
        case 'MethodCalled': {
          return <CodeExecutionEnterEvent>{
            modelName: item.modelName,
            eventName: item.eventName,
            className: item.attributes['typeName'],
            methodName: item.attributes['functionName']
          }
        }
        case 'MethodReturn': {
          return <CodeExecutionReturnEvent>{
            modelName: item.modelName,
            eventName: item.eventName,
            className: item.attributes['typeName'],
            methodName: item.attributes['functionName']
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
          return <TestCaseBDDEvent>{
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

export interface TreeNode {
  name: string,
  children: TreeNode[],
  event?: StitchEvent,
  data? : any
  parent?: TreeNode
}


export function nodeToPath(node: TreeNode) : TreeNode[]{
  let result = []
  let curNode = node
  while(curNode) {
    let b = curNode
    if (b.data?.event) {
      result.push(curNode)
    }
    curNode = curNode.parent
  }
  return result
}
