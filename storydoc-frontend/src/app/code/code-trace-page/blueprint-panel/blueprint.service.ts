import {Injectable, OnDestroy} from '@angular/core';
import {BluePrint, CodeTraceDto, Role} from "@storydoc/models";
import {BehaviorSubject, combineLatest, Subscription} from "rxjs";
import {distinctUntilChanged, map} from "rxjs/operators";
import {CodeRestControllerService} from "@storydoc/services";
import {log, logChangesToObservable} from "@storydoc/common";
import {CodeService} from "../../code.service";
import {CodeExecutionEnterEvent, getSimpleClassName, nodeToPath, TreeNode} from "../../code.functions";

export interface BasePart {
  type: string
  name: string
}

export interface CompositePart extends BasePart {
  subElements: BasePart[]
}

export interface BluePrintPart extends CompositePart {
  type: "BluePrint"
}

export interface MethodPart {
  id: string
  methodName: string
  active: boolean
}

export interface ClassPart extends BasePart {
  type: "ClassElem"
  stereoType: string
  className: string
  methods: MethodPart[]
}

export interface Line {
  idFrom: string
  idTo: string
}

export interface DiagramPart {
  bluePrint: BluePrintPart,
  lines: Line[]
}

interface BlueprintStoreState {
  bluePrint: BluePrint
  diagramPart: DiagramPart
}

@Injectable()
export class BlueprintService implements OnDestroy {

  constructor(private codeRestControllerService: CodeRestControllerService, private codeService: CodeService) { this.init() }

  selectedNode$ = this.codeService.selectedNode$

  codeTraceDto$ = this.codeService.codeTrace$

  private bluePrintStore = new BehaviorSubject<BlueprintStoreState>({bluePrint: null, diagramPart: null})

  blueprint$ = this.bluePrintStore.pipe(
    map(state => state.bluePrint),
    distinctUntilChanged(),
  )

  diagramInput$ = combineLatest([this.codeTraceDto$, this.blueprint$, this.selectedNode$])

  diagramPart$ = this.bluePrintStore.pipe(
    map(state => state.diagramPart),
    distinctUntilChanged(),
  )

  private subscriptions: Subscription[] = []

  init() {
    this.subscriptions.push(logChangesToObservable('blueprintStore::blueprint$ >>', this.blueprint$))
    this.loadBluePrint()
    this.subscriptions.push(this.diagramInput$.subscribe( (arr: [CodeTraceDto, BluePrint, TreeNode])  => {
      log('diagramInput$ -> createDiagram()', arr)
      this.updateDiagram(arr[0], arr[1], arr[2])
    }))

  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }

  loadBluePrint() {
    log('loadBluePrint()')
    this.codeRestControllerService.getBluePrintUsingGet({}).subscribe(blueprint => {
      this.bluePrintStore.next({
        ...this.bluePrintStore.value,
        bluePrint: blueprint
      })
    })

  }

  updateDiagram(codeTrace: CodeTraceDto, bluePrint: BluePrint, treeNode: TreeNode) {
    log('updateDiagram()')
    let classNames = nodeToPath(treeNode).map(pathNode => (<CodeExecutionEnterEvent>(pathNode.data.event)).className)
    this.codeRestControllerService.classifyMultipleUsingPost({body: classNames}).subscribe(response => {
      let classificationMap = new Map(Object.entries(response))
      let diagram = new TreeNodes2DiagramConverter(bluePrint, classificationMap).run(treeNode)
      console.log('diagramPart', diagram)
      this.bluePrintStore.next({
        ...this.bluePrintStore.value,
        diagramPart: diagram
      })
    })

  }



}



class TreeNodes2DiagramConverter {

  bluePrint: BluePrint
  classificationMap: Map<string, string[]>

  idCounter: number = 0
  lastId: string
  lines: Line[] = []

  constructor(bluePrint: BluePrint, classificationMap: Map<string, string[]>) {
    this.bluePrint = bluePrint;
    this.classificationMap = classificationMap;
  }

  public run(node: TreeNode): DiagramPart {
    if (!this.bluePrint || !this.classificationMap) return null
    let diagramPart: DiagramPart = <DiagramPart>{
      bluePrint: <BluePrintPart>{
        name: this.bluePrint.name,
        subElements: []
      },
      lines: this.lines
    }
    this.merge(nodeToPath(node).reverse(), diagramPart)
    return diagramPart
  }

