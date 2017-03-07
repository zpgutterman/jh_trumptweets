import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { User_payment } from './user-payment.model';
import { User_paymentPopupService } from './user-payment-popup.service';
import { User_paymentService } from './user-payment.service';

@Component({
    selector: 'jhi-user-payment-delete-dialog',
    templateUrl: './user-payment-delete-dialog.component.html'
})
export class User_paymentDeleteDialogComponent {

    user_payment: User_payment;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private user_paymentService: User_paymentService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['user_payment']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.user_paymentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'user_paymentListModification',
                content: 'Deleted an user_payment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-payment-delete-popup',
    template: ''
})
export class User_paymentDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private user_paymentPopupService: User_paymentPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.user_paymentPopupService
                .open(User_paymentDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
