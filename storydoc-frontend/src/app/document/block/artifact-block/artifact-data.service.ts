import {Injectable} from "@angular/core";

export interface ArtifactDescriptor {
  key: string
  icon: string
  label: string
  editorUrl: string
}

let uiScenario = {
  key : 'io.storydoc.server.ui.domain.MockUI',
  icon: '/assets/artifact-ui-scenario.png',
  label: 'UI Scenario',
  editorUrl: '/ui-scenario'

} as ArtifactDescriptor

let uiScreenshotCollection = {
  key : 'io.storydoc.server.ui.domain.ScreenShotCollection',
  icon: '/assets/artifact-ui-design.png',
  label: 'UI Screenshot collection',
  editorUrl: '/ui-screenshot-collection'
} as ArtifactDescriptor

let map : {[key: string]: ArtifactDescriptor} = {
  'io.storydoc.server.ui.domain.MockUI' : uiScenario,
  'io.storydoc.server.ui.domain.ScreenShotCollection' : uiScreenshotCollection,
}

let list: ArtifactDescriptor[] = [
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

  list() : ArtifactDescriptor[] {
    return list
  }

}
