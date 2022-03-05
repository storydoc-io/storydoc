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

import { DbDataSetDto } from '../models/db-data-set-dto';
import { NavigationModelDto } from '../models/navigation-model-dto';


/**
 * SQLDB Controller
 */
@Injectable({
  providedIn: 'root',
})
export class SqldbControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation getDbDataSetUsingGet
   */
  static readonly GetDbDataSetUsingGetPath = '/api/sqldb/dataset';

  /**
   * getDBDataSet.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getDbDataSetUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getDbDataSetUsingGet$Response(params?: {
  }): Observable<StrictHttpResponse<DbDataSetDto>> {

    const rb = new RequestBuilder(this.rootUrl, SqldbControllerService.GetDbDataSetUsingGetPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<DbDataSetDto>;
      })
    );
  }

  /**
   * getDBDataSet.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getDbDataSetUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getDbDataSetUsingGet(params?: {
  }): Observable<DbDataSetDto> {

    return this.getDbDataSetUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<DbDataSetDto>) => r.body as DbDataSetDto)
    );
  }

  /**
   * Path part for operation getNavigationModelUsingGet
   */
  static readonly GetNavigationModelUsingGetPath = '/api/sqldb/navigationmodel';

  /**
   * getNavigationModel.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getNavigationModelUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getNavigationModelUsingGet$Response(params?: {
  }): Observable<StrictHttpResponse<NavigationModelDto>> {

    const rb = new RequestBuilder(this.rootUrl, SqldbControllerService.GetNavigationModelUsingGetPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<NavigationModelDto>;
      })
    );
  }

  /**
   * getNavigationModel.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getNavigationModelUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getNavigationModelUsingGet(params?: {
  }): Observable<NavigationModelDto> {

    return this.getNavigationModelUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<NavigationModelDto>) => r.body as NavigationModelDto)
    );
  }

}
