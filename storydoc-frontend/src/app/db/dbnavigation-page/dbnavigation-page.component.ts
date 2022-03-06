import { Component, OnInit } from '@angular/core';
import {DBNavigationService} from "./dbnavigation.service";
import {Observable} from "rxjs";
import {NavigationModelDto} from "@storydoc/models";

@Component({
  selector: 'app-dbnavigation-page',
  templateUrl: './dbnavigation-page.component.html',
  styleUrls: ['./dbnavigation-page.component.scss']
})
export class DBNavigationPageComponent implements OnInit {

  constructor(private service: DBNavigationService) { }

  nav$: Observable<NavigationModelDto> = this.service.navigationModel$

  ngOnInit(): void {
    this.service.init();
  }


}
