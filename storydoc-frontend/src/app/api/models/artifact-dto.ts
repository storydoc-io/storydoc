/* tslint:disable */
/* eslint-disable */
import { ArtifactId } from './artifact-id';
import { ItemDto } from './item-dto';
import { ResourceUrn } from './resource-urn';
export interface ArtifactDto {
  artifactId?: ArtifactId;
  artifactType?: string;
  binary?: boolean;
  binaryType?: string;
  collection?: boolean;
  items?: Array<ItemDto>;
  name?: string;
  state?: 'CREATED' | 'READY';
  urn?: ResourceUrn;
}
