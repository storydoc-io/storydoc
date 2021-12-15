import { Component, OnInit } from '@angular/core';
import {UiRestControllerService} from "../../../api/services/ui-rest-controller.service";
import {Observable} from "rxjs";
import {UiBlockDto} from "../../../api/models/ui-block-dto";

@Component({
  selector: 'app-ui-walkthrough',
  templateUrl: './ui-walkthrough.component.html',
  styleUrls: ['./ui-walkthrough.component.scss']
})
export class UiWalkthroughComponent implements OnInit {

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
