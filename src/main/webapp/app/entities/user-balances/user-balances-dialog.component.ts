import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { User_balances } from './user-balances.model';
import { User_balancesPopupService } from './user-balances-popup.service';
import { User_balancesService } from './user-balances.service';
import { User, UserService } from '../../shared';
import { Category, CategoryService } from '../category';
import { User_tweet_log, User_tweet_logService } from '../user-tweet-log';
@Component({
    selector: 'jhi-user-balances-dialog',
    templateUrl: './user-balances-dialog.component.html'
})
export class User_balancesDialogComponent implements OnInit {

    user_balances: User_balances;
    authorities: any[];
    isSaving: boolean;

    users: User[];

    categories: Category[];

    user_tweet_logs: User_tweet_log[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private user_balancesService: User_balancesService,
        private userService: UserService,
        private categoryService: CategoryService,
        private user_tweet_logService: User_tweet_logService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['user_balances']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.userService.query().subscribe(
            (res: Response) => { this.users = res.json(); }, (res: Response) => this.onError(res.json()));
        this.categoryService.query().subscribe(
            (res: Response) => { this.categories = res.json(); }, (res: Response) => this.onError(res.json()));
        this.user_tweet_logService.query().subscribe(
            (res: Response) => { this.user_tweet_logs = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.user_balances.id !== undefined) {
            this.user_balancesService.update(this.user_balances)
                .subscribe((res: User_balances) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.user_balancesService.create(this.user_balances)
                .subscribe((res: User_balances) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: User_balances) {
        this.eventManager.broadcast({ name: 'user_balancesListModification', content: 'OK'});
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

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackCategoryById(index: number, item: Category) {
        return item.id;
    }

    trackUser_tweet_logById(index: number, item: User_tweet_log) {
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
    selector: 'jhi-user-balances-popup',
    template: ''
})
export class User_balancesPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private user_balancesPopupService: User_balancesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.user_balancesPopupService
                    .open(User_balancesDialogComponent, params['id']);
            } else {
                this.modalRef = this.user_balancesPopupService
                    .open(User_balancesDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
