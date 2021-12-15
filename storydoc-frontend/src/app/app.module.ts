import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FoldersPageComponent } from './folders-page/folders-page.component';
import { DocumentComponent } from './document/document.component';
import {HttpClientModule} from "@angular/common/http";
import { BlockComponent } from './document/block/block.component';
import { MockBlockComponent } from './document/block/mock-block/mock-block.component';
import { TextBlockComponent } from './document/block/text-block/text-block.component';
import { UIBlockComponent } from './document/block/uiblock/uiblock.component';
import { ThumbNailComponent } from './document/block/uiblock/thumb-nail/thumb-nail.component';
import { TitleComponent } from './document/title/title.component';
import { UiWalkthroughComponent } from './document/block/ui-walkthrough/ui-walkthrough.component';
import { ArtifactBlockComponent } from './document/block/artifact-block/artifact-block.component';
import {Routes} from "@angular/router";
import { UIMockupPageComponent } from './uimockup-page/uimockup-page.component';
import { UIScenarioPageComponent } from './uiscenario-page/uiscenario-page.component';
import { DBConnectionSettingsComponent } from './document/block/dbconnection-settings/dbconnection-settings.component';
import { ReactiveFormsModule } from '@angular/forms';
import { CodeTracePageComponent } from './code-trace-page/code-trace-page.component';

@NgModule({
  declarations: [
    AppComponent,
    FoldersPageComponent,
    DocumentComponent,
    BlockComponent,
    MockBlockComponent,
    TextBlockComponent,
    UIBlockComponent,
    ThumbNailComponent,
    TitleComponent,
    UiWalkthroughComponent,
    ArtifactBlockComponent,
    UIMockupPageComponent,
    UIScenarioPageComponent,
    DBConnectionSettingsComponent,
    CodeTracePageComponent,
  ],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
