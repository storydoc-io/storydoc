import {Injectable, OnDestroy} from '@angular/core';
import {BehaviorSubject, Subscription} from "rxjs";
import {CodeRestControllerService} from "@storydoc/services";
import {CodeTraceDto, CodeTraceItemDto, SourceCodeDto} from "@storydoc/models";
import {distinctUntilChanged, map} from "rxjs/operators";
import {log, logChangesToObservable} from "@storydoc/common";

interface TraceStoreState {
  codeTrace?: CodeTraceDto
  selectedItem?: CodeTraceItemDto,
}

interface SourceCodeStoreState {
  className?: string,
  sourceCode? : SourceCodeDto
}

@Injectable({
  providedIn: 'root'
})
export class CodeService implements  OnDestroy{

  constructor(private codeRestControllerService: CodeRestControllerService) {
    this.init()
  }

  private traceStore = new BehaviorSubject<TraceStoreState>({} )

  codeTrace$ = this.traceStore.pipe(
    map(state => state.codeTrace),
    distinctUntilChanged(),
  )

  selectedItem$ = this.traceStore.pipe(
    map(state => state.selectedItem),
    distinctUntilChanged(),
  )

  private sourceCodeStore = new BehaviorSubject<SourceCodeStoreState>({} )

  sourceCode$ =  this.sourceCodeStore.pipe(
    map(state => state.sourceCode),
    distinctUntilChanged(),
  )

  private subscriptions: Subscription[] = []

  init(): void {
    log('init()')
    this.subscriptions.push(logChangesToObservable('traceStore::codeTrace$ >>', this.codeTrace$))
    this.subscriptions.push(logChangesToObservable('traceStore::selectedItem$ >> ', this.selectedItem$))
    this.subscriptions.push(logChangesToObservable('sourceCodeStore::sourceCode$ >> ', this.sourceCode$))
    this.subscriptions.push(this.selectedItem$.subscribe(item => {
      if (item && item.type=='ENTER') {
        log('selectedItem$ -> loadSourceCode()')
        this.loadSourceCode(item.className)
      }
    }))
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }

  loadTrace() {
    log('loadTrace()')
    this.codeRestControllerService.traceUsingGet({})
      .subscribe(codeTrace => {
        this.traceStore.next({ codeTrace})
        this.selectTraceItem(codeTrace.items[0])
      })
  }

  selectTraceItem(item) {
    log('selectTraceItem(item)', item)
    this.traceStore.next({
      ... this.traceStore.getValue(),
      selectedItem: item
    })
  }

  loadSourceCode(className: string) {
    log('loadSourceCode(className)', className)
    this.codeRestControllerService.sourceUsingGet({ className})
      .subscribe(sourceCode => this.sourceCodeStore.next({ className, sourceCode }))
  }

}
