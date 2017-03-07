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
import { User_paymentDetailComponent } from '../../../../../../main/webapp/app/entities/user-payment/user-payment-detail.component';
import { User_paymentService } from '../../../../../../main/webapp/app/entities/user-payment/user-payment.service';
import { User_payment } from '../../../../../../main/webapp/app/entities/user-payment/user-payment.model';

describe('Component Tests', () => {

    describe('User_payment Management Detail Component', () => {
        let comp: User_paymentDetailComponent;
        let fixture: ComponentFixture<User_paymentDetailComponent>;
        let service: User_paymentService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                declarations: [User_paymentDetailComponent],
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
                    User_paymentService
                ]
            }).overrideComponent(User_paymentDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(User_paymentDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(User_paymentService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN
            spyOn(service, 'find').and.returnValue(Observable.of(new User_payment(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.user_payment).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
