import { Component, OnInit } from '@angular/core';
import {DBDataService} from "./dbdata.service";
import {Observable} from "rxjs";
import {DbDataSetDto, NavigationModelDto} from "@storydoc/models";
import {DBNavigationService} from "../dbnavigation-page/dbnavigation.service";

@Component({
  selector: 'app-dbdata-page',
  templateUrl: './dbdata-page.component.html',
  styleUrls: ['./dbdata-page.component.scss']
})
export class DBDataPageComponent implements OnInit {

  constructor(private dataService: DBDataService, private dbNavigationService: DBNavigationService ) { }

  dataSet$: Observable<DbDataSetDto> = this.dataService.dataSet$
  navigationModel$: Observable<NavigationModelDto> = this.dbNavigationService.navigationModel$

  ngOnInit(): void {
    this.dataService.load()
    this.dbNavigationService.init()
  }

  addTableData() {
    this.dataService.addTableData()
  }
}
