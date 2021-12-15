/* tslint:disable */
/* eslint-disable */
import { BlockDto } from './block-dto';
import { StoryDocId } from './story-doc-id';
export interface StoryDocDto {
  blocks?: Array<BlockDto>;
  storyDocId?: StoryDocId;
  title?: string;
}
