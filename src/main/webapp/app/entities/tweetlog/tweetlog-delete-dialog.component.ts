import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Tweetlog } from './tweetlog.model';
import { TweetlogPopupService } from './tweetlog-popup.service';
import { TweetlogService } from './tweetlog.service';

@Component({
    selector: 'jhi-tweetlog-delete-dialog',
    templateUrl: './tweetlog-delete-dialog.component.html'
})
export class TweetlogDeleteDialogComponent {

    tweetlog: Tweetlog;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private tweetlogService: TweetlogService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['tweetlog']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.tweetlogService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tweetlogListModification',
                content: 'Deleted an tweetlog'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tweetlog-delete-popup',
    template: ''
})
export class TweetlogDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private tweetlogPopupService: TweetlogPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.tweetlogPopupService
                .open(TweetlogDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
