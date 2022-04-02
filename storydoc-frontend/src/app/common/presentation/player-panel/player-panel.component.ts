import {Component, EventEmitter, OnInit, Output} from '@angular/core';

import { faStepBackward, faStepForward, faFastBackward, faFastForward } from '@fortawesome/free-solid-svg-icons';


@Component({
  selector: 'app-player-panel',
  templateUrl: './player-panel.component.html',
  styleUrls: ['./player-panel.component.scss']
})
export class PLayerPanelComponent implements OnInit {

  constructor() { }

  @Output()
  private first = new EventEmitter()

  @Output()
  private previous = new EventEmitter()

  @Output()
  private next = new EventEmitter()

  @Output()
  private last = new EventEmitter()

  faFastBackward=faFastBackward
  faStepBackward=faStepBackward
  faStepForward=faStepForward
  faFastForward=faFastForward

  ngOnInit(): void {
  }

  firstClicked() {
    this.first.emit()
  }

  previousClicked() {
    this.previous.emit()
  }

  nextClicked() {
    this.next.emit()
  }

  lastClicked() {
    this.last.emit()
  }
}
