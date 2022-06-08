import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {TimelinePageComponent} from "./ui/timeline-page/timeline-page.component";
import {ScreenshotCollectionPageComponent} from "./ui/screenshot-collection-page/screenshot-collection-page.component";
import {UIScenarioPageComponent} from "./ui/uiscenario-page/uiscenario-page.component";
import {DocumentManagerPageComponent} from './document/document-manager-page/document-manager-page.component';
import {SettingsPageComponent} from "./document/settings-page/settings-page.component";
import {DocumentComponent} from "./document/document-page/document.component";
import {CodeTracePageComponent} from "./code/code-trace-page/code-trace-page.component";
import {DBNavigationPageComponent} from "./db/dbnavigation-page/dbnavigation-page.component";
import {DBDataPageComponent} from "./db/dbdata-page/dbdata-page.component";
import {ScreenDesignPageComponent} from "./ui/screen-design-page/screen-design-page.component";
import {CodeConfigurationPageComponent} from "./code/code-configuration-page/code-configuration-page.component";

const routes: Routes = [
  {path: '', redirectTo: '/fe/documents', pathMatch: 'full'},
  {path: 'fe/documents', component: DocumentManagerPageComponent},
  {path: 'fe/settings', component: SettingsPageComponent},
  {path: 'fe/document/:id', component: DocumentComponent},
  {path: 'fe/db-data', component: DBDataPageComponent},
  {path: 'fe/db-navigation', component: DBNavigationPageComponent},
  {path: 'fe/code-trace/d/:documentId/b/:blockId/a/:artifactId', component: CodeTracePageComponent},
  {path: 'fe/source-code-config/d/:documentId/b/:blockId/a/:artifactId', component: CodeConfigurationPageComponent},
  {path: 'fe/timeline/d/:documentId/b/:blockId/a/:artifactId', component: TimelinePageComponent},
  {path: 'fe/ui-screenshot-collection/d/:documentId/b/:blockId/a/:artifactId', component: ScreenshotCollectionPageComponent},
  {path: 'fe/ui-scenario/d/:documentId/b/:blockId/a/:artifactId', component: UIScenarioPageComponent},
  {path: 'fe/ui-screen-design/d/:documentId/b/:blockId/a/:artifactId', component: ScreenDesignPageComponent},
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
