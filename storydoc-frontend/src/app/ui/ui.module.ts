import { NgModule } from '@angular/core';

import {CoreModule} from "../core.module";
import {StoryDocCommonModule} from "../common/common.module";

// ui
import {TimelinePageComponent} from './timeline-page/timeline-page.component';
import {CreateItemDialogComponent} from './timeline-page/create-item-dialog/create-item-dialog.component';
import { ScreenshotCollectionPanelComponent } from './screenshot-collection-page/screenshot-collection-panel/screenshot-collection-panel.component';
import {ScreenshotCollectionPageComponent} from './screenshot-collection-page/screenshot-collection-page.component';
import {CreateScreenshotDialogComponent} from './screenshot-collection-page/create-screenshot-dialog/create-screenshot-dialog.component';
import {UIScenarioPageComponent} from './uiscenario-page/uiscenario-page.component';
import {AddScreenshotDialogComponent} from './uiscenario-page/add-screenshot-dialog/add-screenshot-dialog.component';
import {TimeLineSelectionPanelComponent} from './uiscenario-page/time-line-selection-panel/time-line-selection-panel.component';
import {ScreenshotPanelComponent} from './uiscenario-page/screenshot-panel/screenshot-panel.component';
import {ScreenshotThumbnailComponent} from './uiscenario-page/screenshot-thumbnail/screenshot-thumbnail.component';
import { ScenarioPanelComponent } from './uiscenario-page/scenario-panel/scenario-panel.component';
import { ScenarioConfigDialogComponent } from './uiscenario-page/scenario-config-dialog/scenario-config-dialog.component';
import { ScenarioPresentationComponent } from './uiscenario-page/scenario-presentation/scenario-presentation.component';
import { ScreenDesignPageComponent } from './screen-design-page/screen-design-page.component';
import { PaletteComponent } from './screen-design-page/palette/palette.component';
import { ComponentDetailsComponent } from './screen-design-page/component-details/component-details.component';
import { ComponentTreeComponent } from './screen-design-page/component-tree/component-tree.component';
import { CanvasComponent } from './screen-design-page/canvas/canvas.component';


@NgModule({
  declarations: [
    // screenshot collection
    CreateScreenshotDialogComponent,
    AddScreenshotDialogComponent,
    ScreenshotCollectionPageComponent,
    // UI scenario
    ScreenshotPanelComponent,
    ScreenshotThumbnailComponent,
    ScenarioPanelComponent,
    ScenarioConfigDialogComponent,
    TimeLineSelectionPanelComponent,
    UIScenarioPageComponent,
    // timeline
    ScreenshotCollectionPanelComponent,
    CreateItemDialogComponent,
    TimelinePageComponent,
    ScenarioPresentationComponent,
    ScreenDesignPageComponent,
    PaletteComponent,
    ComponentDetailsComponent,
    ComponentTreeComponent,
    CanvasComponent,
  ],
  imports: [
    CoreModule,
    StoryDocCommonModule
  ],
  exports: [
    UIScenarioPageComponent,
    ScreenshotCollectionPageComponent,
    TimelinePageComponent,
  ]
})
export class UIModule { }
