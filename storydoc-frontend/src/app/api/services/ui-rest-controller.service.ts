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

import { ScreenShotCollectionDto } from '../models/screen-shot-collection-dto';
import { ScreenShotCollectionId } from '../models/screen-shot-collection-id';
import { ScreenShotId } from '../models/screen-shot-id';
import { UiScenarioCoordinate } from '../models/ui-scenario-coordinate';
import { UiScenarioDto } from '../models/ui-scenario-dto';


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
   * Path part for operation renameScreenshotInCollectionUsingPut
   */
  static readonly RenameScreenshotInCollectionUsingPutPath = '/api/ui/screenshot';

  /**
   * renameScreenshotInCollection.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `renameScreenshotInCollectionUsingPut()` instead.
   *
   * This method doesn't expect any request body.
   */
  renameScreenshotInCollectionUsingPut$Response(params: {

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

    /**
     * name
     */
    name?: string;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, UiRestControllerService.RenameScreenshotInCollectionUsingPutPath, 'put');
    if (params) {
      rb.query('storyDocId', params.storyDocId, {"style":"form"});
      rb.query('blockId', params.blockId, {"style":"form"});
      rb.query('screenshotCollectionId', params.screenshotCollectionId, {"style":"form"});
      rb.query('screenshotId', params.screenshotId, {"style":"form"});
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
   * renameScreenshotInCollection.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `renameScreenshotInCollectionUsingPut$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  renameScreenshotInCollectionUsingPut(params: {

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

    /**
     * name
     */
    name?: string;
  }): Observable<void> {

    return this.renameScreenshotInCollectionUsingPut$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
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
    body?: { 'blockId': string, 'name'?: string, 'screenshotCollectionId': string, 'storyDocId': string }
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
    body?: { 'blockId': string, 'name'?: string, 'screenshotCollectionId': string, 'storyDocId': string }
  }): Observable<ScreenShotId> {

    return this.addScreenshotToCollectionUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<ScreenShotId>) => r.body as ScreenShotId)
    );
  }

  /**
   * Path part for operation removeScreenshotFromCollectionUsingDelete
   */
  static readonly RemoveScreenshotFromCollectionUsingDeletePath = '/api/ui/screenshot';

  /**
   * removeScreenshotFromCollection.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `removeScreenshotFromCollectionUsingDelete()` instead.
   *
   * This method doesn't expect any request body.
   */
  removeScreenshotFromCollectionUsingDelete$Response(params: {

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

    const rb = new RequestBuilder(this.rootUrl, UiRestControllerService.RemoveScreenshotFromCollectionUsingDeletePath, 'delete');
    if (params) {
      rb.query('storyDocId', params.storyDocId, {"style":"form"});
      rb.query('blockId', params.blockId, {"style":"form"});
      rb.query('screenshotCollectionId', params.screenshotCollectionId, {"style":"form"});
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
   * removeScreenshotFromCollection.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `removeScreenshotFromCollectionUsingDelete$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  removeScreenshotFromCollectionUsingDelete(params: {

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

    return this.removeScreenshotFromCollectionUsingDelete$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
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
  getScreenShotCollectionUsingGet$Response(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * id
     */
    id: string;
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
  getScreenShotCollectionUsingGet(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * id
     */
    id: string;
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
  createScreenShotCollectionUsingPost$Response(params: {

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
  createScreenShotCollectionUsingPost(params: {

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
    name?: string;
  }): Observable<ScreenShotCollectionId> {

    return this.createScreenShotCollectionUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<ScreenShotCollectionId>) => r.body as ScreenShotCollectionId)
    );
  }

  /**
   * Path part for operation getUiScenarioUsingGet
   */
  static readonly GetUiScenarioUsingGetPath = '/api/ui/uiscenario';

  /**
   * getUIScenario.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getUiScenarioUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getUiScenarioUsingGet$Response(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * id
     */
    id: string;
  }): Observable<StrictHttpResponse<UiScenarioDto>> {

    const rb = new RequestBuilder(this.rootUrl, UiRestControllerService.GetUiScenarioUsingGetPath, 'get');
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
        return r as StrictHttpResponse<UiScenarioDto>;
      })
    );
  }

  /**
   * getUIScenario.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getUiScenarioUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getUiScenarioUsingGet(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * id
     */
    id: string;
  }): Observable<UiScenarioDto> {

    return this.getUiScenarioUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<UiScenarioDto>) => r.body as UiScenarioDto)
    );
  }

  /**
   * Path part for operation createUiScenarioUsingPost
   */
  static readonly CreateUiScenarioUsingPostPath = '/api/ui/uiscenario';

  /**
   * createUIScenario.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createUiScenarioUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  createUiScenarioUsingPost$Response(params: {

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
  }): Observable<StrictHttpResponse<UiScenarioCoordinate>> {

    const rb = new RequestBuilder(this.rootUrl, UiRestControllerService.CreateUiScenarioUsingPostPath, 'post');
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
        return r as StrictHttpResponse<UiScenarioCoordinate>;
      })
    );
  }

  /**
   * createUIScenario.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `createUiScenarioUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  createUiScenarioUsingPost(params: {

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
  }): Observable<UiScenarioCoordinate> {

    return this.createUiScenarioUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<UiScenarioCoordinate>) => r.body as UiScenarioCoordinate)
    );
  }

  /**
   * Path part for operation addScreenshotToUiScenarioUsingPost
   */
  static readonly AddScreenshotToUiScenarioUsingPostPath = '/api/ui/uiscenarioscreenshot';

  /**
   * addScreenshotToUIScenario.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `addScreenshotToUiScenarioUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  addScreenshotToUiScenarioUsingPost$Response(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * uiScenarioId
     */
    uiScenarioId: string;

    /**
     * screenshotStoryDocId
     */
    screenshotStoryDocId: string;

    /**
     * screenshotBlockId
     */
    screenshotBlockId: string;

    /**
     * screenshotCollectionId
     */
    screenshotCollectionId: string;

    /**
     * screenshotId
     */
    screenshotId: string;

    /**
     * timeLineItemId
     */
    timeLineItemId: string;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, UiRestControllerService.AddScreenshotToUiScenarioUsingPostPath, 'post');
    if (params) {
      rb.query('storyDocId', params.storyDocId, {"style":"form"});
      rb.query('blockId', params.blockId, {"style":"form"});
      rb.query('uiScenarioId', params.uiScenarioId, {"style":"form"});
      rb.query('screenshotStoryDocId', params.screenshotStoryDocId, {"style":"form"});
      rb.query('screenshotBlockId', params.screenshotBlockId, {"style":"form"});
      rb.query('screenshotCollectionId', params.screenshotCollectionId, {"style":"form"});
      rb.query('screenshotId', params.screenshotId, {"style":"form"});
      rb.query('timeLineItemId', params.timeLineItemId, {"style":"form"});
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
   * addScreenshotToUIScenario.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `addScreenshotToUiScenarioUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  addScreenshotToUiScenarioUsingPost(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * uiScenarioId
     */
    uiScenarioId: string;

    /**
     * screenshotStoryDocId
     */
    screenshotStoryDocId: string;

    /**
     * screenshotBlockId
     */
    screenshotBlockId: string;

    /**
     * screenshotCollectionId
     */
    screenshotCollectionId: string;

    /**
     * screenshotId
     */
    screenshotId: string;

    /**
     * timeLineItemId
     */
    timeLineItemId: string;
  }): Observable<void> {

    return this.addScreenshotToUiScenarioUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

  /**
   * Path part for operation setUiScenarioTimeLineModelUsingPost
   */
  static readonly SetUiScenarioTimeLineModelUsingPostPath = '/api/ui/uiscenariotimelinemodel';

  /**
   * setUIScenarioTimeLineModel.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `setUiScenarioTimeLineModelUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  setUiScenarioTimeLineModelUsingPost$Response(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * uiScenarioId
     */
    uiScenarioId: string;

    /**
     * timeLineModelStoryDocId
     */
    timeLineModelStoryDocId: string;

    /**
     * timeLineModelBlockId
     */
    timeLineModelBlockId: string;

    /**
     * timeLineModelId
     */
    timeLineModelId: string;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, UiRestControllerService.SetUiScenarioTimeLineModelUsingPostPath, 'post');
    if (params) {
      rb.query('storyDocId', params.storyDocId, {"style":"form"});
      rb.query('blockId', params.blockId, {"style":"form"});
      rb.query('uiScenarioId', params.uiScenarioId, {"style":"form"});
      rb.query('timeLineModelStoryDocId', params.timeLineModelStoryDocId, {"style":"form"});
      rb.query('timeLineModelBlockId', params.timeLineModelBlockId, {"style":"form"});
      rb.query('timeLineModelId', params.timeLineModelId, {"style":"form"});
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
   * setUIScenarioTimeLineModel.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `setUiScenarioTimeLineModelUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  setUiScenarioTimeLineModelUsingPost(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * uiScenarioId
     */
    uiScenarioId: string;

    /**
     * timeLineModelStoryDocId
     */
    timeLineModelStoryDocId: string;

    /**
     * timeLineModelBlockId
     */
    timeLineModelBlockId: string;

    /**
     * timeLineModelId
     */
    timeLineModelId: string;
  }): Observable<void> {

    return this.setUiScenarioTimeLineModelUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

}
