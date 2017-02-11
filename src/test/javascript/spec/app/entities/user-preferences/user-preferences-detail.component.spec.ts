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
import { User_preferencesDetailComponent } from '../../../../../../main/webapp/app/entities/user-preferences/user-preferences-detail.component';
import { User_preferencesService } from '../../../../../../main/webapp/app/entities/user-preferences/user-preferences.service';
import { User_preferences } from '../../../../../../main/webapp/app/entities/user-preferences/user-preferences.model';

describe('Component Tests', () => {

    describe('User_preferences Management Detail Component', () => {
        let comp: User_preferencesDetailComponent;
        let fixture: ComponentFixture<User_preferencesDetailComponent>;
        let service: User_preferencesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [User_preferencesDetailComponent],
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
                    User_preferencesService
                ]
            }).overrideComponent(User_preferencesDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(User_preferencesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(User_preferencesService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new User_preferences(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.user_preferences).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
