import {Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges} from '@angular/core';
import {SettingDescriptor, SettingsService} from "../settings.service";
import {Subscription} from "rxjs";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {setFocusOn} from "@storydoc/common";

export interface SettingsDialogData {
  descriptor?: SettingDescriptor
  value?: string
}

export interface  SettingsDialogSpec {
  mode: 'UPDATE' | 'NEW',
  data: SettingsDialogData
  confirm: (data: SettingsDialogData) => void
  cancel: () => void
}

@Component({
  selector: 'app-settings-dialog',
  templateUrl: './settings-dialog.component.html',
  styleUrls: ['./settings-dialog.component.scss']
})
export class SettingsDialogComponent implements OnInit, OnDestroy, OnChanges {

  constructor(private settingsService: SettingsService) {
  }

  descriptors$ = this.settingsService.descriptors$

  descriptors:  SettingDescriptor[]

  private subscriptions: Subscription[] = []

  ngOnInit(): void {
    this.subscriptions.push(this.descriptors$.subscribe(descriptors => this.descriptors = descriptors))
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }

  @Input()
  spec: SettingsDialogSpec

  ngOnChanges(changes: SimpleChanges): void {
    if (this.spec != null) {
      this.formGroup.setValue(this.spec.data)
      if (this.updateMode()) {
        this.descriptor.disable()
      } else {
        this.descriptor.enable()
      }
    }
  }

  formGroup: FormGroup = new FormGroup({
    descriptor: new FormControl(null, Validators.required),
    value: new FormControl(null, Validators.required)
  })

  updateMode(): boolean {
    return this.spec.mode=='UPDATE'
  }

  get descriptor(): FormControl {
    return <FormControl> this.formGroup.get('descriptor')
  }

  confirm() {
    this.spec.confirm(this.formGroup.value)
  }

  cancel() {
    this.spec.cancel()
  }

}
