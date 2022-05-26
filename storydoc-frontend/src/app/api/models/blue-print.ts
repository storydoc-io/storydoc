/* tslint:disable */
/* eslint-disable */
import { BluePrintElement } from './blue-print-element';
import { Layout } from './layout';
export interface BluePrint extends BluePrintElement {
  elements?: Array<BluePrintElement>;
  layout?: Layout;
  name?: string;
  subElements?: Array<BluePrintElement>;
  type?: string;
}
