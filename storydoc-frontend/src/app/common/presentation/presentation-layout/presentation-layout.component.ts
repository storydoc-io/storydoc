import {Component, Input, OnInit, TemplateRef} from '@angular/core';

@Component({
  selector: 'app-presentation-layout',
  templateUrl: './presentation-layout.component.html',
  styleUrls: ['./presentation-layout.component.scss']
})
export class PresentationLayoutComponent {

  borderVisible: boolean = false
  mouseVisible: boolean = false

  @Input()
  top: TemplateRef<any>

  @Input()
  center: TemplateRef<any>

  @Input()
  bottom: TemplateRef<any>

  showBorderElements() {
    this.borderVisible = true
  }

  hideBorderElements(){
    this.borderVisible = false
  }

  timer: any
  hideMouseWhenNotMoved(){
    if (this.timer) return
    this.timer = setTimeout( ()=> {
      //this.mouseVisible = false
      this.timer = null
    }, 3000)
  }
}
