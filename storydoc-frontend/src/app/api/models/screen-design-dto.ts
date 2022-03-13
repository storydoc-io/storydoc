/* tslint:disable */
/* eslint-disable */
import { SdContainerDto } from './sd-container-dto';
import { ScreenDesignCoordinate } from './screen-design-coordinate';
import { StoryDocSummaryDto } from './story-doc-summary-dto';
export interface ScreenDesignDto {
  coordinate?: ScreenDesignCoordinate;
  name?: string;
  rootContainer?: SdContainerDto;
  storyDocSummaryDTO?: StoryDocSummaryDto;
}
