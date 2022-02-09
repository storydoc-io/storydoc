import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ModalComponent } from './common/modal.component'
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DocumentComponent } from './document/document.component';
import {HttpClientModule} from "@angular/common/http";
import { BlockComponent } from './document/block/block.component';
import { TextBlockComponent } from './document/block/text-block/text-block.component';
import { TitleComponent } from './document/title/title.component';
import { ArtifactBlockComponent } from './document/block/artifact-block/artifact-block.component';
import { UIScenarioPageComponent } from './uiscenario-page/uiscenario-page.component';
import { ReactiveFormsModule } from '@angular/forms';
import { CodeTracePageComponent } from './code-trace-page/code-trace-page.component';
import { DocumentManagerPageComponent } from './document-manager-page/document-manager-page.component';
import { CreateDocumentDialogComponent } from './document-manager-page/create-document-dialog/create-document-dialog.component';
import { CreateBlockDialogComponent } from './document/create-block-dialog/create-block-dialog.component';
import { CreateArtifactDialogComponent } from './document/create-artifact-dialog/create-artifact-dialog.component';
import { ScreenshotCollectionPageComponent } from './screenshot-collection-page/screenshot-collection-page.component';
import { CreateScreenshotDialogComponent } from './screenshot-collection-page/create-screenshot-dialog/create-screenshot-dialog.component';
import { AddScreenshotDialogComponent } from './uiscenario-page/add-screenshot-dialog/add-screenshot-dialog.component';
import { PopupMenuComponent } from './common/popup-menu/popup-menu.component';
import { PopupMenuComponent2 } from './common/popup-menu2/popup-menu.component';
import { Layout2ColComponent } from './common/layout-2col/layout-2col.component';
import { Layout1ColComponent } from './common/layout-1col/layout-1col.component';
import { TimelinePageComponent } from './timeline-page/timeline-page.component';
import { CreateItemDialogComponent } from './timeline-page/create-item-dialog/create-item-dialog.component';
import { TimeLineSelectionPanelComponent } from './uiscenario-page/time-line-selection-panel/time-line-selection-panel.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { BorderComponent } from './common/border/border.component';
import { ScreenshotPanelComponent } from './uiscenario-page/screenshot-panel/screenshot-panel.component';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { ScreenshotThumbnailComponent } from './uiscenario-page/screenshot-thumbnail/screenshot-thumbnail.component';
import { ConfirmationDialogComponent } from './common/confirmation-dialog/confirmation-dialog.component';

@NgModule({
  declarations: [
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
    PopupMenuComponent2,
    Layout2ColComponent,
    Layout1ColComponent,
    TimelinePageComponent,
    CreateItemDialogComponent,
    TimeLineSelectionPanelComponent,
    BorderComponent,
    ScreenshotPanelComponent,
    ScreenshotThumbnailComponent,
    ConfirmationDialogComponent,
  ],
  imports: [
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
export class AppModule { }
