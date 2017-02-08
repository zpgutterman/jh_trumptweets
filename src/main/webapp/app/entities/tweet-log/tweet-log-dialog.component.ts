import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { Tweet_log } from './tweet-log.model';
import { Tweet_logPopupService } from './tweet-log-popup.service';
import { Tweet_logService } from './tweet-log.service';
@Component({
    selector: 'jhi-tweet-log-dialog',
    templateUrl: './tweet-log-dialog.component.html'
})
export class Tweet_logDialogComponent implements OnInit {

    tweet_log: Tweet_log;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private tweet_logService: Tweet_logService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['tweet_log']);
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
        if (this.tweet_log.id !== undefined) {
            this.tweet_logService.update(this.tweet_log)
                .subscribe((res: Tweet_log) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.tweet_logService.create(this.tweet_log)
                .subscribe((res: Tweet_log) => this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Tweet_log) {
        this.eventManager.broadcast({ name: 'tweet_logListModification', content: 'OK'});
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
    selector: 'jhi-tweet-log-popup',
    template: ''
})
export class Tweet_logPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private tweet_logPopupService: Tweet_logPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.tweet_logPopupService
                    .open(Tweet_logDialogComponent, params['id']);
            } else {
                this.modalRef = this.tweet_logPopupService
                    .open(Tweet_logDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
