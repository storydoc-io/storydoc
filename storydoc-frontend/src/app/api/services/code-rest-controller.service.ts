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

import { CodeExecutionCoordinate } from '../models/code-execution-coordinate';
import { CodeTraceDto } from '../models/code-trace-dto';
import { SourceCodeDto } from '../models/source-code-dto';


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

  /**
   * Path part for operation createCodeExecutionUsingPost
   */
  static readonly CreateCodeExecutionUsingPostPath = '/api/code/codeexecution';

  /**
   * createCodeExecution.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createCodeExecutionUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  createCodeExecutionUsingPost$Response(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * name
     */
    name: string;
  }): Observable<StrictHttpResponse<CodeExecutionCoordinate>> {

    const rb = new RequestBuilder(this.rootUrl, CodeRestControllerService.CreateCodeExecutionUsingPostPath, 'post');
    if (params) {
      rb.query('storyDocId', params.storyDocId, {"style":"form"});
      rb.query('blockId', params.blockId, {"style":"form"});
      rb.query('name', params.name, {"style":"form"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<CodeExecutionCoordinate>;
      })
    );
  }

  /**
   * createCodeExecution.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `createCodeExecutionUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  createCodeExecutionUsingPost(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * name
     */
    name: string;
  }): Observable<CodeExecutionCoordinate> {

    return this.createCodeExecutionUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<CodeExecutionCoordinate>) => r.body as CodeExecutionCoordinate)
    );
  }

  /**
   * Path part for operation sourceUsingGet
   */
  static readonly SourceUsingGetPath = '/api/code/source';

  /**
   * source.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `sourceUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  sourceUsingGet$Response(params?: {

    /**
     * className
     */
    className?: string;
  }): Observable<StrictHttpResponse<SourceCodeDto>> {

    const rb = new RequestBuilder(this.rootUrl, CodeRestControllerService.SourceUsingGetPath, 'get');
    if (params) {
      rb.query('className', params.className, {"style":"form"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<SourceCodeDto>;
      })
    );
  }

  /**
   * source.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `sourceUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  sourceUsingGet(params?: {

    /**
     * className
     */
    className?: string;
  }): Observable<SourceCodeDto> {

    return this.sourceUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<SourceCodeDto>) => r.body as SourceCodeDto)
    );
  }

}
