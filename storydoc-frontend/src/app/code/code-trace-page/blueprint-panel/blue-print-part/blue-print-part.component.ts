import {Component, Input, OnInit} from '@angular/core';
import {BluePrintPart} from "../blueprint.service";

@Component({
  selector: 'app-blue-print-part',
  templateUrl: './blue-print-part.component.html',
  styleUrls: ['./blue-print-part.component.scss']
})
export class BluePrintPartComponent implements OnInit {

  constructor() { }

  @Input()
  bluePrintPart: BluePrintPart

  ngOnInit(): void {
  }

}
