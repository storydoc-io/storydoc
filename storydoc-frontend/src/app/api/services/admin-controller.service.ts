/* tslint:disable */
/* eslint-disable */
import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';
import { RequestBuilder } from '../request-builder';
import { Observable } from 'rxjs';
import { map, filter } from 'rxjs/operators';

import { StoryDocServerProperties } from '../models/story-doc-server-properties';


/**
 * Admin Controller
 */
@Injectable({
  providedIn: 'root',
})
export class AdminControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation getConfigUsingGet
   */
  static readonly GetConfigUsingGetPath = '/api/admin/config';

  /**
   * getConfig.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getConfigUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getConfigUsingGet$Response(params?: {
  }): Observable<StrictHttpResponse<StoryDocServerProperties>> {

    const rb = new RequestBuilder(this.rootUrl, AdminControllerService.GetConfigUsingGetPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<StoryDocServerProperties>;
      })
    );
  }

  /**
   * getConfig.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getConfigUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getConfigUsingGet(params?: {
  }): Observable<StoryDocServerProperties> {

    return this.getConfigUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<StoryDocServerProperties>) => r.body as StoryDocServerProperties)
    );
  }

}
