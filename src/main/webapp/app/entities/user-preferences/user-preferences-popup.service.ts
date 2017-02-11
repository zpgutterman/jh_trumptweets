import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { User_preferences } from './user-preferences.model';
import { User_preferencesService } from './user-preferences.service';
@Injectable()
export class User_preferencesPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private user_preferencesService: User_preferencesService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.user_preferencesService.find(id).subscribe(user_preferences => {
                this.user_preferencesModalRef(component, user_preferences);
            });
        } else {
            return this.user_preferencesModalRef(component, new User_preferences());
        }
    }

    user_preferencesModalRef(component: Component, user_preferences: User_preferences): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.user_preferences = user_preferences;
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
