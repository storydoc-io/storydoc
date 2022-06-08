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

import { SettingsDto } from '../models/settings-dto';
import { SettingsEntryDto } from '../models/settings-entry-dto';


/**
 * Settings Controller
 */
@Injectable({
  providedIn: 'root',
})
export class SettingsControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation setGlobalSettingUsingPost
   */
  static readonly SetGlobalSettingUsingPostPath = '/api/settings/setting';

  /**
   * setGlobalSetting.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `setGlobalSettingUsingPost()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  setGlobalSettingUsingPost$Response(params?: {
    body?: SettingsEntryDto
  }): Observable<StrictHttpResponse<void>> {

    const rb = new RequestBuilder(this.rootUrl, SettingsControllerService.SetGlobalSettingUsingPostPath, 'post');
    if (params) {
      rb.body(params.body, 'application/json');
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
   * setGlobalSetting.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `setGlobalSettingUsingPost$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  setGlobalSettingUsingPost(params?: {
    body?: SettingsEntryDto
  }): Observable<void> {

    return this.setGlobalSettingUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<void>) => r.body as void)
    );
  }

  /**
   * Path part for operation getGlobalSettingsUsingGet
   */
  static readonly GetGlobalSettingsUsingGetPath = '/api/settings/settings';

  /**
   * getGlobalSettings.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getGlobalSettingsUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getGlobalSettingsUsingGet$Response(params?: {
  }): Observable<StrictHttpResponse<SettingsDto>> {

    const rb = new RequestBuilder(this.rootUrl, SettingsControllerService.GetGlobalSettingsUsingGetPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<SettingsDto>;
      })
    );
  }

  /**
   * getGlobalSettings.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getGlobalSettingsUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getGlobalSettingsUsingGet(params?: {
  }): Observable<SettingsDto> {

    return this.getGlobalSettingsUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<SettingsDto>) => r.body as SettingsDto)
    );
  }

}
