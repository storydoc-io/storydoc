/* tslint:disable */
/* eslint-disable */
import { FileSystem } from './file-system';
export interface Path {
  absolute?: boolean;
  fileName?: Path;
  fileSystem?: FileSystem;
  nameCount?: number;
  parent?: Path;
  root?: Path;
}
