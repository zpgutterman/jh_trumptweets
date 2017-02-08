import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Charity } from './charity.model';
import { CharityPopupService } from './charity-popup.service';
import { CharityService } from './charity.service';
import { Category, CategoryService } from '../category';
@Component({
    selector: 'jhi-charity-dialog',
    templateUrl: './charity-dialog.component.html'
})
export class CharityDialogComponent implements OnInit {

    charity: Charity;
    authorities: any[];
    isSaving: boolean;

    categories: Category[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private charityService: CharityService,
        private categoryService: CategoryService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['charity']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.categoryService.query().subscribe(
            (res: Response) => { this.categories = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.charity.id !== undefined) {
            this.charityService.update(this.charity)
                .subscribe((res: Charity) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.charityService.create(this.charity)
                .subscribe((res: Charity) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Charity) {
        this.eventManager.broadcast({ name: 'charityListModification', content: 'OK'});
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

    trackCategoryById(index: number, item: Category) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-charity-popup',
    template: ''
})
export class CharityPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private charityPopupService: CharityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.charityPopupService
                    .open(CharityDialogComponent, params['id']);
            } else {
                this.modalRef = this.charityPopupService
                    .open(CharityDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
