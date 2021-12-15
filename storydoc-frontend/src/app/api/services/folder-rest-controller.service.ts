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
 * Folder Rest Controller
 */
@Injectable({
  providedIn: 'root',
})
export class FolderRestControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation helloUsingGet
   */
  static readonly HelloUsingGetPath = '/api/folder/hello';

  /**
   * hello.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `helloUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  helloUsingGet$Response(params?: {
  }): Observable<StrictHttpResponse<string>> {

    const rb = new RequestBuilder(this.rootUrl, FolderRestControllerService.HelloUsingGetPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<string>;
      })
    );
  }

  /**
   * hello.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `helloUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  helloUsingGet(params?: {
  }): Observable<string> {

    return this.helloUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<string>) => r.body as string)
    );
  }

}
