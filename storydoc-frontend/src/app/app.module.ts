import {environment} from "../environments/environment";
import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {ModalComponent} from '@storydoc/common'
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {DocumentComponent} from './document/document.component';
import {HttpClientModule} from "@angular/common/http";
import {BlockComponent} from './document/block/block.component';
import {TextBlockComponent} from './document/block/text-block/text-block.component';
import {TitleComponent} from './document/title/title.component';
import {ArtifactBlockComponent} from './document/block/artifact-block/artifact-block.component';
import {UIScenarioPageComponent} from './uiscenario-page/uiscenario-page.component';
import {ReactiveFormsModule} from '@angular/forms';
import {CodeTracePageComponent} from './code-trace-page/code-trace-page.component';
import {DocumentManagerPageComponent} from './document-manager-page/document-manager-page.component';
import {CreateDocumentDialogComponent} from './document-manager-page/create-document-dialog/create-document-dialog.component';
import {CreateBlockDialogComponent} from './document/create-block-dialog/create-block-dialog.component';
import {CreateArtifactDialogComponent} from './document/create-artifact-dialog/create-artifact-dialog.component';
import {ScreenshotCollectionPageComponent} from './screenshot-collection-page/screenshot-collection-page.component';
import {CreateScreenshotDialogComponent} from './screenshot-collection-page/create-screenshot-dialog/create-screenshot-dialog.component';
import {AddScreenshotDialogComponent} from './uiscenario-page/add-screenshot-dialog/add-screenshot-dialog.component';
import {PopupMenuComponent} from '@storydoc/common';
import {Layout2ColComponent} from './common/layout-2col/layout-2col.component';
import {Layout1ColComponent} from './common/layout-1col/layout-1col.component';
import {TimelinePageComponent} from './timeline-page/timeline-page.component';
import {CreateItemDialogComponent} from './timeline-page/create-item-dialog/create-item-dialog.component';
import {TimeLineSelectionPanelComponent} from './uiscenario-page/time-line-selection-panel/time-line-selection-panel.component';
import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {BorderComponent} from './common/border/border.component';
import {ScreenshotPanelComponent} from './uiscenario-page/screenshot-panel/screenshot-panel.component';
import {DragDropModule} from '@angular/cdk/drag-drop';
import {ScreenshotThumbnailComponent} from './uiscenario-page/screenshot-thumbnail/screenshot-thumbnail.component';
import {ConfirmationDialogComponent} from '@storydoc/common';
import {ApiModule} from "./api/api.module";
import { ScenarioPanelComponent } from './uiscenario-page/scenario-panel/scenario-panel.component';
import {VarDirective} from "./common/ng-var.directive";

@NgModule({
  declarations: [
    VarDirective,
    ModalComponent,
    AppComponent,
    DocumentComponent,
    BlockComponent,
    TextBlockComponent,
    TitleComponent,
    ArtifactBlockComponent,
    UIScenarioPageComponent,
    CodeTracePageComponent,
    DocumentManagerPageComponent,
    CreateDocumentDialogComponent,
    CreateBlockDialogComponent,
    CreateArtifactDialogComponent,
    ScreenshotCollectionPageComponent,
    CreateScreenshotDialogComponent,
    AddScreenshotDialogComponent,
    PopupMenuComponent,
    Layout2ColComponent,
    Layout1ColComponent,
    TimelinePageComponent,
    CreateItemDialogComponent,
    TimeLineSelectionPanelComponent,
    BorderComponent,
    ScreenshotPanelComponent,
    ScreenshotThumbnailComponent,
    ConfirmationDialogComponent,
    ScenarioPanelComponent,
  ],
  imports: [
    ApiModule.forRoot({ rootUrl: 'http://localhost:' + environment.port }),
    DragDropModule,
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FontAwesomeModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
