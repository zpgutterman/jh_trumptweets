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
import { TestentityDetailComponent } from '../../../../../../main/webapp/app/entities/testentity/testentity-detail.component';
import { TestentityService } from '../../../../../../main/webapp/app/entities/testentity/testentity.service';
import { Testentity } from '../../../../../../main/webapp/app/entities/testentity/testentity.model';

describe('Component Tests', () => {

    describe('Testentity Management Detail Component', () => {
        let comp: TestentityDetailComponent;
        let fixture: ComponentFixture<TestentityDetailComponent>;
        let service: TestentityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [TestentityDetailComponent],
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
                    TestentityService
                ]
            }).overrideComponent(TestentityDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TestentityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TestentityService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new Testentity(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.testentity).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
