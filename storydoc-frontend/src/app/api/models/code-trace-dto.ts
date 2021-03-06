/* tslint:disable */
/* eslint-disable */
import { SourceCodeConfigCoordinate } from './source-code-config-coordinate';
import { StitchConfigCoordinate } from './stitch-config-coordinate';
import { StitchItemDto } from './stitch-item-dto';
import { StoryDocSummaryDto } from './story-doc-summary-dto';
export interface CodeTraceDto {
  config?: SourceCodeConfigCoordinate;
  description?: string;
  items?: Array<StitchItemDto>;
  name?: string;
  stitchConfigCoordinate?: StitchConfigCoordinate;
  storyDocSummary?: StoryDocSummaryDto;
}
