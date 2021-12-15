import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MockBlockComponent } from './mock-block.component';

describe('MockBlockComponent', () => {
  let component: MockBlockComponent;
  let fixture: ComponentFixture<MockBlockComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MockBlockComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MockBlockComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
