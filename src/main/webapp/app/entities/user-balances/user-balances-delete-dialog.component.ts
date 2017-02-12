import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { User_balances } from './user-balances.model';
import { User_balancesPopupService } from './user-balances-popup.service';
import { User_balancesService } from './user-balances.service';

@Component({
    selector: 'jhi-user-balances-delete-dialog',
    templateUrl: './user-balances-delete-dialog.component.html'
})
export class User_balancesDeleteDialogComponent {

    user_balances: User_balances;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private user_balancesService: User_balancesService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['user_balances']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.user_balancesService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'user_balancesListModification',
                content: 'Deleted an user_balances'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-balances-delete-popup',
    template: ''
})
export class User_balancesDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private user_balancesPopupService: User_balancesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.user_balancesPopupService
                .open(User_balancesDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
