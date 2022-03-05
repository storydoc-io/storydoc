import { Injectable } from '@angular/core';
import {AdminControllerService} from "@storydoc/services";
import {BehaviorSubject} from "rxjs";
import {StoryDocServerProperties} from "@storydoc/models";
import {map} from "rxjs/operators";

interface State {
  serverProperties: StoryDocServerProperties;
}

@Injectable({
  providedIn: 'root'
})
export class AdminDataService {

  constructor(private adminControllerService: AdminControllerService) { this.load()}

  private store = new BehaviorSubject<State>({ serverProperties: null })

  settings$ = this.store.asObservable().pipe(
    map(state => state.serverProperties)
  )

  private load() {
    this.adminControllerService.getConfigUsingGet({}).subscribe( serverProperties => this.store.next({ serverProperties }))
  }

}
