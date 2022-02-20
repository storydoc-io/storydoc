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



/**
 * Redirect To Angular
 */
@Injectable({
  providedIn: 'root',
})
export class RedirectToAngularService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation redirectUsingGet
   */
  static readonly RedirectUsingGetPath = '/fe/**';

  /**
   * redirect.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `redirectUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  redirectUsingGet$Response(params?: {
  }): Observable<StrictHttpResponse<string>> {

    const rb = new RequestBuilder(this.rootUrl, RedirectToAngularService.RedirectUsingGetPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'blob',
      accept: '*/*'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<string>;
      })
    );
  }

  /**
   * redirect.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `redirectUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  redirectUsingGet(params?: {
  }): Observable<string> {

    return this.redirectUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<string>) => r.body as string)
    );
  }

  /**
   * Path part for operation redirectUsingPut
   */
  static readonly RedirectUsingPutPath = '/fe/**';

  /**
   * redirect.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `redirectUsingPut()` instead.
   *
   * This method doesn't expect any request body.
   */
  redirectUsingPut$Response(params?: {
  }): Observable<StrictHttpResponse<string>> {

    const rb = new RequestBuilder(this.rootUrl, RedirectToAngularService.RedirectUsingPutPath, 'put');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'blob',
      accept: '*/*'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<string>;
      })
    );
  }

  /**
   * redirect.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `redirectUsingPut$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  redirectUsingPut(params?: {
  }): Observable<string> {

    return this.redirectUsingPut$Response(params).pipe(
      map((r: StrictHttpResponse<string>) => r.body as string)
    );
  }

  /**
   * Path part for operation redirectUsingPost
   */
  static readonly RedirectUsingPostPath = '/fe/**';

  /**
   * redirect.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `redirectUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  redirectUsingPost$Response(params?: {
  }): Observable<StrictHttpResponse<string>> {

    const rb = new RequestBuilder(this.rootUrl, RedirectToAngularService.RedirectUsingPostPath, 'post');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'blob',
      accept: '*/*'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<string>;
      })
    );
  }

  /**
   * redirect.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `redirectUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  redirectUsingPost(params?: {
  }): Observable<string> {

    return this.redirectUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<string>) => r.body as string)
    );
  }

  /**
   * Path part for operation redirectUsingDelete
   */
  static readonly RedirectUsingDeletePath = '/fe/**';

  /**
   * redirect.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `redirectUsingDelete()` instead.
   *
   * This method doesn't expect any request body.
   */
  redirectUsingDelete$Response(params?: {
  }): Observable<StrictHttpResponse<string>> {

    const rb = new RequestBuilder(this.rootUrl, RedirectToAngularService.RedirectUsingDeletePath, 'delete');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'blob',
      accept: '*/*'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<string>;
      })
    );
  }

  /**
   * redirect.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `redirectUsingDelete$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  redirectUsingDelete(params?: {
  }): Observable<string> {

    return this.redirectUsingDelete$Response(params).pipe(
      map((r: StrictHttpResponse<string>) => r.body as string)
    );
  }

  /**
   * Path part for operation redirectUsingOptions
   */
  static readonly RedirectUsingOptionsPath = '/fe/**';

  /**
   * redirect.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `redirectUsingOptions()` instead.
   *
   * This method doesn't expect any request body.
   */
  redirectUsingOptions$Response(params?: {
  }): Observable<StrictHttpResponse<string>> {

    const rb = new RequestBuilder(this.rootUrl, RedirectToAngularService.RedirectUsingOptionsPath, 'options');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'blob',
      accept: '*/*'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<string>;
      })
    );
  }

  /**
   * redirect.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `redirectUsingOptions$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  redirectUsingOptions(params?: {
  }): Observable<string> {

    return this.redirectUsingOptions$Response(params).pipe(
      map((r: StrictHttpResponse<string>) => r.body as string)
    );
  }

  /**
   * Path part for operation redirectUsingHead
   */
  static readonly RedirectUsingHeadPath = '/fe/**';

  /**
   * redirect.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `redirectUsingHead()` instead.
   *
   * This method doesn't expect any request body.
   */
  redirectUsingHead$Response(params?: {
  }): Observable<StrictHttpResponse<string>> {

    const rb = new RequestBuilder(this.rootUrl, RedirectToAngularService.RedirectUsingHeadPath, 'head');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'blob',
      accept: '*/*'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<string>;
      })
    );
  }

  /**
   * redirect.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `redirectUsingHead$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  redirectUsingHead(params?: {
  }): Observable<string> {

    return this.redirectUsingHead$Response(params).pipe(
      map((r: StrictHttpResponse<string>) => r.body as string)
    );
  }

  /**
   * Path part for operation redirectUsingPatch
   */
  static readonly RedirectUsingPatchPath = '/fe/**';

  /**
   * redirect.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `redirectUsingPatch()` instead.
   *
   * This method doesn't expect any request body.
   */
  redirectUsingPatch$Response(params?: {
  }): Observable<StrictHttpResponse<string>> {

    const rb = new RequestBuilder(this.rootUrl, RedirectToAngularService.RedirectUsingPatchPath, 'patch');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'blob',
      accept: '*/*'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<string>;
      })
    );
  }

  /**
   * redirect.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `redirectUsingPatch$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  redirectUsingPatch(params?: {
  }): Observable<string> {

    return this.redirectUsingPatch$Response(params).pipe(
      map((r: StrictHttpResponse<string>) => r.body as string)
    );
  }

  /**
   * Path part for operation redirectUsingTrace
   */
  static readonly RedirectUsingTracePath = '/fe/**';

  /**
   * redirect.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `redirectUsingTrace()` instead.
   *
   * This method doesn't expect any request body.
   */
  redirectUsingTrace$Response(params?: {
  }): Observable<StrictHttpResponse<string>> {

    const rb = new RequestBuilder(this.rootUrl, RedirectToAngularService.RedirectUsingTracePath, 'trace');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'blob',
      accept: '*/*'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<string>;
      })
    );
  }

  /**
   * redirect.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `redirectUsingTrace$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  redirectUsingTrace(params?: {
  }): Observable<string> {

    return this.redirectUsingTrace$Response(params).pipe(
      map((r: StrictHttpResponse<string>) => r.body as string)
    );
  }

}
