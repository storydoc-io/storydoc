/* tslint:disable */
/* eslint-disable */
import { ScreenShotDto } from './screen-shot-dto';
import { StoryDocSummaryDto } from './story-doc-summary-dto';
export interface ScreenShotCollectionDto {
  name?: string;
  screenShots?: Array<ScreenShotDto>;
  storyDocSummary?: StoryDocSummaryDto;
}
