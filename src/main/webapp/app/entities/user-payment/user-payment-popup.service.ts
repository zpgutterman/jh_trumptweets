import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { User_payment } from './user-payment.model';
import { User_paymentService } from './user-payment.service';
@Injectable()
export class User_paymentPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private user_paymentService: User_paymentService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.user_paymentService.find(id).subscribe(user_payment => {
                this.user_paymentModalRef(component, user_payment);
            });
        } else {
            return this.user_paymentModalRef(component, new User_payment());
        }
    }

    user_paymentModalRef(component: Component, user_payment: User_payment): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.user_payment = user_payment;
        modalRef.result.then(result => {
            console.log(`Closed with: ${result}`);
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            console.log(`Dismissed ${reason}`);
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
