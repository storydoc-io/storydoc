import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {DocumentComponent} from "./document/document.component";
import {UIScenarioPageComponent} from "./uiscenario-page/uiscenario-page.component";
import {ScreenshotCollectionPageComponent} from "./screenshot-collection-page/screenshot-collection-page.component";
import {CodeTracePageComponent} from "./code-trace-page/code-trace-page.component";
import { DocumentManagerPageComponent } from './document-manager-page/document-manager-page.component';
import {TimelinePageComponent} from "./timeline-page/timeline-page.component";

const routes: Routes = [
  { path: '',   redirectTo: '/documents', pathMatch: 'full' },
  { path: 'documents', component: DocumentManagerPageComponent },
  { path: 'document/:id', component: DocumentComponent },
  { path: 'timeline/d/:documentId/b/:blockId/a/:artifactId', component: TimelinePageComponent },
  { path: 'ui-scenario/d/:documentId/b/:blockId/a/:artifactId', component: UIScenarioPageComponent },
  { path: 'ui-screenshot-collection/d/:documentId/b/:blockId/a/:artifactId', component: ScreenshotCollectionPageComponent },
  { path: 'code-trace/d/:documentId/b/:blockId/a/:artifactId', component: CodeTracePageComponent },
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
