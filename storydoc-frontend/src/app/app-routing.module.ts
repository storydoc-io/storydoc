import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {FoldersPageComponent} from "./folders-page/folders-page.component";
import {DocumentComponent} from "./document/document.component";
import {UIMockupPageComponent} from "./uimockup-page/uimockup-page.component";
import {UIScenarioPageComponent} from "./uiscenario-page/uiscenario-page.component";
import {CodeTracePageComponent} from "./code-trace-page/code-trace-page.component";

const routes: Routes = [
  { path: '',   redirectTo: '/folder', pathMatch: 'full' },
  { path: 'folder', component: FoldersPageComponent },
  { path: 'document', component: DocumentComponent },
  { path: 'ui-mockup/document/:documentId/artifact/:artifactId', component: UIMockupPageComponent },
  { path: 'ui-scenario/document/:documentId/artifact/:artifactId', component: UIScenarioPageComponent },
  { path: 'code-trace/document/:documentId/artifact/:artifactId', component: CodeTracePageComponent },
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
