import {Component, OnInit} from '@angular/core';
import {ScreenDesignService} from "../screen-design.service";
import {ComponentDescriptorDto} from "@storydoc/models";
import {DragRequest} from "../canvas/canvas.component";

@Component({
  selector: 'app-palette',
  templateUrl: './palette.component.html',
  styleUrls: ['./palette.component.scss']
})
export class PaletteComponent implements OnInit {

  constructor(private service: ScreenDesignService) { }

  palette$ = this.service.palette$

  ngOnInit(): void {
  }

  dragStart(ev: any, desc: ComponentDescriptorDto) {
    let dx = ev.clientX - ev.target.getBoundingClientRect().left
    let dy = ev.clientY - ev.target.getBoundingClientRect().top
    ev.dataTransfer.setData("text", JSON.stringify(<DragRequest>{
      type : 'ADD_COMPONENT',
      add: { dx, dy, desc }
    }));
  }

  componentDescTracker(index, item: ComponentDescriptorDto) {
    return item.type;
  }



}
