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

let uiScreenDesign = {
  key: 'io.storydoc.server.ui.domain.screendesign.ScreenDesign',
  icon: '/assets/artifact-ui-design.png',
  label: 'Screen design',
  editorUrl: '/fe/ui-screen-design'

} as ArtifactDescriptor

let codeExecution = {
  key: 'io.storydoc.server.code.domain.CodeExecution',
  icon: '/assets/test-script.png',
  label: 'Code Execution',
  editorUrl: '/fe/code-trace'
} as ArtifactDescriptor

let sourceCodeConfig = {
  key: 'io.storydoc.server.code.domain.SourceCodeConfig',
  icon: '/assets/config.png',
  label: 'SourceCode Config',
  editorUrl: '/fe/source-code-config'
} as ArtifactDescriptor

let stitchConfig = {
  key: 'io.storydoc.server.code.domain.StitchConfig',
  icon: '/assets/config.png',
  label: 'Stitch Config',
  editorUrl: '/fe/stitch-config'
} as ArtifactDescriptor

let map: { [key: string]: ArtifactDescriptor } = {
  'io.storydoc.server.timeline.domain.TimeLineModel': timeLineModel,
  'io.storydoc.server.ui.domain.screendesign.ScreenDesign': uiScreenDesign,
  'io.storydoc.server.ui.domain.UIScenario': uiScenario,
  'io.storydoc.server.ui.domain.ScreenShotCollection': uiScreenshotCollection,
  'io.storydoc.server.code.domain.CodeExecution': codeExecution,
  'io.storydoc.server.code.domain.SourceCodeConfig': sourceCodeConfig,
  'io.storydoc.server.code.domain.StitchConfig': stitchConfig
}

let list: ArtifactDescriptor[] = [
  timeLineModel,
  uiScreenDesign,
  uiScenario,
  uiScreenshotCollection,
  codeExecution,
  sourceCodeConfig,
  stitchConfig
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
