import {Component, Input, OnInit} from '@angular/core';
import {ClassPart} from "../blueprint.service";


@Component({
  selector: 'app-class-part',
  templateUrl: './class-part.component.html',
  styleUrls: ['./class-part.component.scss']
})
export class ClassPartComponent implements OnInit {

  constructor() { }

  @Input()
  classPart: ClassPart

  ngOnInit(): void {
  }

}
