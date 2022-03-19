import { Component, OnInit } from '@angular/core';
import {ScreenDesignService} from "../screen-design.service";
import {ScreenDesignDto, SdComponentDto, SdComponentId, SdContainerDto} from "@storydoc/models";
import {faBorderAll, faCube} from '@fortawesome/free-solid-svg-icons'

interface item {
  depth: number
  label: string
  type: 'COMPONENT' | 'CONTAINER'
  id: SdComponentId
  component: SdComponentDto
}

@Component({
  selector: 'app-component-tree',
  templateUrl: './component-tree.component.html',
  styleUrls: ['./component-tree.component.scss']
})
export class ComponentTreeComponent implements OnInit {

  constructor(private service: ScreenDesignService) { }

  screenDesign$ = this.service.screenDesign$

  selectedContainer$ = this.service.selectedContainer$

  selection$ = this.service.selection$
  selectedComponent : SdComponentDto

  ngOnInit(): void {
    this.screenDesign$.subscribe(screenDesign => this.refreshTree(screenDesign))
    this.selection$.subscribe(component => {
      if (component && component!=='NONE') {
        this.selectedComponent = component
      }
    })
  }


  tree: item[]
  counter: number

  faBorderAll = faBorderAll
  faCube = faCube

  private refreshTree(screenDesign: ScreenDesignDto) {
    if (!screenDesign) return
    this.tree = []
    this.counter = 1
    this.recursiveAdd(screenDesign.rootContainer, 0)
  }

  private recursiveAdd(container: SdContainerDto, depth: number) {
    this.tree.push({
      depth,
      label: container.label? container.label : `container ${this.counter++}`,
      type: "CONTAINER",
      id: null,
      component: null
    })
    container.children.forEach( (child) => {
      if (child.component) {
        this.tree.push({
          depth: depth+1,
          label: child.component.name ? child.component.name :  `${child.component.type} ${this.counter++}`,
          type: "COMPONENT",
          id: child.component.id,
          component: child.component
        })
      } else if (child.container) {
        this.recursiveAdd(child.container, depth+1)
      }
    })
  }

  isSelected(treeItem: item) {
    return treeItem && treeItem.id?.id === this.selectedComponent?.id.id

  }

  select(treeItem: item) {
    this.service.selectComponent(treeItem.component)
    this.editName(treeItem)
  }

  editing: SdComponentDto

  editName(treeItem: item) {
    this.editing = treeItem.component
  }

  isEditing(treeItem: item) {
    return this.editing && this.editing.id.id===treeItem.id?.id
  }

}
