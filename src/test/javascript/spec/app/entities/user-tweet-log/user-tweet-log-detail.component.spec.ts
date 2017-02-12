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
import { User_tweet_logDetailComponent } from '../../../../../../main/webapp/app/entities/user-tweet-log/user-tweet-log-detail.component';
import { User_tweet_logService } from '../../../../../../main/webapp/app/entities/user-tweet-log/user-tweet-log.service';
import { User_tweet_log } from '../../../../../../main/webapp/app/entities/user-tweet-log/user-tweet-log.model';

describe('Component Tests', () => {

    describe('User_tweet_log Management Detail Component', () => {
        let comp: User_tweet_logDetailComponent;
        let fixture: ComponentFixture<User_tweet_logDetailComponent>;
        let service: User_tweet_logService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [User_tweet_logDetailComponent],
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
                    User_tweet_logService
                ]
            }).overrideComponent(User_tweet_logDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(User_tweet_logDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(User_tweet_logService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new User_tweet_log(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.user_tweet_log).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
