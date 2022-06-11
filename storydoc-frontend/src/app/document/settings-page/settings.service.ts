import { Injectable } from '@angular/core';
import {SettingsControllerService} from "@storydoc/services";
import {SettingsEntryDto} from "@storydoc/models";
import {BehaviorSubject} from "rxjs";
import {distinctUntilChanged, map} from "rxjs/operators";



export interface SettingDescriptor {
  label: string,
  nameSpace: string,
  key: string,
  valueType: 'PATH'
}

interface SettingsStoreState {
  settings: SettingsEntryDto[]
  descriptors: SettingDescriptor[]
}

@Injectable({
  providedIn: 'root'
})
export class SettingsService {

  constructor(private settingsControllerService: SettingsControllerService) { this.init() }

  private settingsStore = new BehaviorSubject<SettingsStoreState>({ settings: null, descriptors: null})

  settings$ = this.settingsStore.pipe(
    map(state => state.settings),
    distinctUntilChanged(),
  )

  descriptors$ = this.settingsStore.pipe(
    map(state => state.descriptors),
    distinctUntilChanged(),
  )

  init() {
    this.loadSettings()
    this.loadSettingDescriptors()
  }

  loadSettings() {
    this.settingsControllerService.getGlobalSettingsUsingGet({}).subscribe(settings =>
      this.settingsStore.next({
        ... this.settingsStore.value,
        settings: settings.entries
      })
    )
  }

  private loadSettingDescriptors() {
    this.settingsStore.next({
      ... this.settingsStore.value,
      descriptors: [
        {
          label: 'Java sourcecode location',
          nameSpace: 'io.storydoc.code',
          key: 'java.source.dir',
          valueType: 'PATH'
        },
        {
          label: 'Stitch output',
          nameSpace: 'io.storydoc.code',
          key: 'stitch.dir',
          valueType: 'PATH'
        }
      ]
    })
  }

  addOrUpdateSetting(param: {nameSpace: string, key: string, value: string}) {
      this.settingsControllerService.setGlobalSettingUsingPost({body: param}).subscribe(() => this.loadSettings())
  }

}
