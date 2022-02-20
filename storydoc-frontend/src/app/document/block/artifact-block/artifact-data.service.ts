import {Injectable} from "@angular/core";

export interface ArtifactDescriptor {
  key: string
  icon: string
  label: string
  editorUrl: string
}

let timeLineModel = {
  key: 'io.storydoc.server.timeline.domain.TimeLineModel',
  icon: '/assets/timeline.png',
  label: 'Timeline',
  editorUrl: '/fe/timeline'

} as ArtifactDescriptor

let uiScenario = {
  key: 'io.storydoc.server.ui.domain.UIScenario',
  icon: '/assets/artifact-ui-scenario.png',
  label: 'UI Scenario',
  editorUrl: '/fe/ui-scenario'

} as ArtifactDescriptor

let uiScreenshotCollection = {
  key: 'io.storydoc.server.ui.domain.ScreenShotCollection',
  icon: '/assets/artifact-ui-design.png',
  label: 'UI Screenshot collection',
  editorUrl: '/fe/ui-screenshot-collection'
} as ArtifactDescriptor

let map: { [key: string]: ArtifactDescriptor } = {
  'io.storydoc.server.timeline.domain.TimeLineModel': timeLineModel,
  'io.storydoc.server.ui.domain.UIScenario': uiScenario,
  'io.storydoc.server.ui.domain.ScreenShotCollection': uiScreenshotCollection,
}

let list: ArtifactDescriptor[] = [
  timeLineModel,
  uiScenario,
  uiScreenshotCollection
]

@Injectable({
  providedIn: 'root'
})
export class ArtifactDataService {

  descriptor(artifactType: string): ArtifactDescriptor {
    return map[artifactType]
  }

  list(): ArtifactDescriptor[] {
    return list
  }

}
