import {environment} from "../../environments/environment";
import {Observable} from "rxjs";

export function log(msg?: any, param1?: any, param2?: any) {
  if (!environment.production) {
    console.log(msg, param1, param2)
  }
}

export function logChangesToObservable(msg: string, observable: Observable<any>) {
  return observable.subscribe((next) => {
    log(msg, next)
  })
}
