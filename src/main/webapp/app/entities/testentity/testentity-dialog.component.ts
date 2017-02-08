import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Testentity } from './testentity.model';
import { TestentityPopupService } from './testentity-popup.service';
import { TestentityService } from './testentity.service';
@Component({
    selector: 'jhi-testentity-dialog',
    templateUrl: './testentity-dialog.component.html'
})
export class TestentityDialogComponent implements OnInit {

    testentity: Testentity;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private testentityService: TestentityService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['testentity']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.testentity.id !== undefined) {
            this.testentityService.update(this.testentity)
                .subscribe((res: Testentity) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.testentityService.create(this.testentity)
                .subscribe((res: Testentity) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Testentity) {
        this.eventManager.broadcast({ name: 'testentityListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-testentity-popup',
    template: ''
})
export class TestentityPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private testentityPopupService: TestentityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.testentityPopupService
                    .open(TestentityDialogComponent, params['id']);
            } else {
                this.modalRef = this.testentityPopupService
                    .open(TestentityDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
