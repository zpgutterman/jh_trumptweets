import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Tweetlog } from './tweetlog.model';
import { TweetlogService } from './tweetlog.service';
@Injectable()
export class TweetlogPopupService {
    private isOpen = false;
    constructor (
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private tweetlogService: TweetlogService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.tweetlogService.find(id).subscribe(tweetlog => {
                tweetlog.tweet_date = this.datePipe.transform(tweetlog.tweet_date, 'yyyy-MM-ddThh:mm');
                tweetlog.categorize_time = this.datePipe.transform(tweetlog.categorize_time, 'yyyy-MM-ddThh:mm');
                this.tweetlogModalRef(component, tweetlog);
            });
        } else {
            return this.tweetlogModalRef(component, new Tweetlog());
        }
    }

    tweetlogModalRef(component: Component, tweetlog: Tweetlog): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.tweetlog = tweetlog;
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
