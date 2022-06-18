/* tslint:disable */
/* eslint-disable */
import { StitchConfigId } from './stitch-config-id';
import { StoryDocSummaryDto } from './story-doc-summary-dto';
export interface StitchConfigDto {
  dir?: string;
  id?: StitchConfigId;
  name?: string;
  storyDocSummary?: StoryDocSummaryDto;
}
