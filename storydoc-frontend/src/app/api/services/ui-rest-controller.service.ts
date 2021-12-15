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

import { MockUidto } from '../models/mock-uidto';
import { UiBlockDto } from '../models/ui-block-dto';


/**
 * UI Rest Controller
 */
@Injectable({
  providedIn: 'root',
})
export class UiRestControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation getUiUsingGet
   */
  static readonly GetUiUsingGetPath = '/api/ui/';

  /**
   * getUI.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getUiUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getUiUsingGet$Response(params?: {
  }): Observable<StrictHttpResponse<UiBlockDto>> {

    const rb = new RequestBuilder(this.rootUrl, UiRestControllerService.GetUiUsingGetPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<UiBlockDto>;
      })
    );
  }

  /**
   * getUI.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getUiUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getUiUsingGet(params?: {
  }): Observable<UiBlockDto> {

    return this.getUiUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<UiBlockDto>) => r.body as UiBlockDto)
    );
  }

  /**
   * Path part for operation getMockUiUsingGet
   */
  static readonly GetMockUiUsingGetPath = '/api/ui/mockui';

  /**
   * getMockUI.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getMockUiUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getMockUiUsingGet$Response(params?: {

    /**
     * id
     */
    id?: string;
  }): Observable<StrictHttpResponse<MockUidto>> {

    const rb = new RequestBuilder(this.rootUrl, UiRestControllerService.GetMockUiUsingGetPath, 'get');
    if (params) {
      rb.query('id', params.id, {"style":"form"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<MockUidto>;
      })
    );
  }

  /**
   * getMockUI.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getMockUiUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getMockUiUsingGet(params?: {

    /**
     * id
     */
    id?: string;
  }): Observable<MockUidto> {

    return this.getMockUiUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<MockUidto>) => r.body as MockUidto)
    );
  }

}
