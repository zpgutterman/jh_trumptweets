import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Tweet_log } from './tweet-log.model';
import { Tweet_logService } from './tweet-log.service';
@Injectable()
export class Tweet_logPopupService {
    private isOpen = false;
    constructor (
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private tweet_logService: Tweet_logService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.tweet_logService.find(id).subscribe(tweet_log => {
                tweet_log.tweet_date = this.datePipe.transform(tweet_log.tweet_date, 'yyyy-MM-ddThh:mm');
                tweet_log.categorize_time = this.datePipe.transform(tweet_log.categorize_time, 'yyyy-MM-ddThh:mm');
                this.tweet_logModalRef(component, tweet_log);
            });
        } else {
            return this.tweet_logModalRef(component, new Tweet_log());
        }
    }

    tweet_logModalRef(component: Component, tweet_log: Tweet_log): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.tweet_log = tweet_log;
        modalRef.result.then(result => {
            console.log(`Closed with: ${result}`);
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            console.log(`Dismissed ${reason}`);
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
