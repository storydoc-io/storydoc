import {Component, Inject, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core'
import {DOCUMENT} from "@angular/common"
import {DiagramPart, Line} from "../blueprint.service";

declare var LeaderLine: any


@Component({
  selector: 'app-diagram-part',
  templateUrl: './diagram-part.component.html',
  styleUrls: ['./diagram-part.component.scss']
})
export class DiagramPartComponent implements OnChanges {

  @Input()
  diagramPart: DiagramPart

  lines: Line[]
  leaderLines: any[] = new Array()

  constructor(@Inject(DOCUMENT) private document) {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.lines != this.diagramPart.lines){
      if(this.leaderLines) {
        this.removeLeaderLines()
      }
      setInterval(() => { this.addLeaderLines() },1)
    }
  }

  addLeaderLines() {
    console.log('+++ adding leaderlines +++')
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
    this.removeLeaderLines();
  }

  private removeLeaderLines() {
    console.log('=== removing leaderlines ===')
    if (this.leaderLines) {
      for (var leaderLine of this.leaderLines) {
        leaderLine.remove()
      }
      this.leaderLines = []
    }
  }
}

