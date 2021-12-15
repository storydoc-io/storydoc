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

import { CodeTraceDto } from '../models/code-trace-dto';


/**
 * Code Rest Controller
 */
@Injectable({
  providedIn: 'root',
})
export class CodeRestControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation traceUsingGet
   */
  static readonly TraceUsingGetPath = '/api/code/';

  /**
   * trace.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `traceUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  traceUsingGet$Response(params?: {
  }): Observable<StrictHttpResponse<CodeTraceDto>> {

    const rb = new RequestBuilder(this.rootUrl, CodeRestControllerService.TraceUsingGetPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<CodeTraceDto>;
      })
    );
  }

  /**
   * trace.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `traceUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  traceUsingGet(params?: {
  }): Observable<CodeTraceDto> {

    return this.traceUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<CodeTraceDto>) => r.body as CodeTraceDto)
    );
  }

}
