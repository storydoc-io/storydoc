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
import { UIMockupPageComponent } from './uimockup-page/uimockup-page.component';
import { ReactiveFormsModule } from '@angular/forms';
import { CodeTracePageComponent } from './code-trace-page/code-trace-page.component';
import { DocumentManagerPageComponent } from './document-manager-page/document-manager-page.component';
import { CreateDocumentDialogComponent } from './document-manager-page/create-document-dialog/create-document-dialog.component';
import { CreateBlockDialogComponent } from './document/create-block-dialog/create-block-dialog.component';
import { CreateArtifactDialogComponent } from './document/create-artifact-dialog/create-artifact-dialog.component';
import { ScreenshotCollectionPageComponent } from './screenshot-collection-page/screenshot-collection-page.component';
import { CreateScreenshotDialogComponent } from './screenshot-collection-page/create-screenshot-dialog/create-screenshot-dialog.component';
import { AddScreenshotDialogComponent } from './uimockup-page/add-screenshot-dialog/add-screenshot-dialog.component';
import { PopupMenuComponent } from './common/popup-menu/popup-menu.component';

@NgModule({
  declarations: [
    ModalComponent,
    AppComponent,
    DocumentComponent,
    BlockComponent,
    TextBlockComponent,
    TitleComponent,
    ArtifactBlockComponent,
    UIMockupPageComponent,
    CodeTracePageComponent,
    DocumentManagerPageComponent,
    CreateDocumentDialogComponent,
    CreateBlockDialogComponent,
    CreateArtifactDialogComponent,
    ScreenshotCollectionPageComponent,
    CreateScreenshotDialogComponent,
    AddScreenshotDialogComponent,
    PopupMenuComponent,
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
