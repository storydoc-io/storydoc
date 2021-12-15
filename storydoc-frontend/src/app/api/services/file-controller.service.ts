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
 * File Controller
 */
@Injectable({
  providedIn: 'root',
})
export class FileControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation getFileUsingGet
   */
  static readonly GetFileUsingGetPath = '/files/{file_name}';

  /**
   * getFile.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getFileUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getFileUsingGet$Response(params: {

    /**
     * file_name
     */
    file_name: string;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, FileControllerService.GetFileUsingGetPath, 'get');
    if (params) {
      rb.path('file_name', params.file_name, {"style":"simple"});
    }

    return this.http.request(rb.build({
      responseType: 'text',
      accept: '*/*'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return (r as HttpResponse<any>).clone({ body: undefined }) as StrictHttpResponse<void>;
      })
    );
  }

  /**
   * getFile.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getFileUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getFileUsingGet(params: {

    /**
     * file_name
     */
    file_name: string;
  }): Observable<void> {

    return this.getFileUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

}
