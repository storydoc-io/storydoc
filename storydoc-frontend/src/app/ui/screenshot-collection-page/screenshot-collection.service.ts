import { environment } from './../../../environments/environment'
import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {map, tap} from "rxjs/operators";
import {HttpClient} from "@angular/common/http";
import {ScreenshotCollectionCoordinate, ScreenShotCollectionDto} from "@storydoc/models";
import {UiRestControllerService} from "@storydoc/services";

interface State {
  coord?: ScreenshotCollectionCoordinate
  screenShotCollection?: ScreenShotCollectionDto
}

@Injectable({
  providedIn: 'root'
})
export class ScreenshotCollectionService {

  constructor(
    private http: HttpClient,
    private uiRestControllerService: UiRestControllerService) {
  }

  private store = new BehaviorSubject<State>({})

  screenshotCollection$ = this.store.asObservable().pipe(
    tap(x => console.log('value read: ', x)),
    map(state => state.screenShotCollection)
  )

  public initId(coord: ScreenshotCollectionCoordinate) {
    this.store.next({coord})
    this.load()
  }

  public AddScreenshot(params: { fileSource: any, name: string }, callback: (msg: any ) => void) {
    const formData = new FormData();
    formData.append('file', params.fileSource);
    formData.set('storyDocId', this.collectionCoord.blockCoordinate.storyDocId.id)
    formData.set('blockId', this.collectionCoord.blockCoordinate.blockId.id)
    formData.set('screenshotCollectionId', this.collectionCoord.screenShotCollectionId.id)
    formData.set('name', params.name)
    this.http.post('http://localhost:'+environment.port+'/api/ui/screenshot' , formData).subscribe({
      next: value => this.load(),
//      error: err => callback.apply(this, ['file size limit exceeded'])
    })
  }

  public get collectionCoord() {
    return this.store.getValue().coord;
  }

  private load() {
    this.uiRestControllerService.getScreenShotCollectionUsingGet({
      storyDocId: this.collectionCoord.blockCoordinate.storyDocId.id,
      blockId: this.collectionCoord.blockCoordinate.blockId.id,
      id: this.collectionCoord.screenShotCollectionId.id
    }).subscribe({
      next:
        dto => {
          console.log('received: ', dto)
          this.store.next({
            ...this.store.getValue(),
            screenShotCollection: dto,
          })
        }
    })

  }

}
