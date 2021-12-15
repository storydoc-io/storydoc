import { Component, OnInit } from '@angular/core';
import {UiRestControllerService} from "../api/services/ui-rest-controller.service";
import {MockUidto} from "../api/models/mock-uidto";
import {Observable} from "rxjs";
import {ActivatedRoute, ParamMap, Params} from '@angular/router';

@Component({
  selector: 'app-uimockup-page',
  templateUrl: './uimockup-page.component.html',
  styleUrls: ['./uimockup-page.component.scss']
})
export class UIMockupPageComponent implements OnInit {

  constructor(private uiRestControllerService: UiRestControllerService, private route: ActivatedRoute) {
  }

  mockUI$: Observable<MockUidto>

  id: string

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      console.log('params: ', params.keys)
      this.id = params.get('artifactId')
      console.log('id: ', this.id)
      if (this.id) {
        this.mockUI$ = this.uiRestControllerService.getMockUiUsingGet({id: this.id })
      }
    });
  }

}
