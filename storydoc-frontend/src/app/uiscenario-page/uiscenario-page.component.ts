import { Component, OnInit } from '@angular/core';
import {UiRestControllerService} from "../api/services/ui-rest-controller.service";
import {Observable} from "rxjs";
import {UiBlockDto} from "../api/models/ui-block-dto";

@Component({
  selector: 'app-uiscenario-page',
  templateUrl: './uiscenario-page.component.html',
  styleUrls: ['./uiscenario-page.component.scss']
})
export class UIScenarioPageComponent implements OnInit {

  constructor(private uiRestControllerService: UiRestControllerService ) {
    this.$uiBlock = this.uiRestControllerService.getUiUsingGet({})
  }

  $uiBlock: Observable<UiBlockDto>

  stepNr: number = 0

  ngOnInit(): void {
  }

  previous() {
    if (this.stepNr>0) {
      this.stepNr--
    }
  }

  next() {
    this.stepNr++
  }


}
