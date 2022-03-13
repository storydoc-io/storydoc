import {Component, OnInit} from '@angular/core';
import {CodeService} from "../code.service";
import {LinkService} from "@storydoc/common";

@Component({
  selector: 'app-code-trace-page',
  templateUrl: './code-trace-page.component.html',
  styleUrls: ['./code-trace-page.component.scss']
})
export class CodeTracePageComponent implements OnInit{

  constructor(
    public link: LinkService,
    private codeService: CodeService) {
  }

  codeTrace$ = this.codeService.codeTrace$

  ngOnInit(): void {
    this.codeService.loadTrace()
  }

}
