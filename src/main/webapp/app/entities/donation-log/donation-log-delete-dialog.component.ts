import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Donation_log } from './donation-log.model';
import { Donation_logPopupService } from './donation-log-popup.service';
import { Donation_logService } from './donation-log.service';

@Component({
    selector: 'jhi-donation-log-delete-dialog',
    templateUrl: './donation-log-delete-dialog.component.html'
})
export class Donation_logDeleteDialogComponent {

    donation_log: Donation_log;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private donation_logService: Donation_logService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['donation_log']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.donation_logService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'donation_logListModification',
                content: 'Deleted an donation_log'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-donation-log-delete-popup',
    template: ''
})
export class Donation_logDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private donation_logPopupService: Donation_logPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.donation_logPopupService
                .open(Donation_logDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
