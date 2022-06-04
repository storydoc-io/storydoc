import {Component, Inject, Input, OnInit} from '@angular/core'
import {DOCUMENT} from "@angular/common"
import {DiagramPart} from "../blueprint.service";

declare var LeaderLine: any


@Component({
  selector: 'app-diagram-part',
  templateUrl: './diagram-part.component.html',
  styleUrls: ['./diagram-part.component.scss']
})
export class DiagramPartComponent implements OnInit {

  @Input()
  diagramPart: DiagramPart

  constructor(@Inject(DOCUMENT) private document) {
  }

  ngOnInit(): void {
  }

  leaderLines: any[] = new Array()

  ngAfterViewInit() {
    this.diagramPart.lines.forEach(line => {
      this.leaderLines.push(new LeaderLine(
        this.document.getElementById(line.idFrom),
        this.document.getElementById(line.idTo),
        {
          path: 'arc',
          color: 'red',
          size: 4,
          //endPlug: 'arrow3'
        }
      ))
    })
  }

  ngOnDestroy() {
    if (this.leaderLines) {
      for (var leaderLine of this.leaderLines) {
        //leaderLine.remove()
      }
      this.leaderLines = []
    }
  }

}

