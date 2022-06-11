import { Component, OnInit } from '@angular/core';
import {CodeService} from "../../code.service";
import {BluePrint, BluePrintElement, Role} from "@storydoc/models";
import {BlueprintService} from "./blueprint.service";

@Component({
  selector: 'app-blueprint-panel',
  templateUrl: './blueprint-panel.component.html',
  styleUrls: ['./blueprint-panel.component.scss'],
  providers: [BlueprintService]
})
export class BlueprintPanelComponent implements OnInit {

  constructor(private service: BlueprintService) { }

  bluePrintDiagram$ = this.service.diagramPart$

  ngOnInit(): void {
  }

  isRole(element: BluePrintElement) {
    return element.type === 'Role'

  }

  toRole(element: BluePrintElement): Role {
    return <Role> element
  }

}
