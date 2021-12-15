import { Component, OnInit } from '@angular/core';
import {CodeRestControllerService} from "../api/services/code-rest-controller.service";
import {Observable} from "rxjs";
import {CodeTraceDto} from "../api/models/code-trace-dto";
import {CodeTraceItemDto} from "../api/models/code-trace-item-dto";

@Component({
  selector: 'app-code-trace-page',
  templateUrl: './code-trace-page.component.html',
  styleUrls: ['./code-trace-page.component.scss']
})
export class CodeTracePageComponent implements OnInit {

  constructor(private codeRestControllerService : CodeRestControllerService) {}

  selectedItem: CodeTraceItemDto

  codeTrace$ : Observable<CodeTraceDto>

  ngOnInit(): void {
    this.codeTrace$ = this.codeRestControllerService.traceUsingGet({})
  }

  getClassName(item: CodeTraceItemDto) {
    return item.className?.split('.').slice(-1)[0]
  }

  selectItem(item) {
    this.selectedItem = item
  }
}
