import { NgModule } from '@angular/core';

import {CodeTracePageComponent} from './code-trace-page/code-trace-page.component';
import {CoreModule} from "../core.module";
import {StoryDocCommonModule} from "../common/common.module";
import { SourceCodePanelComponent } from './code-trace-page/source-code-panel/source-code-panel.component';
import { CodeTracePanelComponent } from './code-trace-page/code-trace-panel/code-trace-panel.component';


@NgModule({
  declarations: [
    CodeTracePageComponent,
    SourceCodePanelComponent,
    CodeTracePanelComponent,
  ],
  imports: [
    CoreModule,
    StoryDocCommonModule,
  ],
  providers: [
  ],
  exports: [
    CodeTracePageComponent
  ]
})
export class CodeModule { }
