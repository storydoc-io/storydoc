import {Box, Coord, Dimensions, layout, LayoutItem} from './layout.functions';

describe('layout', () => {

  beforeEach(function() {jasmine.addCustomEqualityTester(boxEquals) })

  function boxEquals(box1: Box, box2: Box): boolean {
    return coordEquals(box1.position, box2.position) && dimensionsEquals(box1.dimensions, box2.dimensions)
  }

  function dimensionsEquals(d1: Dimensions, d2: Dimensions): boolean {
    return d1.height === d2.height && d1.width === d2.width
  }

  function coordEquals(c1: Coord, c2: Coord): boolean {
    return c1.x === c2.x && c1.y === c2.y
  }

  it('basic layout', () => {
    let layoutItem: LayoutItem = {};
    let container = {
      position: {x: 10, y: 20},
      dimensions: {width: 30, height: 40}
    };
    layout(layoutItem, container)
    expect(layoutItem.content).toEqual(
      {
        position: {x: 10, y: 20},
        dimensions: {width: 30, height: 40}
      })
  });

  it('with margin', () => {
    let layoutItem: LayoutItem = {
      margin: {
        bottom: 1,
        left: 2,
        right: 3,
        top: 4
      }
    };
    let container = {
      position: {x: 10, y: 20},
      dimensions: {width: 30, height: 40}
    };
    layout(layoutItem, container)
    expect(layoutItem.content).toEqual(
      {
        position: {x: 10+4, y: 20+2},
        dimensions: {width: 30-2-3, height: 40-1-4}
      }
    )
  });


});
