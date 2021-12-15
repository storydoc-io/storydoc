import { Component} from '@angular/core';
import {UiRestControllerService} from "../../../api/services/ui-rest-controller.service";
import {Observable} from "rxjs";
import {UiBlockDto} from "../../../api/models/ui-block-dto";

@Component({
  selector: 'app-uiblock',
  templateUrl: './uiblock.component.html',
  styleUrls: ['./uiblock.component.scss']
})
export class UIBlockComponent {

  constructor(private uiRestControllerService: UiRestControllerService ) {
    this.$uiBlock = this.uiRestControllerService.getUiUsingGet({})
  }

  $uiBlock: Observable<UiBlockDto>

}
