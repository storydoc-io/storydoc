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
import { MockUiId } from '../models/mock-ui-id';
import { ScreenShotCollectionDto } from '../models/screen-shot-collection-dto';
import { ScreenShotCollectionId } from '../models/screen-shot-collection-id';
import { ScreenShotDto } from '../models/screen-shot-dto';
import { ScreenShotId } from '../models/screen-shot-id';


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
     * storyDocId
     */
    storyDocId?: string;

    /**
     * blockId
     */
    blockId?: string;

    /**
     * id
     */
    id?: string;
  }): Observable<StrictHttpResponse<MockUidto>> {

    const rb = new RequestBuilder(this.rootUrl, UiRestControllerService.GetMockUiUsingGetPath, 'get');
    if (params) {
      rb.query('storyDocId', params.storyDocId, {"style":"form"});
      rb.query('blockId', params.blockId, {"style":"form"});
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
     * storyDocId
     */
    storyDocId?: string;

    /**
     * blockId
     */
    blockId?: string;

    /**
     * id
     */
    id?: string;
  }): Observable<MockUidto> {

    return this.getMockUiUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<MockUidto>) => r.body as MockUidto)
    );
  }

  /**
   * Path part for operation getScreenshotUsingGet
   */
  static readonly GetScreenshotUsingGetPath = '/api/ui/screenshot';

  /**
   * getScreenshot.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getScreenshotUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getScreenshotUsingGet$Response(params?: {

    /**
     * storyDocId
     */
    storyDocId?: string;

    /**
     * blockId
     */
    blockId?: string;

    /**
     * id
     */
    id?: string;
  }): Observable<StrictHttpResponse<ScreenShotDto>> {

    const rb = new RequestBuilder(this.rootUrl, UiRestControllerService.GetScreenshotUsingGetPath, 'get');
    if (params) {
      rb.query('storyDocId', params.storyDocId, {"style":"form"});
      rb.query('blockId', params.blockId, {"style":"form"});
      rb.query('id', params.id, {"style":"form"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<ScreenShotDto>;
      })
    );
  }

  /**
   * getScreenshot.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getScreenshotUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getScreenshotUsingGet(params?: {

    /**
     * storyDocId
     */
    storyDocId?: string;

    /**
     * blockId
     */
    blockId?: string;

    /**
     * id
     */
    id?: string;
  }): Observable<ScreenShotDto> {

    return this.getScreenshotUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<ScreenShotDto>) => r.body as ScreenShotDto)
    );
  }

  /**
   * Path part for operation addScreenshotToCollectionUsingPost
   */
  static readonly AddScreenshotToCollectionUsingPostPath = '/api/ui/screenshot';

  /**
   * addScreenshotToCollection.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `addScreenshotToCollectionUsingPost()` instead.
   *
   * This method sends `multipart/form-data` and handles request body of type `multipart/form-data`.
   */
  addScreenshotToCollectionUsingPost$Response(params?: {
    body?: { 'blockId'?: string, 'name'?: string, 'screenshotCollectionId'?: string, 'storyDocId'?: string }
  }): Observable<StrictHttpResponse<ScreenShotId>> {

    const rb = new RequestBuilder(this.rootUrl, UiRestControllerService.AddScreenshotToCollectionUsingPostPath, 'post');
    if (params) {
      rb.body(params.body, 'multipart/form-data');
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<ScreenShotId>;
      })
    );
  }

  /**
   * addScreenshotToCollection.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `addScreenshotToCollectionUsingPost$Response()` instead.
   *
   * This method sends `multipart/form-data` and handles request body of type `multipart/form-data`.
   */
  addScreenshotToCollectionUsingPost(params?: {
    body?: { 'blockId'?: string, 'name'?: string, 'screenshotCollectionId'?: string, 'storyDocId'?: string }
  }): Observable<ScreenShotId> {

    return this.addScreenshotToCollectionUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<ScreenShotId>) => r.body as ScreenShotId)
    );
  }

  /**
   * Path part for operation downloadScreenshotUsingGet
   */
  static readonly DownloadScreenshotUsingGetPath = '/api/ui/screenshot/{storyDocId}/{blockId}/{screenshotCollectionId}/{screenshotId}';

  /**
   * downloadScreenshot.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `downloadScreenshotUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  downloadScreenshotUsingGet$Response(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * screenshotCollectionId
     */
    screenshotCollectionId: string;

    /**
     * screenshotId
     */
    screenshotId: string;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, UiRestControllerService.DownloadScreenshotUsingGetPath, 'get');
    if (params) {
      rb.path('storyDocId', params.storyDocId, {"style":"simple"});
      rb.path('blockId', params.blockId, {"style":"simple"});
      rb.path('screenshotCollectionId', params.screenshotCollectionId, {"style":"simple"});
      rb.path('screenshotId', params.screenshotId, {"style":"simple"});
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
   * downloadScreenshot.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `downloadScreenshotUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  downloadScreenshotUsingGet(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * screenshotCollectionId
     */
    screenshotCollectionId: string;

    /**
     * screenshotId
     */
    screenshotId: string;
  }): Observable<void> {

    return this.downloadScreenshotUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

  /**
   * Path part for operation getScreenShotCollectionUsingGet
   */
  static readonly GetScreenShotCollectionUsingGetPath = '/api/ui/screenshotcollection';

  /**
   * getScreenShotCollection.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getScreenShotCollectionUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getScreenShotCollectionUsingGet$Response(params?: {

    /**
     * storyDocId
     */
    storyDocId?: string;

    /**
     * blockId
     */
    blockId?: string;

    /**
     * id
     */
    id?: string;
  }): Observable<StrictHttpResponse<ScreenShotCollectionDto>> {

    const rb = new RequestBuilder(this.rootUrl, UiRestControllerService.GetScreenShotCollectionUsingGetPath, 'get');
    if (params) {
      rb.query('storyDocId', params.storyDocId, {"style":"form"});
      rb.query('blockId', params.blockId, {"style":"form"});
      rb.query('id', params.id, {"style":"form"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<ScreenShotCollectionDto>;
      })
    );
  }

  /**
   * getScreenShotCollection.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getScreenShotCollectionUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getScreenShotCollectionUsingGet(params?: {

    /**
     * storyDocId
     */
    storyDocId?: string;

    /**
     * blockId
     */
    blockId?: string;

    /**
     * id
     */
    id?: string;
  }): Observable<ScreenShotCollectionDto> {

    return this.getScreenShotCollectionUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<ScreenShotCollectionDto>) => r.body as ScreenShotCollectionDto)
    );
  }

  /**
   * Path part for operation createScreenShotCollectionUsingPost
   */
  static readonly CreateScreenShotCollectionUsingPostPath = '/api/ui/screenshotcollection';

  /**
   * createScreenShotCollection.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createScreenShotCollectionUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  createScreenShotCollectionUsingPost$Response(params?: {

    /**
     * storyDocId
     */
    storyDocId?: string;

    /**
     * blockId
     */
    blockId?: string;

    /**
     * name
     */
    name?: string;
  }): Observable<StrictHttpResponse<ScreenShotCollectionId>> {

    const rb = new RequestBuilder(this.rootUrl, UiRestControllerService.CreateScreenShotCollectionUsingPostPath, 'post');
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
        return r as StrictHttpResponse<ScreenShotCollectionId>;
      })
    );
  }

  /**
   * createScreenShotCollection.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `createScreenShotCollectionUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  createScreenShotCollectionUsingPost(params?: {

    /**
     * storyDocId
     */
    storyDocId?: string;

    /**
     * blockId
     */
    blockId?: string;

    /**
     * name
     */
    name?: string;
  }): Observable<ScreenShotCollectionId> {

    return this.createScreenShotCollectionUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<ScreenShotCollectionId>) => r.body as ScreenShotCollectionId)
    );
  }

  /**
   * Path part for operation createMockUiUsingPost
   */
  static readonly CreateMockUiUsingPostPath = '/api/ui/uiscenario';

  /**
   * createMockUI.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createMockUiUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  createMockUiUsingPost$Response(params?: {

    /**
     * storyDocId
     */
    storyDocId?: string;

    /**
     * blockId
     */
    blockId?: string;

    /**
     * name
     */
    name?: string;
  }): Observable<StrictHttpResponse<MockUiId>> {

    const rb = new RequestBuilder(this.rootUrl, UiRestControllerService.CreateMockUiUsingPostPath, 'post');
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
        return r as StrictHttpResponse<MockUiId>;
      })
    );
  }

  /**
   * createMockUI.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `createMockUiUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  createMockUiUsingPost(params?: {

    /**
     * storyDocId
     */
    storyDocId?: string;

    /**
     * blockId
     */
    blockId?: string;

    /**
     * name
     */
    name?: string;
  }): Observable<MockUiId> {

    return this.createMockUiUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<MockUiId>) => r.body as MockUiId)
    );
  }

  /**
   * Path part for operation addScreenshotToUiSceanrioUsingPost
   */
  static readonly AddScreenshotToUiSceanrioUsingPostPath = '/api/ui/uiscenarioscreenshot';

  /**
   * addScreenshotToUISceanrio.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `addScreenshotToUiSceanrioUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  addScreenshotToUiSceanrioUsingPost$Response(params?: {

    /**
     * storyDocId
     */
    storyDocId?: string;

    /**
     * blockId
     */
    blockId?: string;

    /**
     * mockUiId
     */
    mockUiId?: string;

    /**
     * screenshotId
     */
    screenshotId?: string;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, UiRestControllerService.AddScreenshotToUiSceanrioUsingPostPath, 'post');
    if (params) {
      rb.query('storyDocId', params.storyDocId, {"style":"form"});
      rb.query('blockId', params.blockId, {"style":"form"});
      rb.query('mockUiId', params.mockUiId, {"style":"form"});
      rb.query('screenshotId', params.screenshotId, {"style":"form"});
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
   * addScreenshotToUISceanrio.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `addScreenshotToUiSceanrioUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  addScreenshotToUiSceanrioUsingPost(params?: {

    /**
     * storyDocId
     */
    storyDocId?: string;

    /**
     * blockId
     */
    blockId?: string;

    /**
     * mockUiId
     */
    mockUiId?: string;

    /**
     * screenshotId
     */
    screenshotId?: string;
  }): Observable<void> {

    return this.addScreenshotToUiSceanrioUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

}
