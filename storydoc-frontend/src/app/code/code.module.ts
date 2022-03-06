import { NgModule } from '@angular/core';

import {CodeTracePageComponent} from './code-trace-page/code-trace-page.component';
import {CoreModule} from "../core.module";
import {StoryDocCommonModule} from "../common/common.module";


@NgModule({
  declarations: [
    CodeTracePageComponent
  ],
  imports: [
    CoreModule,
    StoryDocCommonModule
  ],
  exports: [
    CodeTracePageComponent
  ]
})
export class CodeModule { }
