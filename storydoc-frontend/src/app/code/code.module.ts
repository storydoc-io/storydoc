import { NgModule } from '@angular/core';

import {CodeTracePageComponent} from './code-trace-page/code-trace-page.component';
import {CoreModule} from "../core.module";
import {StoryDocCommonModule} from "../common/common.module";
import { SourceCodePanelComponent } from './code-trace-page/source-code-panel/source-code-panel.component';
import { CodeTracePanelComponent } from './code-trace-page/code-trace-panel/code-trace-panel.component';
import { CodeConfigurationPageComponent } from './code-configuration-page/code-configuration-page.component';
import { CodeConfigurationDialogComponent } from './code-configuration-page/code-configuration-dialog/code-configuration-dialog.component';
import { CodeTraceConfigurationDialogComponent } from './code-trace-page/code-trace-configuration-dialog/code-trace-configuration-dialog.component';
import { BlueprintPanelComponent } from './code-trace-page/blueprint-panel/blueprint-panel.component';
import { BluePrintPartComponent } from "./code-trace-page/blueprint-panel/blue-print-part/blue-print-part.component";
import { ClassPartComponent } from "./code-trace-page/blueprint-panel/class-part/class-part.component";
import { DiagramPartComponent} from "./code-trace-page/blueprint-panel/diagram-part/diagram-part.component";

@NgModule({
  declarations: [
    CodeTracePageComponent,
    SourceCodePanelComponent,
    CodeTracePanelComponent,
    CodeConfigurationPageComponent,
    CodeConfigurationDialogComponent,
    CodeTraceConfigurationDialogComponent,
    BlueprintPanelComponent,
    BluePrintPartComponent,
    ClassPartComponent,
    DiagramPartComponent
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
