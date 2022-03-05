/* tslint:disable */
/* eslint-disable */
import { ScreenShotDto } from './screen-shot-dto';
import { ScreenshotCollectionCoordinate } from './screenshot-collection-coordinate';
import { StoryDocSummaryDto } from './story-doc-summary-dto';
export interface ScreenShotCollectionDto {
  coordinate?: ScreenshotCollectionCoordinate;
  name?: string;
  screenShots?: Array<ScreenShotDto>;
  storyDocSummary?: StoryDocSummaryDto;
}
