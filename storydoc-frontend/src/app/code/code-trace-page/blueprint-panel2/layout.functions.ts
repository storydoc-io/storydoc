
export interface Coord {
  x, y : number
}

export interface Dimensions {
  width, height: number
}

export interface Box {
  position?: Coord
  dimensions?: Dimensions
}

interface NumberPerSide {
  top?, bottom?, left?, right?, all?: number
}

export interface LayoutItem {
  layoutType?: 'HORIZONTAL' | 'VERTICAL'
  border? : NumberPerSide
  margin? : NumberPerSide
  children?: LayoutItem[]
  content?: Box
  boundingBox?: Box
}

function layoutSize(layoutItem: LayoutItem, maxSize?: Dimensions) {
  if (layoutItem.children) {
    layoutItem.children.forEach(child => layoutSize(child))
  }
  layoutItem.content = { ...layoutItem.content, dimensions : {
    height: maxSize.height - (layoutItem.margin?.top ? layoutItem.margin.top: 0) - (layoutItem.margin?.bottom ? layoutItem.margin.bottom: 0),
    width: maxSize.width - (layoutItem.margin?.left ? layoutItem.margin.left: 0) - (layoutItem.margin?.right ? layoutItem.margin.right: 0)
    } }
}

function layoutPosition(layoutItem: LayoutItem, position: Coord) {
  layoutItem.content = {...layoutItem.content, position: {
    x : position.x + (layoutItem.margin?.top ? layoutItem.margin.top : 0),
    y : position.y + (layoutItem.margin?.left ? layoutItem.margin.left : 0),
  }}
}

export function layout(layoutItem: LayoutItem, container: Box) {
  layoutSize(layoutItem, container.dimensions)
  layoutPosition(layoutItem, container.position)
}
