/* tslint:disable */
/* eslint-disable */
import { TimeLineId } from './time-line-id';
import { TimeLineItemDto } from './time-line-item-dto';
export interface TimeLineDto {
  items?: Array<TimeLineItemDto>;
  name?: string;
  timeLineId?: TimeLineId;
}
