import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import { HostListener } from '@angular/core';
import {ComponentDescriptorDto, SdComponentDto, SdComponentTypeSelectionDto} from "@storydoc/models";
import {ScreenDesignService} from "../screen-design.service";

export interface DragRequest {
  type: 'ADD_COMPONENT' | 'MOVE_COMPONENT'
  add: AddComponentData
  move:  MoveComponentData
}

export interface AddComponentData {
  dx: number,
  dy: number,
  desc: ComponentDescriptorDto
}

export interface MoveComponentData {
  x0: number,
  y0: number,
  component: SdComponentDto
}

@Component({
  selector: 'app-canvas',
  templateUrl: './canvas.component.html',
  styleUrls: ['./canvas.component.scss']
})
export class CanvasComponent implements OnInit {

  constructor(private service: ScreenDesignService) {
  }

  @HostListener('document:keyup', ['$event'])
  handleKeyboardEvent(event: KeyboardEvent) {
    if(event.key === 'Delete'){
      this.service.deleteSelection()
    }
  }

  screenDesign$ = this.service.screenDesign$

  selection$ = this.service.selection$

  ngOnInit(): void {
  }

  allowDrop(ev: DragEvent) {
    ev.preventDefault();
  }

  @ViewChild("canvasElem") canvasElem: ElementRef

  doDrop(ev: any) {
    ev.preventDefault();
    var data = ev.dataTransfer.getData("text");
    let request = <DragRequest>JSON.parse(data)

    if (request.type==='ADD_COMPONENT') {
      let coord =  {
        x: ev.clientX - this.canvasElem.nativeElement.getBoundingClientRect().left - request.add.dx,
        y: ev.clientY - this.canvasElem.nativeElement.getBoundingClientRect().top -request.add.dy
      }
      let componentDescriptor = request.add.desc
      this.service.addComponent(componentDescriptor, coord)
    }
    else if (request.type==='MOVE_COMPONENT') {
      let dx = request.move.x0 - ev.clientX
      let dy = request.move.y0 - ev.clientY
      let coord =  {
        x: request.move.component.x - dx,
        y: request.move.component.y - dy
      }
      this.service.moveComponent(request.move.component, coord)
    }

  }


  getAttributeValue(attName: string, component: SdComponentDto): string {
    let attribute = component.attributes.find(att => att.name===attName)
    return attribute ? attribute.value : component.name
  }

  select(component: SdComponentDto) {
    this.service.selectComponent(component)
  }

  isSelected(component: SdComponentDto, selection: SdComponentDto | "NONE") {
    if (selection==='NONE') return false
    return component?.id.id === selection?.id.id
  }

  style(child: SdComponentTypeSelectionDto, index: number): string {
    let style =  'position: absolute; top:'+(child.component.y)+'px; left:'+(child.component.x) + 'px;'
    return style
  }

  dragStart(ev: DragEvent, child: SdComponentTypeSelectionDto) {
    let x0 = ev.clientX
    let y0 = ev.clientY
    ev.dataTransfer.setData("text", JSON.stringify(<DragRequest>{
      type : 'MOVE_COMPONENT',
      move: { x0, y0, component: child.component}
    }));

  }

  lassoSelect: boolean = false
  lassoSelectBegin(event: any) {
    if (event.target.id != 'canvas') return
    this.lassoSelect = true
    console.log(event)
  }

  lassoSelectStyle() : string {
    return this.lassoSelect? "" : "visibility: hidden;"
  }

}
