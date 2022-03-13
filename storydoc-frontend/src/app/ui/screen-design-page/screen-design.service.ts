import {Injectable, OnDestroy} from '@angular/core';
import {ComponentDescriptorDto} from "../../api/models/component-descriptor-dto";
import {ScreenDesignRestControllerService} from "../../api/services/screen-design-rest-controller.service";
import {BehaviorSubject, Subscription} from "rxjs";
import {map} from "rxjs/operators";
import {ComponentAttributeDescriptorDto, ComponentAttributeDto, ScreenDesignCoordinate, ScreenDesignDto, SdComponentDto, SdContainerDto} from "@storydoc/models";
import {log, logChangesToObservable} from "@storydoc/common";

interface State {
  palette?: ComponentDescriptorDto[]
  coord?: ScreenDesignCoordinate
  screenDesign? : ScreenDesignDto
  selection? : SdComponentDto | 'NONE'
  selectedContainer? : SdContainerDto
}

@Injectable({
  providedIn: 'root'
})
export class ScreenDesignService implements  OnDestroy{

  constructor(private screenDesignRestControllerService: ScreenDesignRestControllerService) { this.init() }

  private store = new BehaviorSubject<State>({})

  palette$ = this.store.asObservable().pipe(
    map(state => state.palette)
  )

  screenDesign$ = this.store.asObservable().pipe(
    map(state => state.screenDesign)
  )

  selectedContainer$ = this.store.asObservable().pipe(
    map(state => state.selectedContainer)
  )

  selection$ = this.store.asObservable().pipe(
    map(state => state.selection)
  )

  get selectedContainer():SdContainerDto {
    return this.store.getValue().selectedContainer
  }

  get coord():ScreenDesignCoordinate {
    return this.store.getValue().coord
  }

  private subscriptions: Subscription[] = []

  private init() {
    log('init()')
    this.loadPalette()
    this.subscriptions.push(logChangesToObservable('store$ >>', this.store))

  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }

  public setScreenDesign(coord: ScreenDesignCoordinate) {
    this.store.next({
      ... this.store.getValue(),
      coord,
      screenDesign: null,
      selectedContainer: null,
      selection: 'NONE'
    })
    this.reload();
  }

  private reload() {
    if (!this.coord) return
    this.screenDesignRestControllerService.getScreenDesignUsingGet({
      storyDocId: this.coord.blockCoordinate.storyDocId.id,
      blockId: this.coord.blockCoordinate.blockId.id,
      screenDesignId: this.coord.screenDesignId.id
    }).subscribe(screenDesign => this.store.next({
      ...this.store.getValue(),
      screenDesign,
      selectedContainer: screenDesign.rootContainer
    }))
  }

  private loadPalette() {
    this.screenDesignRestControllerService.getComponentPaletteUsingGet({}).subscribe(palette => this.store.next({
      ... this.store.getValue(),
      palette
    }))
  }

  addComponent(componentDescriptor: ComponentDescriptorDto, { x, y} ) {
    this.screenDesignRestControllerService.createComponentUsingPost({
      storyDocId: this.coord.blockCoordinate.storyDocId.id,
      blockId: this.coord.blockCoordinate.blockId.id,
      screenDesignId: this.coord.screenDesignId.id,
      containerId: this.selectedContainer.id.id,
      type: componentDescriptor.type,
      x,
      y
    }).subscribe(()=> this.reload())
  }

  moveComponent(component: SdComponentDto, coord: { x: number; y: number }) {

  }


  selectComponent(component: SdComponentDto) {
    this.store.next({
      ... this.store.getValue(),
      selection: component
    })

  }

  setAttribute(component: SdComponentDto | "NONE", attribute: ComponentAttributeDescriptorDto, value: any) {
    if (component=="NONE") return
    let componentAttribute = component.attributes.find(att => att.name===attribute.name)
    if (!componentAttribute) {
      componentAttribute = <ComponentAttributeDto>{
        name: attribute.name,
        value: null
      }
      component.attributes.push(componentAttribute)
    }
    componentAttribute.value = value
    this.store.next({
      ... this.store.getValue(),
      screenDesign: {
        ... this.store.getValue().screenDesign
      }
    })
  }

}
