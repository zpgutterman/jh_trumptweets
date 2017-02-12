import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { User_tweet_log } from './user-tweet-log.model';
import { User_tweet_logPopupService } from './user-tweet-log-popup.service';
import { User_tweet_logService } from './user-tweet-log.service';

@Component({
    selector: 'jhi-user-tweet-log-delete-dialog',
    templateUrl: './user-tweet-log-delete-dialog.component.html'
})
export class User_tweet_logDeleteDialogComponent {

    user_tweet_log: User_tweet_log;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private user_tweet_logService: User_tweet_logService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['user_tweet_log']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.user_tweet_logService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'user_tweet_logListModification',
                content: 'Deleted an user_tweet_log'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-user-tweet-log-delete-popup',
    template: ''
})
export class User_tweet_logDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private user_tweet_logPopupService: User_tweet_logPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.user_tweet_logPopupService
                .open(User_tweet_logDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
