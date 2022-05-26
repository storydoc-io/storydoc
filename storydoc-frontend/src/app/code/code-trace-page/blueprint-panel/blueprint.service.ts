import {Injectable, OnDestroy} from '@angular/core';
import {BluePrint} from "@storydoc/models";
import {BehaviorSubject, Subscription} from "rxjs";
import {distinctUntilChanged, map} from "rxjs/operators";
import {CodeRestControllerService} from "@storydoc/services";
import {log} from "@storydoc/common";
import {CodeService} from "../../code.service";

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
  role: string
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

@Injectable({
  providedIn: 'root'
})
export class BlueprintService implements OnDestroy {

  constructor(private codeRestControllerService: CodeRestControllerService, private codeService: CodeService) { this.init() }

  selectedEvent$ = this.codeService.selectedEvent$

  code

  private bluePrintStore = new BehaviorSubject<BlueprintStoreState>({bluePrint: null, diagramPart: null})

  blueprint$ = this.bluePrintStore.pipe(
    map(state => state.bluePrint),
    distinctUntilChanged(),
  )

  diagramPart$ = this.bluePrintStore.pipe(
    map(state => state.diagramPart),
    distinctUntilChanged(),
  )

  private subscriptions: Subscription[] = []

  init() {
    this.getBluePrint()
    this.subscriptions.push(this.selectedEvent$.subscribe(event => {
      if (!event) return
      log('selectedItem$ -> createDiagram()')
      this.createDiagram()
    }))

  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }

  getBluePrint() {
    log('getBluePrint()')
    this.codeRestControllerService.getBluePrintUsingGet({}).subscribe(blueprint => {
      this.bluePrintStore.next({
        ...this.bluePrintStore.value,
        bluePrint: blueprint
      })
    })

  }

  createDiagram() {

    let diagram = <DiagramPart> {
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
        <Line>{ idFrom: "id1", idTo: "id2"},
        <Line>{ idFrom: "id2", idTo: "id3"}
      ]
    }


    log('createDiagram()')
    this.bluePrintStore.next({
      ...this.bluePrintStore.value,
      diagramPart: diagram
    })
  }

}
