/* tslint:disable */
/* eslint-disable */
import { StitchConfigCoordinate } from './stitch-config-coordinate';
import { StoryDocSummaryDto } from './story-doc-summary-dto';
export interface StitchConfigDto {
  dir?: string;
  name?: string;
  stitchConfigCoordinate?: StitchConfigCoordinate;
  storyDocSummary?: StoryDocSummaryDto;
}
