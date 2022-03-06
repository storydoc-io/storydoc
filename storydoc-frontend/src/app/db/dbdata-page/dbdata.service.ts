import {Injectable, OnDestroy} from '@angular/core';
import {DbDataSetDto, NavigationModelDto} from "@storydoc/models";
import {BehaviorSubject, Subscription} from "rxjs";
import {map} from "rxjs/operators";
import {SqldbControllerService} from "@storydoc/services";
import {log} from '@storydoc/common'

interface State {
  dataSet?: DbDataSetDto
}

@Injectable({
  providedIn: 'root'
})
export class DBDataService implements  OnDestroy{

  constructor(private dbControllerService: SqldbControllerService) { this.init() }

  private store = new BehaviorSubject<State>({})
  state$ = this.store.asObservable()

  dataSet$ = this.store.asObservable().pipe(
    map(state => state.dataSet)
  )

  private get dataset(): DbDataSetDto {
    return <DbDataSetDto> this.store.getValue().dataSet
  }

  private subscriptions: Subscription[] = []

  init(): void {
    log('init()')
    this.subscriptions.push(this.logStateChanges())
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }


  load() {
    this.dbControllerService.getDbDataSetUsingGet({}).subscribe( dataSet => this.store.next({ dataSet }))
  }



  addTableData() {
    this.dataset.tableDataSets.push({})
    this.store.next({
      dataSet: {
        tableDataSets: this.dataset.tableDataSets
      }
    })
  }

  private logStateChanges() {
    return this.state$.subscribe((state)=> { log('   state$ >> ', state) })
  }

}
