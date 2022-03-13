/* tslint:disable */
/* eslint-disable */
import { SdComponentId } from './sd-component-id';
import { SdComponentTypeSelectionDto } from './sd-component-type-selection-dto';
export interface SdContainerDto {
  children?: Array<SdComponentTypeSelectionDto>;
  id?: SdComponentId;
  label?: string;
}
