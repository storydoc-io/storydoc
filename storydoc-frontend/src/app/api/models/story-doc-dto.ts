/* tslint:disable */
/* eslint-disable */
import { BlockDto } from './block-dto';
import { ResourceUrn } from './resource-urn';
import { StoryDocId } from './story-doc-id';
export interface StoryDocDto {
  blocks?: Array<BlockDto>;
  storyDocId?: StoryDocId;
  title?: string;
  urn?: ResourceUrn;
}