  private merge(path: TreeNode[], diagramPart: DiagramPart): DiagramPart {
    for (let pathElem of path) {
      let event: CodeExecutionEnterEvent = <CodeExecutionEnterEvent>pathElem.data.event
      if (!event) continue
      let className = event.className
      let classifications = this.classificationMap.get(className)

      let classificationIdx = 0
      let currentClassification = classifications[classificationIdx]
      let currentBluePrintElement = this.bluePrint
      let currentDiagramPart: BasePart = diagramPart.bluePrint

      for (classificationIdx = 1; classificationIdx < classifications.length; classificationIdx++) {
        currentClassification = classifications[classificationIdx]
        let parentBluePrintElement = currentBluePrintElement
        let parentDiagramPart = currentDiagramPart
//        console.log("parentBluePrintElement", parentBluePrintElement)
//        console.log("parentDiagramPart", parentDiagramPart)

        currentBluePrintElement = parentBluePrintElement.subElements.find(subElem => {
          switch (subElem.type) {
            case "BluePrint":
              return (<BluePrint>subElem).name === currentClassification
            case "Role":
              return (<Role>subElem).name === currentClassification
            default:
              false
          }
        })
        currentDiagramPart = (<BluePrintPart>parentDiagramPart).subElements.find(subPart => subPart.name === currentClassification)
//        console.log("currentBluePrintElement", currentBluePrintElement)
//        console.log("currentDiagramPart", currentDiagramPart)
        if (currentDiagramPart === undefined) {
          switch (currentBluePrintElement.type) {
            case "BluePrint": {
              currentDiagramPart = <BluePrintPart>{
                type: "BluePrint",
                name: currentClassification,
                subElements: []
              }
              break
            }
            case "Role": {
              let id = 'id' + this.idCounter++
              currentDiagramPart = <ClassPart>{
                type: "ClassElem",
                stereoType: currentClassification,
                name: getSimpleClassName(className),
                className: className,
                methods: [{
                  active: true,
                  id,
                  methodName: event.methodName
                }],
              }
              if (this.lastId) {
                this.lines.push(<Line>{
                  idFrom: this.lastId,
                  idTo: id
                })
              }
              this.lastId = id
              break
            }
            default:
              console.log("no currentBluePrintElement.type", currentBluePrintElement.type)
          }
          (<CompositePart>parentDiagramPart).subElements.push(currentDiagramPart)
        }
      }
    }
    return diagramPart
  }

}

function dummyDiagram(): DiagramPart {
  return <DiagramPart>{
    bluePrint: <BluePrintPart>{
      type: "BluePrint",
      name: 'Storydoc Backend',
      subElements: [
        <BluePrintPart>{
          name: "Code Domain",
          type: "BluePrint",
          subElements: [
            <BluePrintPart>{
              type: "BluePrint",
              name: "App Layer",
              subElements: [
                <ClassPart>{
                  type: "ClassElem",
                  name: "CodeServiceImpl",
                  methods: [
                    <MethodPart>{
                      id: "id1",
                      methodName: "createCodeExecution()",
                      active: true,
                    }
                  ]
                }
              ]
            },
            <BluePrintPart>{
              type: "BluePrint",
              name: "Infra Layer",
              subElements: [
                <ClassPart>{
                  type: "ClassElem",
                  name: "CodeStorageImpl",
                  methods: [
                    <MethodPart>{
                      id: "id2",
                      methodName: "createCodeExecution()",
                      active: true,
                    }
                  ]
                }
              ]
            },
          ]

        },
        <BluePrintPart>{
          name: "StoryDoc Domain",
          type: "BluePrint",
          subElements: [
            <BluePrintPart>{
              type: 'BluePrint',
              name: "App Layer",
              subElements: [
                <ClassPart>{
                  type: "ClassElem",
                  name: "DocumentServiceImpl",
                  methods: [
                    <MethodPart>{
                      id: "id3",
                      methodName: "addArtifact()",
                      active: true,
                    }
                  ]
                }
              ]
            }
          ]
        }
      ]
    },
    lines: [
      <Line>{idFrom: "id1", idTo: "id2"},
      <Line>{idFrom: "id2", idTo: "id3"}
    ]
  }
}
