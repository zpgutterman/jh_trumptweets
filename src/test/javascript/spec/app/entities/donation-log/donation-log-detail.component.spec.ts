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
import { Donation_logDetailComponent } from '../../../../../../main/webapp/app/entities/donation-log/donation-log-detail.component';
import { Donation_logService } from '../../../../../../main/webapp/app/entities/donation-log/donation-log.service';
import { Donation_log } from '../../../../../../main/webapp/app/entities/donation-log/donation-log.model';

describe('Component Tests', () => {

    describe('Donation_log Management Detail Component', () => {
        let comp: Donation_logDetailComponent;
        let fixture: ComponentFixture<Donation_logDetailComponent>;
        let service: Donation_logService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [Donation_logDetailComponent],
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
                    Donation_logService
                ]
            }).overrideComponent(Donation_logDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(Donation_logDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Donation_logService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new Donation_log(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.donation_log).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
