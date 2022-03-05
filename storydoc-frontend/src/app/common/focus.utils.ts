import {ElementRef} from "@angular/core";

export function setFocusOn(elem: ElementRef) {
  setTimeout(() => elem.nativeElement.focus(), 0)
}
