/* tslint:disable */
/* eslint-disable */
import { NgModule, ModuleWithProviders, SkipSelf, Optional } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApiConfiguration, ApiConfigurationParams } from './api-configuration';

import { CodeRestControllerService } from './services/code-rest-controller.service';
import { RedirectToAngularService } from './services/redirect-to-angular.service';
import { StoryDocRestControllerService } from './services/story-doc-rest-controller.service';
import { TimeLineControllerService } from './services/time-line-controller.service';
import { UiRestControllerService } from './services/ui-rest-controller.service';
import { WorkspaceRestControllerService } from './services/workspace-rest-controller.service';

/**
 * Module that provides all services and configuration.
 */
@NgModule({
  imports: [],
  exports: [],
  declarations: [],
  providers: [
    CodeRestControllerService,
    RedirectToAngularService,
    StoryDocRestControllerService,
    TimeLineControllerService,
    UiRestControllerService,
    WorkspaceRestControllerService,
    ApiConfiguration
  ],
})
export class ApiModule {
  static forRoot(params: ApiConfigurationParams): ModuleWithProviders<ApiModule> {
    return {
      ngModule: ApiModule,
      providers: [
        {
          provide: ApiConfiguration,
          useValue: params
        }
      ]
    }
  }

  constructor( 
    @Optional() @SkipSelf() parentModule: ApiModule,
    @Optional() http: HttpClient
  ) {
    if (parentModule) {
      throw new Error('ApiModule is already loaded. Import in your base AppModule only.');
    }
    if (!http) {
      throw new Error('You need to import the HttpClientModule in your AppModule! \n' +
      'See also https://github.com/angular/angular/issues/20575');
    }
  }
}
