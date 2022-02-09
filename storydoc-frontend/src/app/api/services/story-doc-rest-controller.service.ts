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
import { StoryDocSummaryDto } from '../models/story-doc-summary-dto';


/**
 * Story Doc Rest Controller
 */
@Injectable({
  providedIn: 'root',
})
export class StoryDocRestControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation getDocumentUsingGet
   */
  static readonly GetDocumentUsingGetPath = '/api/storydoc/';

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

    const rb = new RequestBuilder(this.rootUrl, StoryDocRestControllerService.GetDocumentUsingGetPath, 'get');
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
   * Path part for operation changeDocumentNameUsingPut
   */
  static readonly ChangeDocumentNameUsingPutPath = '/api/storydoc/';

  /**
   * changeDocumentName.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `changeDocumentNameUsingPut()` instead.
   *
   * This method doesn't expect any request body.
   */
  changeDocumentNameUsingPut$Response(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * name
     */
    name: string;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, StoryDocRestControllerService.ChangeDocumentNameUsingPutPath, 'put');
    if (params) {
      rb.query('storyDocId', params.storyDocId, {"style":"form"});
      rb.query('name', params.name, {"style":"form"});
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
   * changeDocumentName.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `changeDocumentNameUsingPut$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  changeDocumentNameUsingPut(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * name
     */
    name: string;
  }): Observable<void> {

    return this.changeDocumentNameUsingPut$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

  /**
   * Path part for operation createDocumentUsingPost
   */
  static readonly CreateDocumentUsingPostPath = '/api/storydoc/';

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

    /**
     * name
     */
    name?: string;
  }): Observable<StrictHttpResponse<StoryDocId>> {

    const rb = new RequestBuilder(this.rootUrl, StoryDocRestControllerService.CreateDocumentUsingPostPath, 'post');
    if (params) {
      rb.query('name', params.name, {"style":"form"});
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

    /**
     * name
     */
    name?: string;
  }): Observable<StoryDocId> {

    return this.createDocumentUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<StoryDocId>) => r.body as StoryDocId)
    );
  }

  /**
   * Path part for operation removeDocumentUsingDelete
   */
  static readonly RemoveDocumentUsingDeletePath = '/api/storydoc/';

  /**
   * removeDocument.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `removeDocumentUsingDelete()` instead.
   *
   * This method doesn't expect any request body.
   */
  removeDocumentUsingDelete$Response(params: {

    /**
     * storyDocId
     */
    storyDocId: string;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, StoryDocRestControllerService.RemoveDocumentUsingDeletePath, 'delete');
    if (params) {
      rb.query('storyDocId', params.storyDocId, {"style":"form"});
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
   * removeDocument.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `removeDocumentUsingDelete$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  removeDocumentUsingDelete(params: {

    /**
     * storyDocId
     */
    storyDocId: string;
  }): Observable<void> {

    return this.removeDocumentUsingDelete$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

  /**
   * Path part for operation addBlockUsingPost
   */
  static readonly AddBlockUsingPostPath = '/api/storydoc/addblock';

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

    /**
     * name
     */
    name?: string;
  }): Observable<StrictHttpResponse<BlockId>> {

    const rb = new RequestBuilder(this.rootUrl, StoryDocRestControllerService.AddBlockUsingPostPath, 'post');
    if (params) {
      rb.query('id', params.id, {"style":"form"});
      rb.query('name', params.name, {"style":"form"});
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

    /**
     * name
     */
    name?: string;
  }): Observable<BlockId> {

    return this.addBlockUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<BlockId>) => r.body as BlockId)
    );
  }

  /**
   * Path part for operation removeBlockUsingDelete
   */
  static readonly RemoveBlockUsingDeletePath = '/api/storydoc/removeblock';

  /**
   * removeBlock.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `removeBlockUsingDelete()` instead.
   *
   * This method doesn't expect any request body.
   */
  removeBlockUsingDelete$Response(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, StoryDocRestControllerService.RemoveBlockUsingDeletePath, 'delete');
    if (params) {
      rb.query('storyDocId', params.storyDocId, {"style":"form"});
      rb.query('blockId', params.blockId, {"style":"form"});
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
   * To access the full response (for headers, for example), `removeBlockUsingDelete$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  removeBlockUsingDelete(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;
  }): Observable<void> {

    return this.removeBlockUsingDelete$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

  /**
   * Path part for operation renameArtifactUsingPut
   */
  static readonly RenameArtifactUsingPutPath = '/api/storydoc/renameartifact';

  /**
   * renameArtifact.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `renameArtifactUsingPut()` instead.
   *
   * This method doesn't expect any request body.
   */
  renameArtifactUsingPut$Response(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * artifactId
     */
    artifactId: string;

    /**
     * name
     */
    name: string;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, StoryDocRestControllerService.RenameArtifactUsingPutPath, 'put');
    if (params) {
      rb.query('storyDocId', params.storyDocId, {"style":"form"});
      rb.query('blockId', params.blockId, {"style":"form"});
      rb.query('artifactId', params.artifactId, {"style":"form"});
      rb.query('name', params.name, {"style":"form"});
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
   * renameArtifact.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `renameArtifactUsingPut$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  renameArtifactUsingPut(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * artifactId
     */
    artifactId: string;

    /**
     * name
     */
    name: string;
  }): Observable<void> {

    return this.renameArtifactUsingPut$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

  /**
   * Path part for operation renameBlockUsingPut
   */
  static readonly RenameBlockUsingPutPath = '/api/storydoc/renameblock';

  /**
   * renameBlock.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `renameBlockUsingPut()` instead.
   *
   * This method doesn't expect any request body.
   */
  renameBlockUsingPut$Response(params: {

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
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, StoryDocRestControllerService.RenameBlockUsingPutPath, 'put');
    if (params) {
      rb.query('storyDocId', params.storyDocId, {"style":"form"});
      rb.query('blockId', params.blockId, {"style":"form"});
      rb.query('name', params.name, {"style":"form"});
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
   * renameBlock.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `renameBlockUsingPut$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  renameBlockUsingPut(params: {

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
  }): Observable<void> {

    return this.renameBlockUsingPut$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

  /**
   * Path part for operation getDocumentsUsingGet
   */
  static readonly GetDocumentsUsingGetPath = '/api/storydoc/storydocs';

  /**
   * getDocuments.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getDocumentsUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getDocumentsUsingGet$Response(params?: {
  }): Observable<StrictHttpResponse<Array<StoryDocSummaryDto>>> {

    const rb = new RequestBuilder(this.rootUrl, StoryDocRestControllerService.GetDocumentsUsingGetPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Array<StoryDocSummaryDto>>;
      })
    );
  }

  /**
   * getDocuments.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getDocumentsUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getDocumentsUsingGet(params?: {
  }): Observable<Array<StoryDocSummaryDto>> {

    return this.getDocumentsUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<Array<StoryDocSummaryDto>>) => r.body as Array<StoryDocSummaryDto>)
    );
  }

  /**
   * Path part for operation addTagUsingPost
   */
  static readonly AddTagUsingPostPath = '/api/storydoc/tag';

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

    const rb = new RequestBuilder(this.rootUrl, StoryDocRestControllerService.AddTagUsingPostPath, 'post');
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
