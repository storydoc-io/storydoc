import {Component, Input, OnInit, TemplateRef} from '@angular/core';
import {animate, style, state, transition, trigger} from "@angular/animations";


@Component({
  selector: 'app-presentation-layout',
  templateUrl: './presentation-layout.component.html',
  styleUrls: ['./presentation-layout.component.scss'],
  animations: [
    trigger('showHideAnim', [
      state('show', style({
        opacity: 1
      })),
      state('hide', style({
        opacity: 0
      })),
      transition('* => *', [
        animate('0.4s')
      ]),
    ])
  ]
})
export class PresentationLayoutComponent {

  borderVisible: boolean = false

  @Input()
  top: TemplateRef<any>

  @Input()
  center: TemplateRef<any>

  @Input()
  bottom: TemplateRef<any>

  showBorder() {
    this.borderVisible = true
  }

  hideBorder(){
    this.borderVisible = false
  }

  get showHideState(): string {
    return this.borderVisible? "show" : 'hide'
  }

}
