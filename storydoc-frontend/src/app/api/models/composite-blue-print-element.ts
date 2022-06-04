/* tslint:disable */
/* eslint-disable */
import { BluePrintElement } from './blue-print-element';
import { Classification } from './classification';
import { Layout } from './layout';
export interface CompositeBluePrintElement extends BluePrintElement {
  classification?: Classification;
  layout?: Layout;
  name?: string;
  subElements?: Array<BluePrintElement>;
  type?: string;
}
