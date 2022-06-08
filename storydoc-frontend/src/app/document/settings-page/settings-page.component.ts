import {Component, OnInit} from '@angular/core';
import {SettingDescriptor, SettingsService} from "./settings.service";
import {SettingsDialogData, SettingsDialogSpec} from "./settings-dialog/settings-dialog.component";
import {ModalService} from "@storydoc/common";
import {SettingsEntryDto} from "@storydoc/models";

@Component({
  selector: 'app-settings-page',
  templateUrl: './settings-page.component.html',
  styleUrls: ['./settings-page.component.scss']
})
export class SettingsPageComponent implements OnInit {

  constructor(private modalService: ModalService, private settingsService: SettingsService) {
  }

  descriptors$ = this.settingsService.descriptors$
  settings$ = this.settingsService.settings$

  ngOnInit(): void {
  }

  descriptorFor(setting: SettingsEntryDto, descriptors: SettingDescriptor[]): SettingDescriptor {
    return descriptors.find(desc => desc.nameSpace===setting.nameSpace && desc.key===setting.key)
  }

  // dialog

  settingsDialogSpec: SettingsDialogSpec

  add() {
    this.openSettingsDialog({
      mode: 'NEW',
      data: {
        descriptor: null,
        value: null
      },
      confirm: (data) => {
        this.confirmAdd(data)
        this.closeSettingsDialog()
      },
      cancel: () => this.closeSettingsDialog()
    })
  }

  edit(setting: SettingsEntryDto, descriptor: SettingDescriptor) {
    this.openSettingsDialog({
      mode: 'UPDATE',
      data: {
        descriptor: descriptor,
        value: setting.value
      },
      confirm: (data) => {
        this.confirmEdit(data, descriptor)
        this.closeSettingsDialog()
      },
      cancel: () => this.closeSettingsDialog()
    })

  }

  delete(setting: SettingsEntryDto) {
    this.settingsService.addOrUpdateSetting({ nameSpace: setting.nameSpace, key: setting.key, value: null})
  }

  settingsDialogId() {
    return 'settings-dialog'
  }

  private openSettingsDialog(spec: SettingsDialogSpec) {
    this.settingsDialogSpec = spec
    this.modalService.open(this.settingsDialogId())
  }

  private closeSettingsDialog() {
    this.modalService.close(this.settingsDialogId())
  }

  private confirmAdd(data: SettingsDialogData) {
    this.settingsService.addOrUpdateSetting({ nameSpace: data.descriptor.nameSpace, key: data.descriptor.key, value: data.value})
  }

  private confirmEdit(data: SettingsDialogData, descriptor: SettingDescriptor) {
    this.settingsService.addOrUpdateSetting({ nameSpace: descriptor.nameSpace, key: descriptor.key, value: data.value})
  }

}
