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

import { FolderDto } from '../models/folder-dto';
import { FolderUrn } from '../models/folder-urn';


/**
 * Folder Rest Controller
 */
@Injectable({
  providedIn: 'root',
})
export class FolderRestControllerService extends BaseService {
  constructor(
    config: ApiConfiguration,
    http: HttpClient
  ) {
    super(config, http);
  }

  /**
   * Path part for operation addFolderUsingPost
   */
  static readonly AddFolderUsingPostPath = '/api/folder/add';

  /**
   * addFolder.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `addFolderUsingPost()` instead.
   *
   * This method doesn't expect any request body.
   */
  addFolderUsingPost$Response(params?: {
    path?: Array<string>;

    /**
     * folderName
     */
    folderName?: string;
  }): Observable<StrictHttpResponse<FolderUrn>> {

    const rb = new RequestBuilder(this.rootUrl, FolderRestControllerService.AddFolderUsingPostPath, 'post');
    if (params) {
      rb.query('path', params.path, {"style":"pipeDelimited"});
      rb.query('folderName', params.folderName, {"style":"form"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<FolderUrn>;
      })
    );
  }

  /**
   * addFolder.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `addFolderUsingPost$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  addFolderUsingPost(params?: {
    path?: Array<string>;

    /**
     * folderName
     */
    folderName?: string;
  }): Observable<FolderUrn> {

    return this.addFolderUsingPost$Response(params).pipe(
      map((r: StrictHttpResponse<FolderUrn>) => r.body as FolderUrn)
    );
  }

  /**
   * Path part for operation getFoldersUsingGet
   */
  static readonly GetFoldersUsingGetPath = '/api/folder/folders';

  /**
   * getFolders.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getFoldersUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getFoldersUsingGet$Response(params?: {
    path?: Array<string>;
  }): Observable<StrictHttpResponse<Array<FolderDto>>> {

    const rb = new RequestBuilder(this.rootUrl, FolderRestControllerService.GetFoldersUsingGetPath, 'get');
    if (params) {
      rb.query('path', params.path, {"style":"pipeDelimited"});
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<Array<FolderDto>>;
      })
    );
  }

  /**
   * getFolders.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getFoldersUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getFoldersUsingGet(params?: {
    path?: Array<string>;
  }): Observable<Array<FolderDto>> {

    return this.getFoldersUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<Array<FolderDto>>) => r.body as Array<FolderDto>)
    );
  }

  /**
   * Path part for operation getRootFolderUsingGet
   */
  static readonly GetRootFolderUsingGetPath = '/api/folder/root';

  /**
   * getRootFolder.
   *
   *
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getRootFolderUsingGet()` instead.
   *
   * This method doesn't expect any request body.
   */
  getRootFolderUsingGet$Response(params?: {
  }): Observable<StrictHttpResponse<FolderUrn>> {

    const rb = new RequestBuilder(this.rootUrl, FolderRestControllerService.GetRootFolderUsingGetPath, 'get');
    if (params) {
    }

    return this.http.request(rb.build({
      responseType: 'json',
      accept: 'application/json'
    })).pipe(
      filter((r: any) => r instanceof HttpResponse),
      map((r: HttpResponse<any>) => {
        return r as StrictHttpResponse<FolderUrn>;
      })
    );
  }

  /**
   * getRootFolder.
   *
   *
   *
   * This method provides access to only to the response body.
   * To access the full response (for headers, for example), `getRootFolderUsingGet$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getRootFolderUsingGet(params?: {
  }): Observable<FolderUrn> {

    return this.getRootFolderUsingGet$Response(params).pipe(
      map((r: StrictHttpResponse<FolderUrn>) => r.body as FolderUrn)
    );
  }

}
