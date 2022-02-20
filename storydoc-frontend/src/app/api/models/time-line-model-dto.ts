/* tslint:disable */
/* eslint-disable */
import { StoryDocSummaryDto } from './story-doc-summary-dto';
import { TimeLineDto } from './time-line-dto';
import { TimeLineModelCoordinate } from './time-line-model-coordinate';
export interface TimeLineModelDto {
  name?: string;
  storyDocSummary?: StoryDocSummaryDto;
  timeLineModelCoordinate?: TimeLineModelCoordinate;
  timeLines?: { [key: string]: TimeLineDto };
}
