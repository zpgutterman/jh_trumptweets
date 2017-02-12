import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { User_tweet_log } from './user-tweet-log.model';
import { User_tweet_logPopupService } from './user-tweet-log-popup.service';
import { User_tweet_logService } from './user-tweet-log.service';
import { User, UserService } from '../../shared';
import { Tweetlog, TweetlogService } from '../tweetlog';
@Component({
    selector: 'jhi-user-tweet-log-dialog',
    templateUrl: './user-tweet-log-dialog.component.html'
})
export class User_tweet_logDialogComponent implements OnInit {

    user_tweet_log: User_tweet_log;
    authorities: any[];
    isSaving: boolean;

    users: User[];

    tweetlogs: Tweetlog[];
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private user_tweet_logService: User_tweet_logService,
        private userService: UserService,
        private tweetlogService: TweetlogService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['user_tweet_log']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.userService.query().subscribe(
            (res: Response) => { this.users = res.json(); }, (res: Response) => this.onError(res.json()));
        this.tweetlogService.query().subscribe(
            (res: Response) => { this.tweetlogs = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.user_tweet_log.id !== undefined) {
            this.user_tweet_logService.update(this.user_tweet_log)
                .subscribe((res: User_tweet_log) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.user_tweet_logService.create(this.user_tweet_log)
                .subscribe((res: User_tweet_log) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: User_tweet_log) {
        this.eventManager.broadcast({ name: 'user_tweet_logListModification', content: 'OK'});
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

    trackTweetlogById(index: number, item: Tweetlog) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-user-tweet-log-popup',
    template: ''
})
export class User_tweet_logPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private user_tweet_logPopupService: User_tweet_logPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.user_tweet_logPopupService
                    .open(User_tweet_logDialogComponent, params['id']);
            } else {
                this.modalRef = this.user_tweet_logPopupService
                    .open(User_tweet_logDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
