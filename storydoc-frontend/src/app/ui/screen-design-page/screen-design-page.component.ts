import {Component, OnDestroy, OnInit} from '@angular/core';
import {ScreenDesignService} from "./screen-design.service";
import {ActivatedRoute} from "@angular/router";
import {Subscription} from "rxjs";
import {LinkService} from "@storydoc/common";

@Component({
  selector: 'app-screen-design-page',
  templateUrl: './screen-design-page.component.html',
  styleUrls: ['./screen-design-page.component.scss']
})
export class ScreenDesignPageComponent implements OnInit, OnDestroy {

  constructor(
    public link: LinkService,
    private route: ActivatedRoute,
    private service: ScreenDesignService) { }

  screenDesign$ = this.service.screenDesign$

  private subscriptions: Subscription[] = []

  ngOnInit(): void {
    this.subscriptions.push(this.route.paramMap.subscribe((params) => {
      let documentId = params.get('documentId')
      let blockId = params.get('blockId')
      let id = params.get('artifactId')
      if (id) {
        this.service.setScreenDesign({
          blockCoordinate: {
            blockId: { id: blockId },
            storyDocId: { id: documentId }
          },
          screenDesignId: {id: id }
        });
      }
    }));

  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(s => s.unsubscribe())
  }


}
