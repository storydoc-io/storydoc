import {NgModule} from "@angular/core";

import {CoreModule} from "../core.module";

import {VarDirective} from "./ng-var.directive";
import {BackButtonComponent} from './back-button/back-button.component';
import {LoadingDirective} from './loading.directive';
import {PanelComponent} from './panel/panel.component';
import {ConfirmationDialogComponent, ModalComponent, PopupMenuComponent} from '@storydoc/common';
import {Layout1ColComponent} from './layout-1col/layout-1col.component';
import {Layout2ColComponent} from './layout-2col/layout-2col.component';


@NgModule({
  declarations: [
    VarDirective,
    ModalComponent,
    PopupMenuComponent,
    Layout2ColComponent,
    Layout1ColComponent,
    PanelComponent,
    ConfirmationDialogComponent,
    BackButtonComponent,
    LoadingDirective,
  ],
  imports: [
    CoreModule
  ],
  exports: [
    VarDirective,
    ModalComponent,
    PopupMenuComponent,
    Layout2ColComponent,
    Layout1ColComponent,
    PanelComponent,
    ConfirmationDialogComponent,
    BackButtonComponent,
    LoadingDirective,
  ]
})
export class StoryDocCommonModule {

}
