/* tslint:disable */
/* eslint-disable */
import { ArtifactDto } from './artifact-dto';
import { BlockId } from './block-id';
export interface BlockDto {
  artifacts?: Array<ArtifactDto>;
  blockId?: BlockId;
  blockType?: string;
  numbering?: Array<number>;
  parentBlockId?: BlockId;
  text?: string;
  title?: string;
}
