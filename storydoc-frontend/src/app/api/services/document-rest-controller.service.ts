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

import { BlockId } from '../models/block-id';
import { StoryDocDto } from '../models/story-doc-dto';
import { StoryDocId } from '../models/story-doc-id';


/**
 * Document Rest Controller
 */
@Injectable({
  providedIn: 'root',
})
export class DocumentRestControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation getDocumentUsingGet
   */
  static readonly GetDocumentUsingGetPath = '/api/document/';

  /**
   * getDocument.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getDocumentUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getDocumentUsingGet$Response(params?: {
    id?: string;
  }): Observable<StrictHttpResponse<StoryDocDto>> {

    const rb = new RequestBuilder(this.rootUrl, DocumentRestControllerService.GetDocumentUsingGetPath, 'get');
    if (params) {
      rb.query('id', params.id, {"style":"form"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<StoryDocDto>;
      })
    );
  }

  /**
   * getDocument.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getDocumentUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getDocumentUsingGet(params?: {
    id?: string;
  }): Observable<StoryDocDto> {

    return this.getDocumentUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<StoryDocDto>) => r.body as StoryDocDto)
    );
  }

  /**
   * Path part for operation createDocumentUsingPost
   */
  static readonly CreateDocumentUsingPostPath = '/api/document/';

  /**
   * createDocument.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createDocumentUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  createDocumentUsingPost$Response(params?: {
  }): Observable<StrictHttpResponse<StoryDocId>> {

    const rb = new RequestBuilder(this.rootUrl, DocumentRestControllerService.CreateDocumentUsingPostPath, 'post');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<StoryDocId>;
      })
    );
  }

  /**
   * createDocument.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `createDocumentUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  createDocumentUsingPost(params?: {
  }): Observable<StoryDocId> {

    return this.createDocumentUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<StoryDocId>) => r.body as StoryDocId)
    );
  }

  /**
   * Path part for operation addBlockUsingPost
   */
  static readonly AddBlockUsingPostPath = '/api/document/addblock';

  /**
   * addBlock.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `addBlockUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  addBlockUsingPost$Response(params?: {
    id?: string;
  }): Observable<StrictHttpResponse<BlockId>> {

    const rb = new RequestBuilder(this.rootUrl, DocumentRestControllerService.AddBlockUsingPostPath, 'post');
    if (params) {
      rb.query('id', params.id, {"style":"form"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<BlockId>;
      })
    );
  }

  /**
   * addBlock.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `addBlockUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  addBlockUsingPost(params?: {
    id?: string;
  }): Observable<BlockId> {

    return this.addBlockUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<BlockId>) => r.body as BlockId)
    );
  }

  /**
   * Path part for operation removeBlockUsingPost
   */
  static readonly RemoveBlockUsingPostPath = '/api/document/removeblock';

  /**
   * removeBlock.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `removeBlockUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  removeBlockUsingPost$Response(params?: {
    id?: string;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, DocumentRestControllerService.RemoveBlockUsingPostPath, 'post');
    if (params) {
      rb.query('id', params.id, {"style":"form"});
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
   * removeBlock.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `removeBlockUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  removeBlockUsingPost(params?: {
    id?: string;
  }): Observable<void> {

    return this.removeBlockUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

  /**
   * Path part for operation addTagUsingPost
   */
  static readonly AddTagUsingPostPath = '/api/document/tag';

  /**
   * addTag.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `addTagUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  addTagUsingPost$Response(params?: {
    id?: string;

    /**
     * tag
     */
    tag?: string;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, DocumentRestControllerService.AddTagUsingPostPath, 'post');
    if (params) {
      rb.query('id', params.id, {"style":"form"});
      rb.query('tag', params.tag, {"style":"form"});
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
   * addTag.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `addTagUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  addTagUsingPost(params?: {
    id?: string;

    /**
     * tag
     */
    tag?: string;
  }): Observable<void> {

    return this.addTagUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

}
