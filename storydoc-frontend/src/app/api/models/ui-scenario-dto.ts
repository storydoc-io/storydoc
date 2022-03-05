/* tslint:disable */
/* eslint-disable */
import { ScreenShotTimeLineItemDto } from './screen-shot-time-line-item-dto';
import { ScreenshotCollectionSummaryDto } from './screenshot-collection-summary-dto';
import { StoryDocSummaryDto } from './story-doc-summary-dto';
import { TimeLineModelCoordinate } from './time-line-model-coordinate';
import { UiScenarioId } from './ui-scenario-id';
export interface UiScenarioDto {
  associatedCollections?: Array<ScreenshotCollectionSummaryDto>;
  id?: UiScenarioId;
  name?: string;
  screenshots?: Array<ScreenShotTimeLineItemDto>;
  state?: 'CREATED' | 'READY';
  storyDocSummary?: StoryDocSummaryDto;
  timeLineModelCoordinate?: TimeLineModelCoordinate;
}
