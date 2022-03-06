import {UIScenarioService} from './uiscenario.service';
import {Observable, of} from "rxjs";
import {TimeLineModelCoordinate, TimeLineModelDto, TimeLineModelSummaryDto, UiScenarioDto} from "@storydoc/models";
import {TimeLineControllerService, UiRestControllerService} from "@storydoc/services";

class UiRestControllerServiceStub extends UiRestControllerService {
  constructor() {
    super(null, null);
  }
}

class TimeLineControllerServiceStub extends TimeLineControllerService {
  constructor() {
    super(null, null);
  }
}

function getTimeLineModelCoordinate(): TimeLineModelCoordinate {
  return {timeLineModelId: {id: 'timeLine-model-id' }, blockCoordinate: {blockId: {id: 'block-id'}, storyDocId: {id: 'storydoc-id'}}}
}

describe('UIScenarioService', () => {

  let uiRestControllerService: UiRestControllerService
  let timeLineControllerService: TimeLineControllerService
  let service: UIScenarioService

  beforeEach(() => {
    uiRestControllerService = new UiRestControllerServiceStub()
    timeLineControllerService = new TimeLineControllerServiceStub()
    service = new UIScenarioService(uiRestControllerService, timeLineControllerService);
  });


  it('loading scenario without a timeline model should return the scenario but no selected timemodel or timeline', () => {
    // given a scenario
    let scenario: UiScenarioDto = {}
    let scenario$: Observable<UiScenarioDto> = of(scenario)
    let spy1 = spyOn(uiRestControllerService, 'getUiScenarioUsingGet').and.returnValue(scenario$)

    // given a list of timeline model
    let summaries: TimeLineModelSummaryDto[] = []
    let summaries$: Observable<TimeLineModelSummaryDto[]> = of(summaries)
    let spy2 = spyOn(timeLineControllerService, 'getTimeLineModelSummariesUsingGet').and.returnValue(summaries$)

    // when I load a scenario
    service.loadUIScenario({
      storyDocId: {id: 'storydoc-id'},
      blockId: {id: 'block-id'},
      uiScenarioId: 'scenario-id'
    })

    // then the scenario should be loaded
    service.uiScenario$.subscribe((scenario) => {
      expect(scenario).toBeTruthy('uiScenario$')
    })
    // and the model selection should have a list of models but not have a selected model
    service.timeLineModelSelection$.subscribe((selection) => {
      expect(selection).toBeTruthy('timeLineModelSelection$')
      expect(selection.models).toBeTruthy('timeLineModelSelection$.models')
      expect(selection.selectedCoord).toBeFalsy('timeLineModelSelection$.selectedCoord')
    })
    // and the timeline selection should be empty
    service.timeLineSelection$.subscribe((selection) => {
      expect(selection).toBeFalsy('timeLineSelection$')
    })

  })

  it('loading scenario without a timeline model ' +
    'and changing the timeline model ' +
    'should change the model selection', () => {
    // given a scenario
    let timeLineModelCoordinate = getTimeLineModelCoordinate()
    let scenario: UiScenarioDto = {
      name: 'ui scenario',
      id: {id: 'scenario-id'},
    }
    let scenario$: Observable<UiScenarioDto> = of(scenario)
    let spy1 = spyOn(uiRestControllerService, 'getUiScenarioUsingGet').and.returnValue(scenario$)

    // given a list of timeline model
    let summaries: TimeLineModelSummaryDto[] = []
    let summaries$: Observable<TimeLineModelSummaryDto[]> = of(summaries)
    let spy2 = spyOn(timeLineControllerService, 'getTimeLineModelSummariesUsingGet').and.returnValue(summaries$)

    // given a timeline model
    let timeLineModel: TimeLineModelDto = {
      timeLineModelCoordinate: timeLineModelCoordinate
    }
    let spy3 = spyOn(timeLineControllerService, 'getTimeLineModelUsingGet').and.returnValue(of(timeLineModel))

    // given I load the scenario
    service.loadUIScenario({
      storyDocId: {id: 'storydoc-id'},
      blockId: {id: 'block-id'},
      uiScenarioId: 'scenario-id'
    })

    // when I select a model
    service.selectTimeLineModel(timeLineModelCoordinate)

    // the model in modelselection is selected
    service.timeLineModelSelection$.subscribe((selection) => {
      expect(selection).toBeTruthy('timeLineModelSelection$')
      expect(selection.models).toBeTruthy('timeLineModelSelection$.models')
      expect(selection.selectedCoord.timeLineModelId.id).toBe(timeLineModelCoordinate.timeLineModelId.id)
    })

    // and the timeline selection is changed to the timelines of the selected model
    service.timeLineSelection$.subscribe((selection) => {
      expect(selection).toBeTruthy('timeLineSelection$')
      expect(selection.timeLineModel).toBeTruthy('timeLineSelection$timeLineModel')
      expect(selection.timeLineModel.timeLineModelCoordinate.timeLineModelId.id).toBe(timeLineModelCoordinate.timeLineModelId.id, 'timeLineSelection$timeLineModel')
    })

  })

  it('loading scenario without a timeline model ' +
    'and setting the timeline model and timeline ' +
    'should change the model and timeline selection', () => {

    // given a scenario
    let timeLineModelCoordinate = getTimeLineModelCoordinate()
    let scenario: UiScenarioDto = {
      name: 'ui scenario',
      id: {id: 'scenario-id'},
    }

    // given a list of timeline model
    let summaries: TimeLineModelSummaryDto[] = []

    // given a timeline model
    let defaultTimeLineId = {id: 'default-timeline-id'}
    let timeLineModel: TimeLineModelDto = {
      timeLineModelCoordinate: timeLineModelCoordinate,
      timeLines: {
        'default': { timeLineId : defaultTimeLineId, name: 'default', items: []}
      }
    }

    let scenarioAfter: UiScenarioDto = {
      name: 'ui scenario',
      id: {id: 'scenario-id'},
      timeLineModelCoordinate: timeLineModelCoordinate,
      timeLineId: defaultTimeLineId,
    }

    let call1 = spyOn(uiRestControllerService, 'getUiScenarioUsingGet').and.returnValue(of(scenario, scenarioAfter))
    let call2 = spyOn(timeLineControllerService, 'getTimeLineModelSummariesUsingGet').and.returnValue(of(summaries))
    let call3 = spyOn(timeLineControllerService, 'getTimeLineModelUsingGet').and.returnValue(of(timeLineModel))
    let call4 = spyOn(uiRestControllerService, 'setUiScenarioTimeLineUsingPost').and.returnValue(of(null))

    // given I load the scenario
    service.loadUIScenario({
      storyDocId: {id: 'storydoc-id'},
      blockId: {id: 'block-id'},
      uiScenarioId: 'scenario-id'
    })

    // when I set the timeline
    service.setScenarioTimeLine({timeLineModelCoordinate, timeLineId: defaultTimeLineId})

    // the model in modelselection is selected
    service.timeLineModelSelection$.subscribe((selection) => {
      expect(selection).toBeTruthy('timeLineModelSelection$')
      expect(selection.models).toBeTruthy('timeLineModelSelection$.models')
      expect(selection.selectedCoord.timeLineModelId.id).toBe(timeLineModelCoordinate.timeLineModelId.id)
    })

    // and the timeline selection is changed to the timelines of the selected model
    service.timeLineSelection$.subscribe((selection) => {
      expect(selection).toBeTruthy('timeLineSelection$')
      expect(selection.timeLineModel).toBeTruthy('timeLineSelection$timeLineModel')
      expect(selection.timeLineModel.timeLineModelCoordinate.timeLineModelId.id).toBe(timeLineModelCoordinate.timeLineModelId.id, 'timeLineSelection$timeLineModel')
    })

  })


  it('loading scenario with a timeline model ' +
    'should return the scenario ' +
    'and the selections for the timeline model and timeline', () => {

    // given a scenario
    let timeLineModelCoordinate = {timeLineModelId: {id: 'timeLine-model-id' }, blockCoordinate: {blockId: {id: 'block-id'}, storyDocId: {id: 'storydoc-id'}}};
    let scenario: UiScenarioDto = {
      name: 'ui scenario',
      id: {id: 'scenario-id'},
      timeLineModelCoordinate: timeLineModelCoordinate,
      timeLineId: {id: 'timeline-id'}
    }
    let scenario$: Observable<UiScenarioDto> = of(scenario)
    let spy1 = spyOn(uiRestControllerService, 'getUiScenarioUsingGet').and.returnValue(scenario$)

    // given a list of timeline model
    let summaries: TimeLineModelSummaryDto[] = []
    let summaries$: Observable<TimeLineModelSummaryDto[]> = of(summaries)
    let spy2 = spyOn(timeLineControllerService, 'getTimeLineModelSummariesUsingGet').and.returnValue(summaries$)

    // given a timeline model
    let timeLineModel: TimeLineModelDto = {
      timeLineModelCoordinate: timeLineModelCoordinate
    }
    let spy3 = spyOn(timeLineControllerService, 'getTimeLineModelUsingGet').and.returnValue(of(timeLineModel))

    // when I load the scenario
    service.loadUIScenario({
      storyDocId: {id: 'storydoc-id'},
      blockId: {id: 'block-id'},
      uiScenarioId: 'scenario-id'
    })

    // then the timeline model selection should have a selected model
    service.timeLineModelSelection$.subscribe((selection) => {
      expect(selection).toBeTruthy('timeLineModelSelection$')
      expect(selection.models).toBeTruthy('timeLineModelSelection$.models')
      expect(selection.selectedCoord).toBeTruthy('timeLineModelSelection$.selectedCoord')
    })
    // and the timeline selection should have the selected timeline model details and the selected timeline
    service.timeLineSelection$.subscribe((selection) => {
      expect(selection).toBeTruthy('timeLineSelection$')
      expect(selection.timeLineModel).toBeTruthy('timeLineSelection$timeLineModel')
      expect(selection.timeLineId).toBeTruthy('timeLineSelection$timeLineId')
    })

  })

})
