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
import { TweetlogDetailComponent } from '../../../../../../main/webapp/app/entities/tweetlog/tweetlog-detail.component';
import { TweetlogService } from '../../../../../../main/webapp/app/entities/tweetlog/tweetlog.service';
import { Tweetlog } from '../../../../../../main/webapp/app/entities/tweetlog/tweetlog.model';

describe('Component Tests', () => {

    describe('Tweetlog Management Detail Component', () => {
        let comp: TweetlogDetailComponent;
        let fixture: ComponentFixture<TweetlogDetailComponent>;
        let service: TweetlogService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [TweetlogDetailComponent],
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
                    TweetlogService
                ]
            }).overrideComponent(TweetlogDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TweetlogDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TweetlogService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new Tweetlog(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.tweetlog).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
