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
import { User_balancesDetailComponent } from '../../../../../../main/webapp/app/entities/user-balances/user-balances-detail.component';
import { User_balancesService } from '../../../../../../main/webapp/app/entities/user-balances/user-balances.service';
import { User_balances } from '../../../../../../main/webapp/app/entities/user-balances/user-balances.model';

describe('Component Tests', () => {

    describe('User_balances Management Detail Component', () => {
        let comp: User_balancesDetailComponent;
        let fixture: ComponentFixture<User_balancesDetailComponent>;
        let service: User_balancesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [User_balancesDetailComponent],
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
                    User_balancesService
                ]
            }).overrideComponent(User_balancesDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(User_balancesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(User_balancesService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new User_balances(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.user_balances).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
