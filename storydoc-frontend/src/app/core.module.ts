import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ApiModule} from "./api/api.module";
import {environment} from "../environments/environment";
import {DragDropModule} from "@angular/cdk/drag-drop";
import {HttpClientModule} from "@angular/common/http";
import {BrowserModule} from "@angular/platform-browser";
import {AppRoutingModule} from "./app-routing.module";
import {ReactiveFormsModule} from "@angular/forms";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import { TreeModule } from '@circlon/angular-tree-component';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    ApiModule.forRoot({ rootUrl: environment.production ? '' : ('http://localhost:' + environment.port) }),
    DragDropModule,
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FontAwesomeModule,
    TreeModule
  ],
  exports: [
    CommonModule,
    DragDropModule,
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    FontAwesomeModule,
    TreeModule
  ]
})
export class CoreModule { }
