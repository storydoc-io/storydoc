/* tslint:disable */
/* eslint-disable */
import { SourceCodeConfigId } from './source-code-config-id';
import { StoryDocSummaryDto } from './story-doc-summary-dto';
export interface SourceCodeConfigDto {
  dirs?: Array<string>;
  id?: SourceCodeConfigId;
  name?: string;
  storyDocSummary?: StoryDocSummaryDto;
}
