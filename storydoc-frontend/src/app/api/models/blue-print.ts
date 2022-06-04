/* tslint:disable */
/* eslint-disable */
import { BluePrintElement } from './blue-print-element';
import { Classification } from './classification';
import { Layout } from './layout';
export interface BluePrint extends BluePrintElement {
  classification?: Classification;
  elements?: Array<BluePrintElement>;
  layout?: Layout;
  name?: string;
  subElements?: Array<BluePrintElement>;
  type?: string;
}
