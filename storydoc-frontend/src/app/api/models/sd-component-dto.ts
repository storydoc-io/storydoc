/* tslint:disable */
/* eslint-disable */
import { ComponentAttributeDto } from './component-attribute-dto';
import { SdComponentId } from './sd-component-id';
export interface SdComponentDto {
  attributes?: Array<ComponentAttributeDto>;
  id?: SdComponentId;
  label?: string;
  name?: string;
  type?: string;
  'x'?: number;
  'y'?: number;
}
