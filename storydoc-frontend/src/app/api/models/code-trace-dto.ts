/* tslint:disable */
/* eslint-disable */
import { CodeTraceItemDto } from './code-trace-item-dto';
import { StoryDocSummaryDto } from './story-doc-summary-dto';
export interface CodeTraceDto {
  description?: string;
  items?: Array<CodeTraceItemDto>;
  storyDocSummary?: StoryDocSummaryDto;
}
