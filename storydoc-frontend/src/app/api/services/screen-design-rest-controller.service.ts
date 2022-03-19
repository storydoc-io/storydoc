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

import { ComponentDescriptorDto } from '../models/component-descriptor-dto';
import { SdComponentId } from '../models/sd-component-id';
import { ScreenDesignCoordinate } from '../models/screen-design-coordinate';
import { ScreenDesignDto } from '../models/screen-design-dto';


/**
 * Screen Design Rest Controller
 */
@Injectable({
  providedIn: 'root',
})
export class ScreenDesignRestControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation getScreenDesignUsingGet
   */
  static readonly GetScreenDesignUsingGetPath = '/api/ui/screendesign/';

  /**
   * getScreenDesign.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getScreenDesignUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getScreenDesignUsingGet$Response(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * screenDesignId
     */
    screenDesignId: string;
  }): Observable<StrictHttpResponse<ScreenDesignDto>> {

    const rb = new RequestBuilder(this.rootUrl, ScreenDesignRestControllerService.GetScreenDesignUsingGetPath, 'get');
    if (params) {
      rb.query('storyDocId', params.storyDocId, {"style":"form"});
      rb.query('blockId', params.blockId, {"style":"form"});
      rb.query('screenDesignId', params.screenDesignId, {"style":"form"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<ScreenDesignDto>;
      })
    );
  }

  /**
   * getScreenDesign.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getScreenDesignUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getScreenDesignUsingGet(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * screenDesignId
     */
    screenDesignId: string;
  }): Observable<ScreenDesignDto> {

    return this.getScreenDesignUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<ScreenDesignDto>) => r.body as ScreenDesignDto)
    );
  }

  /**
   * Path part for operation createScreenDesignUsingPost
   */
  static readonly CreateScreenDesignUsingPostPath = '/api/ui/screendesign/';

  /**
   * createScreenDesign.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createScreenDesignUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  createScreenDesignUsingPost$Response(params: {

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
  }): Observable<StrictHttpResponse<ScreenDesignCoordinate>> {

    const rb = new RequestBuilder(this.rootUrl, ScreenDesignRestControllerService.CreateScreenDesignUsingPostPath, 'post');
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
        return r as StrictHttpResponse<ScreenDesignCoordinate>;
      })
    );
  }

  /**
   * createScreenDesign.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `createScreenDesignUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  createScreenDesignUsingPost(params: {

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
  }): Observable<ScreenDesignCoordinate> {

    return this.createScreenDesignUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<ScreenDesignCoordinate>) => r.body as ScreenDesignCoordinate)
    );
  }

  /**
   * Path part for operation createComponentUsingPost
   */
  static readonly CreateComponentUsingPostPath = '/api/ui/screendesign/component';

  /**
   * createComponent.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createComponentUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  createComponentUsingPost$Response(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * screenDesignId
     */
    screenDesignId: string;

    /**
     * containerId
     */
    containerId: string;

    /**
     * type
     */
    type: string;

    /**
     * x
     */
    'x': number;

    /**
     * y
     */
    'y': number;
  }): Observable<StrictHttpResponse<SdComponentId>> {

    const rb = new RequestBuilder(this.rootUrl, ScreenDesignRestControllerService.CreateComponentUsingPostPath, 'post');
    if (params) {
      rb.query('storyDocId', params.storyDocId, {"style":"form"});
      rb.query('blockId', params.blockId, {"style":"form"});
      rb.query('screenDesignId', params.screenDesignId, {"style":"form"});
      rb.query('containerId', params.containerId, {"style":"form"});
      rb.query('type', params.type, {"style":"form"});
      rb.query('x', params['x'], {"style":"form"});
      rb.query('y', params['y'], {"style":"form"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<SdComponentId>;
      })
    );
  }

  /**
   * createComponent.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `createComponentUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  createComponentUsingPost(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * screenDesignId
     */
    screenDesignId: string;

    /**
     * containerId
     */
    containerId: string;

    /**
     * type
     */
    type: string;

    /**
     * x
     */
    'x': number;

    /**
     * y
     */
    'y': number;
  }): Observable<SdComponentId> {

    return this.createComponentUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<SdComponentId>) => r.body as SdComponentId)
    );
  }

  /**
   * Path part for operation deleteComponentUsingDelete
   */
  static readonly DeleteComponentUsingDeletePath = '/api/ui/screendesign/component';

  /**
   * deleteComponent.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteComponentUsingDelete()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteComponentUsingDelete$Response(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * screenDesignId
     */
    screenDesignId: string;

    /**
     * componenId
     */
    componenId: string;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, ScreenDesignRestControllerService.DeleteComponentUsingDeletePath, 'delete');
    if (params) {
      rb.query('storyDocId', params.storyDocId, {"style":"form"});
      rb.query('blockId', params.blockId, {"style":"form"});
      rb.query('screenDesignId', params.screenDesignId, {"style":"form"});
      rb.query('componenId', params.componenId, {"style":"form"});
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
   * deleteComponent.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `deleteComponentUsingDelete$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteComponentUsingDelete(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * screenDesignId
     */
    screenDesignId: string;

    /**
     * componenId
     */
    componenId: string;
  }): Observable<void> {

    return this.deleteComponentUsingDelete$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

  /**
   * Path part for operation updateComponentLocationUsingPut
   */
  static readonly UpdateComponentLocationUsingPutPath = '/api/ui/screendesign/component/location';

  /**
   * updateComponentLocation.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateComponentLocationUsingPut()` instead.
   *
   * This method doesn't expect any request body.
   */
  updateComponentLocationUsingPut$Response(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * screenDesignId
     */
    screenDesignId: string;

    /**
     * componenId
     */
    componenId: string;

    /**
     * x
     */
    'x': number;

    /**
     * y
     */
    'y': number;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, ScreenDesignRestControllerService.UpdateComponentLocationUsingPutPath, 'put');
    if (params) {
      rb.query('storyDocId', params.storyDocId, {"style":"form"});
      rb.query('blockId', params.blockId, {"style":"form"});
      rb.query('screenDesignId', params.screenDesignId, {"style":"form"});
      rb.query('componenId', params.componenId, {"style":"form"});
      rb.query('x', params['x'], {"style":"form"});
      rb.query('y', params['y'], {"style":"form"});
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
   * updateComponentLocation.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `updateComponentLocationUsingPut$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  updateComponentLocationUsingPut(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * screenDesignId
     */
    screenDesignId: string;

    /**
     * componenId
     */
    componenId: string;

    /**
     * x
     */
    'x': number;

    /**
     * y
     */
    'y': number;
  }): Observable<void> {

    return this.updateComponentLocationUsingPut$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

  /**
   * Path part for operation updateComponentNameUsingPut
   */
  static readonly UpdateComponentNameUsingPutPath = '/api/ui/screendesign/component/name';

  /**
   * updateComponentName.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateComponentNameUsingPut()` instead.
   *
   * This method doesn't expect any request body.
   */
  updateComponentNameUsingPut$Response(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * screenDesignId
     */
    screenDesignId: string;

    /**
     * componenId
     */
    componenId: string;

    /**
     * name
     */
    name: string;
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, ScreenDesignRestControllerService.UpdateComponentNameUsingPutPath, 'put');
    if (params) {
      rb.query('storyDocId', params.storyDocId, {"style":"form"});
      rb.query('blockId', params.blockId, {"style":"form"});
      rb.query('screenDesignId', params.screenDesignId, {"style":"form"});
      rb.query('componenId', params.componenId, {"style":"form"});
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
   * updateComponentName.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `updateComponentNameUsingPut$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  updateComponentNameUsingPut(params: {

    /**
     * storyDocId
     */
    storyDocId: string;

    /**
     * blockId
     */
    blockId: string;

    /**
     * screenDesignId
     */
    screenDesignId: string;

    /**
     * componenId
     */
    componenId: string;

    /**
     * name
     */
    name: string;
  }): Observable<void> {

    return this.updateComponentNameUsingPut$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

  /**
   * Path part for operation getComponentPaletteUsingGet
   */
  static readonly GetComponentPaletteUsingGetPath = '/api/ui/screendesign/palette';

  /**
   * getComponentPalette.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getComponentPaletteUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getComponentPaletteUsingGet$Response(params?: {
  }): Observable<StrictHttpResponse<Array<ComponentDescriptorDto>>> {

    const rb = new RequestBuilder(this.rootUrl, ScreenDesignRestControllerService.GetComponentPaletteUsingGetPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Array<ComponentDescriptorDto>>;
      })
    );
  }

  /**
   * getComponentPalette.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getComponentPaletteUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getComponentPaletteUsingGet(params?: {
  }): Observable<Array<ComponentDescriptorDto>> {

    return this.getComponentPaletteUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<Array<ComponentDescriptorDto>>) => r.body as Array<ComponentDescriptorDto>)
    );
  }

}
