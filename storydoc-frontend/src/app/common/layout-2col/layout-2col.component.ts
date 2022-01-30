import {Component, Input, TemplateRef} from '@angular/core';

@Component({
  selector: 'app-layout-2col',
  templateUrl: './layout-2col.component.html',
  styleUrls: ['./layout-2col.component.scss']
})
export class Layout2ColComponent {

  @Input()
  breadcrumb: TemplateRef<any>

  @Input()
  title: TemplateRef<any>

  @Input()
  leftColumn: TemplateRef<any>

  @Input()
  rightColumn: TemplateRef<any>

}
