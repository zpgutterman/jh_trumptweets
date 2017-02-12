import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { User_tweet_log } from './user-tweet-log.model';
import { User_tweet_logService } from './user-tweet-log.service';
@Injectable()
export class User_tweet_logPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private user_tweet_logService: User_tweet_logService
    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.user_tweet_logService.find(id).subscribe(user_tweet_log => {
                this.user_tweet_logModalRef(component, user_tweet_log);
            });
        } else {
            return this.user_tweet_logModalRef(component, new User_tweet_log());
        }
    }

    user_tweet_logModalRef(component: Component, user_tweet_log: User_tweet_log): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.user_tweet_log = user_tweet_log;
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
