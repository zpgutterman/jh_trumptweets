import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { User_payment } from './user-payment.model';
import { User_paymentPopupService } from './user-payment-popup.service';
import { User_paymentService } from './user-payment.service';
import { User, UserService } from '../../shared';
@Component({
    selector: 'jhi-user-payment-dialog',
    templateUrl: './user-payment-dialog.component.html'
})
export class User_paymentDialogComponent implements OnInit {

    user_payment: User_payment;
    authorities: any[];
    isSaving: boolean;

    users: User[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private user_paymentService: User_paymentService,
        private userService: UserService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['user_payment']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.userService.query().subscribe(
            (res: Response) => { this.users = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.user_payment.id !== undefined) {
            this.user_paymentService.update(this.user_payment)
                .subscribe((res: User_payment) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.user_paymentService.create(this.user_payment)
                .subscribe((res: User_payment) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: User_payment) {
        this.eventManager.broadcast({ name: 'user_paymentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-user-payment-popup',
    template: ''
})
export class User_paymentPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private user_paymentPopupService: User_paymentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.user_paymentPopupService
                    .open(User_paymentDialogComponent, params['id']);
            } else {
                this.modalRef = this.user_paymentPopupService
                    .open(User_paymentDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
