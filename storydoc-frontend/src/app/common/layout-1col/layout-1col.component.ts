import {Component, Input, TemplateRef} from '@angular/core';

@Component({
  selector: 'app-layout-1col',
  templateUrl: './layout-1col.component.html',
  styleUrls: ['./layout-1col.component.scss']
})
export class Layout1ColComponent {

  @Input()
  breadcrumb: TemplateRef<any>

  @Input()
  title: TemplateRef<any>

  @Input()
  middle: TemplateRef<any>

}
