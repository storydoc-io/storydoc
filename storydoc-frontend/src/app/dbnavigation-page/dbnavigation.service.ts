import {Injectable} from '@angular/core';
import {SqldbControllerService} from "@storydoc/services";
import {NavigationModelDto} from "@storydoc/models";
import {BehaviorSubject} from "rxjs";
import {map} from "rxjs/operators";

interface State {
  navigationModel?: NavigationModelDto
}

@Injectable({
  providedIn: 'root'
})
export class DBNavigationService {

  constructor(private sqldbControllerService: SqldbControllerService) { }

  private store = new BehaviorSubject<State>({})

  navigationModel$ = this.store.asObservable().pipe(
    map(state => state.navigationModel)
  )

  init() {
    this.sqldbControllerService.getNavigationModelUsingGet().subscribe((navigationModel)=>
      this.store.next({...this.store.getValue(), navigationModel})
    )
  }

}
