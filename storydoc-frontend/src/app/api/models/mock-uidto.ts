/* tslint:disable */
/* eslint-disable */
import { MockUiId } from './mock-ui-id';
import { ScreenShotCollectionId } from './screen-shot-collection-id';
import { ScreenShotDto } from './screen-shot-dto';
export interface MockUidto {
  associatedCollections?: Array<ScreenShotCollectionId>;
  id?: MockUiId;
  name?: string;
  screenshots?: Array<ScreenShotDto>;
}
