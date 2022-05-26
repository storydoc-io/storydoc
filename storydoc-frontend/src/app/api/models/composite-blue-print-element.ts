/* tslint:disable */
/* eslint-disable */
import { BluePrintElement } from './blue-print-element';
import { Layout } from './layout';
export interface CompositeBluePrintElement extends BluePrintElement {
  layout?: Layout;
  subElements?: Array<BluePrintElement>;
  type?: string;
}
