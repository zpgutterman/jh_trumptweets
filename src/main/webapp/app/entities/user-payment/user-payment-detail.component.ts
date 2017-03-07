import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { User_payment } from './user-payment.model';
import { User_paymentService } from './user-payment.service';

@Component({
    selector: 'jhi-user-payment-detail',
    templateUrl: './user-payment-detail.component.html'
})
export class User_paymentDetailComponent implements OnInit, OnDestroy {

    user_payment: User_payment;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private user_paymentService: User_paymentService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['user_payment']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.user_paymentService.find(id).subscribe(user_payment => {
            this.user_payment = user_payment;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
