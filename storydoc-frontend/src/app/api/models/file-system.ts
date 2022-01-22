/* tslint:disable */
/* eslint-disable */
import { IterableFileStore } from './iterable-file-store';
import { IterablePath } from './iterable-path';
import { UserPrincipalLookupService } from './user-principal-lookup-service';
export interface FileSystem {
  fileStores?: IterableFileStore;
  open?: boolean;
  readOnly?: boolean;
  rootDirectories?: IterablePath;
  separator?: string;
  userPrincipalLookupService?: UserPrincipalLookupService;
}
