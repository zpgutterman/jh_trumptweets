import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Tweet_log } from './tweet-log.model';
import { Tweet_logPopupService } from './tweet-log-popup.service';
import { Tweet_logService } from './tweet-log.service';

@Component({
    selector: 'jhi-tweet-log-delete-dialog',
    templateUrl: './tweet-log-delete-dialog.component.html'
})
export class Tweet_logDeleteDialogComponent {

    tweet_log: Tweet_log;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private tweet_logService: Tweet_logService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['tweet_log']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.tweet_logService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tweet_logListModification',
                content: 'Deleted an tweet_log'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tweet-log-delete-popup',
    template: ''
})
export class Tweet_logDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private tweet_logPopupService: Tweet_logPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.tweet_logPopupService
                .open(Tweet_logDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
