import { TestBed } from '@angular/core/testing';

import { DBDataService } from './dbdata.service';

describe('DBDataService', () => {
  let service: DBDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DBDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
