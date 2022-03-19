import {Component, OnDestroy, OnInit} from '@angular/core';
import {ScreenDesignService} from "../screen-design.service";
import {ComponentAttributeDescriptorDto, ComponentDescriptorDto, SdComponentDto} from "@storydoc/models";
import {combineLatest, Subscription} from "rxjs";
import {map} from "rxjs/operators";

@Component({
  selector: 'app-component-details',
  templateUrl: './component-details.component.html',
  styleUrls: ['./component-details.component.scss']
})
export class ComponentDetailsComponent implements OnInit, OnDestroy {

  constructor(private service: ScreenDesignService) { }

  palette$ = this.service.palette$
  selection$ = this.service.selection$

  palette: ComponentDescriptorDto[]
  selection: SdComponentDto | 'NONE'
  descriptor: ComponentDescriptorDto

  private subscriptions: Subscription[] = []

  ngOnInit(): void {
    this.subscriptions.push(combineLatest([this.palette$, this.selection$]).subscribe(
      ([palette, selection]) => {
        if (palette) {
          this.palette = palette
          this.selection = selection
          if (selection=='NONE') {
            this.descriptor = null
          } else {
            this.descriptor = this.palette.find(p => p.type===selection.type)
          }
        }
      }
    ))
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }

  onChange(attribute: ComponentAttributeDescriptorDto, $event: any) {
    let value = $event.target.value
    this.service.setAttribute(this.selection, attribute, value)
  }

  onNameChange($event: any) {
    let value = $event.target.value
    this.service.renameSelectedComponent(value)
  }
}
