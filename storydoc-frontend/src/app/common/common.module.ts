import {NgModule} from "@angular/core";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations'

import {CoreModule} from "../core.module";

import {VarDirective} from "./ng-var.directive";
import {BackButtonComponent} from './back-button/back-button.component';
import {LoadingDirective} from './loading.directive';
import {PanelComponent} from './panel/panel.component';
import {ConfirmationDialogComponent, ModalComponent, PopupMenuComponent} from '@storydoc/common';
import {Layout1ColComponent} from './layout-1col/layout-1col.component';
import {Layout2ColComponent} from './layout-2col/layout-2col.component';
import { BreadcrumbComponent } from './breadcrumb/breadcrumb.component';
import { HeaderComponent } from './header/header.component';
import { PLayerPanelComponent } from './presentation/player-panel/player-panel.component';
import { PresentationLayoutComponent } from './presentation/presentation-layout/presentation-layout.component';

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
    BreadcrumbComponent,
    HeaderComponent,
    PLayerPanelComponent,
    PresentationLayoutComponent,
  ],
  imports: [
    CoreModule,
    BrowserAnimationsModule
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
    BreadcrumbComponent,
    PresentationLayoutComponent,
    PLayerPanelComponent,
  ]
})
export class StoryDocCommonModule {

}
