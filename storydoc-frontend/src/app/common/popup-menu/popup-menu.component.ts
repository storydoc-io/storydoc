import {Component, HostBinding, HostListener, Input, OnInit} from '@angular/core';

// based on https://mobiarch.wordpress.com/2018/07/07/create-a-popup-menu-in-angular/

export interface PopupMenuItemSpec {
  label: string
  onClick: () => void
}

@Component({
  selector: 'app-popup-menu',
  templateUrl: './popup-menu.component.html',
  styleUrls: ['./popup-menu.component.scss']
})
export class PopupMenuComponent implements OnInit {
  @HostBinding("style.top") y = "0px"
  @HostBinding("style.left") x = "0px"
  @HostBinding("style.visibility") visibility = "hidden"
  @Input() @HostBinding("style.width") width = "200px"

  constructor() {
  }

  items: PopupMenuItemSpec[]

  ngOnInit() {
  }

  open(e: MouseEvent) {
    this.x = `${e.pageX}px`
    this.y = `${e.pageY}px`

    this.visibility = "visible"

    e.stopPropagation()
  }

  close() {
    this.visibility = "hidden"
  }

  @HostListener('document:click')
  public onDocumentClick() {
    if (this.visibility === "visible") {
      this.close()
    }
  }

  onClick(item: PopupMenuItemSpec) {
    item.onClick.apply(this, [])
  }
}
