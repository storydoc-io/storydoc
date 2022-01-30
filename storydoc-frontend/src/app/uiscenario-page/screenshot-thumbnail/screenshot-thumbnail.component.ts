import {Component, Input, OnInit} from '@angular/core';
import {ScreenShotDto} from "../../api/models/screen-shot-dto";
import {ScreenshotCollectionCoordinate} from "../../api/models/screenshot-collection-coordinate";
import {LinkService} from "../../common/link.service";
import {ScreenshotCoordinate} from "../../api/models/screenshot-coordinate";

@Component({
  selector: 'app-screenshot-thumbnail',
  templateUrl: './screenshot-thumbnail.component.html',
  styleUrls: ['./screenshot-thumbnail.component.scss']
})
export class ScreenshotThumbnailComponent implements OnInit {

  constructor(private link: LinkService) {
  }

  @Input()
  screenshotCoordinate: ScreenshotCoordinate

  ngOnInit(): void {
  }

  getScreenshotUrl() {
    return this.link.getScreenshotUrl(this.screenshotCoordinate)
  }
}
