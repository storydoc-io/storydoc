import { Component, OnInit, HostBinding, HostListener, Input } from '@angular/core';

// based on https://mobiarch.wordpress.com/2018/07/07/create-a-popup-menu-in-angular/

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

  constructor() { }

  ngOnInit() {
  }

  open(e:MouseEvent) {
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
}
