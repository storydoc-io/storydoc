/* tslint:disable */
/* eslint-disable */
import { StoryDocSummaryDto } from './story-doc-summary-dto';
import { TimeLineDto } from './time-line-dto';
import { TimeLineModelId } from './time-line-model-id';
export interface TimeLineModelDto {
  name?: string;
  storyDocSummary?: StoryDocSummaryDto;
  timeLineModelId?: TimeLineModelId;
  timeLines?: { [key: string]: TimeLineDto };
}
