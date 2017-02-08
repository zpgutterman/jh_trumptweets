import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { MockBackend } from '@angular/http/testing';
import { Http, BaseRequestOptions } from '@angular/http';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils } from 'ng-jhipster';
import { JhiLanguageService } from 'ng-jhipster';
import { MockLanguageService } from '../../../helpers/mock-language.service';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CharityDetailComponent } from '../../../../../../main/webapp/app/entities/charity/charity-detail.component';
import { CharityService } from '../../../../../../main/webapp/app/entities/charity/charity.service';
import { Charity } from '../../../../../../main/webapp/app/entities/charity/charity.model';

describe('Component Tests', () => {

    describe('Charity Management Detail Component', () => {
        let comp: CharityDetailComponent;
        let fixture: ComponentFixture<CharityDetailComponent>;
        let service: CharityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [CharityDetailComponent],
                providers: [
                    MockBackend,
                    BaseRequestOptions,
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    {
                        provide: Http,
                        useFactory: (backendInstance: MockBackend, defaultOptions: BaseRequestOptions) => {
                            return new Http(backendInstance, defaultOptions);
                        },
                        deps: [MockBackend, BaseRequestOptions]
                    },
                    {
                        provide: JhiLanguageService,
                        useClass: MockLanguageService
                    },
                    CharityService
                ]
            }).overrideComponent(CharityDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CharityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CharityService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new Charity(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.charity).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
