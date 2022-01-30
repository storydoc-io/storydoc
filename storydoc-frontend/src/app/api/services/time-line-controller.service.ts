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

import { TimeLineItemId } from '../models/time-line-item-id';
import { TimeLineModelCoordinate } from '../models/time-line-model-coordinate';
import { TimeLineModelDto } from '../models/time-line-model-dto';
import { TimeLineModelSummaryDto } from '../models/time-line-model-summary-dto';


/**
 * Time Line Controller
 */
@Injectable({
  providedIn: 'root',
})
export class TimeLineControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation createTimeLineItemUsingPost
   */
  static readonly CreateTimeLineItemUsingPostPath = '/api/timeline/timelineitem';

  /**
   * createTimeLineItem.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createTimeLineItemUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  createTimeLineItemUsingPost$Response(params?: {

    /**
     * storyDocId
     */
    storyDocId?: string;

    /**
     * blockId
     */
    blockId?: string;

    /**
     * timeLineModelId
     */
    timeLineModelId?: string;

    /**
     * name
     */
    name?: string;
  }): Observable<StrictHttpResponse<TimeLineItemId>> {

    const rb = new RequestBuilder(this.rootUrl, TimeLineControllerService.CreateTimeLineItemUsingPostPath, 'post');
    if (params) {
      rb.query('storyDocId', params.storyDocId, {"style":"form"});
      rb.query('blockId', params.blockId, {"style":"form"});
      rb.query('timeLineModelId', params.timeLineModelId, {"style":"form"});
      rb.query('name', params.name, {"style":"form"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<TimeLineItemId>;
      })
    );
  }

  /**
   * createTimeLineItem.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `createTimeLineItemUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  createTimeLineItemUsingPost(params?: {

    /**
     * storyDocId
     */
    storyDocId?: string;

    /**
     * blockId
     */
    blockId?: string;

    /**
     * timeLineModelId
     */
    timeLineModelId?: string;

    /**
     * name
     */
    name?: string;
  }): Observable<TimeLineItemId> {

    return this.createTimeLineItemUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<TimeLineItemId>) => r.body as TimeLineItemId)
    );
  }

  /**
   * Path part for operation getTimeLineModelUsingGet
   */
  static readonly GetTimeLineModelUsingGetPath = '/api/timeline/timelinemodel';

  /**
   * getTimeLineModel.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getTimeLineModelUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getTimeLineModelUsingGet$Response(params?: {

    /**
     * storyDocId
     */
    storyDocId?: string;

    /**
     * blockId
     */
    blockId?: string;

    /**
     * timeLineModelId
     */
    timeLineModelId?: string;
  }): Observable<StrictHttpResponse<TimeLineModelDto>> {

    const rb = new RequestBuilder(this.rootUrl, TimeLineControllerService.GetTimeLineModelUsingGetPath, 'get');
    if (params) {
      rb.query('storyDocId', params.storyDocId, {"style":"form"});
      rb.query('blockId', params.blockId, {"style":"form"});
      rb.query('timeLineModelId', params.timeLineModelId, {"style":"form"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<TimeLineModelDto>;
      })
    );
  }

  /**
   * getTimeLineModel.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getTimeLineModelUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getTimeLineModelUsingGet(params?: {

    /**
     * storyDocId
     */
    storyDocId?: string;

    /**
     * blockId
     */
    blockId?: string;

    /**
     * timeLineModelId
     */
    timeLineModelId?: string;
  }): Observable<TimeLineModelDto> {

    return this.getTimeLineModelUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<TimeLineModelDto>) => r.body as TimeLineModelDto)
    );
  }

  /**
   * Path part for operation createTimeLineModelUsingPost
   */
  static readonly CreateTimeLineModelUsingPostPath = '/api/timeline/timelinemodel';

  /**
   * createTimeLineModel.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createTimeLineModelUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  createTimeLineModelUsingPost$Response(params?: {

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
  }): Observable<StrictHttpResponse<TimeLineModelCoordinate>> {

    const rb = new RequestBuilder(this.rootUrl, TimeLineControllerService.CreateTimeLineModelUsingPostPath, 'post');
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
        return r as StrictHttpResponse<TimeLineModelCoordinate>;
      })
    );
  }

  /**
   * createTimeLineModel.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `createTimeLineModelUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  createTimeLineModelUsingPost(params?: {

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
  }): Observable<TimeLineModelCoordinate> {

    return this.createTimeLineModelUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<TimeLineModelCoordinate>) => r.body as TimeLineModelCoordinate)
    );
  }

  /**
   * Path part for operation getTimeLineModelSummariesUsingGet
   */
  static readonly GetTimeLineModelSummariesUsingGetPath = '/api/timeline/timelinemodelsummaries';

  /**
   * getTimeLineModelSummaries.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getTimeLineModelSummariesUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getTimeLineModelSummariesUsingGet$Response(params?: {

    /**
     * storyDocId
     */
    storyDocId?: string;

    /**
     * blockId
     */
    blockId?: string;
  }): Observable<StrictHttpResponse<Array<TimeLineModelSummaryDto>>> {

    const rb = new RequestBuilder(this.rootUrl, TimeLineControllerService.GetTimeLineModelSummariesUsingGetPath, 'get');
    if (params) {
      rb.query('storyDocId', params.storyDocId, {"style":"form"});
      rb.query('blockId', params.blockId, {"style":"form"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Array<TimeLineModelSummaryDto>>;
      })
    );
  }

  /**
   * getTimeLineModelSummaries.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getTimeLineModelSummariesUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getTimeLineModelSummariesUsingGet(params?: {

    /**
     * storyDocId
     */
    storyDocId?: string;

    /**
     * blockId
     */
    blockId?: string;
  }): Observable<Array<TimeLineModelSummaryDto>> {

    return this.getTimeLineModelSummariesUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<Array<TimeLineModelSummaryDto>>) => r.body as Array<TimeLineModelSummaryDto>)
    );
  }

}
