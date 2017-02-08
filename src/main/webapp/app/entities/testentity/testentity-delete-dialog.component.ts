import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Testentity } from './testentity.model';
import { TestentityPopupService } from './testentity-popup.service';
import { TestentityService } from './testentity.service';

@Component({
    selector: 'jhi-testentity-delete-dialog',
    templateUrl: './testentity-delete-dialog.component.html'
})
export class TestentityDeleteDialogComponent {

    testentity: Testentity;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private testentityService: TestentityService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['testentity']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.testentityService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'testentityListModification',
                content: 'Deleted an testentity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-testentity-delete-popup',
    template: ''
})
export class TestentityDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private testentityPopupService: TestentityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.testentityPopupService
                .open(TestentityDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
