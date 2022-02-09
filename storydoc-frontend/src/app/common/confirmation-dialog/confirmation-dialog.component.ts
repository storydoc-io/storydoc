import {Component, Input, OnInit} from '@angular/core';

export interface ConfirmationDialogSpec {
  title: string;
  message: string,
  cancel: ()=>void
  confirm: ()=>void
}

@Component({
  selector: 'app-confirmation-dialog',
  templateUrl: './confirmation-dialog.component.html',
  styleUrls: ['./confirmation-dialog.component.scss']
})
export class ConfirmationDialogComponent {

  @Input()
  spec: ConfirmationDialogSpec

  cancel() {
    this.spec.cancel.apply(this, [])
  }

  confirm() {
    this.spec.confirm.apply(this, [])
  }

}
