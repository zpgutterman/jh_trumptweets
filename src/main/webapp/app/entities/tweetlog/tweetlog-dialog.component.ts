import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Tweetlog } from './tweetlog.model';
import { TweetlogPopupService } from './tweetlog-popup.service';
import { TweetlogService } from './tweetlog.service';
import { Category, CategoryService } from '../category';
import { User, UserService } from '../../shared';
@Component({
    selector: 'jhi-tweetlog-dialog',
    templateUrl: './tweetlog-dialog.component.html'
})
export class TweetlogDialogComponent implements OnInit {

    tweetlog: Tweetlog;
    authorities: any[];
    isSaving: boolean;

    categories: Category[];

    users: User[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private tweetlogService: TweetlogService,
        private categoryService: CategoryService,
        private userService: UserService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['tweetlog']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.categoryService.query().subscribe(
            (res: Response) => { this.categories = res.json(); }, (res: Response) => this.onError(res.json()));
        this.userService.query().subscribe(
            (res: Response) => { this.users = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.tweetlog.id !== undefined) {
            this.tweetlogService.update(this.tweetlog)
                .subscribe((res: Tweetlog) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.tweetlogService.create(this.tweetlog)
                .subscribe((res: Tweetlog) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Tweetlog) {
        this.eventManager.broadcast({ name: 'tweetlogListModification', content: 'OK'});
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

    trackUserById(index: number, item: User) {
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
    selector: 'jhi-tweetlog-popup',
    template: ''
})
export class TweetlogPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private tweetlogPopupService: TweetlogPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.tweetlogPopupService
                    .open(TweetlogDialogComponent, params['id']);
            } else {
                this.modalRef = this.tweetlogPopupService
                    .open(TweetlogDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
