import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-title',
  templateUrl: './title.component.html',
  styleUrls: ['./title.component.scss']
})
export class TitleComponent implements OnInit {

  constructor() { }

  @Input()
  text: string

  @Input()
  numbering: number[]

  ngOnInit(): void {
  }

  numberingAsString(): string {
    if (!this.numbering) return ''
    let val =  ''
    // @ts-ignore
    for(let nr of this.numbering) {
      val += (val.length==0 ? '' :'.') + nr
    }
    return val
  }


}
