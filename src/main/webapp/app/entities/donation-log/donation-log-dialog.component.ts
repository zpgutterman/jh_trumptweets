import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Donation_log } from './donation-log.model';
import { Donation_logPopupService } from './donation-log-popup.service';
import { Donation_logService } from './donation-log.service';
import { User, UserService } from '../../shared';
import { Category, CategoryService } from '../category';
import { User_tweet_log, User_tweet_logService } from '../user-tweet-log';
@Component({
    selector: 'jhi-donation-log-dialog',
    templateUrl: './donation-log-dialog.component.html'
})
export class Donation_logDialogComponent implements OnInit {

    donation_log: Donation_log;
    authorities: any[];
    isSaving: boolean;

    users: User[];

    categories: Category[];

    user_tweet_logs: User_tweet_log[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private donation_logService: Donation_logService,
        private userService: UserService,
        private categoryService: CategoryService,
        private user_tweet_logService: User_tweet_logService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['donation_log']);
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
        if (this.donation_log.id !== undefined) {
            this.donation_logService.update(this.donation_log)
                .subscribe((res: Donation_log) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.donation_logService.create(this.donation_log)
                .subscribe((res: Donation_log) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Donation_log) {
        this.eventManager.broadcast({ name: 'donation_logListModification', content: 'OK'});
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
    selector: 'jhi-donation-log-popup',
    template: ''
})
export class Donation_logPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private donation_logPopupService: Donation_logPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.donation_logPopupService
                    .open(Donation_logDialogComponent, params['id']);
            } else {
                this.modalRef = this.donation_logPopupService
                    .open(Donation_logDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
