import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {DocumentComponent} from "./document/document.component";
import {UIScenarioPageComponent} from "./uiscenario-page/uiscenario-page.component";
import {ScreenshotCollectionPageComponent} from "./screenshot-collection-page/screenshot-collection-page.component";
import {CodeTracePageComponent} from "./code-trace-page/code-trace-page.component";
import {DocumentManagerPageComponent} from './document-manager-page/document-manager-page.component';
import {TimelinePageComponent} from "./timeline-page/timeline-page.component";
import {DBNavigationPageComponent} from "./dbnavigation-page/dbnavigation-page.component";
import {DBDataPageComponent} from "./dbdata-page/dbdata-page.component";

const routes: Routes = [
  {path: '', redirectTo: '/fe/documents', pathMatch: 'full'},
  {path: 'fe/documents', component: DocumentManagerPageComponent},
  {path: 'fe/document/:id', component: DocumentComponent},
  {path: 'fe/timeline/d/:documentId/b/:blockId/a/:artifactId', component: TimelinePageComponent},
  {path: 'fe/ui-scenario/d/:documentId/b/:blockId/a/:artifactId', component: UIScenarioPageComponent},
  {path: 'fe/ui-screenshot-collection/d/:documentId/b/:blockId/a/:artifactId', component: ScreenshotCollectionPageComponent},
  {path: 'fe/code-trace/d/:documentId/b/:blockId/a/:artifactId', component: CodeTracePageComponent},
  {path: 'fe/db-navigation', component: DBNavigationPageComponent},
  {path: 'fe/db-data', component: DBDataPageComponent},
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
