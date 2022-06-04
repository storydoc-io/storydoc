/* tslint:disable */
/* eslint-disable */
import { BluePrintElement } from './blue-print-element';
import { Classification } from './classification';
export interface Role extends BluePrintElement {
  classification?: Classification;
  name?: string;
  type?: string;
}
