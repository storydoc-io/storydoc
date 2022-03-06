// angular
import {NgModule} from '@angular/core';
// app global
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
// core
// common
// document

import {UIModule} from './ui/ui.module'
import {CoreModule} from "./core.module";
import {CodeModule} from "./code/code.module";
import {DBModule} from "./db/db.module";
import {DocumentModule} from "./document/document.module";

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    CoreModule,
    AppRoutingModule,
    CodeModule,
    DBModule,
    UIModule,
    DocumentModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
