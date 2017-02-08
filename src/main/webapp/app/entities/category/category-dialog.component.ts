import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Category } from './category.model';
import { CategoryPopupService } from './category-popup.service';
import { CategoryService } from './category.service';
import { Charity, CharityService } from '../charity';
import { Tweetlog, TweetlogService } from '../tweetlog';
@Component({
    selector: 'jhi-category-dialog',
    templateUrl: './category-dialog.component.html'
})
export class CategoryDialogComponent implements OnInit {

    category: Category;
    authorities: any[];
    isSaving: boolean;

    charities: Charity[];

    tweetlogs: Tweetlog[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private categoryService: CategoryService,
        private charityService: CharityService,
        private tweetlogService: TweetlogService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['category']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.charityService.query().subscribe(
            (res: Response) => { this.charities = res.json(); }, (res: Response) => this.onError(res.json()));
        this.tweetlogService.query().subscribe(
            (res: Response) => { this.tweetlogs = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.category.id !== undefined) {
            this.categoryService.update(this.category)
                .subscribe((res: Category) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.categoryService.create(this.category)
                .subscribe((res: Category) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Category) {
        this.eventManager.broadcast({ name: 'categoryListModification', content: 'OK'});
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

    trackCharityById(index: number, item: Charity) {
        return item.id;
    }

    trackTweetlogById(index: number, item: Tweetlog) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-category-popup',
    template: ''
})
export class CategoryPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private categoryPopupService: CategoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.categoryPopupService
                    .open(CategoryDialogComponent, params['id']);
            } else {
                this.modalRef = this.categoryPopupService
                    .open(CategoryDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
