import {Component, Input, OnInit} from '@angular/core';
import {Uidto} from '../../../../../app/api/models'

@Component({
  selector: 'app-thumb-nail',
  templateUrl: './thumb-nail.component.html',
  styleUrls: ['./thumb-nail.component.scss']
})
export class ThumbNailComponent implements OnInit {

  constructor() { }

  @Input()
  ui: Uidto;

  ngOnInit(): void {
  }

}
