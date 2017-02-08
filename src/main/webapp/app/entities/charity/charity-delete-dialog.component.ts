import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Charity } from './charity.model';
import { CharityPopupService } from './charity-popup.service';
import { CharityService } from './charity.service';

@Component({
    selector: 'jhi-charity-delete-dialog',
    templateUrl: './charity-delete-dialog.component.html'
})
export class CharityDeleteDialogComponent {

    charity: Charity;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private charityService: CharityService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['charity']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.charityService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'charityListModification',
                content: 'Deleted an charity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-charity-delete-popup',
    template: ''
})
export class CharityDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private charityPopupService: CharityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.charityPopupService
                .open(CharityDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
