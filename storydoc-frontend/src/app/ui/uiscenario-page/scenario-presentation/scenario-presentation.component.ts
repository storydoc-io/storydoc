import { Component, OnInit } from '@angular/core';

import {Observable} from "rxjs";
import {UiScenarioDto} from "@storydoc/models";
import {UIScenarioService} from "../uiscenario.service";

@Component({
  selector: 'app-scenario-presentation',
  templateUrl: './scenario-presentation.component.html',
  styleUrls: ['./scenario-presentation.component.scss']
})
export class ScenarioPresentationComponent implements OnInit {

  constructor(
    private uiScenarioService: UIScenarioService
  ) { }

  uiScenario$: Observable<UiScenarioDto> = this.uiScenarioService.uiScenario$

  ngOnInit(): void {
  }

  endPresentationMode() {
    this.uiScenarioService.togglePresentationMode()
  }

  toFirst() {
    this.uiScenarioService.selectFirst()
  }

  toPrevious() {
    this.uiScenarioService.selectPrevious()
  }

  toNext() {
    this.uiScenarioService.selectNext()
  }

  toLast() {
    this.uiScenarioService.selectLast()
  }
}
