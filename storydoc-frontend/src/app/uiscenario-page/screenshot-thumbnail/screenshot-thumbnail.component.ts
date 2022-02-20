import {ChangeDetectionStrategy, Component, Input, OnInit} from '@angular/core';
import {LinkService} from "@storydoc/common";
import {ScreenshotCoordinate} from "@storydoc/models";

@Component({
  selector: 'app-screenshot-thumbnail',
  templateUrl: './screenshot-thumbnail.component.html',
  styleUrls: ['./screenshot-thumbnail.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ScreenshotThumbnailComponent implements OnInit {

  constructor(private link: LinkService) {
  }

  @Input()
  selected: boolean= false

  @Input()
  thumbnail: boolean = true

  @Input()
  screenshotCoordinate: ScreenshotCoordinate

  get containerClass():string {
    return this.thumbnail? 'thumbnail-container' : 'full-width-container'
  }

  get imageClass():string {
    return this.thumbnail? 'thumbnail-image' : 'full-width-image'
  }

  ngOnInit(): void {
  }

  getScreenshotUrl() {
    return this.link.getScreenshotUrl(this.screenshotCoordinate)
  }
}
