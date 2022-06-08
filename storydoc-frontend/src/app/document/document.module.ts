import { NgModule } from '@angular/core';

import {DocumentComponent} from './document-page/document.component';
import {BlockComponent} from './document-page/block/block.component';
import {TextBlockComponent} from './document-page/block/text-block/text-block.component';
import {ArtifactBlockComponent} from './document-page/block/artifact-block/artifact-block.component';
import {CreateArtifactDialogComponent} from './document-page/create-artifact-dialog/create-artifact-dialog.component';
import {CreateBlockDialogComponent} from './document-page/create-block-dialog/create-block-dialog.component';
import {TitleComponent} from './title/title.component';
import {CreateDocumentDialogComponent} from './document-manager-page/create-document-dialog/create-document-dialog.component';
import {DocumentManagerPageComponent} from './document-manager-page/document-manager-page.component';
import {StoryDocCommonModule} from "../common/common.module";
import {CoreModule} from "../core.module";
import { SettingsDialogComponent } from './settings-page/settings-dialog/settings-dialog.component';
import { SettingsPageComponent } from './settings-page/settings-page.component';


@NgModule({
  declarations: [
    DocumentComponent,
    BlockComponent,
    TextBlockComponent,
    TitleComponent,
    ArtifactBlockComponent,
    DocumentManagerPageComponent,
    CreateDocumentDialogComponent,
    CreateBlockDialogComponent,
    CreateArtifactDialogComponent,
    SettingsDialogComponent,
    SettingsPageComponent,
  ],
  imports: [
    CoreModule,
    StoryDocCommonModule
  ], exports: [
        DocumentManagerPageComponent,
        DocumentComponent,
    ]
})
export class DocumentModule { }
