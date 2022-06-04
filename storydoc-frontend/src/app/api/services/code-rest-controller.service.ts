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

import { BluePrint } from '../models/blue-print';
import { CodeExecutionCoordinate } from '../models/code-execution-coordinate';
import { CodeTraceDto } from '../models/code-trace-dto';
import { SourceCodeConfigCoordinate } from '../models/source-code-config-coordinate';
import { SourceCodeConfigDto } from '../models/source-code-config-dto';
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
   * Path part for operation getBluePrintUsingGet
   */
  static readonly GetBluePrintUsingGetPath = '/api/code/blueprint';

  /**
   * getBluePrint.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getBluePrintUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getBluePrintUsingGet$Response(params?: {
  }): Observable<StrictHttpResponse<BluePrint>> {

    const rb = new RequestBuilder(this.rootUrl, CodeRestControllerService.GetBluePrintUsingGetPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<BluePrint>;
      })
    );
  }

  /**
   * getBluePrint.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getBluePrintUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getBluePrintUsingGet(params?: {
  }): Observable<BluePrint> {

    return this.getBluePrintUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<BluePrint>) => r.body as BluePrint)
    );
  }

  /**
   * Path part for operation classifyUsingGet
   */
  static readonly ClassifyUsingGetPath = '/api/code/classify';

  /**
   * classify.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `classifyUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  classifyUsingGet$Response(params?: {

    /**
     * className
     */
    className?: string;
  }): Observable<StrictHttpResponse<Array<string>>> {

    const rb = new RequestBuilder(this.rootUrl, CodeRestControllerService.ClassifyUsingGetPath, 'get');
    if (params) {
      rb.query('className', params.className, {"style":"form"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Array<string>>;
      })
    );
  }

  /**
   * classify.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `classifyUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  classifyUsingGet(params?: {

    /**
     * className
     */
    className?: string;
  }): Observable<Array<string>> {

    return this.classifyUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<Array<string>>) => r.body as Array<string>)
    );
  }

  /**
   * Path part for operation classifyMultipleUsingPost
   */
  static readonly ClassifyMultipleUsingPostPath = '/api/code/classifymultiple';

  /**
   * classifyMultiple.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `classifyMultipleUsingPost()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  classifyMultipleUsingPost$Response(params?: {
    body?: Array<string>
  }): Observable<StrictHttpResponse<{ [key: string]: Array<string> }>> {

    const rb = new RequestBuilder(this.rootUrl, CodeRestControllerService.ClassifyMultipleUsingPostPath, 'post');
    if (params) {
      rb.body(params.body, 'application/json');
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<{ [key: string]: Array<string> }>;
      })
    );
  }

  /**
   * classifyMultiple.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `classifyMultipleUsingPost$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  classifyMultipleUsingPost(params?: {
    body?: Array<string>
  }): Observable<{ [key: string]: Array<string> }> {

    return this.classifyMultipleUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<{ [key: string]: Array<string> }>) => r.body as { [key: string]: Array<string> })
    );
  }

  /**
   * Path part for operation getCodeExecutionUsingGet
   */
  static readonly GetCodeExecutionUsingGetPath = '/api/code/codeexecution';

  /**
   * getCodeExecution.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getCodeExecutionUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getCodeExecutionUsingGet$Response(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * codeExecutionId
     */
    codeExecutionId: string;
  }): Observable<StrictHttpResponse<CodeTraceDto>> {

    const rb = new RequestBuilder(this.rootUrl, CodeRestControllerService.GetCodeExecutionUsingGetPath, 'get');
    if (params) {
      rb.query('storyDocId', params.storyDocId, {"style":"form"});
      rb.query('blockId', params.blockId, {"style":"form"});
      rb.query('codeExecutionId', params.codeExecutionId, {"style":"form"});
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
   * getCodeExecution.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getCodeExecutionUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getCodeExecutionUsingGet(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * codeExecutionId
     */
    codeExecutionId: string;
  }): Observable<CodeTraceDto> {

    return this.getCodeExecutionUsingGet$Response(params).pipe(
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
   * Path part for operation setConfigForCodeExecutionUsingPost
   */
  static readonly SetConfigForCodeExecutionUsingPostPath = '/api/code/codeexecution/config';

  /**
   * setConfigForCodeExecution.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `setConfigForCodeExecutionUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  setConfigForCodeExecutionUsingPost$Response(params: {

    /**
     * execStoryDocId
     */
    execStoryDocId: string;

    /**
     * execBlockId
     */
    execBlockId: string;

    /**
     * codeExecutionId
     */
    codeExecutionId: string;

    /**
     * configStoryDocId
     */
    configStoryDocId: string;

    /**
     * configBlockId
     */
    configBlockId: string;

    /**
     * sourceCodeConfigId
     */
    sourceCodeConfigId: string;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, CodeRestControllerService.SetConfigForCodeExecutionUsingPostPath, 'post');
    if (params) {
      rb.query('execStoryDocId', params.execStoryDocId, {"style":"form"});
      rb.query('execBlockId', params.execBlockId, {"style":"form"});
      rb.query('codeExecutionId', params.codeExecutionId, {"style":"form"});
      rb.query('configStoryDocId', params.configStoryDocId, {"style":"form"});
      rb.query('configBlockId', params.configBlockId, {"style":"form"});
      rb.query('sourceCodeConfigId', params.sourceCodeConfigId, {"style":"form"});
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
   * setConfigForCodeExecution.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `setConfigForCodeExecutionUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  setConfigForCodeExecutionUsingPost(params: {

    /**
     * execStoryDocId
     */
    execStoryDocId: string;

    /**
     * execBlockId
     */
    execBlockId: string;

    /**
     * codeExecutionId
     */
    codeExecutionId: string;

    /**
     * configStoryDocId
     */
    configStoryDocId: string;

    /**
     * configBlockId
     */
    configBlockId: string;

    /**
     * sourceCodeConfigId
     */
    sourceCodeConfigId: string;
  }): Observable<void> {

    return this.setConfigForCodeExecutionUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

  /**
   * Path part for operation setStitchDetailsForCodeExecutionUsingPost
   */
  static readonly SetStitchDetailsForCodeExecutionUsingPostPath = '/api/code/codeexecution/stitch';

  /**
   * setStitchDetailsForCodeExecution.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `setStitchDetailsForCodeExecutionUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  setStitchDetailsForCodeExecutionUsingPost$Response(params: {

    /**
     * execStoryDocId
     */
    execStoryDocId: string;

    /**
     * execBlockId
     */
    execBlockId: string;

    /**
     * codeExecutionId
     */
    codeExecutionId: string;

    /**
     * stitchFile
     */
    stitchFile: string;

    /**
     * testClass
     */
    testClass: string;

    /**
     * testMethod
     */
    testMethod: string;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, CodeRestControllerService.SetStitchDetailsForCodeExecutionUsingPostPath, 'post');
    if (params) {
      rb.query('execStoryDocId', params.execStoryDocId, {"style":"form"});
      rb.query('execBlockId', params.execBlockId, {"style":"form"});
      rb.query('codeExecutionId', params.codeExecutionId, {"style":"form"});
      rb.query('stitchFile', params.stitchFile, {"style":"form"});
      rb.query('testClass', params.testClass, {"style":"form"});
      rb.query('testMethod', params.testMethod, {"style":"form"});
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
   * setStitchDetailsForCodeExecution.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `setStitchDetailsForCodeExecutionUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  setStitchDetailsForCodeExecutionUsingPost(params: {

    /**
     * execStoryDocId
     */
    execStoryDocId: string;

    /**
     * execBlockId
     */
    execBlockId: string;

    /**
     * codeExecutionId
     */
    codeExecutionId: string;

    /**
     * stitchFile
     */
    stitchFile: string;

    /**
     * testClass
     */
    testClass: string;

    /**
     * testMethod
     */
    testMethod: string;
  }): Observable<void> {

    return this.setStitchDetailsForCodeExecutionUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
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
  sourceUsingGet$Response(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * sourceCodeConfigId
     */
    sourceCodeConfigId: string;

    /**
     * className
     */
    className?: string;
  }): Observable<StrictHttpResponse<SourceCodeDto>> {

    const rb = new RequestBuilder(this.rootUrl, CodeRestControllerService.SourceUsingGetPath, 'get');
    if (params) {
      rb.query('storyDocId', params.storyDocId, {"style":"form"});
      rb.query('blockId', params.blockId, {"style":"form"});
      rb.query('sourceCodeConfigId', params.sourceCodeConfigId, {"style":"form"});
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
  sourceUsingGet(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * sourceCodeConfigId
     */
    sourceCodeConfigId: string;

    /**
     * className
     */
    className?: string;
  }): Observable<SourceCodeDto> {

    return this.sourceUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<SourceCodeDto>) => r.body as SourceCodeDto)
    );
  }

  /**
   * Path part for operation getSourceConfigUsingGet
   */
  static readonly GetSourceConfigUsingGetPath = '/api/code/sourcecodeconfig';

  /**
   * getSourceConfig.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getSourceConfigUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getSourceConfigUsingGet$Response(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * sourceCodeConfigId
     */
    sourceCodeConfigId: string;
  }): Observable<StrictHttpResponse<SourceCodeConfigDto>> {

    const rb = new RequestBuilder(this.rootUrl, CodeRestControllerService.GetSourceConfigUsingGetPath, 'get');
    if (params) {
      rb.query('storyDocId', params.storyDocId, {"style":"form"});
      rb.query('blockId', params.blockId, {"style":"form"});
      rb.query('sourceCodeConfigId', params.sourceCodeConfigId, {"style":"form"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<SourceCodeConfigDto>;
      })
    );
  }

  /**
   * getSourceConfig.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getSourceConfigUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getSourceConfigUsingGet(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * sourceCodeConfigId
     */
    sourceCodeConfigId: string;
  }): Observable<SourceCodeConfigDto> {

    return this.getSourceConfigUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<SourceCodeConfigDto>) => r.body as SourceCodeConfigDto)
    );
  }

  /**
   * Path part for operation createSourceConfigUsingPost
   */
  static readonly CreateSourceConfigUsingPostPath = '/api/code/sourcecodeconfig';

  /**
   * createSourceConfig.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createSourceConfigUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  createSourceConfigUsingPost$Response(params: {

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
  }): Observable<StrictHttpResponse<SourceCodeConfigCoordinate>> {

    const rb = new RequestBuilder(this.rootUrl, CodeRestControllerService.CreateSourceConfigUsingPostPath, 'post');
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
        return r as StrictHttpResponse<SourceCodeConfigCoordinate>;
      })
    );
  }

  /**
   * createSourceConfig.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `createSourceConfigUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  createSourceConfigUsingPost(params: {

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
  }): Observable<SourceCodeConfigCoordinate> {

    return this.createSourceConfigUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<SourceCodeConfigCoordinate>) => r.body as SourceCodeConfigCoordinate)
    );
  }

  /**
   * Path part for operation setSourcePathUsingPost
   */
  static readonly SetSourcePathUsingPostPath = '/api/code/sourcecodeconfig/path';

  /**
   * setSourcePath.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `setSourcePathUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  setSourcePathUsingPost$Response(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * sourceCodeConfigId
     */
    sourceCodeConfigId: string;

    /**
     * path
     */
    path: string;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, CodeRestControllerService.SetSourcePathUsingPostPath, 'post');
    if (params) {
      rb.query('storyDocId', params.storyDocId, {"style":"form"});
      rb.query('blockId', params.blockId, {"style":"form"});
      rb.query('sourceCodeConfigId', params.sourceCodeConfigId, {"style":"form"});
      rb.query('path', params.path, {"style":"form"});
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
   * setSourcePath.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `setSourcePathUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  setSourcePathUsingPost(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * sourceCodeConfigId
     */
    sourceCodeConfigId: string;

    /**
     * path
     */
    path: string;
  }): Observable<void> {

    return this.setSourcePathUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

}
