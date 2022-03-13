import { Component, OnInit } from '@angular/core';

import { faStepBackward, faStepForward, faFastBackward, faFastForward } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-player-panel',
  templateUrl: './player-panel.component.html',
  styleUrls: ['./player-panel.component.scss']
})
export class PLayerPanelComponent implements OnInit {

  constructor() { }

  faFastBackward=faFastBackward
  faStepBackward=faStepBackward
  faStepForward=faStepForward
  faFastForward=faFastForward

  ngOnInit(): void {
  }

}
