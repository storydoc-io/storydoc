import {Component, ElementRef, OnChanges, OnDestroy, OnInit, SimpleChanges} from '@angular/core';
import * as d3 from 'd3'
import {BluePrintPart, BlueprintService, ClassPart, DiagramPart} from "../blueprint-panel/blueprint.service";
import {Subscription} from "rxjs";
import {BluePrintPartComponent} from "../blueprint-panel/blue-print-part/blue-print-part.component";

@Component({
  selector: 'app-blueprint-panel2',
  templateUrl: './blueprint-panel2.component.html',
  styleUrls: ['./blueprint-panel2.component.scss'],
  providers: [BlueprintService]
})
export class BlueprintPanel2Component implements OnInit, OnDestroy {

  constructor(private service: BlueprintService, private elRef: ElementRef) {
    this.hostElement = this.elRef.nativeElement;
  }

  hostElement
  svg

  bluePrintDiagram$ = this.service.diagramPart$
  bluePrintDiagram

  subscriptions: Subscription[] = []

  ngOnInit(): void {
    this.bluePrintDiagram$.subscribe((diagramPart)=> this.renderDiagram(diagramPart))
  }

  ngOnDestroy() {
    this.subscriptions.forEach( (subscription) => subscription.unsubscribe())
  }

  private resetChart() {
    d3.select(this.hostElement).select('svg').remove();
    this.counter = 1
  }

  private initChart() {
    let viewBoxHeight = 100;
    let viewBoxWidth = 200;
    this.svg = d3.select(this.hostElement).append('svg')
      .attr('width', '100%')
      .attr('height', '1000px')
  }

  private renderDiagram(diagramPart: DiagramPart) {
    this.resetChart()
    this.initChart()
    if (diagramPart) {
        this.renderBluePrintPart(diagramPart.bluePrint, this.svg)
    }

  }

  counter: number = 1

  private renderBluePrintPart(bluePrintPart: BluePrintPart, parent) {
    let gContainer = parent
      .append('g')

    gContainer.append('text')
      .text(bluePrintPart.name)
      .attr('x', 15)
      .attr('y', 20 * this.counter++)

    bluePrintPart.subElements.forEach(subElem => {
      if (subElem.type==='BluePrint') {
        this.renderBluePrintPart(<BluePrintPart>subElem, gContainer)
      } else if (subElem.type==='ClassElem') {
        this.renderClassPart(<ClassPart>subElem, gContainer)
      }

    })
  }

  private renderClassPart(classPart: ClassPart, parent) {
    let gContainer = parent
      .append('g')

    gContainer.append('text')
      .text(classPart.name)
      .attr('x', 15)
      .attr('y', 20 * this.counter++)
  }

  private addLabel(text: string) {
    this.svg
      .append('text')
      .text(text)
      .attr('id', 'total')
      .attr('x', 5)
      .attr('y', 10)
      .style("font", 'system-ui, -apple-system, "Segoe UI", Roboto, "Helvetica Neue", Arial, "Noto Sans", "Liberation Sans", sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji"')
      .on("click", ()=> { console.log('****  clicked! **** ') })
      //.style("font-size", "5px")
  }

}
