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
import { Tweet_logDetailComponent } from '../../../../../../main/webapp/app/entities/tweet-log/tweet-log-detail.component';
import { Tweet_logService } from '../../../../../../main/webapp/app/entities/tweet-log/tweet-log.service';
import { Tweet_log } from '../../../../../../main/webapp/app/entities/tweet-log/tweet-log.model';

describe('Component Tests', () => {

    describe('Tweet_log Management Detail Component', () => {
        let comp: Tweet_logDetailComponent;
        let fixture: ComponentFixture<Tweet_logDetailComponent>;
        let service: Tweet_logService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [Tweet_logDetailComponent],
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
                    Tweet_logService
                ]
            }).overrideComponent(Tweet_logDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(Tweet_logDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Tweet_logService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new Tweet_log(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.tweet_log).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
