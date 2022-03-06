import {NgModule} from '@angular/core';
import {CoreModule} from "../core.module";
import {DBNavigationPageComponent} from "./dbnavigation-page/dbnavigation-page.component";
import {DBDataPageComponent} from "./dbdata-page/dbdata-page.component";
import {StoryDocCommonModule} from "../common/common.module";


@NgModule({
  declarations: [
    DBNavigationPageComponent,
    DBDataPageComponent,
  ],
  imports: [
    CoreModule,
    StoryDocCommonModule
  ],
  exports: [
    DBNavigationPageComponent,
    DBDataPageComponent,
  ]
})
export class DBModule { }
